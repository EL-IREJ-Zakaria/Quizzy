package com.example.quizzy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        val tvScore: TextView = view.findViewById(R.id.tv_score)
        val tvHighScore: TextView = view.findViewById(R.id.tv_high_score)
        val tvCongrats: TextView = view.findViewById(R.id.tv_congrats)
        val tvStreak: TextView = view.findViewById(R.id.tv_streak)
        val tvRank: TextView = view.findViewById(R.id.tv_rank)
        val btnFinish: Button = view.findViewById(R.id.btn_finish)
        val btnShare: Button = view.findViewById(R.id.btn_share)
        val konfettiView: KonfettiView = view.findViewById(R.id.konfettiView)

        val totalQuestions = arguments?.getInt(Constants.TOTAL_QUESTIONS, 0) ?: 0
        val correctAnswers = arguments?.getInt(Constants.CORRECT_ANSWERS, 0) ?: 0
        val totalScore = arguments?.getInt(Constants.TOTAL_SCORE, 0) ?: 0

        // Persistent stats storage
        val sharedPref = requireActivity().getSharedPreferences("QuizzyUserProfile", Context.MODE_PRIVATE)
        
        // 1. Point-based High Score logic
        val bestPoints = sharedPref.getInt("best_points", 0)
        if (totalScore > bestPoints) {
            sharedPref.edit().putInt("best_points", totalScore).apply()
            tvHighScore.text = "New Best: $totalScore pts"
            tvCongrats.text = "FANTASTIC! New Personal Best!"
        } else {
            tvHighScore.text = "Personal Best: $bestPoints pts"
            tvCongrats.text = when {
                correctAnswers == totalQuestions -> "PERFECT SCORE!"
                correctAnswers >= totalQuestions * 0.8 -> "Excellent performance!"
                correctAnswers >= totalQuestions / 2 -> "Great Job!"
                else -> "Don't give up! Try again!"
            }
        }

        // 2. Progression: Total XP and Rank
        val currentTotalXP = sharedPref.getInt("total_xp", 0) + totalScore
        sharedPref.edit().putInt("total_xp", currentTotalXP).apply()
        
        val rank = when {
            currentTotalXP >= 5000 -> "Grandmaster"
            currentTotalXP >= 2000 -> "Scholar"
            currentTotalXP >= 1000 -> "Veteran"
            currentTotalXP >= 300 -> "Explorer"
            else -> "Novice"
        }
        tvRank.text = rank

        // 3. Daily Streak Logic
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val today = sdf.format(Date())
        val lastPlayed = sharedPref.getString("last_play_day", "")
        var streak = sharedPref.getInt("user_streak", 0)

        if (lastPlayed != today) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val yesterday = sdf.format(calendar.time)
            
            if (lastPlayed == yesterday) {
                streak++
            } else {
                streak = 1 // Streak broken or first time
            }
            sharedPref.edit().putString("last_play_day", today).putInt("user_streak", streak).apply()
        }
        tvStreak.text = "$streak Day${if (streak > 1) "s" else ""}"

        tvScore.text = "Score: $correctAnswers/$totalQuestions ($totalScore pts)"

        btnFinish.setOnClickListener {
            val intent = Intent(requireContext(), CategoryActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                val message = "I'm a $rank in Quizzy! I just scored $totalScore points. Beat my $streak day streak! #Quizzy"
                putExtra(Intent.EXTRA_TEXT, message)
            }
            startActivity(Intent.createChooser(shareIntent, "Share Achievement"))
        }

        // Celebration animation
        if (correctAnswers >= totalQuestions / 2) {
            val party = Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                position = Position.Relative(0.5, 0.3),
                emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100)
            )
            konfettiView.start(party)
        }

        return view
    }
}
