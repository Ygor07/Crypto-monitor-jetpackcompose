package ygor07.com.github.android_crypto_monitor_jetpackcomposer

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ygor07.com.github.android_crypto_monitor_jetpackcomposer.screens.BitcoinScreen
import ygor07.com.github.android_crypto_monitor_jetpackcomposer.screens.LoginScreen
import ygor07.com.github.android_crypto_monitor_jetpackcomposer.ui.theme.Android_crypto_monitor_jetpackComposerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_crypto_monitor_jetpackComposerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                    ) {
                        composable(route = "login") {
                            LoginScreen(modifier =  Modifier.padding(innerPadding),navController= navController)
                        }
                        composable(route = "menu") {
                            BitcoinScreen(modifier = Modifier.padding(innerPadding),navController= navController)
                        }
                    }


                }
            }
        }
    }
}

