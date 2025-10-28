package ygor07.com.github.android_crypto_monitor_jetpackcomposer.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ygor07.com.github.android_crypto_monitor_jetpackcomposer.model.Ticker
import ygor07.com.github.android_crypto_monitor_jetpackcomposer.service.MercadoBitcoinServiceFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat // Importe esta classe
import java.util.Date // Importe esta classe
import java.util.Locale

// Define os possíveis estados da nossa UI
sealed interface TickerUiState {
    data class Success(val ticker: Ticker) : TickerUiState
    data class Error(val message: String) : TickerUiState
    object Loading : TickerUiState
}

class BitcoinViewModel : ViewModel() {

    private val service = MercadoBitcoinServiceFactory().create()

    private val _uiState = MutableStateFlow<TickerUiState>(TickerUiState.Loading)
    val uiState: StateFlow<TickerUiState> = _uiState

    init {
        fetchTicker()
    }

    fun fetchTicker() {
        _uiState.value = TickerUiState.Loading
        viewModelScope.launch {
            try {
                val response = service.getTicker()
                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = TickerUiState.Success(response.body()!!.ticker)
                } else {
                    _uiState.value = TickerUiState.Error("Falha ao buscar dados.")
                }
            } catch (e: Exception) {
                _uiState.value = TickerUiState.Error(e.message ?: "Erro desconhecido.")
            }
        }
    }

    fun formatCurrency(value: String): String {
        return try {
            val number = value.toDouble()
            val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            format.format(number)
        } catch (e: NumberFormatException) {
            "R$ 0,00"
        }
    }

    // ##### FUNÇÃO CORRIGIDA #####
    // Função para formatar a data (timestamp) de forma compatível
    fun formatDate(timestamp: Long): String {
        // O timestamp da API está em segundos, precisamos converter para milissegundos
        val milliseconds = timestamp * 1000L
        val date = Date(milliseconds)
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return formatter.format(date)
    }
}