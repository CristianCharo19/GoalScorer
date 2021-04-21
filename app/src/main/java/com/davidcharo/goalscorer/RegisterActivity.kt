package com.davidcharo.goalscorer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.davidcharo.goalscorer.databinding.ActivityRegisterBinding

private const val EMPTY = ""

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.registerButton.setOnClickListener {
            val name = registerBinding.nameEditText.text.toString()
            val email = registerBinding.emailEditText.text.toString()
            val password = registerBinding.passwordEditText.text.toString()
            val repPassword = registerBinding.repPasswordEditText.text.toString()
            cleanViews()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repPassword.isNotEmpty()) {
                if (password == repPassword) {
                    if (password.length >= 6) {
                        saveUser(email, password)
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

    private fun cleanViews() {
        with(registerBinding) {
            nameEditText.setText(EMPTY)
            emailEditText.setText(EMPTY)
            passwordEditText.setText(EMPTY)
            repPasswordEditText.setText(EMPTY)
        }
    }

    private fun saveUser(email: String, password: String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("password", password)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}