package com.ruskiikot.mentoria01.di

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ruskiikot.mentoria01.R
import com.ruskiikot.mentoria01.databinding.DialogInfoFilmBinding
import com.ruskiikot.mentoria01.databinding.ViewFilmListingBinding
import com.ruskiikot.mentoria01.ui.home.filminfodialog.FilmInfoDialogView
import com.ruskiikot.mentoria01.ui.home.filmlisting.FilmListingAdapter
import com.ruskiikot.mentoria01.ui.home.filmlisting.FilmListingView
import com.ruskiikot.mentoria01.util.getInteger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

    /**
     * Dependencias de FilmInfoDialog
     * */
    @Provides
    fun provideFilmInfoDialogView(binding: DialogInfoFilmBinding): FilmInfoDialogView =
        FilmInfoDialogView(binding)

    @Provides
    fun providesDialogInfoFilmBinding(@ActivityContext context: Context): DialogInfoFilmBinding =
        DialogInfoFilmBinding.inflate(LayoutInflater.from(context), null, false)

    /**
     * Dependencias de HomeFragmentView
     * */
    @Provides
    fun provideHomeFragmentView(
        binding: ViewFilmListingBinding,
        adapter: FilmListingAdapter,
        layoutManager: RecyclerView.LayoutManager
    ): FilmListingView =
        FilmListingView(binding, adapter, layoutManager)

    @Provides
    fun provideFilmListingViewBinding(@ActivityContext context: Context): ViewFilmListingBinding =
        ViewFilmListingBinding.inflate(LayoutInflater.from(context), null, false)

    @Provides
    fun provideFilmAdapter() =
        FilmListingAdapter()

    @Provides
    fun provideLayoutManager(@ActivityContext context: Context): RecyclerView.LayoutManager =
        GridLayoutManager(
            context,
            context.getInteger(R.integer.number_columns_film_adapter)
        )
}
