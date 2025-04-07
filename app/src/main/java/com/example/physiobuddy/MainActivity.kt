package com.example.physiobuddy

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.physiobuddy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        activityMainBinding.navigation.setupWithNavController(navController)
        activityMainBinding.navigation.setOnNavigationItemReselectedListener {
            // ignore the reselection
        }

        // 🟢 Hides BottomNav and Toolbar on LandingFragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val hideBottomNav = destination.id == R.id.landing_fragment || destination.id == R.id.introFragment || destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.dashboardFragment
            activityMainBinding.navigation.visibility = if (hideBottomNav) View.GONE else View.VISIBLE
        }

    }

    override fun onBackPressed() {
        finish()
    }
}
