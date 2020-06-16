package com.mj.movieexample.util

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Singleton


@Singleton
class SharedManager private constructor(val application: Application) {

    private val fileName="secret_shared_prefs";

    companion object{
        private lateinit var Instance: SharedPreferences;
        fun getInstance(): SharedPreferences {
            return Instance;
        }

    }

    fun getSecurePrefrenceEditor(): SharedPreferences.Editor? {
        var masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            fileName,
            masterKeyAlias,
            application,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // use the shared preferences and editor as you normally would
        var editor = sharedPreferences.edit()
        return editor
    }


}