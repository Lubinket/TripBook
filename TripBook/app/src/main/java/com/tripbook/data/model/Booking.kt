package com.tripbook.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a user's trip booking.
 * Status values: "upcoming", "pending", "past", "cancelled"
 */
@Parcelize
data class Booking(
    val id: String = "",
    val userId: String = "",
    val siteId: String = "",
    val siteName: String = "",
    val siteImageRes: Int = 0,
    val date: String = "",
    val travelers: Int = 1,
    val specialRequests: String = "",
    val status: String = "pending",
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable