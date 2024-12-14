package com.github.oxarnau.transsectes_app.core.domain.datasources

interface FileManagerDataSource {

    fun saveToCSV(fileName: String, content: String)
}