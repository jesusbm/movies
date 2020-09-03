package com.ruskiikot.mentoria01.ui.home.filmlisting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ruskiikot.mentoria01.network.OmdbApi
import com.ruskiikot.mentoria01.network.model.FilmRaw
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FilmListingFragment : Fragment(), FilmListingView.FilmListingViewListener {

    private val TAG: String = "FilmListingFragment"

    @Inject
    lateinit var api: OmdbApi

    @Inject
    lateinit var filmListingView: FilmListingView

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        filmListingView.run {
            registerObserver(this@FilmListingFragment)
            rootView
        }

    override fun onStart() {
        super.onStart()
        val x = lifecycle.coroutineScope
        val y = lifecycleScope
        Log.d(TAG, "${x}, ${y}")
        lifecycleScope.launchWhenStarted {
            val dataList = api.filmListing()
            filmListingView.appendFilmsToListing(dataList.Search)
            Log.d(TAG, "${dataList}")
        }
    }

    override fun refreshContentRequested() {
        lifecycle.coroutineScope.launch {
            val dataList = loadItems(currentPage)
            filmListingView.appendFilmsToListing(dataList)
        }
    }

    override fun clickOnItem(item: FilmRaw) {
        lifecycle.coroutineScope.launch {
            item.imdbID?.let {
                val movieDetails = api.detailsMovie(it)
                val action = FilmListingFragmentDirections.actionNavigationHomeToFilmInfoDialog(movieDetails)
                findNavController().navigate(action)
            }
        }
    }

    var isLoading = false
    var currentPage = 1
    override fun endOfListingReached() {
        if (!isLoading) {
            isLoading = true
            lifecycle.coroutineScope.launch {
                Log.d(TAG, "LOADING MORE ITEMS")
                currentPage += 1
                val dataList = loadItems(currentPage)
                filmListingView.appendFilmsToListing(dataList)
                isLoading = false
            }
        }
    }

    private suspend fun loadItems(fromPage: Int): List<FilmRaw> {
        return withContext(Dispatchers.IO) {
            api.filmListing(page = fromPage).Search
        }
    }
}
