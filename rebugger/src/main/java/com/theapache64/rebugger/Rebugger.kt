package com.theapache64.rebugger

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.sql.Ref

private const val TAG = "Rebugger"

private class Ref<T>(var value: T)

@Composable
fun Rebugger(
    trackMap: Map<String, Any?>,
    composableName: String = Thread.currentThread().stackTrace[3].methodName,
) {

    LaunchedEffect(Unit) {
        Log.i(TAG, "🐞 Rebugger activated on `$composableName`")
    }

    val count = remember { Ref(0) }
    val flag = remember { Ref(false) }
    SideEffect {
        count.value++
    }

    val changeLog = StringBuilder()
    for ((key, newArg) in trackMap) {
        var recompositionTrigger by remember { mutableStateOf(false) }
        val oldArg = remember(recompositionTrigger) { newArg }

        if (oldArg != newArg) {
            changeLog.append("\n\t `$key` changed from `$oldArg` to `$newArg`, ")
            flag.value = true

            @Suppress("UNUSED_VALUE")
            recompositionTrigger = !recompositionTrigger
        }
    }

    if (changeLog.isNotEmpty()) {
        Log.i(TAG, "🐞$composableName recomposed because $changeLog")
    } else {
        if (count.value >= 1 && !flag.value) {
            Log.i(TAG, "🐞$composableName recomposed not because of param change")
        } else {
            flag.value = false
        }
    }
}