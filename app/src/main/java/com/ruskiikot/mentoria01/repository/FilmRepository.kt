package com.ruskiikot.mentoria01.repository

import com.ruskiikot.mentoria01.model.network.FilmRaw
import com.ruskiikot.mentoria01.network.OmdbApi
import com.ruskiikot.mentoria01.util.startsAsHttpUrl
import kotlinx.coroutines.delay

class FilmRepository(private val api: OmdbApi) {

    val FULL_PLOT = "full"
    val SHORT_PLOT = "short"

    suspend fun getRemoteFilmListing(search: String, page: Int): List<FilmRaw> {
        randomDelay(500)
        return api.filmListing(search = search, page = page)
            .Search
            .filter { it.Poster.startsAsHttpUrl() }
    }

    suspend fun getFilmDetails(imdbID: String, isFullPlot: Boolean): FilmRaw {
        randomDelay(500)
        return api.detailsMovie(imdbID, plot = if (isFullPlot) FULL_PLOT else SHORT_PLOT)
    }

    private suspend fun randomDelay(maxDuration: Long = 1000) {
        val delayDuration = Math.random() * maxDuration
        delay(delayDuration.toLong())
    }
}
