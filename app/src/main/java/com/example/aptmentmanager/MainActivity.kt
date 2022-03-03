package com.example.aptmentmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aptmentmanager.databinding.ActivityMainBinding
import com.example.aptmentmanager.login.ui.LoginScreen

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmentManager by lazy { supportFragmentManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}