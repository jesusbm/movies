package com.ruskiikot.mentoria01.ui.home.filmlisting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ruskiikot.mentoria01.databinding.ItemFilmBinding
import com.ruskiikot.mentoria01.network.model.FilmRaw

class FilmListingAdapter(
    diffCallback: DiffCallback = DIFF_CALLBACK_DEFAULT
) : ListAdapter<FilmRaw, FilmListingAdapter.FilmItemViewHolder>(diffCallback) {

    private var onItemClickListener: OnClickItemListener? = null

    companion object {
        private val DIFF_CALLBACK_DEFAULT = DiffCallback()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmItemViewHolder {
        val myItemView = FilmItemView(ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        return FilmItemViewHolder(myItemView, onItemClickListener)
    }

    override fun onBindViewHolder(holder: FilmItemViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    /**
     * Auxiliar class. ViewHolder implementation
     * */
    class FilmItemViewHolder(private val filmItemView: FilmItemView, private val onItemClickListener: OnClickItemListener?) :
        RecyclerView.ViewHolder(filmItemView.rootView) {
        fun bindItem(item: FilmRaw) {
            filmItemView.apply {
                updateData(item)
                setOnClickListener { onItemClickListener?.clickOnItem(item) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FilmRaw>() {
        override fun areItemsTheSame(oldItem: FilmRaw, newItem: FilmRaw) = oldItem.imdbID == newItem.imdbID
        override fun areContentsTheSame(oldItem: FilmRaw, newItem: FilmRaw) = true
    }

    interface OnClickItemListener {
        fun clickOnItem(item: FilmRaw)
    }

    fun setOnClickItemListener(onClickItemListener: OnClickItemListener) {
        this.onItemClickListener = onClickItemListener
    }
}
