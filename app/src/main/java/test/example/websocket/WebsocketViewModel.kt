package test.example.websocket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import test.example.websocket.common.qualifier.dispatchers.model.MessageModel
import test.example.websocket.network.repository.WebsocketRepository
import javax.inject.Inject

@HiltViewModel
class WebsocketViewModel @Inject constructor(
    private val websocketRepository: WebsocketRepository,
) : ViewModel() {
    fun sendText(text: String) = viewModelScope.launch {
        websocketRepository.sendText(text)
    }

    val getTextStateFlow: StateFlow<MessageModel> = websocketRepository.getTextFlow().map {
        MessageModel(message = it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000L),
        MessageModel(message = "")
    )

    fun regenerateSession() {
        viewModelScope.launch {
            websocketRepository.regenerateSession()
        }
    }

    fun close() {
        viewModelScope.launch {
            websocketRepository.close()
        }
    }

}
