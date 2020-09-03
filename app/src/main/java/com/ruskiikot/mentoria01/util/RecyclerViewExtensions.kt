package com.ruskiikot.mentoria01.util

import androidx.recyclerview.widget.RecyclerView

inline fun <reified T : RecyclerView.Adapter<*>> RecyclerView.getTypedAdapter(): T? {
    return if (adapter is T) {
        adapter as T
    } else {
        null
    }
}

inline fun <reified T : RecyclerView.LayoutManager> RecyclerView.getTypedLayoutManager(): T? {
    return if (layoutManager is T) {
        layoutManager as T
    } else {
        null
    }
}