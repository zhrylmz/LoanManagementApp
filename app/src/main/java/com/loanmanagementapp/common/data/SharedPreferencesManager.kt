package com.loanmanagementapp.common.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val SHARED_PREF_NAME = "LoanManagementApp"

class SharedPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPref = context.createSharedPref(fileName = SHARED_PREF_NAME)

    fun putBoolean(key: String, value: Boolean) {
        sharedPref.edit { putBoolean(key, value) }
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun putString(key: String, value: String?) {
        sharedPref.edit { putString(key, value) }
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, "")
    }

    private fun Context.createSharedPref(fileName: String): SharedPreferences {
        val pref = runCatching {
            val masterKey = MasterKey.Builder(this)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            EncryptedSharedPreferences.create(
                this,
                fileName,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }.getOrElse {
            this.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }
        return pref
    }
}

