package com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.core.domain.usecases.SignInUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsEmailVerifiedUseCase
import com.github.oxarnau.transsectes_app.features.auth.presentation.actions.SignInIntent
import com.github.oxarnau.transsectes_app.features.auth.presentation.intents.SignInState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// TODO: Que pasa si un usuario está registrado pero no ha validado el email
class SignInViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _navigation = MutableSharedFlow<Route>(replay = 0)
    val navigation: SharedFlow<Route> = _navigation

    private val _state = MutableStateFlow(SignInState())
    val state = _state
        .onStart {
            val isVerified = checkEmailVerification()
            _state.update {
                SignInState(
                    isLoading = false,
                    isUserEmailVerified = isVerified
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500L),
            _state.value
        )

    fun onIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.onSignInClick -> {
                viewModelScope.launch {
                    SignInUseCase(authRepository).invoke(
                        intent.email,
                        intent.password
                    )
                }
            }

            is SignInIntent.onSignUpClick -> {
                viewModelScope.launch {
                    _navigation.emit(Route.SignUp)
                }
            }

            is SignInIntent.onForgotPassword -> {
                viewModelScope.launch {
                    _navigation.emit(Route.ForgotPassword)
                }
            }
        }
    }

    private suspend fun checkEmailVerification(): Boolean {
        val result: Result<Boolean, DataError.Remote> =
            IsEmailVerifiedUseCase(authRepository).invoke()

        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> false
        }
    }
}
