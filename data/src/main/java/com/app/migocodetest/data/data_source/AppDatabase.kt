package com.app.migocodetest.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.migocodetest.data.data_source.pass.WalletDao
import com.app.migocodetest.data.dto.wallet.PassDto

@Database(entities = [PassDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao
}