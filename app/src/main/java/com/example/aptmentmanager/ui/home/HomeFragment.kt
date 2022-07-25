package com.example.aptmentmanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.aptmentmanager.R
import com.example.aptmentmanager.databinding.HomeFragmentBinding
import com.example.aptmentmanager.databinding.LoadImageBinding
import com.example.aptmentmanager.databinding.NavHeaderMainBinding
import com.example.aptmentmanager.ui.authorization.AuthorizationFragment
import com.example.aptmentmanager.ui.calls.CallsFragment
import com.example.aptmentmanager.ui.contacts.ContactsFragment
import com.example.aptmentmanager.ui.minutes.MinutesFragment
import com.example.aptmentmanager.ui.reservation.ReservationFragment
import com.example.aptmentmanager.ui.rules.RulesFragment
import com.example.aptmentmanager.ui.services.ServicesFragment
import com.example.aptmentmanager.ui.warnings.WarningsFragment
import com.example.aptmentmanager.utils.extensions.text
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var bindingHeader: NavHeaderMainBinding
    private lateinit var bindingDialog: LoadImageBinding

    private val navigationView: NavigationView by lazy {
        binding.navView
    }

    private val drawer: DrawerLayout by lazy {
        binding.drawerLayout
    }

    private val controller by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        bindingDialog = LoadImageBinding.inflate(layoutInflater)
        bindingHeader = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (!viewModel.logged()) {
            navigateLogin()
        }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        binding.viewmodel = viewModel

        logOut()
        recoverLoginData()
        setupNavDraw()
        loadImage()

    }

    private fun logOut() {
        bindingHeader.ivMenuLogout.setOnClickListener {
            viewModel.logout()
            navigateLogin()
        }
    }

    private fun recoverLoginData() {

        viewModel.recoverDataLogin().observe(viewLifecycleOwner) { user ->
            if (user != null) {
                bindingHeader.tvMenuName.text = user.name
                println(user.name)
                bindingHeader.tvMenuEmail.text = user.email
            } else {
                bindingHeader.tvMenuName.text = context?.getString(R.string.anonimo)
                bindingHeader.tvMenuEmail.text = context?.getString(R.string.anonimo)
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

        //    val toolbar = binding.drawerLayout.findViewById<Toolbar>(R.id.topAppBar)
        //    activity?.setActionBar(toolbar)
        //    toolbar.setOnClickListener {
        //        if (toggle.isDrawerIndicatorEnabled)
        //        drawer.openDrawer(GravityCompat.START)
        //        drawer.closeDrawer(GravityCompat.START)
        //    }
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

    private fun navigateLogin() {
        val action = HomeFragmentDirections.actionHomeFragmentToLoginScreen()
        controller.navigate(action)
    }

    fun loadImage() {
        bindingHeader.imageView.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            val inflater = requireActivity().layoutInflater
            // ARRUMAR OS ERROS AQUI
            builder.setView(inflater.inflate(R.layout.load_image, null))
                .setNeutralButton("Carregar") { dialog, id ->
                    val url = bindingDialog.imagemUrl.text.toString()
                    if (url.isEmpty()) {
                        bindingDialog.ivImage.setImageResource(R.drawable.ic_building);
                    } else{
                        Picasso.get().load(url).into(bindingDialog.ivImage);
                    }
                    builder.setView(inflater.inflate(R.layout.load_image, null))
                }
                .setPositiveButton("Salvar") { dialog, _ ->
                    val url = bindingDialog.imagemUrl.text.toString()
                    if (url.isEmpty()) {
                        viewModel.savePhoto("Falhou")
                        dialog.dismiss()

                    } else{
                        viewModel.savePhoto(url)
                        dialog.dismiss()
                    }

                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
            builder.show()
        }
    }
}