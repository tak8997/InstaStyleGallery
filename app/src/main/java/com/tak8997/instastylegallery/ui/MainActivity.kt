package com.tak8997.instastylegallery.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tak8997.instastylegallery.PermissionsUtil
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

internal class MainActivity : DaggerAppCompatActivity() {

    companion object {
        const val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1000
        private const val INDEX_CURRENT_FRAG = 0
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
            viewmodel = viewModel
        }

        setupBottomNav()
    }

    override fun onStart() {
        super.onStart()
        requestPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        viewModel.onRequestPermissionResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = host?.childFragmentManager?.fragments?.get(INDEX_CURRENT_FRAG)

        (currentFragment as? BaseView)?.onBackPressed()
    }

    fun superOnBackPressed() {
        super.onBackPressed()
    }

    private fun requestPermissions() {
        if (PermissionsUtil.isPermissionGranted(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            viewModel.setPermissions(true)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun setupBottomNav() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        navController = host.navController
        binding.bottomNav.setupWithNavController(navController)
    }
}
