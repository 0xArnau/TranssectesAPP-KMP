package com.github.oxarnau.transsectes_app.features.transect.presentation.records.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.domain.usecases.GetTransectByCurrentUserUseCase
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.intents.RecordsIntent
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.states.RecordsState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for managing records-related actions and navigation in the Transects app.
 *
 * This ViewModel is responsible for:
 * - Handling user intents such as navigation to different screens.
 * - Managing the state of records and handling state updates.
 * - Emitting navigation events to direct the user to different routes.
 */
class RecordsViewModel(
    private val getTransectByCurrentUserUseCase: GetTransectByCurrentUserUseCase
) : ViewModel() {

    // Mutable SharedFlow that emits navigation events to different routes.
    private val _navigation = MutableSharedFlow<Route>(replay = 1)
    val navigation: SharedFlow<Route> =
        _navigation  // Exposed as a SharedFlow for observation

    // Mutable state for managing the UI state related to records.
    private val _state = MutableStateFlow(RecordsState())
    val state = _state
        .stateIn(
            viewModelScope,  // State will be scoped to the ViewModel's lifecycle
            SharingStarted.WhileSubscribed(500L),  // State will be retained while subscribed
            _state.value  // Initial value of the state
        )

    /**
     * Initializes the ViewModel, setting up any initial state or logic.
     * Currently only prints a message indicating initialization.
     */
    init {
        initializeRecordsState()
    }

    /**
     * Handles user intents by triggering corresponding navigation actions.
     *
     * This method is called when an intent is received, such as a user clicking a button.
     *
     * @param intent The [RecordsIntent] that represents the user's action.
     */
    fun onIntent(intent: RecordsIntent) {
        when (intent) {
            is RecordsIntent.onRemoveClick -> navigate(Route.RemoveTransects)  // Navigate to Remove Transects screen
            is RecordsIntent.onDownloadClick -> navigate(Route.DonwloadTransects)  // Navigate to Download Transects screen
            is RecordsIntent.onMyTransectsClick -> navigate(Route.MyTransects)  // Navigate to My Transects screen
            is RecordsIntent.onAllTransectsClick -> navigate(Route.AllTransects)  // Navigate to All Transects screen
            is RecordsIntent.onGoBackClick -> navigate(Route.Home)  // Navigate back to Home screen
        }
    }

    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = null) }
    }

    /**
     * Initializes the state for records. This could include setting up any necessary data
     * or pre-processing before the UI is displayed.
     */
    private fun initializeRecordsState() {
        // TODO: remove
        println("Records ViewModel initialized.")

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val response = getTransectByCurrentUserUseCase.invoke()) {
                is Result.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        records = response.data,
                    )
                }

                is Result.Error -> _state.update {
                    it.copy(
                        isLoading = false,
                        records = listOf(),
                        errorMessage = response.error.toString(),
                    )
                }
            }

            println("initializeRecordsState: ${_state.value.records}")

        }
    }

    /**
     * Emits a navigation event to the specified route, allowing the app to navigate to a new screen.
     *
     * @param route The [Route] representing the destination screen to navigate to.
     */
    private fun navigate(route: Route) {
        viewModelScope.launch {
            _navigation.emit(route)  // Emit the route as a navigation event
        }
    }
}
