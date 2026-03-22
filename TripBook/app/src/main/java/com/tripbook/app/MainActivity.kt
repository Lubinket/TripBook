package com.tripbook.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tripbook.app.databinding.ActivityMainBinding

/**
 * MainActivity is the single-activity shell for the entire app.
 * It hosts:
 *   - A NavHostFragment (the container where all fragments are shown)
 *   - A BottomNavigationView (5 tabs: Home, Explore, Bookings, Reviews, Profile)
 *
 * The Navigation Component handles all fragment transitions automatically
 * based on nav_graph.xml.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Navigation Component
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Connect bottom navigation to nav controller
        // This automatically handles tab selection, back stack, and re-selection
        binding.bottomNavigation.setupWithNavController(navController)

        // Hide bottom nav on detail screens (SiteDetail, Booking, WriteReview)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val hideBottomNavOn = setOf(
                R.id.siteDetailFragment,
                R.id.bookingFragment,
                R.id.bookingConfirmationFragment,
                R.id.writeReviewFragment
            )
            binding.bottomNavigation.visibility =
                if (destination.id in hideBottomNavOn)
                    android.view.View.GONE
                else
                    android.view.View.VISIBLE
        }
    }
}