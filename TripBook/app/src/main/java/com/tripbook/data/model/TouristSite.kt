package com.tripbook.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a single touristic site within a region.
 *
 * lat/lng stored as Double instead of LatLng so Safe Args
 * can generate navigation code without issues.
 * Convert to LatLng in the fragment when needed:
 *   LatLng(site.lat, site.lng)
 */
@Parcelize
data class TouristSite(
    val id: String = "",
    val name: String = "",
    val regionId: String = "",
    val description: String = "",
    val imageRes: Int = 0,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val isSaved: Boolean = false
) : Parcelable