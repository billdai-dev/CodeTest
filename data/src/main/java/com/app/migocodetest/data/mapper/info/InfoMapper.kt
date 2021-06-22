package com.app.migocodetest.data.mapper.info

import com.app.migocodetest.data.dto.info.InfoDto
import com.app.migocodetest.data.mapper.Mapper
import com.app.migocodetest.domain.entity.info.InfoEntity
import javax.inject.Inject

class InfoMapper @Inject constructor() : Mapper<InfoEntity, InfoDto> {
    override fun toDto(entity: InfoEntity?): InfoDto {
        return InfoDto(status = entity?.status, message = entity?.message)
    }

    override fun toEntity(dto: InfoDto?): InfoEntity {
        return InfoEntity(status = dto?.status, message = dto?.message)
    }
}