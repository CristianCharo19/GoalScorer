package com.davidcharo.goalscorer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.davidcharo.goalscorer.databinding.ActivityLoginBinding

private const val EMPTY = ""

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBainding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBainding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBainding.root)

        loginBainding.loginButton.setOnClickListener {
            val emailLog = loginBainding.emailEditText.text.toString()
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
            }
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

}
