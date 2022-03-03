package com.example.aptmentmanager.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aptmentmanager.databinding.FragmentLoginScreenBinding

class LoginScreen : Fragment() {

    private lateinit var binding: FragmentLoginScreenBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ScrollView {
        binding = FragmentLoginScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
            setupButtons()
        }

    }

    private fun setupButtons() {
        binding.btCriarconta.setOnClickListener {
            val controller = findNavController()
            val action = LoginScreenDirections.actionLoginScreenToCadastroScreen()
            controller.navigate(action)

        }
        binding.tvEsquecisenha.setOnClickListener {

        }
        binding.btLogin.setOnClickListener {

        }
    }

    private fun initComponents(){
        val email = binding.etEmail
        val senha = binding.etSenha
    }

}