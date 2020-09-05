package com.ruskiikot.mentoria01.ui.notifications

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ruskiikot.mentoria01.R

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val textView: TextView = view.findViewById(R.id.text_notifications)
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
    }
}