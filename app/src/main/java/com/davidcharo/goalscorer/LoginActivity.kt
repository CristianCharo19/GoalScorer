package com.davidcharo.goalscorer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat
import com.davidcharo.goalscorer.databinding.ActivityLoginBinding

private const val EMPTY = ""

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBainding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBainding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBainding.root)

        loginBainding.loginButton.setOnClickListener {
            validate()
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

    private fun validate() {
        val result = arrayOf(validateEmail(), validatePassword())
        if (false in result) {
            return
        } else {
            val emailLog = loginBainding.emailEditText.text.toString()
            val passwordLog = loginBainding.passwordEditText.text.toString()
            val extras = intent.extras
            val email = extras?.getString("email")
            val password = extras?.getString("password")
            if (emailLog == email && passwordLog == password) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", password)
                startActivity(intent)
                finish()
                cleanViews()
                loginBainding.passwordTextInputLayout.error = null
            } else {
                if (emailLog != email && passwordLog != password) {
                    loginBainding.emailTextInputLayout.error = getString(R.string.mail_match)
                    loginBainding.passwordTextInputLayout.error = getString(R.string.password_match)
                } else {
                    if (emailLog != email) {
                        loginBainding.emailTextInputLayout.error = getString(R.string.mail_match)
                    } else {
                        loginBainding.passwordTextInputLayout.error = getString(R.string.password_match)
                    }
                }
            }
        }
    }

        private fun validateEmail(): Boolean {
            val emailLog = loginBainding.emailEditText.text.toString()
            return if (emailLog.isEmpty()) {
                loginBainding.emailTextInputLayout.error = getString(R.string.empty_field)
                false
            } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailLog).matches()) {
                loginBainding.emailTextInputLayout.error = getString(R.string.unwanted_mail)
                cleanViews()
                false
            } else {
                loginBainding.emailTextInputLayout.error = null
                true
            }
        }

        private fun validatePassword(): Boolean {
            val passwordLog = loginBainding.passwordEditText.text.toString()
            val passwordRegex = java.util.regex.Pattern.compile(
                "^" +
                        ".{6,}" +                   //at leats 6 characters
                        "$"
            )
            return if (passwordLog.isEmpty()) {
                loginBainding.passwordTextInputLayout.error = getString(R.string.empty_field)
                false
            } else if (!passwordRegex.matcher(passwordLog).matches()) {
                loginBainding.passwordTextInputLayout.error = getString(R.string.least_password)
                cleanViews()
                false
            } else {
                loginBainding.passwordTextInputLayout.error = null
                true
            }
        }
    }
