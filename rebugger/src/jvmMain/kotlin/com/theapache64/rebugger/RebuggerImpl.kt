package com.theapache64.rebugger

internal actual fun findComposableName() : String{
    return Thread.currentThread().stackTrace[3].methodName
}