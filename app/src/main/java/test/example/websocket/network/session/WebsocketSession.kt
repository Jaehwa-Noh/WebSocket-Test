package test.example.websocket.network.session

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import test.example.websocket.common.qualifier.dispatchers.ApplicationScope
import test.example.websocket.common.qualifier.dispatchers.DispatcherIO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebsocketSession @Inject constructor(
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
    private val client: HttpClient,
) {
    var session: WebSocketSession? = null

    val event = MutableSharedFlow<String>(replay = 1)
    fun receiveStream(): Flow<String> = event

    init {
        scope.launch {
            session = withContext(ioDispatcher) {
                client.webSocketSession {
                    url {
                        host = "echo.websocket.org"
                    }
                }
            }
            withContext(ioDispatcher) {
                session?.incoming?.consumeAsFlow()?.filterIsInstance<Frame.Text>()
                    ?.mapNotNull { it.readText() }
                    ?.onEach {
                        Log.i("jaewha", "receiveData $it")
                        event.tryEmit(it)
                    }
                    ?.collect()
            }
        }
    }


    suspend fun sendText(text: String) = withContext(ioDispatcher) {
        if (session?.isActive == true) {
            session?.outgoing?.send(
                Frame.Text(text)
            )
        }
        Log.i("jaehwa", "send Data text $text")
    }

    fun generateSession() {
        scope.launch {
            withContext(ioDispatcher) {
                close("regenerate connection")
            }
            session = withContext(ioDispatcher) {
                client.webSocketSession {
                    url {
                        host = "echo.websocket.org"
                    }
                }
            }
            withContext(ioDispatcher) {
                session?.incoming?.consumeAsFlow()?.filterIsInstance<Frame.Text>()
                    ?.mapNotNull { it.readText() }
                    ?.onEach {
                        Log.i("jaewha", "receiveData $it")
                        event.tryEmit(it)
                    }
                    ?.collect()
            }
        }
    }

    suspend fun close(reasonText: String = "") {
        withContext(ioDispatcher) {
            session?.close(CloseReason(CloseReason.Codes.NORMAL, reasonText))
            Log.i("jaehwa", "session end: ${session?.isActive}")
            session = null
        }
    }
}
