package com.tripbook.app.data.model

data class TouristSite(
    val id: String = "",
    val name: String = "",
    val regionId: String = "",
    val description: String = "",
    val imageRes: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val rating: Float = 0f,
    val reviewCount: Int = 0
)