package com.andrewvora.apps.jaru.di

import android.app.Application
import com.andrewvora.apps.data.JaruRepository
import com.andrewvora.apps.data.RepositoryProvider
import com.andrewvora.apps.domain.CoroutineContextProvider
import com.andrewvora.apps.jaru.config.AppConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 * Created on 1/27/2019.
 */
@Module
class AppModule {

    @Provides
    fun provideAppConfig(): AppConfig {
        return AppConfig
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        appConfig: AppConfig,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(appConfig.apiUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Reusable
    fun provideRepositoryProvider(
        app: Application,
        retrofit: Retrofit
    ): RepositoryProvider {
        return RepositoryProvider(app = app, retrofit = retrofit)
    }

    @Provides
    @Reusable
    fun provideQuestionSetRepo(
        repositoryProvider: RepositoryProvider
    ): JaruRepository {
        return repositoryProvider.provideQuestionSetRepository()
    }

    @Provides
    @Reusable
    fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return object: CoroutineContextProvider {
            override fun computationContext(): CoroutineContext {
                return Dispatchers.IO
            }

            override fun uiContext(): CoroutineContext {
                return Dispatchers.Main
            }
        }
    }
}