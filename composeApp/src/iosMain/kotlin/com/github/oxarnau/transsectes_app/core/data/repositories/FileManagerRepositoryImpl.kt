package com.github.oxarnau.transsectes_app.core.data.repositories

import com.github.oxarnau.transsectes_app.core.domain.datasources.FileManagerDataSource
import com.github.oxarnau.transsectes_app.core.domain.repositories.FileManagerRepository

class FileManagerRepositoryImpl(private val dataSource: FileManagerDataSource) :
    FileManagerRepository {

    override fun saveCSV(fileName: String, content: String) {
        return dataSource.saveToCSV(fileName, content)
    }
}
