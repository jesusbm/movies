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
import com.ruskiikot.mentoria01.model.network.FilmRaw
import com.ruskiikot.mentoria01.repository.FilmRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FilmListingFragment : Fragment(), FilmListingView.FilmListingViewListener {

    private val TAG: String = FilmListingFragment::class.java.simpleName

    //@Inject
    //lateinit var api: OmdbApi

    @Inject
    lateinit var repository: FilmRepository

    @Inject
    lateinit var filmListingView: FilmListingView

    private lateinit var homeViewModel: HomeViewModel

    private val INITIAL_PAGE = 1
    private var currentPage = INITIAL_PAGE
    private var isLoading = false

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
            val dataList = repository.getFilmListing()
            filmListingView.appendFilmsToListing(dataList)
            Log.d(TAG, "${dataList}")
        }
    }

    override fun refreshContentRequested() {
        lifecycle.coroutineScope.launch {
            currentPage = INITIAL_PAGE
            val dataList = repository.getFilmListing(page = currentPage)
            filmListingView.setFilmsToListing(dataList)
        }
    }

    override fun clickOnItem(item: FilmRaw) {
        lifecycle.coroutineScope.launch {
            item.imdbID?.let {
                val movieDetails = repository.getFilmDetail(it)
                val action = FilmListingFragmentDirections.actionNavigationHomeToFilmInfoDialog(movieDetails)
                findNavController().navigate(action)
            }
        }
    }

    override fun endOfListingReached() {
        if (!isLoading) {
            isLoading = true
            lifecycle.coroutineScope.launch {
                Log.d(TAG, "LOADING MORE ITEMS")
                currentPage += 1
                val dataList = repository.getFilmListing(page = currentPage)
                filmListingView.appendFilmsToListing(dataList)
                isLoading = false
            }
        }
    }
}
