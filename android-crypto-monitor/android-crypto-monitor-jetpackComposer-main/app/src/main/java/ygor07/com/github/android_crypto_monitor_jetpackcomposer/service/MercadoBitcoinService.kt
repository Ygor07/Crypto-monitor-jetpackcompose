package ygor07.com.github.android_crypto_monitor_jetpackcomposer.service

import ygor07.com.github.android_crypto_monitor_jetpackcomposer.model.TicketResponse
import retrofit2.Response
import retrofit2.http.GET

interface MercadoBitcoinService {
    @GET("api/BTC/ticker/")
    suspend fun getTicker(): Response<TicketResponse>
}