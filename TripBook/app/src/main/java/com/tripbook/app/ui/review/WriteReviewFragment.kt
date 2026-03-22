package com.tripbook.app.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

/**
 * STUB — To be fully implemented by backend partner.
 *
 * This screen handles:
 *   - Star rating selector (1–5)
 *   - Review title + body text inputs
 *   - Optional photo upload via CameraX (sensor requirement)
 *   - Submit → writes Review document to Firestore
 *
 * Receives: siteId (String) via Safe Args
 */
class WriteReviewFragment : Fragment() {

    private val args: WriteReviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "Write a Review for site: ${args.siteId}\n(Backend partner implements this)"
            setPadding(48, 48, 48, 48)
            textSize = 16f
        }
    }
}