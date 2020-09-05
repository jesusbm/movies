package com.ruskiikot.mentoria01.di

import com.ruskiikot.mentoria01.interactor.FilmDetailsInteractor
import com.ruskiikot.mentoria01.interactor.FilmListingInteractor
import com.ruskiikot.mentoria01.repository.FilmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module(includes = [RepositoryModule::class])
@InstallIn(FragmentComponent::class)
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
