package com.tripbook.app.ui.mybookings

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
 *   - Tabs: Upcoming / Pending / Past / Cancelled
 *   - RecyclerView of bookings fetched from Firestore for current user
 *   - Tap a booking → shows booking detail
 */
class MyBookingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "My Bookings\n(Backend partner implements this)"
            setPadding(48, 48, 48, 48)
            textSize = 16f
        }
    }
}