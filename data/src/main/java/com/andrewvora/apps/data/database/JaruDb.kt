package com.andrewvora.apps.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created on 1/19/2019.
 */
@Database(entities = [
    AnswerEntity::class,
    QuestionEntity::class,
    QuestionSetEntity::class,
    GlossaryItemEntity::class,
    GlossaryEntity::class
], version = 1)
internal abstract class JaruDb : RoomDatabase() {
    abstract fun questionSetDao(): QuestionSetDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao
    abstract fun glossaryItemDao(): GlossaryItemDao
    abstract fun glossaryDao(): GlossaryDao
}