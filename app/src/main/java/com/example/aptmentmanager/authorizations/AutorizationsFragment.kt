package com.example.aptmentmanager.authorizations

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aptmentmanager.R

class AutorizationsFragment : Fragment() {

    companion object {
        fun newInstance() = AutorizationsFragment()
    }

    private lateinit var viewModel: AutorizationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.autorizations_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AutorizationsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}