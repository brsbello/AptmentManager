package com.example.aptmentmanager.registerUser.ui

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aptmentmanager.databinding.FragmentRegisterScreenBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterScreen : Fragment() {

    private lateinit var binding: FragmentRegisterScreenBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var usuarioID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        auth = Firebase.auth
        activity?.let {
            viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
            setupBTRegister()
        }
    }

    private fun setupBTRegister() {
        binding.btCreateAccount.setOnClickListener {
            val (email, pass, name) = initComponents()
            registerUser(name, email, pass)
        }
    }

    private fun initComponents(): Triple<String, String, String> {
        val email = binding.etTextEmailRegister.text.toString()
        val pass = binding.etTextPassRegister.text.toString()
        val name = binding.etTextNameRegister.text.toString()
        return Triple(email, pass, name)
    }

    private fun registerUser(name: String, email: String, pass: String) {
        if (!validateForm()) {
            return
        }

        binding.pbRegister.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener(requireActivity()) {
                        setupSnack("Conta Criada com sucesso!")
                    }
                    usuarioID = auth.uid.toString()
                    salvarDados(name)
                    auth.signOut()
                    val controller = findNavController()
                    val action = RegisterScreenDirections.actionCadastroScreenToLoginScreen()
                    controller.navigate(action)
                }
            }.addOnFailureListener(requireActivity()) {
                val error: String = try {
                    throw it
                } catch (e: FirebaseAuthWeakPasswordException) {
                    "Digite uma senha com no mínimo 6 caracteres"
                } catch (e: FirebaseAuthUserCollisionException) {
                    "Este e-mail já está cadastrado"
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    "E-mail invalido"
                } catch (e: FirebaseNetworkException) {
                    "Falha na conexão"
                } catch (e: Exception) {
                    "Não foi possível realizar o cadastro"
                }
                setupSnack(error)
            }
        binding.pbRegister.visibility = View.GONE
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

    private fun salvarDados(name: String) {

        val usuarios = hashMapOf("name" to name)
        db.collection("Usuarios").document(usuarioID).set(usuarios)
            .addOnSuccessListener {
            // colocar log
            Log.e("teste do nome", "nome foi salvo", null)
        }.addOnFailureListener {
            // colocar log
            Log.e("teste do nome", "nome não foi salvo", null)
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val nome = binding.etTextNameRegister.text.toString()
        if (TextUtils.isEmpty(nome)) {
            binding.etNameRegister.error = "Digite um nome válido"
            valid = false
        } else {
            binding.etNameRegister.error = null
        }

        val email = binding.etTextEmailRegister.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.etEmailRegister.error = "Digite um email válido"
            valid = false
        } else {
            binding.etEmailRegister.error = null
        }

        val password = binding.etTextPassRegister.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.etPassRegister.error = "Digite uma senha válida"
            valid = false
        } else {
            binding.etPassRegister.error = null
        }

        return valid
    }
}
