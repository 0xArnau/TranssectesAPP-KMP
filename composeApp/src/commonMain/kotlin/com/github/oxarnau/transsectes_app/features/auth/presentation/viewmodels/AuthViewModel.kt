package com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsEmailVerifiedUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsUserAuthenticatedUseCase
import com.github.oxarnau.transsectes_app.features.auth.presentation.actions.AuthState
import com.github.oxarnau.transsectes_app.features.auth.presentation.intents.AuthIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the authentication process and navigation between related screens.
 *
 * @property authRepository Repository for authentication-related operations.
 */
class AuthViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _navigation = MutableSharedFlow<Route>(replay = 0)
    val navigation: SharedFlow<Route> = _navigation

    private val _state = MutableStateFlow(AuthState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500L),
            _state.value
        )

    init {
        initializeAuthState()
    }

    /**
     * Handles user intents for the authentication screen.
     *
     * @param intent The user intent to be processed.
     */
    fun onIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.onSignInClick -> {
                viewModelScope.launch {
                    _navigation.emit(Route.SignIn)
                }
            }

            is AuthIntent.onSignUpClick -> {
                viewModelScope.launch {
                    _navigation.emit(Route.SignUp)
                }
            }
        }
    }

    /**
     * Initializes the authentication state by checking user authentication and email verification status.
     */
    private fun initializeAuthState() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val isAuthenticated = checkUserAuthentication()
            val isEmailVerified =
                if (isAuthenticated) checkEmailVerification() else false

            _state.update {
                AuthState(
                    isLoading = false,
                    isUserAuthenticated = isAuthenticated,
                    isUserEmailVerified = isEmailVerified
                )
            }

            navigateBasedOnState(isAuthenticated, isEmailVerified)
        }
    }

    /**
     * Navigates to the appropriate route based on the user's authentication state.
     *
     * @param isAuthenticated Whether the user is authenticated.
     * @param isEmailVerified Whether the user's email is verified.
     */
    private suspend fun navigateBasedOnState(
        isAuthenticated: Boolean,
        isEmailVerified: Boolean
    ) {
        when {
            isAuthenticated && isEmailVerified -> _navigation.emit(Route.Home)
            isAuthenticated && !isEmailVerified -> _navigation.emit(Route.VerifyEmail)
        }
    }

    private suspend fun checkEmailVerification(): Boolean {
        val result = IsEmailVerifiedUseCase(authRepository).invoke()

        return result is Result.Success && result.data
    }

    private suspend fun checkUserAuthentication(): Boolean {
        val result = IsUserAuthenticatedUseCase(authRepository).invoke()

        return result is Result.Success && result.data
    }
}
