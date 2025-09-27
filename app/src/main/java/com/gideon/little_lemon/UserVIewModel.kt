package com.gideon.little_lemon

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.gideon.little_lemon.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.content.edit


@HiltViewModel
class UserViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    init {
        // Load user data from SharedPreferences on initialization
        loadUserFromSharedPreferences()
    }

    private val _user = mutableStateOf(User())
    val user: State<User> = _user

    private val _isRegistered = mutableStateOf(false)
    val isRegistered: State<Boolean> = _isRegistered


    fun registerUser(firstName: String, lastName: String, email: String) {
        _user.value = User(firstName, lastName, email)
        _isRegistered.value = true

        // Save to SharedPreferences for persistence across app restarts
        saveToSharedPreferences(firstName, lastName, email)
    }

    fun logout() {
        _user.value = User()
        _isRegistered.value = false

        // Clear SharedPreferences
        clearSharedPreferences()
    }

    fun isUserRegistered(): Boolean {
        // For simple in-memory persistence, just check the current state
        return _isRegistered.value
    }


    // SharedPreferences methods for true persistence:
    private fun saveToSharedPreferences(firstName: String, lastName: String, email: String) {
        val sharedPref = context.getSharedPreferences(Constant.USER_PREF, Context.MODE_PRIVATE)
        sharedPref.edit {
            putString(Constant.FIRST_NAME, firstName)
            putString(Constant.LAST_NAME, lastName)
            putString(Constant.EMAIL, email)
            putBoolean(Constant.IS_REGISTERED, true)
        }
    }

    private fun clearSharedPreferences() {
        val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPref.edit {
            clear()
            apply()
        }
    }

    private fun loadUserFromSharedPreferences() {
        val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isRegistered = sharedPref.getBoolean("is_registered", false)
        if (isRegistered) {
            val firstName = sharedPref.getString("first_name", "") ?: ""
            val lastName = sharedPref.getString("last_name", "") ?: ""
            val email = sharedPref.getString("email", "") ?: ""
            _user.value = User(firstName, lastName, email)
            _isRegistered.value = true
        }
    }
}