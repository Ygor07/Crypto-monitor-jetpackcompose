package ygor07.com.github.android_crypto_monitor_jetpackcomposer.model

// Convertido para data class
data class TicketResponse(
    val ticker: Ticker
)

// Convertido para data class
data class Ticker(
    val high: String,
    val low: String,
    val vol: String,
    val last: String,
    val buy: String,
    val sell: String,
    val date: Long
)