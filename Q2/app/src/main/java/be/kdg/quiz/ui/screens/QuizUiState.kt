package be.kdg.quiz.ui.screens

import be.kdg.quiz.model.Question

sealed interface QuizUiState{
  data class Active(val questions: List<Question>, var currentQuestion :Int = 0): QuizUiState
  data object Error : QuizUiState
  data object Loading : QuizUiState
  data class Finished(val correct: Int, val questions: Int): QuizUiState
}