package com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.usecases.SignInUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsUserTechnicianUseCase
import com.github.oxarnau.transsectes_app.features.auth.presentation.intents.SignInIntent
import com.github.oxarnau.transsectes_app.features.auth.presentation.states.SignInState
import com.github.oxarnau.transsectes_app.features.user.usecases.SaveUserUseCase
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
 * @param signInUseCase Use case for performing the sign-in operation.
 */
class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val isUserTechnicianUseCase: IsUserTechnicianUseCase,
) : ViewModel() {

    // Navigation flow for directing the user to different routes.
    private val _navigation = MutableSharedFlow<Route>(replay = 1)
    val navigation: SharedFlow<Route> = _navigation

    // State flow for representing the current state of the sign-in screen.
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
            is SignInIntent.onEmailChange -> updateEmail(intent.email)
            is SignInIntent.onPasswordChange -> updatePassword(intent.password)
            is SignInIntent.onNextClick -> performSignIn()
            is SignInIntent.onBackClick -> navigate(Route.Auth)
            is SignInIntent.onForgotPassword -> navigate(Route.ForgotPassword)
        }
    }

    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = null) }
    }

    /**
     * Updates the email field in the current state.
     *
     * @param email The new email value.
     */
    private fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    /**
     * Updates the password field in the current state.
     *
     * @param password The new password value.
     */
    private fun updatePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    /**
     * Triggers the sign-in process.
     */
    private fun performSignIn() {
        viewModelScope.launch {
            submitSignIn()
        }
    }

    /**
     * Submits the sign-in form and performs the authentication process.
     * This function validates input fields and invokes the sign-in use case.
     */
    private suspend fun submitSignIn() {
        // Validate email field
        if (_state.value.email.isNullOrEmpty()) {
            _state.update { it.copy(errorMessage = "Email field cannot be empty") }
            return
        }

        // Validate password field
        if (_state.value.password.isNullOrEmpty()) {
            _state.update { it.copy(errorMessage = "Password field cannot be empty") }
            return
        }

        // Perform sign-in operation
        val result = signInUseCase(
            email = _state.value.email!!,
            password = _state.value.password!!
        )

        // Update the state or navigate based on the result
        when (result) {
            is Result.Success -> {
                saveUserUseCase(result.data)
                navigate(Route.Home)
            }

            is Result.Error -> {
                saveUserUseCase(null)
                _state.update {
                    it.copy(
                        errorMessage = result
                            .error.toString()
                    )
                }
            }
        }
    }

    /**
     * Emits a navigation event to the specified route.
     *
     * @param route The route to navigate to.
     */
    private fun navigate(route: Route) {
        viewModelScope.launch {
            _navigation.emit(route)
        }
    }
}
