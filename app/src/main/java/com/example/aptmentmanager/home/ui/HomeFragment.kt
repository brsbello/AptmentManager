package com.example.aptmentmanager.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aptmentmanager.R
import com.example.aptmentmanager.authorization.AuthorizationFragment
import com.example.aptmentmanager.calls.CallsFragment
import com.example.aptmentmanager.configs.SettingsFragment
import com.example.aptmentmanager.contacts.ContactsFragment
import com.example.aptmentmanager.databinding.HomeFragmentBinding
import com.example.aptmentmanager.databinding.NavHeaderMainBinding
import com.example.aptmentmanager.minutes.MinutesFragment
import com.example.aptmentmanager.reservation.ReservationFragment
import com.example.aptmentmanager.rules.RulesFragment
import com.example.aptmentmanager.services.ServicesFragment
import com.example.aptmentmanager.warnings.WarningsFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var bindingHeader: NavHeaderMainBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        bindingHeader = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        initComponents()
        activity?.let {
            viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
            logOut()
            setupNavDraw()
        }
    }

    private fun navigateLogin() {
        val controller = findNavController()
        val action = HomeFragmentDirections.actionHomeFragmentToLoginScreen()
        controller.navigate(action)
    }

    private fun logOut() {
        bindingHeader.ivMenuLogout.setOnClickListener {
            auth.signOut()
            navigateLogin()
        }
    }

    private fun setupNavDraw() {

        val toggle = ActionBarDrawerToggle(
            activity, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setCheckedItem(R.id.nav_avisos)
        val fragmentManager = childFragmentManager
        val fragment = WarningsFragment()
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()

        navigationView.setNavigationItemSelectedListener {
            onNavigationItemSelected(it)
        }

    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {

        val id: Int = item.itemId
        var fragment: Fragment? = null
        val fragmentManager = childFragmentManager
        when (id) {
            R.id.nav_avisos -> fragment = WarningsFragment()
            R.id.nav_reservas -> fragment = ReservationFragment()
            R.id.nav_chamados -> fragment = CallsFragment()
            R.id.nav_autorizacoes -> fragment = AuthorizationFragment()
            R.id.nav_atas -> fragment = MinutesFragment()
            R.id.nav_regras -> fragment = RulesFragment()
            R.id.nav_contatos -> fragment = ContactsFragment()
            R.id.nav_servicos -> fragment = ServicesFragment()
            R.id.nav_configuracoes -> fragment = SettingsFragment()
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
        }
        drawer.closeDrawer(GravityCompat.START)

        return true
    }

    private fun initComponents() {
        auth = Firebase.auth
        drawer = binding.drawerLayout
        navigationView = binding.navView
    }
}