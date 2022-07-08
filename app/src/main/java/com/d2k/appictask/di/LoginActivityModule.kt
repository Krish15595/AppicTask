package com.d2k.appictask.di

import com.d2k.appictask.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginActivityInjector():LoginActivity
}