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

/**
 * Converts a single Transect entity into a CSV-formatted string.
 *
 * @receiver Transect The Transect entity to be converted.
 * @return A CSV-formatted string representing the Transect entity.
 */
fun Transect.toCSV(): String {
    return listOf(
        administrativeAreaFirst,
        administrativeAreaLast,
        localityAreaFirst,
        localityAreaLast,
        subAdministrativeAreaFirst,
        subAdministrativeAreaLast,
        coordinates.toString(),
        createdAt.toString(),
        createdBy,
        informedPeople.toString(),
        observations.replace("\"", "\"\""), // Escape quotes
        tractor.toString()
    ).joinToString(";") { it ?: "" } // Null-safe
}

/**
 * Converts a list of Transect entities into a list of CSV-formatted lines.
 * The first line contains the CSV headers.
 *
 * @receiver List<Transect> The list of Transect entities to be converted.
 * @return A list of strings representing the CSV lines, including headers.
 */
fun List<Transect>.toCSV(): String {
    val headers = listOf(
        "administrativeAreaFirst",
        "administrativeAreaLast",
        "localityAreaFirst",
        "localityAreaLast",
        "subAdministrativeAreaFirst",
        "subAdministrativeAreaLast",
        "coordinates",
        "createdAt",
        "createdBy",
        "informedPeople",
        "observations",
        "tractor"
    ).joinToString(";")

    val rows = this.map { it.toCSV() }

    return headers + "\n" + rows.toStringWithNewlines()
}

private fun List<String>.toStringWithNewlines(): String {
    return this.joinToString("\n")
}
