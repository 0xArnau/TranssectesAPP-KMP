package com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.core.domain.Result
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
 * @property isEmailVerifiedUseCase Use case for checking if the user's email is verified.
 * @property isUserAuthenticatedUseCase Use case for checking if the user is authenticated.
 */
class AuthViewModel(
    private val isEmailVerifiedUseCase: IsEmailVerifiedUseCase,
    private val isUserAuthenticatedUseCase: IsUserAuthenticatedUseCase
) : ViewModel() {

    // Navigation flow for directing the user to different routes.
    private val _navigation = MutableSharedFlow<Route>(replay = 1)
    val navigation: SharedFlow<Route> = _navigation

    // State flow for representing the current state of the authentication screen.
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
     * Processes user intents for the authentication screen.
     *
     * @param intent The user intent to be handled.
     */
    fun onIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.onSignInClick -> navigate(Route.SignIn)
            is AuthIntent.onSignUpClick -> navigate(Route.SignUp)
        }
    }

    /**
     * Initializes the authentication state by verifying authentication and email status.
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
     * Navigates to a specific route based on the current authentication state.
     *
     * @param isAuthenticated Whether the user is authenticated.
     * @param isEmailVerified Whether the user's email is verified.
     */
    private suspend fun navigateBasedOnState(
        isAuthenticated: Boolean,
        isEmailVerified: Boolean
    ) {
        when {
            isAuthenticated && isEmailVerified -> navigate(Route.Home)
            isAuthenticated && !isEmailVerified -> navigate(Route.VerifyEmail)
        }
    }

    /**
     * Checks if the user's email is verified by invoking the use case.
     *
     * @return True if the email is verified, otherwise false.
     */
    private suspend fun checkEmailVerification(): Boolean {
        val result = isEmailVerifiedUseCase()
        return result is Result.Success && result.data
    }

    /**
     * Checks if the user is authenticated by invoking the use case.
     *
     * @return True if the user is authenticated, otherwise false.
     */
    private suspend fun checkUserAuthentication(): Boolean {
        val result = isUserAuthenticatedUseCase()
        return result is Result.Success && result.data
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
