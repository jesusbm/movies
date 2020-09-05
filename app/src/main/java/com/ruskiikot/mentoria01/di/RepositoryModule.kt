package com.ruskiikot.mentoria01.di

import com.ruskiikot.mentoria01.network.OmdbApi
import com.ruskiikot.mentoria01.repository.FilmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent

@Module(includes = [NetworkModule::class])
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    fun provideFilmRepositoryRepository(omdbApi: OmdbApi) = FilmRepository(omdbApi)

}
