package com.tripbook.app.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * STUB — To be fully implemented by backend partner.
 *
 * This screen shows:
 *   - All reviews written by the current user
 *   - Fetched from Firestore filtered by userId
 *   - Each row shows site name, rating, comment excerpt
 */
class MyReviewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "My Reviews\n(Backend partner implements this)"
            setPadding(48, 48, 48, 48)
            textSize = 16f
        }
    }
}