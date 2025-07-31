package com.loanmanagementapp

import androidx.lifecycle.ViewModel
import com.loanmanagementapp.common.data.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private var startScreen = if (sharedPreferencesManager.getBoolean(IS_LOGIN)) {
        Screen.Home(sharedPreferencesManager.getString(USERNAME).orEmpty())
    } else {
        Screen.Login
    }
    private val _startDestination = MutableStateFlow(startScreen)
    val startDestination: StateFlow<Screen> = _startDestination.asStateFlow()

    fun setUserLoggedIn(username: String) {
        sharedPreferencesManager.putBoolean(IS_LOGIN, true)
        sharedPreferencesManager.putString(USERNAME, username)
        _startDestination.update { Screen.Home(username) }
    }

    fun logoutUser() {
        sharedPreferencesManager.putBoolean(IS_LOGIN, false)
        sharedPreferencesManager.putString(USERNAME, null)
        _startDestination.update { Screen.Login }
    }
}

private const val IS_LOGIN = "isLogin"
private const val USERNAME = "username"
