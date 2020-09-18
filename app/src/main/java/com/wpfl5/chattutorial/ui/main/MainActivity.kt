package com.wpfl5.chattutorial.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.ActivityMainBinding
import com.wpfl5.chattutorial.ext.setToastObserver
import com.wpfl5.chattutorial.ui.base.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            mainViewModel = viewModel
        }

        initNavigation()
        eventObserving()

    }

    private fun initNavigation(){
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    private fun eventObserving(){
        viewModel.backButtonEvent.eventObserving { onBackPressed() }
        setToastObserver(viewModel.toastObservable)
    }

}