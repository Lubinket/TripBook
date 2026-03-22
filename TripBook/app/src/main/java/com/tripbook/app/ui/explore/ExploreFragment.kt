package com.tripbook.app.ui.explore

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.tripbook.app.R
import com.tripbook.app.databinding.FragmentExploreBinding
import com.tripbook.app.ui.region.SiteAdapter

/**
 * ExploreFragment allows users to search and filter all 20 sites.
 *
 * UI elements:
 *   - EditText search bar (filters as user types)
 *   - ChipGroup with one chip per region (tap to filter, tap again to clear)
 *   - RecyclerView of matching sites using the same SiteAdapter as RegionFragment
 *   - Empty state message when no results match
 */
class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExploreViewModel by viewModels()
    private lateinit var siteAdapter: SiteAdapter

    // Map from chip ID → region ID string (matches SiteData.regions ids)
    private val chipRegionMap = mutableMapOf<Int, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchBar()
        setupRegionChips()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        siteAdapter = SiteAdapter { site ->
            val action = ExploreFragmentDirections.actionExploreToSiteDetail(site)
            findNavController().navigate(action)
        }
        binding.recyclerResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = siteAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupSearchBar() {
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchQueryChanged(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Clear button inside the search bar
        binding.imageSearchClear.setOnClickListener {
            binding.editSearch.text?.clear()
        }
    }

    private fun setupRegionChips() {
        // Dynamically create one chip per region
        // This keeps chips in sync with SiteData without hardcoding IDs in XML
        val regions = listOf(
            "littoral" to "Littoral",
            "southwest" to "South West",
            "centre" to "Centre",
            "north" to "North",
            "west" to "West"
        )

        regions.forEach { (regionId, regionName) ->
            val chip = Chip(requireContext()).apply {
                text = regionName
                isCheckable = true
                id = View.generateViewId()
                setChipBackgroundColorResource(R.color.chip_background_selector)
                setTextColor(resources.getColorStateList(R.color.chip_text_selector, null))
            }
            chipRegionMap[chip.id] = regionId
            binding.chipGroupRegions.addView(chip)

            chip.setOnClickListener {
                viewModel.onRegionFilterChanged(regionId)
                syncChipStates()
            }
        }
    }

    /** Keep chip checked states in sync with ViewModel filter state */
    private fun syncChipStates() {
        val activeFilter = viewModel.getActiveRegionFilter()
        for (i in 0 until binding.chipGroupRegions.childCount) {
            val chip = binding.chipGroupRegions.getChildAt(i) as? Chip ?: continue
            chip.isChecked = chipRegionMap[chip.id] == activeFilter && activeFilter.isNotEmpty()
        }
    }

    private fun observeViewModel() {
        viewModel.results.observe(viewLifecycleOwner) { sites ->
            siteAdapter.submitList(sites)

            // Show/hide empty state
            val isEmpty = sites.isEmpty()
            binding.textEmptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.recyclerResults.visibility = if (isEmpty) View.GONE else View.VISIBLE

            // Update result count label
            binding.textResultCount.text = resources.getQuantityString(
                R.plurals.result_count, sites.size, sites.size
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}