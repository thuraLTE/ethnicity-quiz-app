package com.example.quizapp.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.viewmodel.QuizViewModel
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentResultBinding
import com.example.quizapp.datasource.MAX_NO_OF_QUIZ

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = this@ResultFragment
            maxNoOfQuiz = MAX_NO_OF_QUIZ

            trophyImg.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)

            finishBtn.setOnClickListener {
                this@ResultFragment.findNavController().navigate(
                    R.id.action_resultFragment_to_loginFragment
                )
            }
        }
    }
}