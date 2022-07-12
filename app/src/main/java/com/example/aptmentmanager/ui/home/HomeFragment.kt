package com.example.aptmentmanager.ui.home

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
import com.example.aptmentmanager.databinding.HomeFragmentBinding
import com.example.aptmentmanager.databinding.NavHeaderMainBinding
import com.example.aptmentmanager.ui.authorization.AuthorizationFragment
import com.example.aptmentmanager.ui.calls.CallsFragment
import com.example.aptmentmanager.ui.contacts.ContactsFragment
import com.example.aptmentmanager.ui.minutes.MinutesFragment
import com.example.aptmentmanager.ui.reservation.ReservationFragment
import com.example.aptmentmanager.ui.rules.RulesFragment
import com.example.aptmentmanager.ui.services.ServicesFragment
import com.example.aptmentmanager.ui.warnings.WarningsFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var bindingHeader: NavHeaderMainBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView

    private lateinit var db: FirebaseFirestore
    private lateinit var usuarioID: String

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
        db = FirebaseFirestore.getInstance()
        initComponents()
        activity?.let {
            viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
            logOut()
            setupNavDraw()
            recoverLoginData()
        }
    }

    private fun navigateLogin() {
        val controller = findNavController()
        val action = HomeFragmentDirections.actionHomeFragmentToLoginScreen()
        controller.navigate(action)
    }

    private fun logOut() {
        bindingHeader.ivMenuLogout.setOnClickListener {
            viewModel.logout()
            navigateLogin()
        }
    }

    private fun recoverLoginData() {

        usuarioID = viewModel.getUid().toString()

        db.collection("Usuarios").document(usuarioID).addSnapshotListener { value, _ ->
            if (value != null) {
                bindingHeader.tvMenuName.text = value.getString("name")
                bindingHeader.tvMenuEmail.text = value.getString("email")
            } else {
                bindingHeader.tvMenuName.text = getString(R.string.anonimo)
                bindingHeader.tvMenuEmail.text = getString(R.string.anonimo)
            }
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

//        val toolbar = binding.drawerLayout.findViewById<Toolbar>(R.id.topAppBar)
//        activity?.setActionBar(toolbar)
//        toolbar.setOnClickListener {
//            if (toggle.)
//            drawer.openDrawer(GravityCompat.START)
//            drawer.closeDrawer(GravityCompat.START)
//        }
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {

        val id: Int = item.itemId
        var fragment: Fragment? = null
        when (id) {
            R.id.nav_avisos -> fragment = WarningsFragment()
            R.id.nav_reservas -> fragment = ReservationFragment()
            R.id.nav_chamados -> fragment = CallsFragment()
            R.id.nav_autorizacoes -> fragment = AuthorizationFragment()
            R.id.nav_atas -> fragment = MinutesFragment()
            R.id.nav_regras -> fragment = RulesFragment()
            R.id.nav_contatos -> fragment = ContactsFragment()
            R.id.nav_servicos -> fragment = ServicesFragment()
        }

        if (fragment != null) {
            childFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment)
                .commit()
        }
        drawer.closeDrawer(GravityCompat.START)

        return true
    }

    private fun initComponents() {
        drawer = binding.drawerLayout
        navigationView = binding.navView
    }
}