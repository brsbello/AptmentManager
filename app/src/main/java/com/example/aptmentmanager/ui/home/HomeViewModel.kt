package com.example.aptmentmanager.ui.home

import androidx.lifecycle.ViewModel
import com.example.aptmentmanager.data.repositories.UserRepository

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var uid: String? = null

    val user by lazy {
        repository.currentUser()
    }

    fun logout() {
        repository.logout()
    }

    fun getUid() {
        if (user != null) {
            uid = repository.getUid()
        }

    }
}