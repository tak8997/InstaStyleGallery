package com.tak8997.instastylegallery.ui.gallery.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.data.GalleryItem
import com.tak8997.instastylegallery.databinding.ItemGalleryBinding

internal class GalleryItemAdapter : ListAdapter<GalleryItem, GalleryItemAdapter.GalleryItemViewHolder>(sDiffUtil) {

    companion object {

        private val sDiffUtil = object : DiffUtil.ItemCallback<GalleryItem>() {
            override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GalleryItem,
                newItem: GalleryItem
            ): Boolean {
                return oldItem.bucketId == newItem.bucketId
            }
        }
    }


    private val galleryItems = mutableListOf<GalleryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemGalleryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_gallery,
            parent,
            false
        )

        return GalleryItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return galleryItems.size
    }

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        holder.bind(galleryItems[position])
    }

    class GalleryItemViewHolder(private val binding: ItemGalleryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(galleryItem: GalleryItem) {
            binding.galleryItem = galleryItem
        }
    }
}