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
import com.ruskiikot.mentoria01.interactor.FilmDetailsInteractor
import com.ruskiikot.mentoria01.interactor.FilmListingInteractor
import com.ruskiikot.mentoria01.model.network.FilmRaw
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FilmListingFragment : Fragment(), FilmListingView.FilmListingViewListener {

    private val TAG: String = FilmListingFragment::class.java.simpleName

    @Inject
    lateinit var listingInteractor: FilmListingInteractor

    @Inject
    lateinit var detailsInteractor: FilmDetailsInteractor

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
            filmListingView.run {
                appendItems {
                    listingInteractor.getFilmListing(page = currentPage)
                }
            }
        }
    }

    override fun refreshContentRequested() {
        lifecycle.coroutineScope.launch {
            currentPage = INITIAL_PAGE
            filmListingView.run {
                setItems {
                    listingInteractor.getFilmListing(page = currentPage)
                }
            }
        }
    }

    override fun clickOnItem(item: FilmRaw) {
        lifecycle.coroutineScope.launch {
            item.imdbID?.let {
                val movieDetails = detailsInteractor.getFilmDetails(it)
                val action = FilmListingFragmentDirections.actionNavigationHomeToFilmInfoDialog(movieDetails)
                findNavController().navigate(action)
            }
        }
    }

    override fun endOfScrollReached() {
        if (!isLoading) {
            isLoading = true
            lifecycle.coroutineScope.launch {
                Log.d(TAG, "LOADING MORE ITEMS")
                currentPage += 1
                val dataList = listingInteractor.getFilmListing(page = currentPage)
                if (dataList.isNotEmpty()) {
                    filmListingView.appendItems { dataList }
                } else {
                    filmListingView.isEndOFListingReached = true
                }
                isLoading = false
            }
        }
    }
}
