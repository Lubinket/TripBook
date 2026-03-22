package com.tripbook.app.utils

object Constants {
    // Booking statuses
    const val STATUS_PENDING = "pending"
    const val STATUS_CONFIRMED = "confirmed"
    const val STATUS_CANCELLED = "cancelled"
    const val STATUS_PAST = "past"

    // Firestore collection names
    const val COLLECTION_USERS = "users"
    const val COLLECTION_REGIONS = "regions"
    const val COLLECTION_SITES = "sites"
    const val COLLECTION_BOOKINGS = "bookings"
    const val COLLECTION_REVIEWS = "reviews"

    // Region IDs
    const val REGION_LITTORAL = "littoral"
    const val REGION_SOUTHWEST = "southwest"
    const val REGION_CENTRE = "centre"
    const val REGION_NORTH = "north"
    const val REGION_WEST = "west"
}