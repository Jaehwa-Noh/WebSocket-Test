package test.example.websocket

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import test.example.websocket.network.repository.WebsocketRepository
import javax.inject.Inject

@HiltViewModel
class WebsocketViewModel @Inject constructor(
    private val websocketRepository: WebsocketRepository,
): ViewModel() {

}
