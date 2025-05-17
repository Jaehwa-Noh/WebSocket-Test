package test.example.websocket.common.qualifier.dispatchers.model

data class MessageModel(
    val id: Long = System.currentTimeMillis(),
    val message: String = "",
)
