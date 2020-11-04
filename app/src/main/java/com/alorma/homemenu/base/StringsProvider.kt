package com.alorma.homemenu.base

import androidx.annotation.StringRes

interface StringsProvider {
    fun getString(@StringRes id: Int): String
}