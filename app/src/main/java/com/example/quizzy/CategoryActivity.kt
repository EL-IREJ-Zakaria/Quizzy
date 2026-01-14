package com.example.quizzy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        setupCategoryClick(R.id.card_geography, "Geography")
        setupCategoryClick(R.id.card_science, "Science")
        setupCategoryClick(R.id.card_history, "History")
        setupCategoryClick(R.id.card_art, "Art")
        setupCategoryClick(R.id.card_sports, "Sports")
        setupCategoryClick(R.id.card_movies, "Movies")
        setupCategoryClick(R.id.card_music, "Music")
        setupCategoryClick(R.id.card_literature, "Literature")
        setupCategoryClick(R.id.card_nature, "Nature")
        setupCategoryClick(R.id.card_technology, "Technology")
    }

    private fun setupCategoryClick(id: Int, categoryName: String) {
        findViewById<MaterialCardView>(id).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.CATEGORY, categoryName)
            startActivity(intent)
        }
    }
}
