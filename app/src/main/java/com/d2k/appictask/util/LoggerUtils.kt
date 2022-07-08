package com.d2k.appictask.util

import android.util.Log
import com.d2k.appictask.BuildConfig

class LoggerUtils {

    companion object {
//        context.applicationInfo.loadLabel(context.packageManager).toString()
        private var enableLog: Boolean = BuildConfig.DEBUG

        fun isEnableLog(): Boolean {
            return enableLog
        }

        fun setEnableLog(enableLog: Boolean) {
            Companion.enableLog = enableLog
        }


        fun d(tag: String?, msg: String?) {
            if (isEnableLog()) msg?.let { Log.d(tag, it) }
        }

        fun e(tag: String?, msg: String?) {
            if (isEnableLog()) msg?.let { Log.e(tag, it) }
        }

        fun i(tag: String?, msg: String?) {
            if (isEnableLog()) msg?.let { Log.i(tag, it) }
        }

        fun v(tag: String?, msg: String?) {
            if (isEnableLog()) msg?.let { Log.v(tag, it) }
        }

        fun w(tag: String?, msg: String?) {
            if (isEnableLog()) msg?.let { Log.w(tag, it) }
        }
    }
}