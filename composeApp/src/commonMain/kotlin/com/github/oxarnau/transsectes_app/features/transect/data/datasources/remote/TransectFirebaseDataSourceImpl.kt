package com.github.oxarnau.transsectes_app.features.transect.data.datasources.remote

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.data.mappers.TransectFirebaseMapperImpl
import com.github.oxarnau.transsectes_app.features.transect.domain.datasources.TransectDataSource
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Implementation of the [TransectDataSource] interface using Firebase as the data source.
 * This class provides operations to interact with transect-related data stored in Firestore.
 *
 * @property auth Firebase Authentication instance to get the current user.
 * @property store Firebase Firestore instance to access and manipulate transect data.
 */
class TransectFirebaseDataSourceImpl : TransectDataSource, KoinComponent {

    private val auth: FirebaseAuth by inject()
    private val store: FirebaseFirestore by inject()

    override suspend fun getTransectByCurrentUser(): Result<List<Transect>, DataError> {
        return try {
            // Get current user email
            val currentUserEmail = auth.currentUser?.email
                ?: return Result.Error(DataError.User.EMAIL_NOT_FOUND)

            // Get documents & Filter by createdBy == currentUserEmail
            val docRef = store
                .collection("transects")
                .get()
                .documents

            val transects =
                docRef
                    .map { TransectFirebaseMapperImpl().toEntity(it) }
                    .filter { it.createdBy == currentUserEmail }
                    .sortedByDescending { it.createdAt }

            // TODO: remove
            println("getTransectByCurrentUser: ${docRef} ${transects}")

            Result.Success(transects)
        } catch (e: Exception) {
            // TODO: remove
            println("getTransectByCurrentUser: ${e.message}")

            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun getAllTransects(): Result<List<Transect>, DataError> {
        return try {
            // Get documents & Filter by createdBy == currentUserEmail
            val docRef = store
                .collection("transects")
                .get()
                .documents

            val transects =
                docRef
                    .map { TransectFirebaseMapperImpl().toEntity(it) }
                    .sortedByDescending { it.createdAt }

            // TODO: remove
            println("getAllTransects: ${docRef} ${transects}")

            Result.Success(transects)
        } catch (e: Exception) {
            // TODO: remove
            println("getAllTransects: ${e.message}")

            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun saveTransect(transect: Transect): Result<Nothing, DataError> {
        TODO("Not yet implemented")
    }
}