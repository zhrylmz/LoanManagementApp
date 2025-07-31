package com.loanmanagementapp

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.loanmanagementapp.common.ui.theme.LoanManagementAppTheme
import com.loanmanagementapp.features.home.ui.HomeScreen
import com.loanmanagementapp.features.home.ui.HomeViewModel
import com.loanmanagementapp.features.login.ui.LoginScreen
import com.loanmanagementapp.features.login.ui.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoanManagementAppTheme {
                LoanApp()
            }
        }
    }
}

@Composable
fun LoanApp() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val startDestination by mainViewModel.startDestination.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Login> {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(viewModel = viewModel) { mainViewModel.setUserLoggedIn(it) }
        }
        composable<Screen.Home> { backStackEntry ->
            val userName: String = backStackEntry.toRoute<Screen.Home>().username
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(viewModel, userName = userName) {
                mainViewModel.logoutUser()
            }
        }
    }
}
@Serializable
sealed class Screen(val route: String) {
    @Serializable
    object Login : Screen("login")

    @Serializable
    data class Home(val username: String = "") : Screen("home")
}
