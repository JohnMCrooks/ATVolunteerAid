package com.skoorc.atvolunteeraid.util

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(msgId: Int, length: Int) {
    showSnackbar(context.getString(msgId), length)
}

fun View.showSnackbar(msg: String, length: Int) {
    showSnackbar(msg, length, null, {})
}

fun View.showSnackbar(
    msgId: Int,
    length: Int,
    actionMessageId: Int,
    action: (View) -> Unit
) {
    showSnackbar(context.getString(msgId), length, context.getString(actionMessageId), action)
}

fun View.showSnackbar(
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    }
}
fun View.showToast(message: String, length: Int){
    when (length) {
        0 -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        1 -> Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        else -> Toast.makeText(context, "something went horribly wrong... ", Toast.LENGTH_SHORT).show()
    }
}