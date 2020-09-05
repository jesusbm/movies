package com.ruskiikot.mentoria01.di

import FilmDetailsInteractor
import com.ruskiikot.mentoria01.interactor.FilmListingInteractor
import com.ruskiikot.mentoria01.repository.FilmRepository
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
class InteractorModule {

    @Provides
    fun provideFilmListingInteractor(filmRepository: FilmRepository): FilmListingInteractor {
        return FilmListingInteractor(filmRepository)
    }

    @Provides
    fun provideFilmDetailsInteractor(filmRepository: FilmRepository): FilmDetailsInteractor {
        return FilmDetailsInteractor(filmRepository)
    }
}
