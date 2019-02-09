package com.andrewvora.apps.domain.mappers

import com.andrewvora.apps.data.dtos.GlossaryDto
import com.andrewvora.apps.data.dtos.GlossaryItemDto
import com.andrewvora.apps.domain.models.Glossary
import com.andrewvora.apps.domain.models.GlossaryItem


object GlossaryMapper {

    fun from(glossaryDto: GlossaryDto): Glossary {
        return Glossary(
            id = glossaryDto.id ?: "",
            title = glossaryDto.title ?: "",
            subtitle = glossaryDto.subtitle ?: "",
            items = glossaryDto.items?.map { from(it) } ?: listOf()
        )
    }

    fun from(itemDto: GlossaryItemDto): GlossaryItem {
        return GlossaryItem(
            itemId = itemDto.id ?: "",
            text = itemDto.text ?: "",
            transcript = itemDto.transcript ?: "",
            displayOrder = itemDto.order
        )
    }
}