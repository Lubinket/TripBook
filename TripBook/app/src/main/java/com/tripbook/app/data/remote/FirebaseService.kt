package com.tripbook.app.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseService {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    val usersRef get() = db.collection("users")
    val regionsRef get() = db.collection("regions")
    val sitesRef get() = db.collection("sites")
    val bookingsRef get() = db.collection("bookings")
    val reviewsRef get() = db.collection("reviews")
}