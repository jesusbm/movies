package com.ruskiikot.mentoria01.ui.home.filmlisting

import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ruskiikot.mentoria01.databinding.ViewFilmListingBinding
import com.ruskiikot.mentoria01.model.network.FilmRaw
import com.ruskiikot.mentoria01.ui.base.ObservableView
import com.ruskiikot.mentoria01.util.getTypedLayoutManager

class FilmListingView(
    private val binding: ViewFilmListingBinding,
    val filmListingAdapter: FilmListingAdapter,
    private val filmListingLayoutManager: RecyclerView.LayoutManager
) : ObservableView<FilmListingView.FilmListingViewListener>(),
    FilmListingAdapter.OnClickItemListener {

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            this@FilmListingView.onScrolled(recyclerView, dx, dy)
        }
    }

    init {
        //RecyclerView Configuration
        binding.recyclerView.apply {
            layoutManager = filmListingLayoutManager
            adapter = filmListingAdapter
            addItemDecoration(LoadingItemsItemDecoration())
            addOnScrollListener(onScrollListener)
        }

        //refresh layout configuration
        binding.refreshContainer.setOnRefreshListener {
            notifyObservers {
                it.refreshContentRequested()
            }
        }

        //register listener in adapter
        filmListingAdapter.setOnClickItemListener(this)
    }

    override val rootView: View = binding.root

    fun setFilmsToListing(listing: List<FilmRaw>) {
        filmListingAdapter.submitList(listing)
        binding.refreshContainer.isRefreshing = false
    }

    fun appendFilmsToListing(filmListing: List<FilmRaw>) {
        filmListingAdapter.submitList(filmListingAdapter.currentList + filmListing)
    }

    override fun clickOnItem(item: FilmRaw) {
        notifyObservers {
            it.clickOnItem(item)
        }
    }

    private fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        for (child in recyclerView.children) {
            val childAdapterPosition = recyclerView.getChildAdapterPosition(child)
            if (childAdapterPosition + 1 == recyclerView.getTypedLayoutManager<GridLayoutManager>()?.itemCount) {
                notifyObservers {
                    it.endOfListingReached()
                }
            }
        }
    }

    /**
     * Interface to be implemented by any class that can respond to the events of this view
     * */
    interface FilmListingViewListener {
        fun refreshContentRequested()
        fun clickOnItem(item: FilmRaw)
        fun endOfListingReached()
    }
}
