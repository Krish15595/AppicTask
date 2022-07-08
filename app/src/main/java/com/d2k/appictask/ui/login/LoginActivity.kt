package com.d2k.appictask.ui.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.d2k.appictask.databinding.ActivityLoginBinding
import com.d2k.appictask.ui.login.model.LoginRequest
import com.d2k.appictask.ui.login.viewmodel.LoginViewModel
import com.d2k.appictask.ui.main.MainActivity
import com.d2k.appictask.util.Constants
import com.d2k.appictask.util.Constants.Companion.USER_LOGGED_IN
import com.d2k.appictask.util.Status
import com.d2k.tmb.base.BaseActivity
import com.d2k.tmb.extension.launchActivity
import com.d2k.tmb.extension.showProgress
import com.d2k.tmb.extension.showToast
import dagger.android.AndroidInjection
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    lateinit var loginBinding: ActivityLoginBinding

    @Inject
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this@LoginActivity)
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        loginBinding.viewmodel = loginViewModel
        setContentView(loginBinding.root)
        if (prefUtils.getBoolean(USER_LOGGED_IN)) {
            launchActivity<MainActivity> {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                finish()
            }
        }
        init()
    }

    private fun init() {
        val dialog = Dialog(this)
        loginBinding.etUsername.setOnFocusChangeListener { view, b ->
            if (loginBinding.etUsername.text.toString().length > 0) {
                loginBinding.tilUsername.setError(null)
            }
        }
        loginBinding.etPassword.setOnFocusChangeListener { view, b ->
            if (loginBinding.etPassword.text.toString().length > 0) {
                loginBinding.tilPassword.setError(null)
            }
        }
        loginBinding.btnLogin.setOnClickListener {
            if (loginBinding.etUsername.text.toString().isNullOrEmpty()) {
                loginBinding.tilUsername.setError("Please enter username")
                return@setOnClickListener
            }
            if (loginBinding.etPassword.text.toString().isNullOrEmpty()) {
                loginBinding.tilPassword.setError("Please enter password")
                return@setOnClickListener
            }
            loginViewModel.loginUsingFlow(
                LoginRequest(
                    loginBinding.etPassword.text.toString(),
                    loginBinding.etUsername.text.toString()
                )
            )
            lifecycleScope.launchWhenCreated {
                loginViewModel.loginUserFlow.collect {

                    when (it.status) {
                        Status.SUCCESS -> {
                            showProgress(1, dialog)
                            if (it.data != null) {
                                prefUtils.setString(
                                    Constants.ACCESS_TOKEN,
                                    it.data.token
                                )
                                showToast("Login successful")
                                launchActivity<MainActivity> {
                                    prefUtils.setBoolean(USER_LOGGED_IN, true)
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    finish()
                                }
                            }
                        }
                        Status.ERROR -> {
                            showProgress(1, dialog)
                            showToast(it.message.toString())
                        }
                        Status.LOADING -> {
                            showProgress(0, dialog)
                        }
                    }
                }
            }
        }
    }
}
