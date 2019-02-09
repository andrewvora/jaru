package com.andrewvora.apps.data

import android.app.Application
import androidx.room.Room
import com.andrewvora.apps.data.apis.JaruApi
import com.andrewvora.apps.data.database.JaruDb
import com.andrewvora.apps.data.mappers.*
import com.andrewvora.apps.data.sources.LearningSetRoomSource
import com.andrewvora.apps.data.sources.LearningSetNetworkSource
import retrofit2.Retrofit

private const val DB_NAME = "jaru"

/**
 * Make the creation of the database lazy backed by a singleton
 * This module should have no knowledge of the [Application]
 * implementation, so it's all delayed to client modules.
 */
internal object DatabaseProvider {
    private lateinit var app: Application
    private val database: JaruDb by lazy {
        Room.databaseBuilder(app, JaruDb::class.java, DB_NAME)
            .build()
    }

    fun getDatabase(app: Application): JaruDb {
        this.app = app
        return database
    }
}

/**
 * Used to instantiate the [DualSourceQuestionSetRepository] as necessary.
 * This is to prevent unnecessarily instantiating a class
 * where this module is a dependency
 */
class RepositoryProvider(
    private val app: Application,
    private val retrofit: Retrofit
) {
    private val db: JaruDb by lazy {
        DatabaseProvider.getDatabase(app)
    }

    fun provideQuestionSetRepository(): JaruRepository {
        val localSource = LearningSetRoomSource(
            jaruDb = db,
            questionMapper = QuestionMapper,
            questionSetMapper = QuestionSetMapper,
            answerMapper = AnswerMapper,
            glossaryMapper = GlossaryMapper,
            glossaryItemMapper = GlossaryItemMapper
        )

        val api = retrofit.create(JaruApi::class.java)
        val remoteSource = LearningSetNetworkSource(jaruApi = api)

        return DualSourceQuestionSetRepository(
            localSource = localSource,
            remoteSource = remoteSource
        )
    }
}