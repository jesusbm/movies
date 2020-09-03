package com.ruskiikot.mentoria01.ui.home.filminfodialog

import android.view.View
import com.ruskiikot.mentoria01.databinding.DialogInfoFilmBinding
import com.ruskiikot.mentoria01.network.model.FilmRaw
import com.ruskiikot.mentoria01.ui.base.ObservableView
import com.squareup.picasso.Picasso

class FilmInfoDialogView(private val binding: DialogInfoFilmBinding) :
    ObservableView<FilmInfoDialogView.FilmInfoDialogViewEventListener>() {

    override val rootView: View
        get() = binding.root


    fun bindData(itemValue: FilmRaw) = binding.run {
        Picasso.get().load(itemValue.Poster).into(idFilmImage)
        idFilmTitle.text = itemValue.Title
        idFilmPlot.text = itemValue.Plot
        idFilmYear.text = itemValue.Year
        idFilmDuration.text = itemValue.Runtime
        idFilmRated.text = itemValue.Rated
    }

    /**
     * Interface to be implemented by any class that can respond to the events of this view
     * */
    interface FilmInfoDialogViewEventListener {

    }
}
