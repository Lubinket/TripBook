package com.tripbook.app.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripbook.app.data.SiteData
import com.tripbook.app.data.model.TouristSite

/**
 * ExploreViewModel manages the search and filter state for ExploreFragment.
 *
 * Features:
 *   - Full-text search across site name + description + regionId
 *   - Region filter chip (all | littoral | southwest | centre | north | west)
 *   - Combined filtering: search query AND region filter applied together
 */
class ExploreViewModel : ViewModel() {

    private val _results = MutableLiveData<List<TouristSite>>()
    val results: LiveData<List<TouristSite>> = _results

    private var currentQuery: String = ""
    private var currentRegionFilter: String = "" // empty string = no filter (show all)

    init {
        // Show all 20 sites by default
        applyFilters()
    }

    fun onSearchQueryChanged(query: String) {
        currentQuery = query
        applyFilters()
    }

    fun onRegionFilterChanged(regionId: String) {
        // If the same chip is tapped again, clear the filter
        currentRegionFilter = if (currentRegionFilter == regionId) "" else regionId
        applyFilters()
    }

    private fun applyFilters() {
        var filtered = SiteData.sites

        // Apply region filter first (fast lookup)
        if (currentRegionFilter.isNotEmpty()) {
            filtered = filtered.filter { it.regionId == currentRegionFilter }
        }

        // Apply search query
        if (currentQuery.isNotBlank()) {
            val q = currentQuery.lowercase().trim()
            filtered = filtered.filter {
                it.name.lowercase().contains(q) ||
                        it.description.lowercase().contains(q)
            }
        }

        _results.value = filtered
    }

    fun getActiveRegionFilter(): String = currentRegionFilter
}