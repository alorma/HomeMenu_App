package com.alorma.homemenu.base

import android.content.Context
import androidx.annotation.StringRes

data class NativeStringsProvider(
    private val context: Context
): StringsProvider {

    override fun getString(@StringRes id: Int): String = context.resources.getString(id)

}
