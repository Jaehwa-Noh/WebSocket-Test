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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import test.example.websocket.ui.theme.WebSocketTestTheme

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
    var sendText by remember {
        mutableStateOf("")
    }
    var getText by remember {
        mutableStateOf("")
    }

    var clientWebSocket: HttpClient? by remember {
        mutableStateOf(
            null
        )
    }

    var sessions: WebSocketSession? by remember {
        mutableStateOf(
            null
        )
    }

    val sessionState = sessions?.isActive

    val currentScope = rememberCoroutineScope()

    LaunchedEffect(sessions) {
        launch {
            withContext(Dispatchers.IO) {
                while (sessions?.isActive == true) {
                    sessions?.let { websocketSessions ->
                        websocketSessions.incoming.consumeAsFlow()
                            .filterIsInstance<Frame.Text>()
                            .map {
                                getText = it.readText()
                            }
                            .collect()

                    }
                }
            }
        }
    }

    Column(modifier = modifier) {
        Button(onClick = {
            clientWebSocket = HttpClient(CIO) {
                install(WebSockets) {
                    pingIntervalMillis = 20_000
                    KotlinxWebsocketSerializationConverter(Json)
                }

                install(Logging) {
                    logger = Logger.ANDROID
                }
            }
        }) {
            Text("Create Client")
        }

        Button(onClick = {
            currentScope.launch {
                sessions = clientWebSocket?.webSocketSession {
                    url {
                        host = "echo.websocket.org"
                    }
                }
            }
        }) {
            Text("Create Session")
        }

        Text(getText)

        TextField(
            value = sendText,
            onValueChange = { newText ->
                sendText = newText
            }
        )

        Button(
            onClick = {
                currentScope.launch {
                    sessions?.outgoing?.send(
                        Frame.Text(sendText)
                    )
                }
            }
        ) {
            Text("Send")
        }

        Button(onClick = {
            currentScope.launch {
                sessions?.close()
            }
        }) {
            Text("Close Session")
        }

        Button(
            onClick = {
                clientWebSocket?.close()
            }
        ) {
            Text("Close Client")
        }

        if (sessionState == true) {
            Text("Is active")
        } else {
            Text("Is not Active")
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
