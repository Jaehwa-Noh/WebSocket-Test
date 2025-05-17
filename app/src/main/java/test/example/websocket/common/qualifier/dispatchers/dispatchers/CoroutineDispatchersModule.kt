package test.example.websocket.common.qualifier.dispatchers.dispatchers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import test.example.websocket.common.qualifier.dispatchers.DispatcherDefault
import test.example.websocket.common.qualifier.dispatchers.DispatcherIO

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatchersModule {
    @DispatcherIO
    @Provides
    fun provideCoroutineDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @DispatcherDefault
    @Provides
    fun provideCoroutineDispatcherDefault(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}
