package com.tripbook.app.ui.sitedetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tripbook.app.R
import com.tripbook.app.data.model.Review
import com.tripbook.app.databinding.ItemReviewBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ReviewAdapter displays user reviews on the Site Detail screen.
 *
 * Each row shows:
 *   - Reviewer's avatar (loaded via Glide from photoUrl, or placeholder)
 *   - Reviewer name
 *   - Star rating
 *   - Review title + comment
 *   - Optional review photo
 *   - Formatted date
 */
class ReviewAdapter : ListAdapter<Review, ReviewAdapter.ReviewViewHolder>(ReviewDiffCallback()) {

    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReviewViewHolder(
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.apply {
                textReviewerName.text = review.userName.ifBlank { "Anonymous" }
                textReviewTitle.text = review.title
                textReviewComment.text = review.comment
                ratingBarReview.rating = review.rating
                textReviewDate.text = dateFormat.format(Date(review.createdAt))

                // Load reviewer avatar from Firestore URL, or show placeholder
                Glide.with(imageReviewerAvatar.context)
                    .load(review.userPhotoUrl)
                    .placeholder(R.drawable.ic_person_placeholder)
                    .circleCrop()
                    .into(imageReviewerAvatar)

                // Load optional review photo if present
                if (!review.photoUrl.isNullOrEmpty()) {
                    imageReviewPhoto.visibility = View.VISIBLE
                    Glide.with(imageReviewPhoto.context)
                        .load(review.photoUrl)
                        .placeholder(R.drawable.ic_image_placeholder)
                        .into(imageReviewPhoto)
                } else {
                    imageReviewPhoto.visibility = View.GONE
                }
            }
        }
    }

    private class ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Review, newItem: Review) =
            oldItem == newItem
    }
}