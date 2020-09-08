package com.ruskiikot.mentoria01.ui.home.filmlisting

import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ruskiikot.mentoria01.R
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

    enum class State {
        OPENING,
        READY,
        LOADING,
        ALL_LOADED
    }

    private var state: State = State.OPENING
        get() = field
        set(value) {
            field = value
            setViewState(value)
        }

    private var _isEndOFListingReached: Boolean = false

    var isEndOFListingReached: Boolean
        set(value) {
            _isEndOFListingReached = value
            state = State.ALL_LOADED
        }
        get() = _isEndOFListingReached

    private val ITEM_DECORATION_LOADING: RecyclerView.ItemDecoration = LoadingItemsItemDecoration()
    private val ITEM_DECORATION_INDEX = 0

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            this@FilmListingView.onScrolled(recyclerView)
        }
    }

    init {
        state = State.OPENING

        //RecyclerView Configuration
        binding.recyclerView.apply {
            layoutManager = filmListingLayoutManager
            adapter = filmListingAdapter
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

    suspend fun appendItems(loadFunction: suspend () -> List<FilmRaw>) {
        state = State.LOADING
        appendFilmsToListing(loadFunction())
        state = State.READY
    }

    suspend fun setItems(loadFunction: suspend () -> List<FilmRaw>) {
        state = State.LOADING
        setFilmsToListing(loadFunction())
        state = State.READY
    }

    private fun setFilmsToListing(listing: List<FilmRaw>) {
        filmListingAdapter.submitList(listing)
        binding.refreshContainer.isRefreshing = false
    }

    private fun appendFilmsToListing(filmListing: List<FilmRaw>) {
        filmListingAdapter.submitList(filmListingAdapter.currentList + filmListing)
    }

    override fun clickOnItem(item: FilmRaw) {
        notifyObservers {
            it.clickOnItem(item)
        }
    }

    private fun onScrolled(recyclerView: RecyclerView) {
        for (child in recyclerView.children) {
            val childAdapterPosition = recyclerView.getChildAdapterPosition(child)
            if (1+ childAdapterPosition == recyclerView.getTypedLayoutManager<GridLayoutManager>()?.itemCount) {
                notifyObservers {
                    if (!_isEndOFListingReached) {
                        it.endOfScrollReached()
                    }
                }
            }
        }
    }

    private fun activateRecyclerViewIndicators(indicatorsActive: Boolean) {
        binding.recyclerView.apply {
            if (indicatorsActive) {
                if (itemDecorationCount == 0) addItemDecoration(ITEM_DECORATION_LOADING, ITEM_DECORATION_INDEX)
            } else {
                if (itemDecorationCount > 0) removeItemDecorationAt(ITEM_DECORATION_INDEX)
            }
        }
        binding.bottomIcon.apply {
            if (indicatorsActive) setImageResource(R.drawable.filmstrip_icon)
            else setImageResource(R.drawable.emoticon_happy_icon)
        }
    }

    private fun setViewState(state: State): Unit = when (state) {
        State.OPENING -> {
            binding.progressBarOpenListing.isVisible = true
            binding.refreshContainer.isVisible = false
            binding.refreshContainer.isRefreshing = false
            activateRecyclerViewIndicators(indicatorsActive = false)
            _isEndOFListingReached = false
        }
        State.READY -> {
            binding.progressBarOpenListing.isVisible = false
            binding.refreshContainer.isVisible = true
            binding.refreshContainer.isRefreshing = false
            activateRecyclerViewIndicators(indicatorsActive = true)
        }
        State.LOADING -> {
            binding.refreshContainer.isVisible = true
            binding.refreshContainer.isRefreshing = true
            _isEndOFListingReached = false
        }
        State.ALL_LOADED -> {
            binding.progressBarOpenListing.isVisible = false
            binding.refreshContainer.isVisible = true
            binding.refreshContainer.isRefreshing = false
            activateRecyclerViewIndicators(indicatorsActive = false)
        }
    }

    /**
     * Interface to be implemented by any class that can respond to the events of this view
     * */
    interface FilmListingViewListener {
        fun refreshContentRequested()
        fun clickOnItem(item: FilmRaw)
        fun endOfScrollReached()
    }
}
