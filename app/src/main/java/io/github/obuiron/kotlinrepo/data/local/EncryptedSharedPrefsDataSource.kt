package io.github.obuiron.kotlinrepo.data.local

import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import io.github.obuiron.kotlinrepo.core.android.delegate

class EncryptedSharedPrefsDataSource(private val sharedPrefs: EncryptedSharedPreferences) {
    companion object {
        private const val TOKEN = "token"
    }

    var token by pref(TOKEN, "")

    fun clear() = remove(TOKEN, commit = true)

    private fun <T> pref(key: String, defValue: T) = sharedPrefs.delegate(key, defValue)

    private fun remove(vararg keys: String, commit: Boolean = false) {
        sharedPrefs.edit(commit) {
            for (key in keys) {
                remove(key)
            }
        }
    }
}
