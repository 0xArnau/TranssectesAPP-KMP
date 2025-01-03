package com.github.oxarnau.transsectes_app.features.transect.data.repositories

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.domain.datasources.TransectDataSource
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect
import com.github.oxarnau.transsectes_app.features.transect.domain.repositories.TransectRepository

class TransectRepositoryImpl(private val dataSource: TransectDataSource) :
    TransectRepository {

    override suspend fun getTransectByCurrentUser(): Result<List<Transect>, DataError> {
        return dataSource.getTransectByCurrentUser()
    }

    override suspend fun getAllTransects(): Result<List<Transect>, DataError> {
        return dataSource.getAllTransects()
    }

    override suspend fun saveTransect(transect: Transect): Result<Nothing, DataError> {
        return dataSource.saveTransect(transect)
    }
}