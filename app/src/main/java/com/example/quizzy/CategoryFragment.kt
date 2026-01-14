package com.example.quizzy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView

class CategoryFragment : Fragment() {

    interface CategorySelectionListener {
        fun onCategorySelected(category: String)
    }

    private var listener: CategorySelectionListener? = null

    fun setCategorySelectionListener(listener: CategorySelectionListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        setupCategoryClick(view, R.id.card_geography, "Geography")
        setupCategoryClick(view, R.id.card_science, "Science")
        setupCategoryClick(view, R.id.card_history, "History")
        setupCategoryClick(view, R.id.card_art, "Art")
        setupCategoryClick(view, R.id.card_sports, "Sports")
        setupCategoryClick(view, R.id.card_movies, "Movies")
        setupCategoryClick(view, R.id.card_music, "Music")
        setupCategoryClick(view, R.id.card_literature, "Literature")
        setupCategoryClick(view, R.id.card_nature, "Nature")
        setupCategoryClick(view, R.id.card_technology, "Technology")

        return view
    }

    private fun setupCategoryClick(view: View, id: Int, categoryName: String) {
        view.findViewById<MaterialCardView>(id).setOnClickListener {
            listener?.onCategorySelected(categoryName)
        }
    }
}
