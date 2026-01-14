package com.example.quizzy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            // On lance maintenant l'activité des catégories au lieu de MainActivity directement
            startActivity(Intent(this, CategoryActivity::class.java))
            finish()
        }, 2000)
    }
}
