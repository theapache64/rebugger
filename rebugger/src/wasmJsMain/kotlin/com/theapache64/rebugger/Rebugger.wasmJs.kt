package com.theapache64.rebugger

internal actual fun findComposableName(): String? {
    val error = Error("dummy")
    return error.stackTraceToString()
        .split("\n")
        .getOrNull(3)
        ?.split("$")
        ?.getOrNull(0)
        ?.split(" ")
        ?.lastOrNull()
}