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
    private val glide: GlideRequests,
    private val itemCallback: (GalleryItem) -> Unit
) : PagedListAdapter<GalleryItem, GalleryItemAdapter.GalleryItemViewHolder>(diffUtil) {

    companion object {

        private val diffUtil = object : DiffUtil.ItemCallback<GalleryItem>() {
            override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
                return oldItem.imageId == newItem.imageId
            }

            override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
                return oldItem.imagePath == newItem.imagePath
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

        return GalleryItemViewHolder(binding, glide).apply {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    getItem(adapterPosition)?.let { item ->
                        itemCallback.invoke(item)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GalleryItemViewHolder(
        private val binding: ItemGalleryBinding,
        private val glide: GlideRequests
    ) : RecyclerView.ViewHolder(binding.root) {

        var galleryItem: GalleryItem? = null

        fun bind(galleryItem: GalleryItem?) {
            binding.imgPhoto.setImageThumbnail(galleryItem, glide)
            this.galleryItem = galleryItem
        }
    }
}
