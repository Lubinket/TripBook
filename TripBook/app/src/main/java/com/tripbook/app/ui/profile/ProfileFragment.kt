package com.tripbook.app.ui.profile

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
 *   - User avatar + name + bio (editable)
 *   - Step counter reading from SensorManager (sensor requirement)
 *   - Saved sites list (from Firestore user.savedSites)
 *   - My Bookings shortcut
 *   - My Reviews shortcut
 *   - Sign Out button → Firebase Auth signOut → back to Login
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "User Profile\n(Backend partner implements this)"
            setPadding(48, 48, 48, 48)
            textSize = 16f
        }
    }
}