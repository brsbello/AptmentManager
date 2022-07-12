package com.example.aptmentmanager.utils

import android.content.Context
import android.content.Intent
import com.example.aptmentmanager.ui.home.HomeFragment
import com.example.aptmentmanager.ui.login.LoginScreen

class ViewUtils {

    fun Context.startHomeActivity() =
        Intent(this, HomeFragment::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

    fun Context.startLoginActivity() =
        Intent(this, LoginScreen::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

}