package com.example.quizzy

import android.content.Context
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvTotalXP: TextView = findViewById(R.id.tv_total_xp)
        val tvBestScore: TextView = findViewById(R.id.tv_best_score)
        val btnBack: ImageButton = findViewById(R.id.btn_back)
        val btnReset: MaterialButton = findViewById(R.id.btn_reset_stats)

        val sharedPref = getSharedPreferences("QuizzyUserProfile", Context.MODE_PRIVATE)
        
        // Load persistent data
        val totalXP = sharedPref.getInt("total_experience_points", 0)
        val bestPoints = sharedPref.getInt("personal_best_points", 0)

        tvTotalXP.text = totalXP.toString()
        tvBestScore.text = bestPoints.toString()

        btnBack.setOnClickListener {
            finish()
        }

        btnReset.setOnClickListener {
            showResetConfirmationDialog()
        }
    }

    private fun showResetConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Reset Progress")
            .setMessage("Are you sure you want to clear all your stats, XP, and streaks? This action cannot be undone.")
            .setPositiveButton("Reset") { _, _ ->
                val sharedPref = getSharedPreferences("QuizzyUserProfile", Context.MODE_PRIVATE)
                sharedPref.edit().clear().apply()
                
                // Update UI after reset
                findViewById<TextView>(R.id.tv_total_xp).text = "0"
                findViewById<TextView>(R.id.tv_best_score).text = "0"
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
