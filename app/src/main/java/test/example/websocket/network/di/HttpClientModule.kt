package test.example.websocket.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Singleton
    @Provides
    fun providesWebSocket(): HttpClient {
        return HttpClient(CIO) {
            install(WebSockets) {
                pingIntervalMillis = 20_000
                KotlinxWebsocketSerializationConverter(Json)
            }
            install(Logging) {
                logger = Logger.ANDROID
            }
        }
    }
}
