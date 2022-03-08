package com.example.aptmentmanager.login.ui

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aptmentmanager.databinding.FragmentLoginScreenBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class LoginScreen : Fragment() {

    private lateinit var binding: FragmentLoginScreenBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        auth = Firebase.auth
        activity?.let {
            viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
            setupButtons()
        }
    }

    override fun onStart() {
        super.onStart()
        val userLogged = auth.currentUser
        if(userLogged != null){
            navigateHome()
        }
    }

    private fun setupButtons() {
        binding.btCriarconta.setOnClickListener {
            val controller = findNavController()
            val action = LoginScreenDirections.actionLoginScreenToCadastroScreen()
            controller.navigate(action)

        }
        binding.tvEsquecisenha.setOnClickListener {
            // Implementar
        }
        binding.btLogin.setOnClickListener {
            val email = binding.etTextemaillogin.text.toString()
            val pass = binding.etTextsenhalogin.text.toString()
            signIn(email, pass)

        }
    }

    private fun signIn(email: String, password: String) {

        if (!validateForm()) {
            return
        }

        binding.pbLogin.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    navigateHome()
                }
            }.addOnFailureListener(requireActivity()) {
                val error: String = try {
                    throw it
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    "Senha incorreta"
                } catch (e : FirebaseNetworkException) {
                    "Falha na conexão"
                } catch (e: FirebaseAuthInvalidUserException) {
                    "E-mail invalido"
                } catch (e: Exception) {
                    "Não foi possível realizar o login"
                }
                val errorSnackbar = Snackbar.make(
                    binding.root,
                    error,
                    Snackbar.LENGTH_SHORT
                )
                errorSnackbar.setBackgroundTint(Color.parseColor("#831A00"))
                errorSnackbar.setTextColor(Color.WHITE)
                errorSnackbar.show()
            }
        binding.pbLogin.visibility = View.GONE
    }

    private fun navigateHome() {
        val controller = findNavController()
        val action = LoginScreenDirections.actionLoginScreenToHomeFragment()
        controller.navigate(action)
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.etTextemaillogin.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = "Digite um email válido"
            valid = false
        } else {
            binding.etEmail.error = null
        }

        val password = binding.etTextsenhalogin.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.etSenha.error = "Digite uma senha válida"
            valid = false
        } else {
            binding.etSenha.error = null
        }

        return valid
    }

}