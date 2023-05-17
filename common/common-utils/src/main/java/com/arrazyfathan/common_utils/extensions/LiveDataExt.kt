package com.arrazyfathan.common_utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */


fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            observer.onChanged(value)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.singleShotObserve(owner: LifecycleOwner, onEventUnhandledContent: (T) -> Unit) {
    observe(owner, object : Observer<T> {
        private var hasReceivedEvent = false

        override fun onChanged(event: T) {
            if (!hasReceivedEvent) {
                hasReceivedEvent = true
                onEventUnhandledContent(event)
            }
        }
    })
}