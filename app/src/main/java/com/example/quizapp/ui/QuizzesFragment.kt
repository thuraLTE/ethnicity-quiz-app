package com.example.quizapp.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.viewmodel.QuizViewModel
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizzesBinding
import com.example.quizapp.datasource.MAX_NO_OF_QUIZ

class QuizzesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentQuizzesBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: QuizViewModel by activityViewModels()

    private val tvList: ArrayList<TextView> = ArrayList()
    private var userChoice: String = ""
    private lateinit var selectedTextView: TextView
    private var clickProcess = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quizzes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.getNextQuiz()
        defaultOption()

        sharedViewModel.currentQuiz.observe(viewLifecycleOwner) {
            binding.ethnicImage.setImageResource(it.question)
        }

        tvList.add(binding.optionOne)
        tvList.add(binding.optionTwo)
        tvList.add(binding.optionThree)
        tvList.add(binding.optionFour)

        binding.apply {
            viewModel = sharedViewModel
            maxNoOfQuiz = MAX_NO_OF_QUIZ
            lifecycleOwner = this@QuizzesFragment

            progressBar.apply {
                progressTintList = ColorStateList.valueOf(
                    Color.parseColor("#FF6200EE")
                )
                progressBackgroundTintList = ColorStateList.valueOf(
                    Color.parseColor("#FFBB86FC")
                )
            }

            optionOne.setOnClickListener(this@QuizzesFragment)
            optionTwo.setOnClickListener(this@QuizzesFragment)
            optionThree.setOnClickListener(this@QuizzesFragment)
            optionFour.setOnClickListener(this@QuizzesFragment)

            submitBtn.setOnClickListener {
                when (clickProcess) {
                    0 -> {
                        Toast.makeText(
                            requireContext(),
                            "You must first select a choice!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    1 -> {
                        checkAnswer()
                        changeButtonText()
                        disableClickEvents()
                        clickProcess = 2
                    }
                    2 -> {
                        defaultOption()
                        enableClickEvents()
                        submitBtn.text = getString(R.string.submit_button)
                        clickProcess = 0
                        if (sharedViewModel.count.value!! < MAX_NO_OF_QUIZ) {
                            sharedViewModel.nextQuiz()
                        } else {
                            this@QuizzesFragment.findNavController().navigate(
                                R.id.action_quizzesFragment_to_resultFragment
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.option_one -> {
                defaultOption()
                selectOption(view as TextView)
            }
            R.id.option_two -> {
                defaultOption()
                selectOption(view as TextView)
            }
            R.id.option_three -> {
                defaultOption()
                selectOption(view as TextView)
            }
            R.id.option_four -> {
                defaultOption()
                selectOption(view as TextView)
            }
        }
    }

    /**
     *  Disable clicks on option textviews
     */
    private fun disableClickEvents() {
        for (tv in tvList) {
            tv.isClickable = false
        }
    }

    /**
     *  Re-enable clicks on option textviews
     */
    private fun enableClickEvents() {
        for (tv in tvList) {
            tv.isClickable = true
        }
    }

    private fun changeButtonText() {
        if (sharedViewModel.count.value!! < MAX_NO_OF_QUIZ) {
            binding.submitBtn.text = getString(R.string.next_button)
        } else
            binding.submitBtn.text = getString(R.string.finish_button)
    }

    /**
     *  Set the background color of the default option textview
     */
    private fun defaultOption() {
        for (tv in tvList) {
            tv.apply {
                background = ContextCompat.getDrawable(requireContext(), R.drawable.radio_textview)
                typeface = Typeface.DEFAULT
                setTextColor(Color.parseColor("#817F7F"))
            }
        }
    }

    /**
     *  Set the background color of the selected option textview
     */
    private fun selectOption(textView: TextView) {
        userChoice = textView.text.toString()
        selectedTextView = textView
        clickProcess = 1
        textView.apply {
            background = ResourcesCompat.getDrawable(
                resources, R.drawable.selected_radio_textview, null
            )
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#FF000000"))
        }
    }

    /**
     *  Validate user's answer
     */
    private fun checkAnswer() {
        if (sharedViewModel.checkAnswer(userChoice))
            setCorrectAnswerBackground()
        else {
            setCorrectAnswerBackground()
            setIncorrectAnswerBackground()
        }
    }

    /**
     *  Change the background of correct option textview to green
     */
    private fun setCorrectAnswerBackground() {
        for (tv in tvList) {
            if (tv.text.equals(sharedViewModel.currentQuiz.value!!.correctOption)) {
                defaultOption()
                tv.apply {
                    background = ContextCompat.getDrawable(
                        requireContext(), R.drawable.correct_background
                    )
                    setTextColor(Color.WHITE)
                }
            }
        }
    }

    /**
     *  Change the background of user's answer option textview to red
     */
    private fun setIncorrectAnswerBackground() {
        selectedTextView.apply {
            background = ContextCompat.getDrawable(
                requireContext(), R.drawable.incorrect_background
            )
            setTextColor(Color.WHITE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}