package be.kdg.quiz.data

import be.kdg.quiz.model.Question

interface QuestionRepository {
  suspend fun getQuestions(): List<Question>
}



class MemoryQuestionRepository() : QuestionRepository {
  override suspend fun getQuestions(): List<Question> {
    return listOf(
      Question(
        "1", "How do you get a constant from another javascript file?", listOf(
          "copy it",
          "import",
          "require",
          "you can only get functions from to other javascript files, not constants"
        ), 1
      ),
      Question(
        "2", "How do you make radio buttons in javascript?", listOf(
          "<select>",
          "<option>",
          "<check>",
          "<input type='radio'>"
        ), 3
      ),
      Question(
        "3", "What is NOT a good way to add text to an element?", listOf(
          "appendChild(document.createTextNode())",
          "innerHtml",
          "innerText",
          "textContent"
        ), 1
      ),
    )
  }

}