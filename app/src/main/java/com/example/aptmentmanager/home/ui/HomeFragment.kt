package com.example.aptmentmanager.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.aptmentmanager.databinding.HomeFragmentBinding
import com.example.aptmentmanager.databinding.NavHeaderMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var bindingHeader: NavHeaderMainBinding
    //private lateinit var bindingContentMainBinding: ContentMainBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        bindingHeader = NavHeaderMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        auth = Firebase.auth
        activity?.let {
            viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        }
    }

    fun setupDrawer() {
        //val drawer = binding.drawerLayout
        // verificar os dois ultimos parametros da funcao abaixo
        //val toggle = ActionBarDrawerToggle(requireActivity(), drawer, 0,0)
        //drawer.addDrawerListener(toggle)
        //toggle.syncState()
    }

    fun setupNavigationView() {
        //val navigationView = binding.navView
        //bindingHeader.ivMenuLogout
        //navigationView.setCheckedItem(navigationView.)
        //navigationView.setNavigationItemSelectedListener()
    }
}