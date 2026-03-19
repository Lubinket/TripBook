package com.tripbook.app.data.model

data class Booking(
    val id: String = "",
    val userId: String = "",
    val siteId: String = "",
    val siteName: String = "",
    val date: String = "",
    val travelers: Int = 1,
    val specialRequests: String = "",
    val status: String = "pending",
    val createdAt: Long = 0L
)