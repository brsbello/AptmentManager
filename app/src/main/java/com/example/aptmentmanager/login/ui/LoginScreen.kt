package com.example.aptmentmanager.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aptmentmanager.databinding.FragmentLoginScreenBinding
import com.google.android.material.button.MaterialButton

class LoginScreen : Fragment() {

    private var binding: FragmentLoginScreenBinding? = null
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ScrollView? {
        binding = FragmentLoginScreenBinding.inflate(layoutInflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        activity?.let {

            binding?.let {
                initComponents()
            }
        }
    }

    private fun initComponents(){
        val email = binding?.etEmail
        val senha = binding?.etSenha
        val btCadastrar = binding?.btCriarconta
        val btLogin = binding?.btLogin
        val btEsqueciSenha = binding?.tvEsquecisenha

        setupBT(btCadastrar, btLogin, btEsqueciSenha)

    }

    private fun setupBT(
        btCadastrar: MaterialButton?,
        btLogin: MaterialButton?,
        btEsqueciSenha : TextView?
    ) {
        btCadastrar?.setOnClickListener {
            //val action =
            //findNavController().navigate(action)
        }

        btLogin?.setOnClickListener {

        }

        btEsqueciSenha?.setOnClickListener {

        }
    }

}