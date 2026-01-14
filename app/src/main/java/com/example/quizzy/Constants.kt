package com.example.quizzy

object Constants {
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"
    const val TOTAL_POINTS: String = "total_points"
    const val MAX_STREAK: String = "max_streak"

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        val que1 = Question(
            1, "Which planet is known as the Red Planet?",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/OSIRIS_Mars_true_color.jpg/1200px-OSIRIS_Mars_true_color.jpg",
            "Venus", "Mars", "Jupiter", "Saturn", 2
        )
        questionsList.add(que1)

        val que2 = Question(
            2, "What is the largest mammal in the world?",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1c/Blue_Whale_underwater.jpg/1200px-Blue_Whale_underwater.jpg",
            "Elephant", "Blue Whale", "Giraffe", "Great White Shark", 2
        )
        questionsList.add(que2)

        val que3 = Question(
            3, "In which country can you find the Great Pyramid of Giza?",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/All_Gizah_Pyramids.jpg/1200px-All_Gizah_Pyramids.jpg",
            "Mexico", "Egypt", "China", "Peru", 2
        )
        questionsList.add(que3)

        val que4 = Question(
            4, "Who painted the Mona Lisa?",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/1200px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg",
            "Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet", 3
        )
        questionsList.add(que4)

        val que5 = Question(
            5, "What is the capital city of Japan?",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Skyscrapers_of_Shinjuku_2009_January.jpg/1200px-Skyscrapers_of_Shinjuku_2009_January.jpg",
            "Beijing", "Seoul", "Tokyo", "Bangkok", 3
        )
        questionsList.add(que5)

        val que6 = Question(
            6, "Which element has the chemical symbol 'O'?",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c8/Periodic_table_large.png/1200px-Periodic_table_large.png",
            "Gold", "Oxygen", "Osmium", "Iron", 2
        )
        questionsList.add(que6)

        val que7 = Question(
            7, "How many continents are there on Earth?",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/World_map_-_low_resolution.svg/1200px-World_map_-_low_resolution.svg.png",
            "5", "6", "7", "8", 3
        )
        questionsList.add(que7)

        val que8 = Question(
            8, "Which is the longest river in the world?",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/Nile_River_and_Delta_at_night.jpg/1200px-Nile_River_and_Delta_at_night.jpg",
            "Amazon", "Nile", "Yangtze", "Mississippi", 2
        )
        questionsList.add(que8)

        val que9 = Question(
            9, "What is the hardest natural substance on Earth?",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Rough_diamond.jpg/1200px-Rough_diamond.jpg",
            "Gold", "Iron", "Diamond", "Quartz", 3
        )
        questionsList.add(que9)

        val que10 = Question(
            10, "Who wrote 'Romeo and Juliet'?",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/Shakespeare.jpg/1200px-Shakespeare.jpg",
            "Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen", 2
        )
        questionsList.add(que10)

        return questionsList
    }
}
