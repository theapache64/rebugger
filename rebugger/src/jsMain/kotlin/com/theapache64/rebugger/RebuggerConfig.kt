package com.theapache64.rebugger

internal actual fun defaultLogger(tag: String, message: String) {
    console.log("$tag : $message")
}