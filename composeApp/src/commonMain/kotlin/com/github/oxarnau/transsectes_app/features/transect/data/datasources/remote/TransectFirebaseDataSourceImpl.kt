package com.github.oxarnau.transsectes_app.features.transect.data.datasources.remote

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.domain.datasources.TransectDataSource
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect

class TransectFirebaseDataSourceImpl : TransectDataSource {
    override suspend fun getTransectByCreatedBy(createBy: String): Result<List<Transect>, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTransects(): Result<List<Transect>, DataError> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTransect(transect: Transect): Result<Nothing, DataError> {
        TODO("Not yet implemented")
    }
}