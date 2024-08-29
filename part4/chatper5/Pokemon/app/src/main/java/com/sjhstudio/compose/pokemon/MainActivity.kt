package com.sjhstudio.compose.pokemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sjhstudio.compose.pokemon.ui.screen.MainScreen
import com.sjhstudio.compose.pokemon.ui.screen.types.MainNavRoute
import com.sjhstudio.compose.pokemon.ui.theme.PokemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            PokemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TopLevel()
                }
            }
        }
    }
}

@Composable
fun TopLevel(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "Home",
        modifier = modifier
    ) {
        composable(MainNavRoute.Home.name) {
            MainScreen {
                Log.d("SJH", "click pokemon >> url:$it")
            }
        }
    }
}