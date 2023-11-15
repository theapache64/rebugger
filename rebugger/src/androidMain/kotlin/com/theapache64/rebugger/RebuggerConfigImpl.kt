package com.theapache64.rebugger

import android.util.Log

internal actual fun defaultLogger(tag: String, message: String) {
    Log.d(tag, message)
}