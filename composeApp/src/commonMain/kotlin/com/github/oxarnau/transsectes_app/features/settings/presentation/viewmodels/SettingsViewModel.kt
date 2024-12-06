package com.github.oxarnau.transsectes_app.features.settings.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.SignOutUseCase
import com.github.oxarnau.transsectes_app.features.settings.presentation.intents.SettingsIntent
import com.github.oxarnau.transsectes_app.features.settings.presentation.states.SettingsState
import com.github.oxarnau.transsectes_app.features.user.usecases.GetUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {

    // Navigation flow for directing the user to different routes
    private val _navigation = MutableSharedFlow<Route>(replay = 1)
    val navigation: SharedFlow<Route> = _navigation

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(500L),
            _state.value
        )

    init {
        initializeSettingsState()
    }

    /**
     * Handles user intents and triggers appropriate actions or navigation.
     *
     * @param intent The action that the user performed.
     */
    fun onIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.GoBackClick -> navigate(Route.Home)
            is SettingsIntent.SignOutClick -> {
                viewModelScope.launch {
                    val response = signOutUseCase()

                    when (response) {
                        is Result.Success -> navigate(Route.Auth)
                        else -> setErrorMessage("Cannot sign out") // TODO
                    }
                }
            }

            is SettingsIntent.DeleteAccountClick -> TODO()
        }
    }

    /**
     * Initializes the settings state by fetching the current user's information.
     *
     * This function updates the state by first setting the loading state to `true`,
     * then fetching the user's details using [getUserUseCase]. Once the user data is fetched,
     * it updates the state with the user's email and technician status, and sets the loading state to `false`.
     */
    private fun initializeSettingsState() {
        viewModelScope.launch {
            // Set the loading state to true while fetching user data
            _state.update { it.copy(isLoading = true) }

            // Fetch user data using the GetUserUseCase
            val user = getUserUseCase()

            // TODO: remove
            println(
                "id=${user?.id}, email=${user?.email} " +
                        "isEmailVerified=${user?.isEmailVerified} " +
                        "isTechnician=${user?.isTechnician} "
            )


            // Update the state with the fetched user data and set loading to false
            _state.update {
                it.copy(
                    isLoading = false,
                    email = user?.email,
                    isTechnician = user?.isTechnician
                        ?: false,  // Default to false if user is null
                )
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

    /**
     * Update the state with an error message.
     *
     * @param error The error message to display.
     */
    fun setErrorMessage(error: String) {
        _state.value = _state.value.copy(errorMessage = error)
    }
}
