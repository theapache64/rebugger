package com.theapache64.rebuggersample

import android.app.Application
import android.util.Log
import com.theapache64.rebugger.RebuggerConfig

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        RebuggerConfig.init(
            tag = "MyAppRebugger", // custom tag
            logger = { tag, message -> Log.i(tag, message) } // custom logger
        )
    }
}