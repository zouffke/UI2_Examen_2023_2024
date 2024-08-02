package be.kdg.quiz.di

import be.kdg.quiz.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
  private const val BASE_URL="http://10.0.2.2:3000/"
  // questions can be retrieved from http://10.0.2.2:3000/questions



  @Provides
  @Singleton
  fun provideRepository(): QuestionRepository {
    return MemoryQuestionRepository()
  }
}