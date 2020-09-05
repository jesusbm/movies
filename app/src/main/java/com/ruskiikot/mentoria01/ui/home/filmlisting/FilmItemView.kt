package com.ruskiikot.mentoria01.ui.home.filmlisting

import android.view.ViewGroup
import com.ruskiikot.mentoria01.databinding.ItemFilmBinding
import com.ruskiikot.mentoria01.model.network.FilmRaw
import com.ruskiikot.mentoria01.util.loadImage

class FilmItemView(private val binding: ItemFilmBinding) {

    val rootView: ViewGroup = binding.root

    fun updateData(item: FilmRaw) {
        binding.apply {
            idFilmTitle.text = item.Title
            item.Poster?.let { idFilmImage.loadImage(it) }
        }
    }

    fun setOnClickListener(listener: () -> Unit) {
        rootView.setOnClickListener { listener() }
    }
}
