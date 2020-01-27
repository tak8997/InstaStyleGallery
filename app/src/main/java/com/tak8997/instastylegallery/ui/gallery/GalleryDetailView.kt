package com.tak8997.instastylegallery.ui.gallery

import android.app.Activity
import android.content.Context
import android.transition.Scene
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.bumptech.glide.load.DecodeFormat
import com.tak8997.instastylegallery.GlideApp
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.data.model.GalleryItem
import com.tak8997.instastylegallery.transitions.HideGalleryDetailTransitionSet
import com.tak8997.instastylegallery.transitions.ShowGalleryDetailTransitionSet

internal class GalleryDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    companion object {

        fun showScene(
            activity: Activity,
            container: ViewGroup,
            itemView: View?,
            transitionName: String?,
            item: GalleryItem?
        ): Scene {

            val galleryDetailView = activity.layoutInflater.inflate(
                R.layout.gallery_detail_view,
                container,
                false
            ) as? GalleryDetailView

            if (itemView == null || transitionName == null || galleryDetailView == null) {
                return Scene(container)
            }

            galleryDetailView.setGalleryDetail(galleryDetailView, item)

            val transitionSet = ShowGalleryDetailTransitionSet(activity, transitionName, itemView, galleryDetailView)
            val scene = Scene(container, galleryDetailView as? View)
            TransitionManager.go(scene, transitionSet)

            return scene
        }

        fun hideScene(
            activity: Activity,
            container: ViewGroup,
            itemView: View?,
            transitionName: String?
        ): Scene {

            val galleryDetailView = container.findViewById(R.id.detail_root_container) as? GalleryDetailView

            if (itemView == null || transitionName == null || galleryDetailView == null) {
                return Scene(container)
            }

            val transitionSet = HideGalleryDetailTransitionSet(activity, transitionName, itemView, galleryDetailView)
            val scene = Scene(container, galleryDetailView as? View)
            TransitionManager.go(scene, transitionSet)

            return scene
        }
    }

    private fun setGalleryDetail(
        galleryDetailView: GalleryDetailView,
        item: GalleryItem?
    ) {
        GlideApp.with(this)
            .load(item?.imagePath)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .into(galleryDetailView.findViewById(R.id.gallery_image))
//        galleryDetailView.findViewById<TextView>(R.id.gallery_title).text = item?.imageName
//        galleryDetailView.findViewById<TextView>(R.id.description).text = item?.mimeType
    }
}
