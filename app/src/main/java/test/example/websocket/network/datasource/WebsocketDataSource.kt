package test.example.websocket.network.datasource

import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import test.example.websocket.common.qualifier.dispatchers.DispatcherIO
import javax.inject.Inject
import javax.inject.Singleton

interface WebsocketDataSource {
    suspend fun sendText(text: String)
    fun getTextFlow(): Flow<String>
    suspend fun close()
}

@Singleton
class WebsocketKtorDataSource @Inject constructor(
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    private val client: HttpClient,
) : WebsocketDataSource {

    override suspend fun sendText(text: String) {
        TODO("Not yet implemented")
    }

    override fun getTextFlow(): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun close() {
        TODO("Not yet implemented")
    }
}
