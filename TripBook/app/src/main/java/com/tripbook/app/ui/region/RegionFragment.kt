package com.tripbook.app.ui.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripbook.app.databinding.FragmentRegionBinding

/**
 * RegionFragment shows the 4 touristic sites for the region
 * the user tapped on the Home screen.
 *
 * It receives the Region object via Safe Args (navArgs).
 * A back arrow in the toolbar returns the user to HomeFragment.
 */
class RegionFragment : Fragment() {

    private var _binding: FragmentRegionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegionViewModel by viewModels()
    private val args: RegionFragmentArgs by navArgs()
    private lateinit var siteAdapter: SiteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pass the received region to the ViewModel
        viewModel.setRegion(args.region)

        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        siteAdapter = SiteAdapter { site ->
            val action = RegionFragmentDirections.actionRegionToSiteDetail(site)
            findNavController().navigate(action)
        }

        binding.recyclerSites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = siteAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        viewModel.region.observe(viewLifecycleOwner) { region ->
            binding.toolbar.title = region.name
            binding.textRegionDescription.text = region.description
            binding.imageRegionHeader.setImageResource(region.imageRes)
        }

        viewModel.sites.observe(viewLifecycleOwner) { sites ->
            siteAdapter.submitList(sites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}