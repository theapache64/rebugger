import androidx.compose.ui.window.ComposeUIViewController
import com.github.theapache64.rebugger.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
