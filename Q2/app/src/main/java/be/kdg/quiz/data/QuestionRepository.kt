package be.kdg.quiz.data

import be.kdg.quiz.model.Question
import be.kdg.quiz.network.QuestionApiService

interface QuestionRepository {
    suspend fun getQuestions(): List<Question>
}


class NetworkQuestionRepository(private val retrofitService: QuestionApiService) :
    QuestionRepository {
    override suspend fun getQuestions(): List<Question> {
        return retrofitService.getQuestions()
    }

}