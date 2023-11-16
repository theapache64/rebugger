import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.theapache64.rebuggersample.RebuggerSample
import java.awt.Dimension

fun main() = application {
    Window(
        title = "Rebugger",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        RebuggerSample()
    }
}