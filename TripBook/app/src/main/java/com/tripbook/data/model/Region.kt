package com.tripbook.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents one of the 5 Cameroonian regions in the app.
 * imageRes is a drawable resource ID — images are bundled in the app.
 */
@Parcelize
data class Region(
    val id: String = "",
    val name: String = "",
    val tagline: String = "",
    val imageRes: Int = 0,
    val description: String = ""
) : Parcelable