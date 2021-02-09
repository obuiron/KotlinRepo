package io.github.obuiron.kotlinrepo.core.android

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

class Preference<T>(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defValue: T
) {

    operator fun getValue(thisRef: Any, property: KProperty<*>): T = findPreference(key, defValue)

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
        putPreference(key, value)

    @Suppress("UNCHECKED_CAST")
    private fun findPreference(key: String, defValue: T): T {
        val res: Any = when (defValue) {
            is Long -> prefs.getLong(key, defValue)
            is String -> prefs.getString(key, defValue)!!
            is Int -> prefs.getInt(key, defValue)
            is Boolean -> prefs.getBoolean(key, defValue)
            is Float -> prefs.getFloat(key, defValue)
            else -> throw IllegalArgumentException("This type can't be saved into shared preferences")
        }
        return res as T
    }

    private fun putPreference(key: String, value: T) {
        prefs.edit {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                else -> throw IllegalArgumentException("This type can't be saved into shared preferences")
            }
        }
    }
}

fun <T> SharedPreferences.delegate(key: String, defValue: T) = Preference(this, key, defValue)
