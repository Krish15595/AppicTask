package com.d2k.tmb.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.d2k.appictask.util.PrefUtils

abstract class BaseActivity : AppCompatActivity() {
    val prefUtils: PrefUtils by lazy {
        PrefUtils(this@BaseActivity)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}