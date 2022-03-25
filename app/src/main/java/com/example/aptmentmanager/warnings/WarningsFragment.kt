package com.example.aptmentmanager.warnings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aptmentmanager.R
import com.example.aptmentmanager.databinding.WarningsFragmentBinding
import com.example.aptmentmanager.warnings.adapter.WarningAdapter
import com.example.aptmentmanager.warnings.addwarning.AddWarningFragment
import com.example.aptmentmanager.warnings.data.WarningDataBase

class WarningsFragment : Fragment() {

    private lateinit var viewModel: WarningsViewModel
    private lateinit var binding: WarningsFragmentBinding
    private val db by lazy { activity?.let { WarningDataBase.intance(it.baseContext).WarningDao() } }
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
        configFAB()
        updateList()
        viewModel = ViewModelProvider(this)[WarningsViewModel::class.java]

    }

    override fun onResume() {
        super.onResume()
        updateList()
    }

    private fun setupAdapter() {
        binding.RVWarnings.adapter = adapter
    }

    private fun editWarning() {
        adapter.listenerEdit = {
            val content = "my_data"
            val bundle = Bundle()
            val fragment = AddWarningFragment()
            bundle.putParcelable(content, it)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment).commit()
        }
    }

    private fun deleteWarning() {
        adapter.listenerDelete = {
            db?.delete(it)
            updateList()
        }
    }

    private fun updateList() {
        adapter.submitList(db?.searchAll())
    }

    private fun configFAB() {
        binding.FABAdd.setOnClickListener {
            val fragment = AddWarningFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment).commit()
        }
    }
}