package com.tripbook.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripbook.app.data.SiteData
import com.tripbook.app.data.model.Region

/**
 * HomeViewModel provides data to HomeFragment.
 *
 * It loads the 5 regions from SiteData and exposes them as LiveData.
 * The ViewModel survives configuration changes (screen rotation),
 * so the fragment never reloads data unnecessarily.
 */
class HomeViewModel : ViewModel() {

    private val _regions = MutableLiveData<List<Region>>()
    val regions: LiveData<List<Region>> = _regions

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    init {
        loadRegions()
        loadUserName()
    }

    private fun loadRegions() {
        _regions.value = SiteData.regions
    }

    private fun loadUserName() {
        // Get the display name from Firebase Auth
        // If user has no name set, fall back to "Traveler"
        val firebaseUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
        _userName.value = firebaseUser?.displayName?.ifBlank { "Traveler" } ?: "Traveler"
    }
}