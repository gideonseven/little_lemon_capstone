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
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import androidx.room.Room
import com.gideon.little_lemon.data.MenuItemNetwork
import com.gideon.little_lemon.data.MenuResponse
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Little_lemonTheme {
                AppScreen(database)
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                val menuItemsNetwork = fetchMenu()
                saveMenuToDatabase(menuItemsNetwork)
            }
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        return httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuResponse>()
            .menu
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }
}


@Composable
private fun AppScreen(database: AppDatabase) {
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
            MyNavigation(database)
        }
    }
}


@Composable
fun MyNavigation(database: AppDatabase) {
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
                userViewModel = userViewModel
            )
        }
        composable(Home.route) {
            HomeScreen(
                navController = navController,
                database = database
            )
        }
        composable(Profile.route) {
            ProfileScreen(
                userViewModel = userViewModel
            )
        }
    }
}