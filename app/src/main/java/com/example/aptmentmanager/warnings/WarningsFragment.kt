package com.example.aptmentmanager.warnings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aptmentmanager.R
import com.example.aptmentmanager.databinding.WarningsFragmentBinding
import com.example.aptmentmanager.warnings.adapter.WarningAdapter

class WarningsFragment : Fragment() {

    private lateinit var viewModel: WarningsViewModel
    private lateinit var binding: WarningsFragmentBinding
    private val adapter: WarningAdapter = WarningAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WarningsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        setupAdapter()
        viewModel = ViewModelProvider(this)[WarningsViewModel::class.java]

    }

    private fun setupAdapter() {
        binding.RVWarnings.adapter = adapter
    }

    private fun configFAB() {
        binding.FABAdd.setOnClickListener {

        }
    }
}