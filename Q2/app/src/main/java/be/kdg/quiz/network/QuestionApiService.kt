package be.kdg.quiz.network

import be.kdg.quiz.model.Question
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "http://10.0.2.2:3000/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface QuestionApiService {
    @GET("questions")
    suspend fun getQuestions(): List<Question>
}

object QuestionApi {
    val retrofitService: QuestionApiService by lazy {
        retrofit.create(QuestionApiService::class.java)
    }
}