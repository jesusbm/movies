package com.ruskiikot.mentoria01.network

import com.ruskiikot.mentoria01.model.network.FilmRaw
import com.ruskiikot.mentoria01.model.network.SearchResultRaw
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET("/")
    suspend fun filmListing(
        @Query("s") search: String,
        @Query("page") page: Int
    ): SearchResultRaw

    @GET("/")
    suspend fun detailsMovie(
        @Query("i") idMovie: String,
        @Query("plot") plot: String
    ): FilmRaw
}
