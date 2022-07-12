package com.example.aptmentmanager.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.aptmentmanager.data.repositories.UserRepository
import com.example.aptmentmanager.ui.login.LoginScreen
import com.example.aptmentmanager.ui.registerUser.RegisterScreen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var name: String? = null
    var uid: String? = null
    var authListener: AuthListener? = null
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }

    fun login() {

        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email inválido")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Senha inválida")
            return
        }

        authListener?.onStarted()

        val disposable = repository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun forgetPassword() {
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email inválido")
            return
        }

        authListener?.onStarted()

        val disposable = repository.forgetPassword(email!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun signup() {
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email inválido")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Senha inválida")
            return
        }
        authListener?.onStarted()
        val disposable = repository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun goToSignup(view: View) {
        Intent(view.context, RegisterScreen::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLogin(view: View) {
        Intent(view.context, LoginScreen::class.java).also {
            view.context.startActivity(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun saveData() {

        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email inválido")
            return
        }

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Nome inválido")
            return
        }
        getUid()
        if (uid.isNullOrEmpty()) {
            authListener?.onFailure("Usuario inválido")
            return
        }

        authListener?.onStarted()
        val disposable = repository.saveData(name!!, email!!, uid!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun getUid() {
        if (user != null) {
            uid = repository.getUid()
        }

    }

}



