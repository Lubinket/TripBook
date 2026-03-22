package com.tripbook.app.ui.sitedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tripbook.app.R
import com.tripbook.app.databinding.FragmentSiteDetailBinding

class SiteDetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentSiteDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SiteDetailViewModel by viewModels()
    private val args: SiteDetailFragmentArgs by navArgs()

    private var googleMap: GoogleMap? = null
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSiteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setSite(args.site)

        setupToolbar()
        setupMap()
        setupReviewsRecyclerView()
        observeViewModel()
        setupButtons()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    private fun setupReviewsRecyclerView() {
        reviewAdapter = ReviewAdapter()
        binding.recyclerReviews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun observeViewModel() {
        viewModel.site.observe(viewLifecycleOwner) { site ->
            binding.imageSiteHero.setImageResource(site.imageRes)
            binding.textSiteName.text = site.name
            binding.textRegionTag.text = site.regionId.replaceFirstChar { it.uppercase() }
            binding.ratingBar.rating = site.rating
            binding.textRatingValue.text = String.format("%.1f", site.rating)
            binding.textReviewCount.text = getString(R.string.review_count_format, site.reviewCount)
            binding.textSiteDescription.text = site.description

            googleMap?.let { map ->
                placeMapPin(map, LatLng(site.lat, site.lng), site.name)
            }
        }

        viewModel.isSaved.observe(viewLifecycleOwner) { saved ->
            binding.fabSave.setImageResource(
                if (saved) R.drawable.ic_bookmark_filled
                else R.drawable.ic_bookmark_outline
            )
        }

        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            reviewAdapter.submitList(reviews)
            binding.textNoReviews.visibility =
                if (reviews.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerReviews.visibility =
                if (reviews.isEmpty()) View.GONE else View.VISIBLE
        }

        viewModel.isLoadingReviews.observe(viewLifecycleOwner) { loading ->
            binding.progressReviews.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    private fun setupButtons() {
        binding.fabSave.setOnClickListener {
            viewModel.toggleSave()
        }

        binding.buttonBookNow.setOnClickListener {
            val site = viewModel.site.value ?: return@setOnClickListener
            val action = SiteDetailFragmentDirections.actionSiteDetailToBooking(site)
            findNavController().navigate(action)
        }

        binding.buttonWriteReview.setOnClickListener {
            val siteId = viewModel.site.value?.id ?: return@setOnClickListener
            val action = SiteDetailFragmentDirections.actionSiteDetailToWriteReview(siteId)
            findNavController().navigate(action)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        map.uiSettings.apply {
            isScrollGesturesEnabled = false
            isZoomGesturesEnabled = false
            isRotateGesturesEnabled = false
            isTiltGesturesEnabled = false
            isZoomControlsEnabled = true
        }
        viewModel.site.value?.let { site ->
            placeMapPin(map, LatLng(site.lat, site.lng), site.name)
        }
    }

    private fun placeMapPin(map: GoogleMap, location: LatLng, title: String) {
        map.clear()
        map.addMarker(MarkerOptions().position(location).title(title))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}