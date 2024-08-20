package com.example.compose.exercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.exercise.ui.theme.ExerciseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            ExerciseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    ExerciseCompositionLocal()
//                    ExerciseNav()
                    ExerciseGithubRepos()
                }
            }
        }
    }
}

/* Exercise CompositionLocal */
@Composable
fun ExerciseCompositionLocal() {
    val LocalElevation = compositionLocalOf { 8.dp }    // custom composition local (with. elevation)
    CompositionLocalProvider(value = LocalElevation provides 12.dp) {
        Card(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                CompositionLocalProvider(LocalContentColor provides Color.Black) {
                    Text("Hello, World!")
                    Text("Hello, World!")
                }
                Text("Hello, World!")
                Button(
                    shape = RoundedCornerShape(6.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text("Hello, World")
                }
                // LocalContent Alpha don't exist in Material3
            }
        }
    }
}

@Composable
fun ExerciseNav(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "Home",
        modifier = modifier
    ) {
//        composable(route = "Home") {
//            Column {
//                Text(text = "Home")
//                Button(onClick = { navController.navigate("Office") }) {
//                    Text("Go to Office")
//                }
//                Button(onClick = { navController.navigate("PlayGround") }) {
//                    Text(text = "Go to PlayGround")
//                }
//            }
//        }
//        composable("Office") {
//            Column {
//                Text(text = "Office")
//                Button(onClick = {
//                    navController.navigate("Home") {
//                        popUpTo("Home") {
//                            inclusive = true
//                        }
//                    }
//                }) {
//                    Text("Go to Home")
//                }
//                Button(onClick = { navController.navigate("PlayGround") }) {
//                    Text(text = "Go to PlayGround")
//                }
//            }
//        }
//        composable("PlayGround") {
//            Column {
//                Text(text = "PlayGround")
//                Button(onClick = {
//                    navController.navigate("Home") {
//                        popUpTo("Home") {
//                            inclusive = true
//                        }
//                    }
//                }) {
//                    Text("Go to Home")
//                }
//                Button(onClick = { navController.navigate("Office") }) {
//                    Text(text = "Go to Office")
//                }
//            }
//        }
        composable(route = "Home") {
            Column {
                Text(text = "Home")
                Button(onClick = {
                    navController.navigate("Login") {
                    }
                }) {
                    Text(text = "Login")
                }
                Button(onClick = {
                    navController.navigate("Mail") {
                    }
                }) {
                    Text(text = "Mail")
                }
                Button(onClick = {
                    navController.navigate("Argument/1") {
                    }
                }) {
                    Text(text = "Argument(1)")
                }
                Button(onClick = {
                    navController.navigate("Argument/4") {
                    }
                }) {
                    Text(text = "Argument(4)")
                }
            }
        }
        composable(route = "Login") {
            Column {
                Text(text = "Login")
                Button(onClick = {
                    navController.navigate("Mail") {
                        popUpTo("Login") { inclusive = true }
                    }
                }) {
                    Text(text = "Mail")
                }
            }
        }
        composable(route = "Mail") {
            Column {
                Text(text = "Mail")
                Button(onClick = {
                    navController.navigate(route = "Mail") {
                        launchSingleTop = true
                    }
                }) {
                    Text(text = "Mail")
                }
            }
        }
        composable("Argument/{screenId}") {
            when (val id = it.arguments?.getString("screenId")) {
                "1" -> Text("1 : $id")
                "2" -> Text("2 : $id")
                "3" -> Text("3 : $id")
                else -> Text("$id")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseCompositionLocalPreview() {
    ExerciseTheme {
        ExerciseCompositionLocal()
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseGithubReposPreview() {
    ExerciseTheme {
        ExerciseGithubRepos()
    }
}

@Composable
fun ExerciseGithubRepos(viewModel: MainViewModel = viewModel()) {
    LazyColumn {
        item {
            Button(onClick = { viewModel.getRepos() }) {
                Text("Search Github Repository")
            }
        }
        items(viewModel.repos) {
            Text(it.name)
        }
    }
}