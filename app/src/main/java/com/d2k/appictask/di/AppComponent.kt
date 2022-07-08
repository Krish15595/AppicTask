package com.d2k.appictask.di

import com.d2k.appictask.ui.MyAppiclation
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    LoginActivityModule::class,
    AppModule::class
])
interface AppComponent {
    fun inject(application:MyAppiclation)
}