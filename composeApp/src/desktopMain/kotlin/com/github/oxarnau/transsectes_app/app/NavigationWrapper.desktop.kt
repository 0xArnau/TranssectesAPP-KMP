package com.github.oxarnau.transsectes_app.app

import java.util.logging.Logger

val logger = Logger.getLogger("KMPLogger")

actual fun log(tag: String, message: String) {

    logger.info(tag + message)
}