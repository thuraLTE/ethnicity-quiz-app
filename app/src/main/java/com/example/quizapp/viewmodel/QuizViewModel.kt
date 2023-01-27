package com.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.datasource.*

class QuizViewModel : ViewModel() {

    private var _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    private var _count = MutableLiveData(1)
    val count: LiveData<Int> get() = _count

    private var _currentQuiz = MutableLiveData<Quiz>()
    val currentQuiz: LiveData<Quiz> get() = _currentQuiz

    private var _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    /*init {
        Log.d("QuizViewModel", "QuizViewModel created!")
        getNextQuiz()
    }*/

    fun setName(name: String) {
        _name.value = name
    }

    fun getNextQuiz() {
        val quizList = getQuizList()
        _currentQuiz.value = quizList[count.value!! - 1]
    }

    fun nextQuiz() {
        _count.value = (_count.value)?.inc()
        getNextQuiz()
    }

    fun checkAnswer(userAnswer: String): Boolean {
        if (userAnswer.equals(currentQuiz.value!!.correctOption, false)) {
            _score.value = (_score.value)!!.inc()
            return true
        }
        return false
    }

    /**
     *  Reset all data
     */
    fun resetData() {
        _name.value = ""
        _count.value = 1
        _score.value = 0
    }
}