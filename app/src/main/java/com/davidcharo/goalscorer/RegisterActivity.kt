package com.davidcharo.goalscorer

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.davidcharo.goalscorer.databinding.ActivityRegisterBinding
import com.davidcharo.goalscorer.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


private const val EMPTY = ""

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth
    private var urlImage : String? = null
    private val REQUEST_IMAGE_CAPTURE = 1000


    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            registerBinding.takePictureImageView.setImageBitmap(imageBitmap)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        auth = Firebase.auth

        registerBinding.takePictureImageView.setOnClickListener {
            dispatchTakePictureIntent()
        }

        registerBinding.registerButton.setOnClickListener {
            val name = registerBinding.nameEditText.text.toString()
            val email = registerBinding.emailEditText.text.toString()
            val password = registerBinding.passwordEditText.text.toString()
            val repPassword = registerBinding.repPasswordEditText.text.toString()
            cleanViews()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repPassword.isNotEmpty()) {
                if (password == repPassword) {
                    if (password.length >= 6) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    Log.d("register", "createUserWithEmail:success")
                                    Toast.makeText(baseContext, "Registro Exitoso",
                                        Toast.LENGTH_SHORT).show()
                                    createUser(name,email)
                                } else {
                                    var msg = ""
                                    if (task.exception?.localizedMessage == "The email address is badly formatted.")
                                        msg = "El correo está mal escrito"
                                    else if (task.exception?.localizedMessage == "The given password is invalid. [ Password should be at least 6 characters ]")
                                        msg = "La contraseña dede tener minimo 6 caracteres"
                                    else if (task.exception?.localizedMessage == "The email address is already in use by another account.")
                                        msg = "Ya existe una cuenta con ese correo electrónico"
                                    Log.w("register", "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(baseContext, msg,
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        registerBinding.repPasswordTextInputLayout.error = null
                    } else {
                        Toast.makeText(this, getString(R.string.least_password), Toast.LENGTH_LONG).show()
                    }
                } else {
                    registerBinding.repPasswordTextInputLayout.error = getString(R.string.password_error)
                }
            } else {
                Toast.makeText(this, getString(R.string.form_error), Toast.LENGTH_LONG).show()
                cleanViews()
            }
        }
    }



    private fun createUser(name: String, email: String) {
        val db = Firebase.firestore
        val document = db.collection("users").document()
        val id = document.id

        val storage = FirebaseStorage.getInstance()
        val pictureRef = storage.reference.child("users").child(id)

        registerBinding.takePictureImageView.isDrawingCacheEnabled = true
        registerBinding.takePictureImageView.buildDrawingCache()
        val bitmap = (registerBinding.takePictureImageView.drawable as BitmapDrawable).bitmap
        val baos =ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = pictureRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful){
                task.exception?.let{
                    throw it
                }
            }
            pictureRef.downloadUrl
        }.addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                val urlPicture = task.result.toString()
                with(registerBinding) {
                    val user = User(id, name, email, urlPicture)
                    db.collection("users").document(id).set(user)
                    saveUser()
                    cleanViews()
                }
            } else {
                // Handle failures
                // ...
            }
        }
}
    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intent)
    }

private fun cleanViews() {
    with(registerBinding) {
        nameEditText.setText(EMPTY)
        emailEditText.setText(EMPTY)
        passwordEditText.setText(EMPTY)
        repPasswordEditText.setText(EMPTY)
    }
}

private fun saveUser() {
    val intent = Intent(this, LoginActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    startActivity(intent)
}

}