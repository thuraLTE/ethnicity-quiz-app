package com.example.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapp.viewmodel.QuizViewModel
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        _binding = loginBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.resetData()

        binding.startBtn.setOnClickListener {
            validateUsername()
        }
    }

    private fun validateUsername() {
        val username = binding.nameEdt.text.toString()
        if (username.isNotBlank()) {
            sharedViewModel.setName(username)
            this.findNavController().navigate(R.id.action_loginFragment_to_quizzesFragment)
        }
        else {
            binding.nameTextfield.apply {
                isErrorEnabled = true
                error = "Name must not be empty!"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}