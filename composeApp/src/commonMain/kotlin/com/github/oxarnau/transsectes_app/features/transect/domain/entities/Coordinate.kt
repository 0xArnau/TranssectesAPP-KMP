package com.github.oxarnau.transsectes_app.features.transect.domain.entities

data class Coordinate(
    val latitude: Double,
    val longitude: Double,
) {
    override fun toString(): String {
        return "(${this.latitude},${this.longitude})"
    }
}
