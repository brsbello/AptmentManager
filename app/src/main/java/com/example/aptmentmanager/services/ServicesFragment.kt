package com.example.aptmentmanager.services

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aptmentmanager.R
import com.example.aptmentmanager.databinding.ServicesFragmentBinding

class ServicesFragment : Fragment() {

    private lateinit var viewModel: ServicesViewModel
    private lateinit var binding: ServicesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ServicesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel = ViewModelProvider(this)[ServicesViewModel::class.java]

    }

}