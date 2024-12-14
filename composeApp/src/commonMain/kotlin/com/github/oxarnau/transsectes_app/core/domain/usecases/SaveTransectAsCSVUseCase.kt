package com.github.oxarnau.transsectes_app.core.domain.usecases

import com.github.oxarnau.transsectes_app.core.domain.repositories.FileManagerRepository

class SaveTransectAsCSVUseCase(private val repository: FileManagerRepository) {

    operator fun invoke(fileName: String, content: String) {
        return repository.saveCSV(fileName, content)
    }
}