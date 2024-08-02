/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.kdg.quiz.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.kdg.quiz.data.QuestionRepository
import be.kdg.quiz.model.Question
import be.kdg.quiz.ui.screens.QuizUiState.Active
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(private val repository: QuestionRepository) : ViewModel() {


    private val _uiState: MutableStateFlow<QuizUiState> = MutableStateFlow(QuizUiState.Loading)
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    /**
     * Call getQuestions() on init so we can display status immediately.
     */
    init {
        getQuestions()
    }


    fun getQuestions() {
        viewModelScope.launch {
            try {
                _uiState.update { Active(repository.getQuestions(), 0) }
            } catch (e: IOException) {
                Log.e(javaClass.simpleName, "Error getting data", e)
                _uiState.update { QuizUiState.Error }
            }
        }
    }

    fun setAnswer(option: Int) {
        val currentState = uiState.value
        if (currentState is Active) {
            // copy to assure new reference values are used
            // if references are unchanged, no new state is emitted!
            val questions = currentState.questions.toMutableList()
            questions[currentState.currentQuestion] =
                currentState.questions[currentState.currentQuestion].copy(answer = option)
            (_uiState as MutableStateFlow<Active>).update {
                it.copy(questions = questions)
            }
        }
    }

    fun increaseCurrentQuestion() {
        val currentState = uiState.value
        if (currentState is Active) {
            val index = currentState.currentQuestion + 1
            val questions = currentState.questions
            if (index >= questions.size) {
                finished()
                return
            }
            _uiState.update { Active(questions, index) }
        }
    }

    private fun finished() {
        val currentState = uiState.value
        if (currentState is Active) {
            val questions = currentState.questions
            var corrects = 0
            for (question in questions) {
                if (checkAnswer(question)) corrects++
            }
            _uiState.update { QuizUiState.Finished(corrects, questions.size) }
        }
    }

    private fun checkAnswer(question: Question): Boolean {
        return question.answer == question.correctAnswer
    }

}
