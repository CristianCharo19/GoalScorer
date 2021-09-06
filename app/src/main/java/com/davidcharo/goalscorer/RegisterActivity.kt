package com.davidcharo.goalscorer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.davidcharo.goalscorer.databinding.ActivityRegisterBinding
import com.davidcharo.goalscorer.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val EMPTY = ""

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        auth = Firebase.auth

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
                                    createUser(email)
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

    private fun createUser(email: String) {
        val id = auth.currentUser?.uid
        id?.let { id ->
        val user = User(id = id, email = email)
            val db = Firebase.firestore
            db.collection("users").document(id)
                .set(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("createInDB", "DocumentSnapshot added with ID: ${id}")
                    saveUser()
                }
                .addOnFailureListener { e ->
                    Log.w("createInDB", "Error adding document", e)
                }
    }
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