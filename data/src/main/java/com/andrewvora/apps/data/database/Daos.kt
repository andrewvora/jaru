package com.andrewvora.apps.data.database

import androidx.room.*

@Dao
internal interface QuestionSetDao {
    @Query("SELECT * FROM $TABLE_QUESTION_SET")
    fun getQuestionSets(): List<QuestionSetEntity>

    @Query("SELECT * FROM $TABLE_QUESTION_SET WHERE $COLUMN_QUESTION_SET_ID = :setId")
    fun get(setId: String): QuestionSetEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg set: QuestionSetEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg set: QuestionSetEntity)

    @Query("DELETE FROM $TABLE_QUESTION_SET")
    fun deleteAll()
}

@Dao
internal interface QuestionDao {
    @Query("SELECT * FROM $TABLE_QUESTION WHERE $COLUMN_QUESTION_SET_ID = :setId")
    fun getQuestions(setId: String): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg set: QuestionEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg set: QuestionEntity)

    @Query("DELETE FROM $TABLE_QUESTION")
    fun deleteAll()
}

@Dao
internal interface AnswerDao {
    @Query("SELECT * FROM $TABLE_ANSWER WHERE $COLUMN_QUESTION_ID  = :questionId")
    fun getAnswers(questionId: String): List<AnswerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg set: AnswerEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg set: AnswerEntity)

    @Query("DELETE FROM $TABLE_ANSWER")
    fun deleteAll()
}

@Dao
internal interface GlossaryDao {
    @Query("SELECT * FROM $TABLE_GLOSSARY")
    fun getGlossary(): List<GlossaryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg glossary: GlossaryEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg glossary: GlossaryEntity)

    @Query("DELETE FROM $TABLE_GLOSSARY")
    fun deleteAll()
}

@Dao
internal interface GlossaryItemDao {
    @Query("SELECT * FROM $TABLE_GLOSSARY_ITEM WHERE $COLUMN_GLOSSARY_ID = :glossaryId")
    fun getGlossaryItems(glossaryId: String): List<GlossaryItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg item: GlossaryItemEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg item: GlossaryItemEntity)

    @Query("DELETE FROM $TABLE_GLOSSARY_ITEM")
    fun deleteAll()
}