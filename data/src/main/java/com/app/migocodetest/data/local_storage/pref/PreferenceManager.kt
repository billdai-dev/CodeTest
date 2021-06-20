package com.app.migocodetest.data.local_storage.pref

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceManager @Inject constructor(@ApplicationContext private val applicationContext: Context) :
    IPreferenceManager {
}

