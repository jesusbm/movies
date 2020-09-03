package com.ruskiikot.mentoria01.util

import android.content.Context
import androidx.annotation.IntegerRes

fun Context.getInteger(@IntegerRes idRes: Int) = resources.getInteger(idRes)
