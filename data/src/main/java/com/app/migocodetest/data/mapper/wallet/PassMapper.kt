package com.app.migocodetest.data.mapper.wallet

import com.app.migocodetest.data.dto.wallet.PassDto
import com.app.migocodetest.data.mapper.Mapper
import com.app.migocodetest.domain.entity.wallet.PassEntity
import javax.inject.Inject

class PassMapper @Inject constructor() : Mapper<PassEntity, PassDto> {
    override fun toDto(entity: PassEntity?): PassDto {
        return PassDto(
            id = entity?.id,
            duration = entity?.duration,
            type = entity?.type?.name,
            status = entity?.status?.name,
            insertionTimestamp = entity?.insertionTimestamp,
            activationTimestamp = entity?.insertionTimestamp,
            expirationTimestamp = entity?.insertionTimestamp
        )
    }

    override fun toEntity(dto: PassDto?): PassEntity {
        val type = dto?.type
        val status = dto?.status
        return PassEntity(
            id = dto?.id,
            duration = dto?.duration,
            type = if (type.isNullOrBlank()) null else PassEntity.PassType.valueOf(type),
            status = if (status.isNullOrBlank()) null else PassEntity.PassStatus.valueOf(status),
            insertionTimestamp = dto?.insertionTimestamp,
            activationTimestamp = dto?.insertionTimestamp,
            expirationTimestamp = dto?.insertionTimestamp
        )
    }
}