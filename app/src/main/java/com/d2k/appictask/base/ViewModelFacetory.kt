package com.d2k.tmb.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.d2k.appictask.ui.login.repo.LoginRepository
import com.d2k.appictask.ui.login.viewmodel.LoginViewModel
import com.d2k.tmb.networking.IApiService

class ViewModelFactory(private val apiService: IApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(LoginRepository(apiService)) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}