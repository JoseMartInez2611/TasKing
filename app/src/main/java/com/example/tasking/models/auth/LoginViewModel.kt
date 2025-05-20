package com.example.tasking.models.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasking.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {

                val response = repository.login(username, password)

                if (response.isSuccessful) {
                    _loginState.value = LoginState.Success(response.body()?.token ?: "")

                } else {
                    _loginState.value = LoginState.Error("Credenciales incorrectas")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error de red: ${e.localizedMessage}")
            }
        }
    }
}
