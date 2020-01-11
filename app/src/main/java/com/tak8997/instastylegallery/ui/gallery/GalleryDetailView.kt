package com.tak8997.instastylegallery.ui.gallery

import android.app.Activity
import android.content.Context
import android.transition.Scene
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.data.model.GalleryItem
import com.tak8997.instastylegallery.transitions.ShowDetailTransitionSet

internal class GalleryDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    companion object {

        private lateinit var galleryDetailView: GalleryDetailView

        fun showScene(
            activity: Activity,
            container: ViewGroup,
            itemView: View,
            transitionName: String,
            item: GalleryItem?
        ): Scene {

            galleryDetailView = activity.layoutInflater.inflate(
                R.layout.gallery_detail_view,
                container,
                false
            ) as? GalleryDetailView
                ?: return Scene(container)

            galleryDetailView.setGalleryDetail(item)

            val transitionSet = ShowDetailTransitionSet(activity, transitionName, itemView, galleryDetailView)
            val scene = Scene(container, galleryDetailView as? View)
            TransitionManager.go(scene, transitionSet)

            return scene
        }

        fun hideScene() {
        }
    }

    private fun setGalleryDetail(item: GalleryItem?) {
        galleryDetailView.findViewById<TextView>(R.id.gallery_title).text = item?.imageName
        galleryDetailView.findViewById<TextView>(R.id.description).text = item?.mimeType
    }
}
