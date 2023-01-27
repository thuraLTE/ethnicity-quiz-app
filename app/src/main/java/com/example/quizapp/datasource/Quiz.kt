package com.example.quizapp.datasource

const val MAX_NO_OF_QUIZ = 8

// Given Quiz Model
data class Quiz(
    val question: Int,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctOption: String,
)

// Given Quiz Lists
fun getQuizList(): List<Quiz> {
    return listOf(
        Quiz(
            imageList[6],
            answerList[2], answerList[5], answerList[7], answerList[6], answerList[6]
        ),
        Quiz(
            imageList[3],
            answerList[7], answerList[1], answerList[3], answerList[5], answerList[3]
        ),
        Quiz(
            imageList[5],
            answerList[7], answerList[2], answerList[0], answerList[5], answerList[5]
        ),
        Quiz(
            imageList[1],
            answerList[3], answerList[1], answerList[6], answerList[4], answerList[1]
        ),
        Quiz(
            imageList[0],
            answerList[0], answerList[4], answerList[1], answerList[2], answerList[0]
        ),
        Quiz(
            imageList[4],
            answerList[5], answerList[2], answerList[4], answerList[0], answerList[4]
        ),
        Quiz(
            imageList[7],
            answerList[6], answerList[3], answerList[2], answerList[7], answerList[7]
        ),
        Quiz(
            imageList[2],
            answerList[2], answerList[3], answerList[6], answerList[1], answerList[2]
        )
    )
}

/*data class QuizImage(
    val imgSourceId: Int
)

data class QuizAnswer(
    val choiceAnswer: String
)*/
