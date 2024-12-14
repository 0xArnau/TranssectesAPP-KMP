package com.github.oxarnau.transsectes_app.features.transect.presentation.records.intents

/**
 * Represents the different user intents related to records (Transects) in the app.
 * These intents are used to trigger navigation or actions based on the user's input.
 */
sealed interface RecordsIntent {

    /**
     * Intent triggered when the user clicks on "My Transects".
     */
    data object onMyTransectsClick : RecordsIntent

    /**
     * Intent triggered when the user clicks on "All Transects".
     */
    data object onAllTransectsClick : RecordsIntent

    /**
     * Intent triggered when the user clicks on the "Download" button.
     */
    data class onDownloadClick(val content: String) : RecordsIntent

    /**
     * Intent triggered when the user clicks on the "Remove" button.
     */
    data object onRemoveClick : RecordsIntent

    /**
     * Intent triggered when the user clicks on the "Go Back" button.
     */
    data object onGoBackClick : RecordsIntent

    /**
     * Intent triggered when the user clicks on the "Transect" card.
     */
    data class onTransectClick(val index: Int) : RecordsIntent

    data object fetchMyTransects : RecordsIntent

    data object fetchAllTransects : RecordsIntent
}
