package com.tak8997.instastylegallery.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

internal class MainActivity : DaggerAppCompatActivity() {

    companion object {
        private const val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1000
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private lateinit var binding: ActivityMainBinding

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

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MY_LOG", "onDestroy")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            REQUEST_PERMISSION_READ_EXTERNAL_STORAGE -> {
                Log.d("MY_LOG", "2")
                viewModel.setPermissions(true)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

//            if (viewModel.permissionChecked.value == false) {
//                Log.d("MY_LOG", "123")
//                setupBottomNav()
//            }
            Log.d("MY_LOG", "requestPermissions")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_READ_EXTERNAL_STORAGE
            )
            return
        } else {
            Log.d("MY_LOG", "1")
            viewModel.setPermissions(true)
        }
    }

    private fun setupBottomNav() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        binding.bottomNav.setupWithNavController(navController)
    }
}
