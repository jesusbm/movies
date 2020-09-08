package com.ruskiikot.mentoria01.util

import android.view.View

var View.isVisible: Boolean
    get() = this.visibility == View.VISIBLE
    set(value) = this.run { visibility = if (value) View.VISIBLE else View.GONE }
