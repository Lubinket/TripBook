package com.tripbook.app.ui.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripbook.app.data.SiteData
import com.tripbook.app.data.model.Region
import com.tripbook.app.data.model.TouristSite

/**
 * RegionViewModel loads the 4 sites for the selected region.
 *
 * The region is set once (after fragment creation) via setRegion().
 * This avoids passing constructor arguments to the ViewModel,
 * which is not directly supported by ViewModelProvider.
 */
class RegionViewModel : ViewModel() {

    private val _sites = MutableLiveData<List<TouristSite>>()
    val sites: LiveData<List<TouristSite>> = _sites

    private val _region = MutableLiveData<Region>()
    val region: LiveData<Region> = _region

    fun setRegion(region: Region) {
        _region.value = region
        _sites.value = SiteData.getSitesByRegion(region.id)
    }
}