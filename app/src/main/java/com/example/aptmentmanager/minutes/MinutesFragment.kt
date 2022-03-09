package com.example.aptmentmanager.minutes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aptmentmanager.R
import com.example.aptmentmanager.databinding.MinutesFragmentBinding

class MinutesFragment : Fragment() {

    private lateinit var viewModel: MinutesViewModel
    private lateinit var binding: MinutesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MinutesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel = ViewModelProvider(this)[MinutesViewModel::class.java]

    }

}