package com.github.oxarnau.transsectes_app.features.transect.presentation.records.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.usecases.SaveTransectAsCSVUseCase
import com.github.oxarnau.transsectes_app.features.transect.domain.usecases.GetAllTransectsUseCase
import com.github.oxarnau.transsectes_app.features.transect.domain.usecases.GetTransectByCurrentUserUseCase
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.intents.RecordsIntent
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.states.RecordsOwner
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.states.RecordsState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

/**
 * ViewModel for managing records-related actions and navigation in the Transects app.
 *
 * This ViewModel is responsible for:
 * - Handling user intents such as navigation to different screens.
 * - Managing the state of records and handling state updates.
 * - Emitting navigation events to direct the user to different routes.
 */
class RecordsViewModel(
    private val getTransectByCurrentUserUseCase: GetTransectByCurrentUserUseCase,
    private val getAllTransectsUseCase: GetAllTransectsUseCase,
    private val saveTransectAsCSVUseCase: SaveTransectAsCSVUseCase,
) : ViewModel() {

    // Mutable SharedFlow that emits navigation events to different routes.
    private val _navigation = MutableSharedFlow<Route>(replay = 0)
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
//    init {
//        initializeRecordsState()
//    }

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
            is RecordsIntent.onDownloadClick -> {
                val fileName = Clock.System.now()

                saveTransectAsCSVUseCase(
                    fileName = "$fileName.csv",
                    content = intent.content,
                )

                // TODO: i18n
                _state.update { it.copy(okMessage = "CSV file has been downloaded. Go to \nAndroid: Files > Downloads\niOS: Browse > TranssectesAPP") }
            }

            is RecordsIntent.onMyTransectsClick -> {
                navigate(Route.MyTransects)
            }

            is RecordsIntent.onAllTransectsClick -> {
                navigate(Route.AllTransects)
            }

            is RecordsIntent.onGoBackClick -> {
                navigate(Route.GoBack)
            }

            is RecordsIntent.onTransectClick -> {
                // Update loading
                _state.update { it.copy(isLoading = true) }

                // Search in _state.records for the intent.index
                val transect = _state.value.records.getOrNull(intent.index)

                // TODO: remove
                println("$transect")

                // Update _state.detailedRecord
                _state.update {
                    it.copy(
                        isLoading = false,
                        detailedRecord = transect
                    )
                }

                // navigate to detailed route
                navigate(Route.DetailedTransect)
            }

            RecordsIntent.fetchAllTransects -> {
                if (_state.value.recordsOwner != RecordsOwner.TECHNICIAN) {
                    fetchTransects(getAllTransectsUseCase)
                    _state.update { it.copy(recordsOwner = RecordsOwner.TECHNICIAN) }
                }
                println("fetchAllTransects: ${_state.value}") // TODO: remove
            }

            RecordsIntent.fetchMyTransects -> {
                if (_state.value.recordsOwner != RecordsOwner.USER) {
                    fetchTransects(getTransectByCurrentUserUseCase)
                    _state.update { it.copy(recordsOwner = RecordsOwner.USER) }
                }
                println("fetchMyTransects: ${_state.value}") // TODO: remove
            }
        }
    }

    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = null) }
    }

    fun clearOkMessage() {
        _state.update { it.copy(okMessage = null) }
    }

    /**
     * Initializes the state for records. This could include setting up any necessary data
     * or pre-processing before the UI is displayed.
     */
    private fun initializeRecordsState() {
        fetchTransects(getTransectByCurrentUserUseCase)
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

    /**
     * Fetches transects based on the provided use case.
     *
     * This function handles fetching transects by calling the appropriate use case,
     * depending on the type of the provided use case instance.
     * It updates the UI state based on the result of the operation (success or error).
     *
     * @param usecase The use case responsible for fetching transects. It can be either:
     * - [GetTransectByCurrentUserUseCase] for fetching transects created by the current user.
     * - [GetAllTransectsUseCase] for fetching all transects.
     */
    private fun <T> fetchTransects(usecase: T) {
        viewModelScope.launch {
            // Start loading state
            _state.update { it.copy(isLoading = true) }

            // Identify which use case to execute and fetch the data
            val result = when (usecase) {
                is GetTransectByCurrentUserUseCase -> usecase.invoke()  // Fetch transects created by current user
                is GetAllTransectsUseCase -> usecase.invoke()  // Fetch all transects
                else -> {
                    // If the use case is unsupported, update the state with an error message
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Unsupported use case"
                        )
                    }
                    return@launch  // Exit early if use case is unsupported
                }
            }

            // Handle the result of the use case invocation
            when (result) {
                is Result.Success -> {
                    // If the operation succeeded, update the state with the fetched transects
                    _state.update {
                        it.copy(
                            isLoading = false,
                            records = result.data,
                        )
                    }
                }

                is Result.Error -> {
                    // If an error occurred, update the state with an error message and empty records
                    _state.update {
                        it.copy(
                            isLoading = false,
                            records = listOf(),
                            errorMessage = result.error.toString(),
                        )
                    }
                }
            }
        }
    }

}
