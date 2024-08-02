package be.kdg.quiz.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import be.kdg.quiz.R
import be.kdg.quiz.model.Question
import be.kdg.quiz.ui.screens.QuizUiState.Active
import be.kdg.quiz.ui.theme.QuizTheme

@Composable
fun QuestionScreen(modifier: Modifier = Modifier, viewModel: QuizViewModel = hiltViewModel()) {
    val quizUiState by viewModel.uiState.collectAsState()

    when (quizUiState) {
        is QuizUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is Active -> {
            val active = quizUiState as Active
            MultipleChoiceScreen(
                active.questions[active.currentQuestion],
                onNext = viewModel::increaseCurrentQuestion,
                onAnswer = viewModel::setAnswer,
                modifier = modifier.fillMaxSize()
            )
        }

        is QuizUiState.Finished -> {
            val active = quizUiState as QuizUiState.Finished
            FinishedScreen(
                amountCorrect = active.correct,
                questions = active.questions,
                onRetry = {
                    viewModel.getQuestions()
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        is QuizUiState.Error -> ErrorScreen(modifier = modifier.fillMaxWidth())
        // TODO Q1b add result screen
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}


@Composable
fun MultipleChoiceScreen(
    question: Question,
    onNext: () -> Unit,
    onAnswer: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        //modifier = Modifier.padding(PaddingValues(dimensionResource(R.dimen.padding_medium),dimensionResource(R.dimen.padding_large))),
        modifier = modifier
            .padding(
                PaddingValues(
                    dimensionResource(R.dimen.padding_medium),
                    dimensionResource(R.dimen.padding_large)
                )
            ),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(text = question.text, style = MaterialTheme.typography.headlineSmall)
            question.options.forEachIndexed { index, it ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(('A' + index).toString())
                    RadioButton(selected = index == question.answer, onClick = { onAnswer(index) })
                    Text(text = it)
                }

            }
        }
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
        ) {
            //    Button(
            //      onClick = { },
            //      modifier = Modifier.align(Alignment.End),
            //      contentPadding = PaddingValues(0.dp)
            //   ) {
            // Button(onClick = { }, modifier = Modifier.fillMaxWidth().padding()){
            Text(text = stringResource(R.string.next))
        }
    }
}

@Composable
fun FinishedScreen(
    amountCorrect: Int,
    questions: Int,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                PaddingValues(
                    dimensionResource(R.dimen.padding_medium),
                    dimensionResource(R.dimen.padding_large)
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${stringResource(R.string.you_scored)} $amountCorrect ${stringResource(id = R.string.points_out_of)} $questions")
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.try_again))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewQuizScreen() {
    QuizTheme {
        MultipleChoiceScreen(
            Question(
                "1",
                "What is the capital of Belgium?",
                listOf("Brussels", "Antwerp", "Ghent", "Bruges"),
                0
            ),
            onNext = { },
            onAnswer = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

