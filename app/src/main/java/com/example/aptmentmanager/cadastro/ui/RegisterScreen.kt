package com.example.aptmentmanager.cadastro.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aptmentmanager.databinding.FragmentRegisterScreenBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterScreen : Fragment() {

    private lateinit var binding: FragmentRegisterScreenBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ScrollView {
        binding = FragmentRegisterScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
            binding.btCriarconta.setOnClickListener {
                val email = binding.etTextemailcadastro.text.toString()
                val pass = binding.etTextsenhacadastro.text.toString()
                val nome = binding.etTextnomecadastro.text.toString()
                cadastrarUsuario(nome, email, pass)
            }
        }
        auth = Firebase.auth
    }

    private fun cadastrarUsuario(nome: String, email: String, pass: String) {
        if (!validateForm()) {
            return
        }

        binding.pbRegister.visibility = View.VISIBLE

        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful) {
                val user = auth.currentUser
                user?.sendEmailVerification()?.addOnCompleteListener(requireActivity()) {
                    val sucessSnackbar =
                        Snackbar.make(binding.root, "Conta Criada com sucesso!", Snackbar.LENGTH_SHORT)
                    sucessSnackbar.show()
                }
                //navigation
            }
            binding.pbRegister.visibility = View.GONE
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val nome = binding.etTextnomecadastro.text.toString()
        if (TextUtils.isEmpty(nome)) {
            binding.etNome.error = "Digite um nome válido"
            valid = false
        } else {
            binding.etNome.error = null
        }

        val email = binding.etTextemailcadastro.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.etEmailcadastro.error = "Digite um email válido"
            valid = false
        } else {
            binding.etEmailcadastro.error = null
        }

        val password = binding.etTextsenhacadastro.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.etSenhacadastro.error = "Digite uma senha válida"
            valid = false
        } else {
            binding.etSenhacadastro.error = null
        }

        return valid
    }
}