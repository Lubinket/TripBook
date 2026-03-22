package com.tripbook.app.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tripbook.app.R

/**
 * STUB — To be fully implemented by backend partner.
 *
 * This screen handles:
 *   - Date selection for the trip
 *   - Number of travellers input
 *   - Special requests text field
 *   - Confirm Booking button → writes to Firestore → navigates to BookingConfirmationFragment
 *
 * Receives: TouristSite via Safe Args
 */
class BookingFragment : Fragment() {

    private val args: BookingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Temporary placeholder view — replace with real layout
        return TextView(requireContext()).apply {
            text = "Booking screen for: ${args.site.name}\n(Backend partner implements this)"
            setPadding(48, 48, 48, 48)
            textSize = 16f
        }
    }
}