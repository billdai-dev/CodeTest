package com.app.migocodetest.data.local_storage

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalStorage @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : ILocalStorage {

}

