package com.tripbook.app.ui.sitedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tripbook.app.data.model.Review
import com.tripbook.app.data.model.TouristSite

/**
 * SiteDetailViewModel manages the state of the Site Detail screen.
 *
 * Responsibilities:
 *  - Hold the current site data
 *  - Track whether the site is saved/bookmarked by the current user
 *  - Load reviews for this site from Firestore
 *  - Toggle save state and persist it to Firestore
 */
class SiteDetailViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _site = MutableLiveData<TouristSite>()
    val site: LiveData<TouristSite> = _site

    private val _isSaved = MutableLiveData<Boolean>(false)
    val isSaved: LiveData<Boolean> = _isSaved

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    private val _isLoadingReviews = MutableLiveData<Boolean>(false)
    val isLoadingReviews: LiveData<Boolean> = _isLoadingReviews

    fun setSite(site: TouristSite) {
        _site.value = site
        checkIfSaved(site.id)
        loadReviews(site.id)
    }

    /** Check Firestore to see if the current user has saved this site */
    private fun checkIfSaved(siteId: String) {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId).get()
            .addOnSuccessListener { doc ->
                val savedSites = doc.get("savedSites") as? List<*> ?: emptyList<String>()
                _isSaved.value = savedSites.contains(siteId)
            }
    }

    /** Load reviews for this site from Firestore, ordered by most recent */
    fun loadReviews(siteId: String) {
        _isLoadingReviews.value = true
        db.collection("reviews")
            .whereEqualTo("siteId", siteId)
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { snapshot ->
                val reviewList = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Review::class.java)?.copy(id = doc.id)
                }
                _reviews.value = reviewList
                _isLoadingReviews.value = false
            }
            .addOnFailureListener {
                _reviews.value = emptyList()
                _isLoadingReviews.value = false
            }
    }

    /**
     * Toggle the save/bookmark state for the current user.
     * Updates Firestore using arrayUnion / arrayRemove.
     */
    fun toggleSave() {
        val userId = auth.currentUser?.uid ?: return
        val siteId = _site.value?.id ?: return
        val currentlySaved = _isSaved.value ?: false

        val userRef = db.collection("users").document(userId)
        val update = if (currentlySaved) {
            hashMapOf<String, Any>(
                "savedSites" to com.google.firebase.firestore.FieldValue.arrayRemove(siteId)
            )
        } else {
            hashMapOf<String, Any>(
                "savedSites" to com.google.firebase.firestore.FieldValue.arrayUnion(siteId)
            )
        }

        // Optimistic update — flip UI immediately, then persist
        _isSaved.value = !currentlySaved
        userRef.update(update).addOnFailureListener {
            // If Firestore fails, revert the UI
            _isSaved.value = currentlySaved
        }
    }
}