package com.tak8997.instastylegallery.ui.gallery.gallery

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
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
            itemView.setOnLongClickListener { _ ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
//                    val pos = IntArray(2)
//
//                    val loc = itemView.locationOnScreen()
//                    val loc1 = itemView.locationInWindow()
//
//                    Log.d("MY_LOG", "loc : $loc , ${itemView.left}, ${itemView.top}")
//                    Log.d("MY_LOG", "loc1 : $loc1")
                    getItem(adapterPosition)?.let { it ->
                        it.posX = itemView.left
                        it.posY = itemView.top
//                        itemCallback.invoke(it)
                    }
                    itemView1 = itemView

                    photoDetailAnimator.start()
                }
                return@setOnLongClickListener true
            }
        }
    }
    private lateinit var itemView1: View
    private val photoDetailAnimator by lazy {
        //        val scaleX = ObjectAnimator.ofFloat(binding.container, "ScaleX", 0.6f, 1f).apply {
//            duration = ANIMATION_DURATION
//        }
//        val scaleY = ObjectAnimator.ofFloat(binding.container, "ScaleY", 0.6f, 1f).apply {
//            duration = ANIMATION_DURATION
//        }
        val moveX = ObjectAnimator.ofFloat(itemView1, "x", itemView1.left.toFloat(), 42f).apply {
            duration = 1500
        }
        val moveY = ObjectAnimator.ofFloat(itemView1, "y", itemView1.top.toFloat(), 334f).apply {
            duration = 1500
        }

        AnimatorSet().apply {
            interpolator = AccelerateInterpolator()
            playTogether(
                moveX,
                moveY
            )
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
