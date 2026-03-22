package com.tripbook.app.ui.region

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.app.data.model.TouristSite
import com.tripbook.app.databinding.ItemSiteCardBinding

/**
 * SiteAdapter is used in both RegionFragment and ExploreFragment.
 *
 * Each card shows:
 *   - Site image
 *   - Site name
 *   - Star rating + review count
 *   - Region tag label
 */
class SiteAdapter(
    private val onSiteClick: (TouristSite) -> Unit
) : ListAdapter<TouristSite, SiteAdapter.SiteViewHolder>(SiteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        val binding = ItemSiteCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SiteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SiteViewHolder(
        private val binding: ItemSiteCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(site: TouristSite) {
            binding.apply {
                imageSite.setImageResource(site.imageRes)
                textSiteName.text = site.name
                ratingBar.rating = site.rating
                textRatingValue.text = String.format("%.1f", site.rating)
                textReviewCount.text = "(${site.reviewCount} reviews)"

                root.setOnClickListener { onSiteClick(site) }
            }
        }
    }

    private class SiteDiffCallback : DiffUtil.ItemCallback<TouristSite>() {
        override fun areItemsTheSame(oldItem: TouristSite, newItem: TouristSite) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TouristSite, newItem: TouristSite) =
            oldItem == newItem
    }
}