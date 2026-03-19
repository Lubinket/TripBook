package com.tripbook.app.data.model

data class Review(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val siteId: String = "",
    val rating: Float = 0f,
    val title: String = "",
    val comment: String = "",
    val photoUrl: String? = null,
    val createdAt: Long = 0L
)