package com.ufes.automobile.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState?>(null)
    val authState: StateFlow<AuthState?> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            try{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        _authState.value = AuthState(isSuccess = true)
                    }
                    .addOnFailureListener { e ->
                        _authState.value = AuthState(errorMessage = "Login failed: ${e.message}")
                    }

            } catch (e: Exception) {
                _authState.value = AuthState(errorMessage = "Unexpected error: ${e.message}")
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        _authState.value = AuthState(isSuccess = true)
                    }
                    .addOnFailureListener { e ->
                        _authState.value = AuthState(errorMessage = "Register failed: ${e.message}")
                    }
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                auth.signOut()
                _authState.value = AuthState(isSuccess = true)
            } catch (e: Exception) {
                _authState.value = AuthState(errorMessage = "Logout failed: ${e.message}")
            }
        }
    }

    fun resetAuthState() {
        _authState.value = null
    }
}