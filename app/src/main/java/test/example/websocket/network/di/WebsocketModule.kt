package test.example.websocket.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import test.example.websocket.network.datasource.WebsocketDataSource
import test.example.websocket.network.datasource.WebsocketKtorDataSource
import test.example.websocket.network.repository.WebsocketRemoteRepository
import test.example.websocket.network.repository.WebsocketRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WebsocketModule {

    @Binds
    @Singleton
    abstract fun bindVoiceWebsocketKtorDataSource(impl: WebsocketKtorDataSource): WebsocketDataSource

    @Binds
    @Singleton
    abstract fun bindVoiceWebSocketRemoteRepository(impl: WebsocketRemoteRepository): WebsocketRepository
}
