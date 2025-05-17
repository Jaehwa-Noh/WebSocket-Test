package test.example.websocket.network.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import test.example.websocket.common.qualifier.dispatchers.DispatcherIO
import test.example.websocket.network.datasource.WebsocketDataSource
import javax.inject.Inject
import javax.inject.Singleton

interface WebsocketRepository {
    suspend fun sendText(text: String)
    fun getTextFlow(): Flow<String>
    suspend fun regenerateSession()
    suspend fun close()
}

@Singleton
class WebsocketRemoteRepository @Inject constructor(
    val websocketDataSource: WebsocketDataSource,
) : WebsocketRepository {
    override suspend fun sendText(text: String) = websocketDataSource.sendText(text)
    override suspend fun regenerateSession() = websocketDataSource.regenerateSession()

    override fun getTextFlow(): Flow<String> = websocketDataSource.getTextFlow()

    override suspend fun close() = websocketDataSource.close()
}
