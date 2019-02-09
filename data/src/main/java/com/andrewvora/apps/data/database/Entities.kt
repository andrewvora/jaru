package com.andrewvora.apps.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


internal const val TABLE_ANSWER = "answer"
@Entity(
    tableName = TABLE_ANSWER,
    foreignKeys = [ForeignKey(
        entity = QuestionEntity::class,
        onDelete = ForeignKey.CASCADE,
        parentColumns = [COLUMN_QUESTION_ID],
        childColumns = [COLUMN_QUESTION_ID])])
internal data class AnswerEntity(
    @PrimaryKey
    @ColumnInfo(name = "answer_id")
    val answerId: String,

    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = COLUMN_QUESTION_ID) val questionId: String?
)

internal const val TABLE_QUESTION = "question"
internal const val COLUMN_QUESTION_ID = "question_id"
@Entity(tableName = TABLE_QUESTION,
    foreignKeys = [ForeignKey(
        entity = QuestionSetEntity::class,
        onDelete = ForeignKey.CASCADE,
        parentColumns = [COLUMN_QUESTION_SET_ID],
        childColumns = [COLUMN_QUESTION_SET_ID])])
internal data class QuestionEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_QUESTION_ID)
    val questionId: String,

    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "transcript") val transcript: String?,
    @ColumnInfo(name = "answer_id") val answerId: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = COLUMN_QUESTION_SET_ID) val setId: String?
)

internal const val TABLE_QUESTION_SET = "question_set"
internal const val COLUMN_QUESTION_SET_ID = "question_set_id"
@Entity(tableName = TABLE_QUESTION_SET)
internal data class QuestionSetEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_QUESTION_SET_ID)
    val setId: String,

    @ColumnInfo(name = "difficulty") val difficulty: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?
)

internal const val TABLE_GLOSSARY_ITEM = "glossary_item"
@Entity(tableName = TABLE_GLOSSARY_ITEM,
    foreignKeys = [ForeignKey(
        entity = GlossaryEntity::class,
        onDelete = ForeignKey.CASCADE,
        parentColumns = [COLUMN_GLOSSARY_ID],
        childColumns = [COLUMN_GLOSSARY_ID])])
internal data class GlossaryItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "glossary_item_id")
    val itemId: String,

    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "transcript") val transcript: String?,
    @ColumnInfo(name = "displayOrder") val displayOrder: Int,
    @ColumnInfo(name = COLUMN_GLOSSARY_ID) val glossaryId: String?
)

internal const val TABLE_GLOSSARY = "glossary"
internal const val COLUMN_GLOSSARY_ID = "glossary_id"
@Entity(tableName = TABLE_GLOSSARY)
internal data class GlossaryEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_GLOSSARY_ID)
    val glossaryId: String,

    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "subtitle") val subtitle: String?
)
