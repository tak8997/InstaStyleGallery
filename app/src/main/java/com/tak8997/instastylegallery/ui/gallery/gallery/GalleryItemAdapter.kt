package com.tak8997.instastylegallery.ui.gallery.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tak8997.instastylegallery.GlideRequests
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.data.model.GalleryItem
import com.tak8997.instastylegallery.databinding.ItemGalleryBinding

internal class GalleryItemAdapter(
    private val glide: GlideRequests
) : PagedListAdapter<GalleryItem, GalleryItemAdapter.GalleryItemViewHolder>(diffUtil) {

    companion object {

        private val diffUtil = object : DiffUtil.ItemCallback<GalleryItem>() {
            override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
                return oldItem.bucketId == newItem.bucketId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemGalleryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_gallery,
            parent,
            false
        )

        return GalleryItemViewHolder(binding, glide)
    }

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GalleryItemViewHolder(
        private val binding: ItemGalleryBinding,
        private val glide: GlideRequests
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(galleryItem: GalleryItem?) {
            binding.imgPhoto.setImageThumbnail(galleryItem, glide)
        }
    }
}
