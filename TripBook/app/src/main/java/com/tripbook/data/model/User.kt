package com.tripbook.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents the logged-in user's profile.
 * savedSites is a list of TouristSite IDs the user has bookmarked.
 * stepCount is read from the device step counter sensor.
 */
@Parcelize
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val bio: String = "",
    val savedSites: List<String> = emptyList(),
    val stepCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable