package com.davidcharo.goalscorer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.davidcharo.goalscorer.databinding.ActivityLoginBinding
import com.davidcharo.goalscorer.utils.MIN_SIZE_PASSWORD
import com.davidcharo.goalscorer.utils.validateEmail
import com.davidcharo.goalscorer.utils.validatePassword

private const val EMPTY = ""

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBainding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBainding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBainding.root)

        bindOnChangeListeners()

        loginBainding.loginButton.setOnClickListener {
            validate()
        }

        loginBainding.registerNewAccountButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bindOnChangeListeners() {
        with(loginBainding){
            emailEditText.doAfterTextChanged {
                validateEmail()
                validateFields()
            }
            passwordEditText.doAfterTextChanged {
                validatePassword()
                validateFields()
            }
        }

    }

    private fun validateFields() {
        with(loginBainding){
            val fields = listOf(
                validateEmail(),
                validatePassword()
            )
            validateFields(fields)
        }
    }

    fun validateFields(areValid: List<Boolean>){
        for (isValid in areValid){
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
            val extras = intent.extras
            val email = extras?.getString("email")
            val password = extras?.getString("password")
            if (emailLog == email && passwordLog == password) {
                goToMainActivity(email, password)
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
        }else{
            validateEmail()
            validatePassword()
        }
    }

    private fun goToMainActivity(email: String?, password: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("password", password)
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
