package com.andrewvora.apps.data.mappers

import com.andrewvora.apps.data.database.GlossaryItemEntity
import com.andrewvora.apps.data.dtos.GlossaryItemDto

internal object GlossaryItemMapper {

    fun entityToDto(entity: GlossaryItemEntity?): GlossaryItemDto? {
        return entity?.let {
            GlossaryItemDto(
                id = it.itemId,
                text = it.text,
                transcript = it.transcript,
                order = it.displayOrder
            )
        }
    }

    fun dtoToEntity(glossaryId: String, dto: GlossaryItemDto): GlossaryItemEntity? {
        return dto.takeIf {
            !dto.id.isNullOrBlank()
        }?.let {
            GlossaryItemEntity(
                itemId = it.id!!,
                text = it.text,
                transcript = it.transcript,
                displayOrder = it.order,
                glossaryId = glossaryId
            )
        }
    }
}