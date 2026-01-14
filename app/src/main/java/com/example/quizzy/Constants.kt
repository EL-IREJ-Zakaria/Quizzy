package com.example.quizzy

object Constants {
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"
    const val CATEGORY: String = "category"

    fun getQuestions(category: String): ArrayList<Question> {
        val allQuestions = getAllQuestions()
        val filteredQuestions = ArrayList<Question>()
        for (q in allQuestions) {
            if (q.category == category) {
                filteredQuestions.add(q)
            }
        }
        return filteredQuestions
    }

    fun getAllQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        // Geography
        questionsList.add(Question(1, "Which country has the largest area?", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f3/Flag_of_Russia.svg/1200px-Flag_of_Russia.svg.png", "Canada", "China", "USA", "Russia", 3, "Geography"))
        
        // Science
        questionsList.add(Question(2, "Which planet is known as the Red Planet?", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/OSIRIS_Mars_true_color.jpg/1200px-OSIRIS_Mars_true_color.jpg", "Venus", "Mars", "Jupiter", "Saturn", 2, "Science"))
        
        // Art
        questionsList.add(Question(3, "Who painted the Mona Lisa?", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/1200px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg", "Van Gogh", "Picasso", "Da Vinci", "Monet", 3, "Art"))
        
        // History
        questionsList.add(Question(4, "In which year did WWII end?", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/The_Great_Wall_of_China_at_Jinshanling.jpg/1200px-The_Great_Wall_of_China_at_Jinshanling.jpg", "1943", "1944", "1945", "1946", 3, "History"))
        
        // Sports
        questionsList.add(Question(5, "Which country won the 2022 World Cup?", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Flag_of_Argentina.svg/1200px-Flag_of_Argentina.svg.png", "France", "Brazil", "Argentina", "Germany", 3, "Sports"))
        
        // Movies
        questionsList.add(Question(6, "Which movie features the character 'Simba'?", "https://upload.wikimedia.org/wikipedia/en/thumb/3/3d/The_Lion_King_poster.jpg/220px-The_Lion_King_poster.jpg", "Aladdin", "Frozen", "The Lion King", "Shrek", 3, "Movies"))
        
        // Music
        questionsList.add(Question(7, "Who is known as the King of Pop?", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Michael_Jackson_in_1984.jpg/1200px-Michael_Jackson_in_1984.jpg", "Prince", "Michael Jackson", "Elvis Presley", "Madonna", 2, "Music"))
        
        // Literature
        questionsList.add(Question(8, "Who wrote 'Romeo and Juliet'?", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/Shakespeare.jpg/1200px-Shakespeare.jpg", "Dickens", "Shakespeare", "Twain", "Austen", 2, "Literature"))
        
        // Nature
        questionsList.add(Question(9, "What is the largest mammal?", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1c/Blue_Whale_underwater.jpg/1200px-Blue_Whale_underwater.jpg", "Elephant", "Blue Whale", "Giraffe", "Shark", 2, "Nature"))
        
        // Technology
        questionsList.add(Question(10, "Who co-founded Apple?", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Steve_Jobs_Headshot_2010-edit2.jpg/1200px-Steve_Jobs_Headshot_2010-edit2.jpg", "Bill Gates", "Mark Zuckerberg", "Steve Jobs", "Elon Musk", 3, "Technology"))

        return questionsList
    }
}
