package com.github.oxarnau.transsectes_app.app

import platform.Foundation.NSLog

actual fun log(tag: String, message: String) {

    NSLog(tag + message)
}