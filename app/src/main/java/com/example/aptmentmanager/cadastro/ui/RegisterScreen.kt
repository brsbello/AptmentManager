package com.example.aptmentmanager.cadastro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aptmentmanager.databinding.FragmentRegisterScreenBinding

class RegisterScreen : Fragment() {

    private lateinit var binding: FragmentRegisterScreenBinding
    private lateinit var viewModel: RegisterViewModel

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
        binding = FragmentRegisterScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun InitComponentes(){
        val nome = binding.etNome
        val email = binding.etEmailcadastro
        val senha = binding.etSenhacadastro
        val btCadastrar = binding.btCriarconta

        btCadastrar.setOnClickListener {

        }

    }

}