package com.ruskiikot.mentoria01.network

import com.ruskiikot.mentoria01.network.model.FilmRaw
import com.ruskiikot.mentoria01.network.model.SearchResultRaw
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET("/")
    suspend fun filmListing(
        @Query("s") search: String = "star+wars",
        @Query("page") page: Int = 1
    ): SearchResultRaw

    @GET("/")
    suspend fun detailsMovie(
        @Query("i") idMovie: String = "tt0076759",
        @Query("plot") plot: String = "full"
    ): FilmRaw
}
