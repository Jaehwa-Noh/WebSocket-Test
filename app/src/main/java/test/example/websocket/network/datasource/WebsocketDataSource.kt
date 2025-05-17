package test.example.websocket.network.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import test.example.websocket.common.qualifier.dispatchers.DispatcherIO
import test.example.websocket.network.session.WebsocketSession
import javax.inject.Inject
import javax.inject.Singleton

interface WebsocketDataSource {
    suspend fun sendText(text: String)
    fun getTextFlow(): Flow<String>
    suspend fun regenerateSession()
    suspend fun close()
}

@Singleton
class WebsocketKtorDataSource @Inject constructor(
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    private val websocketSession: WebsocketSession,
) : WebsocketDataSource {

    override suspend fun sendText(text: String) = withContext(ioDispatcher) {
        websocketSession.sendText(text)
        return@withContext
    }

    override suspend fun regenerateSession() {
        websocketSession.generateSession()
    }

    override fun getTextFlow(): Flow<String> = websocketSession.receiveStream()


    override suspend fun close() = withContext(ioDispatcher) {
        websocketSession.close()
    }
}
