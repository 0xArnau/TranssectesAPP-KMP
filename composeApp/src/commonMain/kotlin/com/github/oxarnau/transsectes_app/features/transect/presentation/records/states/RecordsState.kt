package com.github.oxarnau.transsectes_app.features.transect.presentation.records.states

import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect

/**
 * Representa el estado de la vista de registros (records).
 *
 * @property isLoading Indica si la aplicación está en proceso de carga.
 * @property records Lista de transectos disponibles.
 */
data class RecordsState(
    val isLoading: Boolean = false,
    val records: List<Transect> = listOf(),
)
