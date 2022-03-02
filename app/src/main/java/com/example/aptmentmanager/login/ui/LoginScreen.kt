package com.example.aptmentmanager.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aptmentmanager.databinding.FragmentLoginScreenBinding

class LoginScreen : Fragment() {

    private lateinit var binding: FragmentLoginScreenBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        InitComponentes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun InitComponentes(){
        val email = binding.etEmail
        val senha = binding.etSenha
        val btCadastrar = binding.btCriarconta
        val btLogin = binding.btLogin

        btCadastrar.setOnClickListener {

        }

        btLogin.setOnClickListener {

        }

    }

}