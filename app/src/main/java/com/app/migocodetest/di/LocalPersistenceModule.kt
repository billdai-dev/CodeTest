package com.app.migocodetest.di

import com.app.migocodetest.data.local_storage.ILocalStorage
import com.app.migocodetest.data.local_storage.LocalStorage
import com.app.migocodetest.data.local_storage.pref.IPreferenceManager
import com.app.migocodetest.data.local_storage.pref.PreferenceManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalPersistenceModule {
    @Singleton
    @Binds
    abstract fun bindPreferenceManager(preferenceManager: PreferenceManager): IPreferenceManager

    @Singleton
    @Binds
    abstract fun bindLocalStorage(localStorage: LocalStorage): ILocalStorage
}