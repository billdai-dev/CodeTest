package com.app.migocodetest.data.dto.wallet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.migocodetest.domain.entity.wallet.PassEntity

@Entity(tableName = "passes")
data class PassDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo
    val duration: Int? = null,
    @ColumnInfo
    val type: String? = null,
    @ColumnInfo
    val status: String? = null,
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val insertionTimestamp: Long? = null,
    @ColumnInfo
    val activationTimestamp: Long? = null,
    @ColumnInfo
    val expirationTimestamp: Long? = null
)