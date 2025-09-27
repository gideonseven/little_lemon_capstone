package com.gideon.little_lemon


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gideon.little_lemon.ui.screen.HomeScreen
import com.gideon.little_lemon.ui.screen.Onboarding
import com.gideon.little_lemon.ui.screen.ProfileScreen
import com.gideon.little_lemon.ui.theme.Little_lemonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Little_lemonTheme {
                AppScreen()
            }
        }
    }
}


@Composable
private fun AppScreen() {
    Scaffold(
        topBar = {
//            TopAppBar()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            MyNavigation()
        }
    }
}


@Composable
fun MyNavigation() {
    val navController = rememberNavController()

    // Using Hilt - no need to pass anything!
    val userViewModel: UserViewModel = hiltViewModel()

    // Check if user is already registered
    val startDestination = if (userViewModel.isUserRegistered()) {
        Home.route
    } else {
        Onboarding.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Onboarding.route) {
            Onboarding(
                navController = navController,
                userViewModel
            )
        }
        composable(Home.route) {
            HomeScreen(
                navController,
                userViewModel
            )
        }
        composable(Profile.route) {
            ProfileScreen(
                navController,
                userViewModel
            )
        }
    }
}