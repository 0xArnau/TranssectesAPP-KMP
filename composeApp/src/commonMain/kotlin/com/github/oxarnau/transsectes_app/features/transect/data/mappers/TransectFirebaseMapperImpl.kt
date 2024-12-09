package com.github.oxarnau.transsectes_app.features.transect.data.mappers

import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Coordinate
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect
import com.github.oxarnau.transsectes_app.features.transect.domain.mappers.TransectMapper
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.GeoPoint
import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TransectFirebaseMapperImpl : TransectMapper<DocumentSnapshot> {

    override fun toEntity(item: DocumentSnapshot): Transect {
        return Transect(
            administrativeAreaFirst = item.get("administrativeAreaFirst"),
            administrativeAreaLast = item.get("administrativeAreaLast"),
            localityAreaFirst = item.get("localityFirst"),
            localityAreaLast = item.get("localityLast"),
            subAdministrativeAreaFirst = item.get("subAdministrativeAreaFirst"),
            subAdministrativeAreaLast = item.get("subAdministrativeAreaLast"),
            coordinates = toCoordinates(item.get("coordinates")),
            createdAt = toLocalDateTime(item.get("createdAt")),
            createdBy = item.get("createdBy"),
            informedPeople = item.get("informedPeople"),
            observations = item.get("observations"),
            tractor = item.get("tractor")
        )
    }

    private fun toCoordinates(geopoints: List<GeoPoint>): List<Coordinate> {
        return geopoints.map { toCoordinate(it) }
    }

    private fun toCoordinate(geoPoint: GeoPoint): Coordinate {
        return Coordinate(
            latitude = geoPoint.latitude,
            longitude = geoPoint.longitude,
        )
    }

    private fun toLocalDateTime(timestamp: Timestamp): LocalDateTime {
        val instant = Instant.fromEpochSeconds(timestamp.seconds)
        return instant.toLocalDateTime(TimeZone.UTC)
    }
}