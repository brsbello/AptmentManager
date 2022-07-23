package com.example.aptmentmanager.ui.login

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aptmentmanager.databinding.FragmentLoginScreenBinding
import com.example.aptmentmanager.ui.auth.AuthListener
import com.example.aptmentmanager.ui.auth.AuthViewModel
import com.example.aptmentmanager.ui.auth.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class LoginScreen : Fragment(), AuthListener, KodeinAware {

    override val kodein: Kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: FragmentLoginScreenBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
            binding.viewmodel = viewModel
            viewModel.authListener = this
            setupButtons()

        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.user?.let {
            navigateHome()
        }
    }

    override fun onStarted() {
        binding.pbLogin.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        binding.pbLogin.visibility = View.GONE
        navigateHome()
    }

    override fun onFailure(message: String) {
        binding.pbLogin.visibility = View.GONE
        setupSnack(message)
    }

    private fun navigateHome() {
        val controller = findNavController()
        val action = LoginScreenDirections.actionLoginScreenToHomeFragment()
        controller.navigate(action)
    }

    private fun setupButtons() {

        binding.btCriarconta.setOnClickListener {
            val controller = findNavController()
            val action = LoginScreenDirections.actionLoginScreenToCadastroScreen()
            controller.navigate(action)
        }

        binding.tvEsquecisenha.setOnClickListener {
            viewModel.email = binding.etTextemaillogin.text.toString()
            viewModel.forgetPassword()
        }

        binding.btLogin.setOnClickListener {
            viewModel.email = binding.etTextemaillogin.text.toString()
            viewModel.password = binding.etTextsenhalogin.text.toString()

            viewModel.login()

        }
    }

    private fun setupSnack(text: String) {
        val errorSnackbar = view?.let {
            Snackbar.make(
                it,
                text,
                Snackbar.LENGTH_SHORT
            )
        }
        errorSnackbar?.setBackgroundTint(Color.parseColor("#831A00"))
        errorSnackbar?.setTextColor(Color.WHITE)
        errorSnackbar?.show()
    }
}
