package com.example.quizzy

object Constants {
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val que1 = Question(
            1, "What country does this flag belong to?",
            android.R.drawable.ic_menu_help, // Using a default icon as placeholder
            "Argentina", "Australia", "Armenia", "Austria", 1
        )
        questionsList.add(que1)

        val que2 = Question(
            2, "What country does this flag belong to?",
            android.R.drawable.ic_menu_help,
            "Brazil", "Belgium", "Belize", "Benin", 1
        )
        questionsList.add(que2)

        val que3 = Question(
            3, "What country does this flag belong to?",
            android.R.drawable.ic_menu_help,
            "Canada", "Cambodia", "Cameroon", "Chad", 1
        )
        questionsList.add(que3)

        return questionsList
    }
}
