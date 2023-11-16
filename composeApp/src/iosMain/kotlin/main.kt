import androidx.compose.ui.window.ComposeUIViewController
import com.theapache64.rebuggersample.RebuggerSample
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { RebuggerSample() }
