package com.ruskiikot.mentoria01.repository

import com.ruskiikot.mentoria01.model.network.FilmRaw
import com.ruskiikot.mentoria01.network.OmdbApi
import com.ruskiikot.mentoria01.util.startsAsHttpUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FilmRepository(private val api: OmdbApi) {

    suspend fun getFilmListing(search: String = "star+wars", page: Int = 1): List<FilmRaw> {
        return withContext(Dispatchers.IO) {// This class is not the right place to set threading information, use a Interactor
            val delayDuration = Math.random() * 500
            delay(delayDuration.toLong())
            api.filmListing(search = search, page = page)
                .Search
                .filter { it.Poster.startsAsHttpUrl() }
        }
    }

    suspend fun getFilmDetail(imdbID: String): FilmRaw {
        return withContext(Dispatchers.IO) {
            api.detailsMovie(imdbID)
        }
    }
}
