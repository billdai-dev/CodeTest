package com.app.migocodetest.data.local_storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.migocodetest.data.dto.info.InfoDto
import com.app.migocodetest.data.dto.wallet.PassDto
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface WalletDao {
    @Query("SELECT * FROM passes")
    fun getAllPass(): Flowable<List<PassDto>>

    @Insert
    fun addPass(pass: PassDto): Single<Long>

    @Update
    fun updateUsers(activatedPass: PassDto): Single<Unit>
}