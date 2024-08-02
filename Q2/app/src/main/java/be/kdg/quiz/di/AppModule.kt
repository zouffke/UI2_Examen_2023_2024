package be.kdg.quiz.di

import be.kdg.quiz.data.*
import be.kdg.quiz.network.QuestionApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "http://10.0.2.2:3000/"
    // questions can be retrieved from http://10.0.2.2:3000/questions


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): QuestionApiService {
        return retrofit.create(QuestionApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(retrofitService: QuestionApiService): QuestionRepository {
        return NetworkQuestionRepository(retrofitService)
    }
}