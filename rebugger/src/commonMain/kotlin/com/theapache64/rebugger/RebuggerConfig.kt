package com.theapache64.rebugger

internal expect fun defaultLogger(tag: String, message: String)

object RebuggerConfig {

    /*Default values*/
    private const val DEFAULT_TAG = "Rebugger"

    /**/
    var tag = DEFAULT_TAG
    var logger: (tag: String, message: String) -> Unit = ::defaultLogger


    /**
     * To override Rebugger's default variables
     */
    fun init(
        tag: String = DEFAULT_TAG,
        logger: (tag: String, message: String) -> Unit = ::defaultLogger,
    ) {
        RebuggerConfig.tag = tag
        RebuggerConfig.logger = logger
    }

}