package com.tripbook.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tripbook.app.data.model.Region
import com.tripbook.app.databinding.ItemRegionCardBinding

/**
 * RegionAdapter populates the 2-column grid on the Home screen.
 *
 * Uses ListAdapter + DiffUtil for efficient, animated list updates.
 * Each card shows the region image, name, and tagline.
 *
 * @param onRegionClick callback invoked when the user taps a card
 */
class RegionAdapter(
    private val onRegionClick: (Region) -> Unit
) : ListAdapter<Region, RegionAdapter.RegionViewHolder>(RegionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding = ItemRegionCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RegionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RegionViewHolder(
        private val binding: ItemRegionCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(region: Region) {
            binding.apply {
                textRegionName.text = region.name
                textRegionTagline.text = region.tagline
                imageRegion.setImageResource(region.imageRes)

                // Entire card is clickable
                root.setOnClickListener { onRegionClick(region) }
            }
        }
    }

    /** DiffUtil tells RecyclerView exactly which items changed, enabling smooth animations */
    private class RegionDiffCallback : DiffUtil.ItemCallback<Region>() {
        override fun areItemsTheSame(oldItem: Region, newItem: Region) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Region, newItem: Region) =
            oldItem == newItem
    }
}