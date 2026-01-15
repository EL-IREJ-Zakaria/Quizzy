package com.example.quizzy

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
import java.util.concurrent.TimeUnit

class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        val tvScore: TextView = view.findViewById(R.id.tv_score)
        val btnFinish: Button = view.findViewById(R.id.btn_finish)
        val konfettiView: KonfettiView = view.findViewById(R.id.konfettiView)

        val totalQuestions = arguments?.getInt(Constants.TOTAL_QUESTIONS, 0) ?: 0
        val correctAnswers = arguments?.getInt(Constants.CORRECT_ANSWERS, 0) ?: 0

        tvScore.text = "Your Score is $correctAnswers out of $totalQuestions"

        btnFinish.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        // Trigger celebration animation
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

        return view
    }
}
