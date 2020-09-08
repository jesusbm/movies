package com.ruskiikot.mentoria01.interactor

import com.ruskiikot.mentoria01.model.network.FilmRaw
import com.ruskiikot.mentoria01.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmListingInteractor(private val repository: FilmRepository) {

    suspend fun getFilmListing(search: String = "simpsons", page: Int): List<FilmRaw> {
        return withContext(Dispatchers.IO) {
            repository.getRemoteFilmListing(search, page)
        }
    }

}
