package com.app.migocodetest.data.local_storage.database

import androidx.room.Dao
import androidx.room.Query
import com.app.migocodetest.data.dto.wallet.PassDto
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import java.util.*

@Dao
interface WalletDao {
    @Query("SELECT * FROM passes")
    fun getAllPass(): Flowable<List<PassDto>>

    @Query("INSERT INTO passes (duration,type,status,insertionTimestamp) VALUES(:duration,:type,:status,:createdAt)")
    fun addPass(
        duration: Int,
        type: String,
        status: String,
        createdAt: Long = Date().time / 1000
    ): Single<Unit>

    @Query("UPDATE passes SET status = :status," +
            " activationTimestamp = :activationTimestamp, expirationTimestamp = :expirationTimestamp WHERE id = :id")
    fun activatePass(
        id: Int, status: String, activationTimestamp: Long, expirationTimestamp: Long
    ): Single<Unit>
}