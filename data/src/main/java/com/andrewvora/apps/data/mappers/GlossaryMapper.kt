package com.andrewvora.apps.data.mappers

import com.andrewvora.apps.data.database.GlossaryEntity
import com.andrewvora.apps.data.dtos.GlossaryDto


internal object GlossaryMapper {

    fun entityToDto(entity: GlossaryEntity?): GlossaryDto? {
        return entity?.let {
            GlossaryDto(
                id = it.glossaryId,
                title = it.title,
                subtitle = it.subtitle,
                items = null
            )
        }
    }

    fun dtoToEntity(dto: GlossaryDto): GlossaryEntity? {
        return dto.takeIf {
            !dto.id.isNullOrBlank()
        }?.let {
            GlossaryEntity(
                glossaryId = it.id!!,
                title = it.title,
                subtitle = it.subtitle
            )
        }
    }
}