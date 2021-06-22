package com.app.migocodetest.data.dto.wallet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.migocodetest.domain.entity.wallet.PassEntity

@Entity(tableName = "passes")
data class PassDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo
    val duration: Int,
    @ColumnInfo
    val type: PassEntity.PassType,
    @ColumnInfo
    val status: PassEntity.PassStatus,
    @ColumnInfo
    val insertionTimestamp: Long,
    @ColumnInfo
    val activationTimestamp: Long? = null,
    @ColumnInfo
    val expirationTimestamp: Long? = null
)