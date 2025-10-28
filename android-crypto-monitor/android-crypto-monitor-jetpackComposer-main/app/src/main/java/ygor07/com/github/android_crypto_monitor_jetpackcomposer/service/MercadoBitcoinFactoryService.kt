package ygor07.com.github.android_crypto_monitor_jetpackcomposer.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MercadoBitcoinServiceFactory {

    fun create(): MercadoBitcoinService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.mercadobitcoin.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MercadoBitcoinService::class.java)
    }
}