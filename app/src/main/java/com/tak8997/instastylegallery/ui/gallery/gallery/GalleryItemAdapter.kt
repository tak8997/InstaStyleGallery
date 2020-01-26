package com.tak8997.instastylegallery.ui.gallery.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tak8997.instastylegallery.GlideRequests
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.data.model.GalleryItem
import com.tak8997.instastylegallery.databinding.ItemGalleryBinding
import com.tak8997.instastylegallery.util.TransitionUtils

internal class GalleryItemAdapter(
    private val glide: GlideRequests,
    private val itemCallback: (View, String, GalleryItem?) -> Unit
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
            itemView.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    itemCallback.invoke(binding.itemRoot, TransitionUtils.getRecyclerViewTransitionName(adapterPosition), getItem(adapterPosition))
                }
                return@setOnLongClickListener true
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

        fun bind(galleryItem: GalleryItem?) {
            binding.imgPhoto.setImageThumbnail(galleryItem, glide)
        }
    }
}
