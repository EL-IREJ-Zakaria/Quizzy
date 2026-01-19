package com.example.quizzy

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mTotalScore: Int = 0
    private var mCategory: String? = null
    
    // Timer Variables
    private var mCountDownTimer: CountDownTimer? = null
    private val mTimerDuration: Long = 15000 // 15 seconds
    private var mSecondsRemaining: Int = 15
    
    private var progressBar: ProgressBar? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null
    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var btnSubmit: Button? = null
    private var btnHint: Button? = null
    private var tvTimer: TextView? = null
    private var timerProgress: ProgressBar? = null
    private var tvScoreDisplay: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCategory = intent.getStringExtra(Constants.CATEGORY)

        progressBar = findViewById(R.id.progressBar)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)
        btnHint = findViewById(R.id.btn_hint)
        tvTimer = findViewById(R.id.tv_timer)
        timerProgress = findViewById(R.id.timer_progress)
        tvScoreDisplay = findViewById(R.id.tv_title) // Using title as a placeholder for score if needed, or I'll just keep it

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
        btnHint?.setOnClickListener(this)

        mQuestionsList = Constants.getQuestions(mCategory ?: "Geography")

        setQuestion()
    }

    private fun startTimer() {
        mCountDownTimer?.cancel()
        mCountDownTimer = object : CountDownTimer(mTimerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mSecondsRemaining = (millisUntilFinished / 1000).toInt()
                tvTimer?.text = mSecondsRemaining.toString()
                timerProgress?.progress = (mSecondsRemaining * 100 / (mTimerDuration / 1000)).toInt()
            }

            override fun onFinish() {
                tvTimer?.text = "0"
                mSecondsRemaining = 0
                timerProgress?.progress = 0
                
                showCorrectAnswerOnTimeout()
                
                Handler(Looper.getMainLooper()).postDelayed({
                    moveToNextQuestion()
                }, 2000)
            }
        }.start()
    }

    private fun showCorrectAnswerOnTimeout() {
        val question = mQuestionsList?.get(mCurrentPosition - 1)
        if (mSelectedOptionPosition > 0) {
            if (question!!.correctAnswer != mSelectedOptionPosition) {
                answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            } else {
                mCorrectAnswers++
                mTotalScore += calculatePoints(true, question.difficulty, (mTimerDuration/1000).toInt() - mSecondsRemaining, (mTimerDuration/1000).toInt())
            }
        }
        answerView(question!!.correctAnswer, R.drawable.correct_option_border_bg)
        setClickableState(false)
    }

    private fun moveToNextQuestion() {
        mCurrentPosition++
        mSelectedOptionPosition = 0
        setClickableState(true)
        
        if (mCurrentPosition <= mQuestionsList!!.size) {
            setQuestion()
        } else {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList?.size)
            intent.putExtra(Constants.TOTAL_SCORE, mTotalScore)
            startActivity(intent)
            finish()
        }
    }

    private fun setClickableState(enabled: Boolean) {
        tvOptionOne?.isEnabled = enabled
        tvOptionTwo?.isEnabled = enabled
        tvOptionThree?.isEnabled = enabled
        tvOptionFour?.isEnabled = enabled
        btnSubmit?.isEnabled = enabled
        btnHint?.isEnabled = enabled
    }

    private fun setQuestion() {
        val question: Question = mQuestionsList!![mCurrentPosition - 1]

        defaultOptionsView()
        resetOptionsVisibility()
        btnHint?.isEnabled = true

        if (mCurrentPosition == mQuestionsList!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "SUBMIT"
        }

        progressBar?.max = mQuestionsList!!.size
        progressBar?.progress = mCurrentPosition
        
        tvTitle?.text = "${question.category} - ${question.difficulty}"

        tvQuestion?.text = question.text
        
        ivImage?.let {
            Glide.with(this)
                .load(question.imageUrl)
                .placeholder(android.R.drawable.ic_menu_help)
                .error(android.R.drawable.ic_menu_report_image)
                .into(it)
        }
        
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour
        
        startTimer()
    }

    private fun resetOptionsVisibility() {
        tvOptionOne?.visibility = View.VISIBLE
        tvOptionTwo?.visibility = View.VISIBLE
        tvOptionThree?.visibility = View.VISIBLE
        tvOptionFour?.visibility = View.VISIBLE
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tvOptionOne?.let { options.add(0, it) }
        tvOptionTwo?.let { options.add(1, it) }
        tvOptionThree?.let { options.add(2, it) }
        tvOptionFour?.let { options.add(3, it) }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> tvOptionOne?.let { selectedOptionView(it, 1) }
            R.id.tv_option_two -> tvOptionTwo?.let { selectedOptionView(it, 2) }
            R.id.tv_option_three -> tvOptionThree?.let { selectedOptionView(it, 3) }
            R.id.tv_option_four -> tvOptionFour?.let { selectedOptionView(it, 4) }
            R.id.btn_hint -> useHint()
            R.id.btn_submit -> handleSubmission()
        }
    }

    private fun useHint() {
        val question = mQuestionsList!![mCurrentPosition - 1]
        val options = mutableListOf(1, 2, 3, 4)
        options.remove(question.correctAnswer)
        options.shuffle()
        
        for (i in 0..1) {
            when (options[i]) {
                1 -> tvOptionOne?.visibility = View.INVISIBLE
                2 -> tvOptionTwo?.visibility = View.INVISIBLE
                3 -> tvOptionThree?.visibility = View.INVISIBLE
                4 -> tvOptionFour?.visibility = View.INVISIBLE
            }
        }
        btnHint?.isEnabled = false
        // Penalize hint usage by capping max score or just reduce current question potential points
    }

    private fun handleSubmission() {
        if (mSelectedOptionPosition == -1) {
             moveToNextQuestion()
             return
        }

        if (mSelectedOptionPosition == 0) {
            // No option selected, but clicking submit? Maybe show a toast or just do nothing
            return
        }

        mCountDownTimer?.cancel()
        
        val question = mQuestionsList?.get(mCurrentPosition - 1)
        if (question!!.correctAnswer != mSelectedOptionPosition) {
            answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
        } else {
            mCorrectAnswers++
            val timeSpent = (mTimerDuration/1000).toInt() - mSecondsRemaining
            mTotalScore += calculatePoints(true, question.difficulty, timeSpent, (mTimerDuration/1000).toInt())
        }
        answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

        if (mCurrentPosition == mQuestionsList!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "GO TO NEXT"
        }
        mSelectedOptionPosition = -1
    }

    private fun calculatePoints(isCorrect: Boolean, difficulty: String, timeSpent: Int, timeLimit: Int): Int {
        if (!isCorrect) return 0
        
        val basePoints = when (difficulty) {
            "Easy" -> 10
            "Medium" -> 20
            "Hard" -> 30
            else -> 10
        }
        
        val timeRatio = timeSpent.toFloat() / timeLimit
        val speedMultiplier = when {
            timeRatio <= 0.3f -> 1.5f
            timeRatio <= 0.5f -> 1.3f
            timeRatio <= 0.7f -> 1.1f
            else -> 1.0f
        }
        
        return (basePoints * speedMultiplier).toInt()
    }
    
    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> tvOptionOne?.background = ContextCompat.getDrawable(this, drawableView)
            2 -> tvOptionTwo?.background = ContextCompat.getDrawable(this, drawableView)
            3 -> tvOptionThree?.background = ContextCompat.getDrawable(this, drawableView)
            4 -> tvOptionFour?.background = ContextCompat.getDrawable(this, drawableView)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        if (mSelectedOptionPosition == -1) return
        
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    private val tvTitle: TextView?
        get() = findViewById(R.id.tv_title)

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer?.cancel()
    }
}
