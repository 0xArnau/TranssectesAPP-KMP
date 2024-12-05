package com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.core.domain.usecases.SignInUseCase
import com.github.oxarnau.transsectes_app.features.auth.presentation.actions.SignInState
import com.github.oxarnau.transsectes_app.features.auth.presentation.intents.SignInIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Sign In screen.
 * Handles business logic, navigation, and state updates for the Sign In process.
 *
 * @param authRepository A repository to handle authentication operations.
 */
class SignInViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _navigation = MutableSharedFlow<Route>(replay = 0)
    val navigation: SharedFlow<Route> = _navigation

    private val _state = MutableStateFlow(SignInState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500L),
            _state.value
        )

    /**
     * Handle user intents and trigger the corresponding actions.
     *
     * @param intent The user action represented as a SignInIntent.
     */
    fun onIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.onEmailChange -> {
                _state.update { it.copy(email = intent.email) }
            }

            is SignInIntent.onPasswordChange -> {
                _state.update { it.copy(password = intent.password) }
            }

            is SignInIntent.onNextClick -> {
                viewModelScope.launch {
                    submitSignIn()
                }
            }

            is SignInIntent.onBackClick -> {
                viewModelScope.launch {
                    _navigation.emit(Route.Auth)
                }
            }

            is SignInIntent.onForgotPassword -> {
                viewModelScope.launch {
                    _navigation.emit(Route.ForgotPassword)
                }
            }
        }
    }

    /**
     * Submit the sign-in form and perform authentication.
     * This function checks for missing email/password and calls the authentication use case.
     */
    private suspend fun submitSignIn() {
        if (_state.value.email.isNullOrEmpty()) {
            _state.update { it.copy(errorMessage = "Email field cannot be empty") }
            return
        }

        if (_state.value.password.isNullOrEmpty()) {
            _state.update { it.copy(errorMessage = "Password field cannot be empty") }
            return
        }

        val result =
            SignInUseCase(authRepository).invoke(
                _state.value.email!!,
                _state.value.password!!
            )

        when (result) {
            is Result.Success -> {
                _navigation.emit(Route.Home)
            }

            is Result.Error -> {
                _state.update { it.copy(errorMessage = result.error.toString()) }
            }
        }
    }
}
