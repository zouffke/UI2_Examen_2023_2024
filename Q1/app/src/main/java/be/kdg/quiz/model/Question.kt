package be.kdg.quiz.model

import java.util.Objects

// annotate for serialization
data class Question(
  val id: String,
  val text: String,
  val options: List<String>,
  val correctAnswer: Int,
  var answer: Int = -1
) {


  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Question
    return (id == other.id && answer == other.answer)
  }

  override fun hashCode(): Int {
    return Objects.hash(id,answer)
  }

}
