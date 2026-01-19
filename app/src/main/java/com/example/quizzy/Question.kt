package com.example.quizzy

data class Question(
    val id: Int,
    val text: String,
    val imageUrl: String,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int,
    val category: String,
    val difficulty: String = "Medium" // Added difficulty with default
)
