package com.tripbook.app.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val bio: String = "",
    val savedSites: List<String> = emptyList(),
    val stepCount: Int = 0,
    val createdAt: Long = 0L
)