package com.d2k.appictask.util

import android.content.Context
import androidx.appcompat.app.AlertDialog

public fun Context.showDialog(cancelable: Boolean = false, cancelableTouchOutside: Boolean = false, builderFunction: AlertDialog.Builder.() -> Any) {
    val builder = AlertDialog.Builder(this)
    builder.builderFunction()
    val dialog = builder.create()
    dialog.setCancelable(cancelable)
    dialog.setCanceledOnTouchOutside(cancelableTouchOutside)
    dialog.show()
}


public fun AlertDialog.Builder.positiveButton(text: String = "OK", handleClick: (i: Int) -> Unit = {}) {
    this.setPositiveButton(text, { dialogInterface, i -> handleClick(i) })
}

public fun AlertDialog.Builder.negativeButton(text: String = "CANCEL", handleClick: (i: Int) -> Unit = {}) {
    this.setNegativeButton(text, { dialogInterface, i -> handleClick(i) })
}

public fun AlertDialog.Builder.neutralButton(text: String, handleClick: (i: Int) -> Unit = {}) {
    this.setNeutralButton(text, { dialogInterface, i -> handleClick(i) })
}
/*
    usage
    alert {
        setTitle("Confirm")
        setMessage("Are you sure you want to delete this item?")
        positiveButton { // Do something }
            negativeButton { // Do something }
            }*/
