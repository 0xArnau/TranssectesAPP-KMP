package com.github.oxarnau.transsectes_app.core.domain.repositories

interface FileManagerRepository {

    fun saveCSV(fileName: String, content: String)
}