package com.example.aptmentmanager.ui.registerUser

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aptmentmanager.databinding.FragmentRegisterScreenBinding
import com.example.aptmentmanager.ui.auth.AuthListener
import com.example.aptmentmanager.ui.auth.AuthViewModel
import com.example.aptmentmanager.ui.auth.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class RegisterScreen : Fragment(), AuthListener, KodeinAware {

    private lateinit var binding: FragmentRegisterScreenBinding
    override val kodein: Kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel: AuthViewModel
    private val controller by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
            binding.viewmodel = viewModel
            viewModel.authListener = this
            setupBTRegister()
        }
    }

    override fun onStarted() {
        binding.pbRegister.visibility = View.VISIBLE

    }

    override fun onSuccess() {
        binding.pbRegister.visibility = View.GONE
        controller.popBackStack()
        setupSnack("Conta criada com sucesso")

    }

    override fun onFailure(message: String) {
        binding.pbRegister.visibility = View.GONE
        setupSnack(message)
    }

    private fun setupBTRegister() {
        binding.btCreateAccount.setOnClickListener {
            viewModel.email = binding.etTextEmailRegister.text.toString()
            viewModel.password = binding.etTextPassRegister.text.toString()
            viewModel.name = binding.etTextNameRegister.text.toString()

            viewModel.signup()

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
