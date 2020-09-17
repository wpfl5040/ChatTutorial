package com.wpfl5.chattutorial.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.ActivityMainBinding
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

    }
}