package com.d2k.appictask.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d2k.appictask.ui.login.model.LoginRequest
import com.d2k.appictask.ui.login.model.LoginResponse
import com.d2k.appictask.ui.login.repo.LoginRepository
import com.d2k.appictask.util.Resources
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

public class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {
    private val _loginUserFlow = Channel<Resources<LoginResponse>>(BUFFERED)
    val loginUserFlow = _loginUserFlow.receiveAsFlow()
    fun loginUsingFlow(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _loginUserFlow.send(Resources.loading(null))
            loginRepository.login(loginRequest).catch {
                _loginUserFlow.send(Resources.error(null, message = it.message ?: "Error occured"))
//               _loginUserFlow.value = Resources.error(null, message = it.message ?: "Error occured")
            }.collect {
                _loginUserFlow.send(it)
            }
        }
    }
}