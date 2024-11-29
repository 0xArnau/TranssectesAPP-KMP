package com.github.oxarnau.transsectes_app.app

import android.util.Log

actual fun log(tag: String, message: String) {

    Log.d(tag, message)
}