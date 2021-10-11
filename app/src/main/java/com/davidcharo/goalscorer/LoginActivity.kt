package com.davidcharo.goalscorer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.davidcharo.goalscorer.databinding.ActivityLoginBinding
import com.davidcharo.goalscorer.utils.MIN_SIZE_PASSWORD
import com.davidcharo.goalscorer.utils.validateEmail
import com.davidcharo.goalscorer.utils.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val EMPTY = ""

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBainding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBainding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBainding.root)

        auth = Firebase.auth

        bindOnChangeListeners()

        loginBainding.loginButton.setOnClickListener {
            validate()
        }

        loginBainding.registerNewAccountButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        val email = loginBainding.emailEditText.text.toString()
        val password = loginBainding.passwordEditText.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Login", "signInWithEmail:success")
                    goToMainActivity()
                    val user = auth.currentUser
                } else {
                    var msg = ""
                    if (task.exception?.localizedMessage == "The email address is badly formatted.")
                        msg = "El correo está mal escrito"
                    else if (task.exception?.localizedMessage == "There is no user record corresponding to this identifier. The user may have been deleted.")
                        msg = "No existe una cuenta con ese correo electrónico"
                    else if (task.exception?.localizedMessage == "The password is invalid or the user does not have a password.")
                        msg = "Correo o contraseña invalida"
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun bindOnChangeListeners() {
        with(loginBainding) {
            emailEditText.doAfterTextChanged {
                validateEmail()
                //validateFields()
            }
            passwordEditText.doAfterTextChanged {
                validatePassword()
                validateFields()
            }
        }
    }

    private fun validateFields() {
        with(loginBainding) {
            val fields = listOf(
                validateEmail(),
                validatePassword()
            )
            validateFields(fields)
        }
    }

    fun validateFields(areValid: List<Boolean>) {
        for (isValid in areValid) {
            enableSigInButton(isValid)
            if (!isValid) break
        }
    }

    fun enableSigInButton(isEnable: Boolean) {
        loginBainding.loginButton.isEnabled = isEnable
    }


    private fun cleanViews() {
        with(loginBainding) {
            emailEditText.setText(EMPTY)
            passwordEditText.setText(EMPTY)
        }
    }

    private fun validate() {
        if (validateEmail() && validatePassword()) {
            val emailLog = loginBainding.emailEditText.text.toString()
            val passwordLog = loginBainding.passwordEditText.text.toString()
            signIn()
        } else {
            validateEmail()
            validatePassword()
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateEmail(): Boolean {
        val validateEmail = validateEmail(loginBainding.emailEditText.text.toString())
        loginBainding.emailTextInputLayout.error = if (!validateEmail) getString(R.string.unwanted_mail) else null
        return validateEmail
    }

    private fun validatePassword(): Boolean {
        val passwordLog = validatePassword(loginBainding.passwordEditText.text.toString(), MIN_SIZE_PASSWORD)
        loginBainding.passwordTextInputLayout.error = if (!passwordLog) getString(R.string.least_password) else null
        return passwordLog
    }
}
