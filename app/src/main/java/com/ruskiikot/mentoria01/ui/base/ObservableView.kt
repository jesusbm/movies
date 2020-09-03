package com.ruskiikot.mentoria01.ui.base

import android.view.View

abstract class ObservableView<LISTENER> {

    abstract val rootView: View

    private val _observers: MutableSet<LISTENER> = mutableSetOf()

    fun registerObserver(observer: LISTENER) = _observers.add(observer)

    fun unregisterObserver(observer: LISTENER) = _observers.remove(observer)

    fun notifyObservers(callback: (LISTENER) -> Unit) {
        _observers.forEach {
            callback(it)
        }
    }
}
