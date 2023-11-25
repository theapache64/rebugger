package com.theapache64.rebugger

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

internal expect fun findComposableName() : String?

private class Ref<T>(var value: T)

@Composable
fun Rebugger(
    trackMap: Map<String, Any?>,
    logger: (tag: String, message: String) -> Unit = { tag, message ->
        RebuggerConfig.logger.invoke(
            tag,
            message
        )
    },
    composableName: String? = findComposableName()
) {

    LaunchedEffect(Unit) {
        logger(RebuggerConfig.tag, "🐞 Rebugger activated on `$composableName`")
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
        val reason = when {
            oldArg != newArg -> {
                "`$key` changed from `$oldArg` to `$newArg`, "
            }

            oldArg !== newArg -> {
                "`$key` instance changed, but content remains the same -> `$oldArg`"
            }

            else -> {
                null
            }
        }
        if (reason != null) {
            changeLog.append("\n\t $reason")
            flag.value = true
            recompositionTrigger = !recompositionTrigger
        }
    }

    if (changeLog.isNotEmpty()) {
        logger(RebuggerConfig.tag, "🐞$composableName recomposed because $changeLog")
    } else {
        if (count.value >= 1 && !flag.value) {
            logger(
                RebuggerConfig.tag,
                "🐞$composableName recomposed, but reason is unknown. Are you sure you added all params to `trackMap`? 🤔"
            )
        } else {
            flag.value = false
        }
    }
}