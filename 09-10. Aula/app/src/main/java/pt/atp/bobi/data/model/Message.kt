package pt.atp.bobi.data.model

data class Message(val id: String,
                   val user: String,
                   val content: String,
                   val timestamp: String,
                   var outgoing: Boolean = false)