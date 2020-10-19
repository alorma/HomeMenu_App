package com.alorma.homemenu.base

import android.content.Context
import androidx.annotation.StringRes

data class StringsProvider(
    private val context: Context
) {

    fun getString(@StringRes id: Int): String = context.resources.getString(id)

}
