package com.tripbook.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tripbook.app.R
import com.tripbook.app.data.model.Region
import com.tripbook.app.databinding.FragmentHomeBinding

/**
 * HomeFragment is the landing screen after login.
 *
 * It shows:
 *   - A greeting with the user's name
 *   - A 2-column grid of the 5 Cameroonian regions
 *
 * Tapping a region card navigates to RegionFragment,
 * passing the Region object via Safe Args.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var regionAdapter: RegionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        regionAdapter = RegionAdapter { region ->
            // Navigate to RegionFragment, passing the selected Region
            val action = HomeFragmentDirections.actionHomeToRegion(region)
            findNavController().navigate(action)
        }

        binding.recyclerRegions.apply {
            // 2-column grid
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = regionAdapter
            // Smooth scroll performance hint
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        // Show greeting text
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.textGreeting.text = getString(R.string.greeting_format, name)
        }

        // Populate the region grid
        viewModel.regions.observe(viewLifecycleOwner) { regions ->
            regionAdapter.submitList(regions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // IMPORTANT: Always null the binding in onDestroyView to avoid memory leaks
        _binding = null
    }
}