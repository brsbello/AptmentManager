package com.example.aptmentmanager.ui.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aptmentmanager.databinding.AuthorizationsFragmentBinding

class AuthorizationFragment : Fragment() {

    private lateinit var viewModel: AuthorizationViewModel
    private lateinit var binding: AuthorizationsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthorizationsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthorizationViewModel::class.java]

    }

}