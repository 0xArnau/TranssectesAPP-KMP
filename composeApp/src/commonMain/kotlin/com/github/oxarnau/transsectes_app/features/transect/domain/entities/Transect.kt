package com.github.oxarnau.transsectes_app.features.transect.domain.entities

import kotlinx.datetime.LocalDateTime

data class Transect(
    val administrativeAreaFirst: String?,
    val administrativeAreaLast: String?,
    val localityAreaFirst: String?,
    val localityAreaLast: String?,
    val subAdministrativeAreaFirst: String?,
    val subAdministrativeAreaLast: String?,
    val coordinates: List<Coordinate>,
    val createdAt: LocalDateTime,
    val createdBy: String,
    val informedPeople: Int,
    val observations: String,
    val tractor: Boolean,
)