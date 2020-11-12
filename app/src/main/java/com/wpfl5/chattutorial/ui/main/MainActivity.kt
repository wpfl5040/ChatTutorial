package com.wpfl5.chattutorial.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.ActivityMainBinding
import com.wpfl5.chattutorial.ext.gone
import com.wpfl5.chattutorial.ext.setToastObserver
import com.wpfl5.chattutorial.ext.visible
import com.wpfl5.chattutorial.ui.base.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            mainViewModel = viewModel
        }

        initNavigation()
        eventObserving()

    }

    private fun initNavigation(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.myProfileFragment -> bottomNavVisibility(false)
                R.id.friendProfileFragment -> bottomNavVisibility(false)
                R.id.chatActivity -> bottomNavVisibility(false)
                else -> bottomNavVisibility(true)
            }
        }
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    private fun eventObserving(){
        viewModel.backButtonEvent.eventObserving(this) { navController.navigateUp() }
        setToastObserver(viewModel.toastObservable)
    }

    private fun bottomNavVisibility(visibility: Boolean) {
        if(visibility) binding.bottomNavigationView.visible()
        else binding.bottomNavigationView.gone()
    }


}