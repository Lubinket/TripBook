package com.tripbook.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a user review for a touristic site.
 * photoUrl is nullable — camera photo is optional when writing a review.
 */
@Parcelize
data class Review(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val userPhotoUrl: String? = null,
    val siteId: String = "",
    val rating: Float = 0f,
    val title: String = "",
    val comment: String = "",
    val photoUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable