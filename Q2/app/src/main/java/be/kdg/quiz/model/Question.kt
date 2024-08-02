package be.kdg.quiz.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.util.Objects


@Serializable
data class Question(
    val id: String,
    val text: String,
    val options: List<String>,
    @SerialName("correct-answer")
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
        return Objects.hash(id, answer)
    }

}
