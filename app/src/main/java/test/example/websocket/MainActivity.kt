package test.example.websocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import test.example.websocket.ui.theme.WebSocketTestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebSocketTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FirstScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FirstScreen(modifier: Modifier = Modifier) {
    val websocketViewModel: WebsocketViewModel = viewModel()

    var sendText by remember {
        mutableStateOf("")
    }
    val getText by websocketViewModel.getTextStateFlow.collectAsStateWithLifecycle()


    Column(modifier = modifier) {

        Text(getText.message)

        TextField(
            value = sendText,
            onValueChange = { newText ->
                sendText = newText
            }
        )

        Button(
            onClick = {
                websocketViewModel.sendText(sendText)
            }
        ) {
            Text("Send")
        }

        Button(onClick = {
            websocketViewModel.regenerateSession()
        }) {
            Text("Regenerate session")
        }

        Button(onClick = {
            websocketViewModel.close()
        }) {
            Text("Close Session")
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WebSocketTestTheme {
        Greeting("Android")
    }
}
