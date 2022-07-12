package com.example.aptmentmanager.ui.rules

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aptmentmanager.databinding.RulesFragmentBinding

class RulesFragment : Fragment() {

    private lateinit var viewModel: RulesViewModel
    private lateinit var binding: RulesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RulesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel = ViewModelProvider(this)[RulesViewModel::class.java]

    }

}