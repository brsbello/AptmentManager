package com.example.aptmentmanager.ui.auth

import androidx.lifecycle.ViewModel
import com.example.aptmentmanager.data.repositories.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var name: String? = null
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

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Nome inválido")
            return
        }

        authListener?.onStarted()
        val disposable = repository.register(name!!, email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun getId(): String {
        return repository.currentUser()?.uid.toString()
    }

    fun logout() {
        repository.logout()
    }

}



