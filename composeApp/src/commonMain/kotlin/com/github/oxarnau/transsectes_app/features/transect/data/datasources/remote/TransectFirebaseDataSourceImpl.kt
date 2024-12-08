package com.github.oxarnau.transsectes_app.features.transect.data.datasources.remote

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.domain.datasources.TransectDataSource
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TransectFirebaseDataSourceImpl : TransectDataSource, KoinComponent {

    private val auth: FirebaseAuth by inject()
    private val firestore: FirebaseFirestore by inject()

    override suspend fun getTransectByCurrentUser(): Result<List<Transect>, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTransects(): Result<List<Transect>, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTransect(transect: Transect): Result<Nothing, DataError> {
        TODO("Not yet implemented")
    }
}