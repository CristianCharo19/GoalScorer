package com.davidcharo.goalscorer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat
import com.davidcharo.goalscorer.databinding.ActivityLoginBinding
import org.intellij.lang.annotations.Pattern
import java.util.regex.Pattern.compile

private const val EMPTY = ""

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBainding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBainding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBainding.root)

        loginBainding.loginButton.setOnClickListener {
            validate()
            /*val emailLog = loginBainding.emailEditText.text.toString()
            val passwordLog = loginBainding.passwordEditText.text.toString()
            if (emailLog.isNotEmpty() && passwordLog.isNotEmpty()) {
                val extras = intent.extras
                val email = extras?.getString("email")
                val password = extras?.getString("password")
                if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
                    Toast.makeText(this, getString(R.string.must_register), Toast.LENGTH_LONG).show()
                } else {
                    if (emailLog == email && passwordLog == password) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("password", password)
                        startActivity(intent)
                        finish()
                        loginBainding.passwordTextInputLayout.error = null
                    } else {
                        loginBainding.passwordTextInputLayout.error = getString(R.string.password_error)
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.form_error), Toast.LENGTH_LONG).show()
                cleanViews()
            }*/
        }

        loginBainding.registerNewAccountButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cleanViews() {
        with(loginBainding) {
            emailEditText.setText(EMPTY)
            passwordEditText.setText(EMPTY)
        }
    }

    private fun validate(){
        val result = arrayOf(validateEmail(), validatePassword())
        if (false in result){
            return
        }else{
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateEmail() : Boolean {
        val emailLog = loginBainding.emailEditText?.text.toString()
        return if (emailLog.isEmpty()){
            loginBainding.emailTextInputLayout.error = "El campo no puede estar vacio"
            false
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailLog).matches()){
            loginBainding.emailTextInputLayout.error = "Por favor ingrese un correo electronico valido"
            false
        }else{
            loginBainding.emailTextInputLayout.error = null
            true
        }
    }

    private fun validatePassword() : Boolean {
        val passwordLog = loginBainding.passwordEditText?.text.toString()
        val passwordRegex = java.util.regex.Pattern.compile(
            "^" +
                "(?=.*[0-9])" +             //at leats 1 digit
                "(?=.*[a-z])" +             //at leats 1 lower case letter
                "(?=.*[A-Z])" +             //at leats 1 upper case letter
                "(?=.*[@#$%^&+=])" +        //at leats 1 special character
                "(?=\\S+$)" +               //no white spaces
                ".{6,}" +                   //at leats 6 characters
                "$"
        )
        return if (passwordLog.isEmpty()){
            loginBainding.passwordTextInputLayout.error = "El campo no puede estar vacio"
            false
        }else if (!passwordRegex.matcher(passwordLog).matches()){
            loginBainding.passwordTextInputLayout.error = "La contraseña es no cumple"
            false
        }else{
            loginBainding.passwordTextInputLayout.error = null
            true
        }
    }

}
