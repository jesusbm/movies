package com.ruskiikot.mentoria01.ui.home.filminfodialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.ruskiikot.mentoria01.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilmInfoDialog : DialogFragment() {

    @Inject
    lateinit var filmInfoView: FilmInfoDialogView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return filmInfoView.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //dialog configuration
        isCancelable = true

        //configuration of window content
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.background_dialog)

        //binding of content
        arguments?.let {
            FilmInfoDialogArgs.fromBundle(it).itemValue.also { filmInfoView.bindData(it) }
        }
    }
}
