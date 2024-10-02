package com.example.noteapp.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.example.noteapp.data.local.model.NoteSortOrder
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class CustomSharedPreferences {

    companion object {
        private const val SET_ORDER = "setOrder"
        private const val IS_ASCENDING = "isAscending"
        private const val IS_GRID_LAYOUT = "isGridLayout"

        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: CustomSharedPreferences? = null
        private val lock = Any()

        @OptIn(InternalCoroutinesApi::class)
        operator fun invoke(context: Context): CustomSharedPreferences =
            instance ?: synchronized(lock) {
                instance ?: makeCustomSharedPreferences(context).also {
                    instance = it
                }
            }

        private fun makeCustomSharedPreferences(context: Context): CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }

    fun saveOrderType(setOrder: NoteSortOrder) {
        sharedPreferences?.edit(commit = true) {
            putInt(SET_ORDER, setOrder.ordinal)
        }
    }

    fun saveIsGrid(isGridLayout: Boolean) {
        sharedPreferences?.edit(commit = true) {
            putBoolean(IS_GRID_LAYOUT, isGridLayout)
        }
    }

    fun saveIsAscending(isAscending: Boolean) {
        sharedPreferences?.edit(commit = true) {
            putBoolean(IS_ASCENDING, isAscending)
        }
    }

    fun getOrderType() = sharedPreferences?.getInt(SET_ORDER, 0)
    fun getLayoutType() = sharedPreferences?.getBoolean(IS_GRID_LAYOUT, false)
    fun getIsAscending() = sharedPreferences?.getBoolean(IS_ASCENDING, false)
}