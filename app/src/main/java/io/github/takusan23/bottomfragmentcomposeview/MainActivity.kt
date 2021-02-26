package io.github.takusan23.bottomfragmentcomposeview

import android.app.Dialog
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.WindowRecomposerFactory
import androidx.compose.ui.platform.compositionContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import io.github.takusan23.bottomfragmentcomposeview.ui.bottomfragment.BottomFragmentCompose
import io.github.takusan23.bottomfragmentcomposeview.ui.theme.BottomFragmentComposeViewTheme
import java.util.concurrent.atomic.AtomicReference

class MainActivity : AppCompatActivity() {

    @InternalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomFragmentComposeViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen(onClickBottomFragment = { showBottomFragment() }, onClickDialog = { showDialog() })
                }
            }
        }
    }

    @InternalComposeUiApi
    private fun showDialog() {
        Dialog(this).apply {
            setContentView(ComposeView(context).apply {

                ViewTreeLifecycleOwner.set(this, this@MainActivity)
                ViewTreeSavedStateRegistryOwner.set(this, this@MainActivity)

                val newRecomposer = AtomicReference(WindowRecomposerFactory.LifecycleAware).get().createRecomposer(this)
                compositionContext = newRecomposer

                setContent {
                    Text(
                        text = "Dialog + ComposeView",
                        modifier = Modifier.padding(10.dp)
                    )
                }
            })
        }.show()
    }

    private fun showBottomFragment() {
        BottomFragmentCompose().show(supportFragmentManager, "bottom_fragment")
    }

}

@Composable
fun HomeScreen(
    onClickBottomFragment: () -> Unit,
    onClickDialog: () -> Unit,
) {
    Column(modifier = Modifier.padding(10.dp)) {
        Button(onClick = { onClickBottomFragment() }, modifier = Modifier.padding(10.dp)) {
            Text(text = "BottomFragment + ComposeView Sample")
        }
        Button(onClick = { onClickDialog() }, modifier = Modifier.padding(10.dp)) {
            Text(text = "Dialog + ComposeView Sample")
        }
    }
}