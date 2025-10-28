package ygor07.com.github.android_crypto_monitor_jetpackcomposer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ygor07.com.github.android_crypto_monitor_jetpackcomposer.R

@Composable
fun BitcoinScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BitcoinViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Ação de navegação para voltar à tela de login, limpando a pilha de navegação
    val navigateToLogin = {
        navController.navigate("login") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        ToolbarMain()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is TickerUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is TickerUiState.Success -> {
                    val ticker = state.ticker
                    QuoteInformation(
                        value = viewModel.formatCurrency(ticker.last),
                        date = viewModel.formatDate(ticker.date),
                        onRefreshClick = { viewModel.fetchTicker() },
                        // Passando a ação de voltar para a tela
                        onBackClick = navigateToLogin
                    )
                }
                is TickerUiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Erro ao carregar dados:\n${state.message}",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        RefreshButton(onClick = { viewModel.fetchTicker() })
                        Spacer(modifier = Modifier.height(8.dp))
                        // Adicionando o botão Voltar também na tela de erro
                        BackButton(onClick = navigateToLogin)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarMain() {
    TopAppBar(
        title = {
            Text(
                text = "Crypto Monitor",
                color = Color.Black
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.mintGreen)
        )
    )
}

@Composable
fun QuoteInformation(
    value: String,
    date: String,
    onRefreshClick: () -> Unit,
    // Novo parâmetro para a ação do botão Voltar
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = value,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Última atualização:")
        Text(text = date)
        Spacer(modifier = Modifier.height(24.dp))
        RefreshButton(onClick = onRefreshClick)

        // Espaço entre os botões
        Spacer(modifier = Modifier.height(8.dp))

        // Botão Voltar adicionado aqui
        BackButton(onClick = onBackClick)
    }
}

@Composable
fun RefreshButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(120.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.mintGreen),
            contentColor = Color.Black
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text("Atualizar")
    }
}

// NOVO COMPOSABLE PARA O BOTÃO VOLTAR
@Composable
fun BackButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(120.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            // Usei uma cor diferente (secundária) para diferenciar da ação principal
            containerColor = colorResource(id = R.color.mintGreen),
            contentColor = Color.Black
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text("Voltar")
    }
}