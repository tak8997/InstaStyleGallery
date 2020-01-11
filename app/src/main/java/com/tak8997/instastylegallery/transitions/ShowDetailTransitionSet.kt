package com.tak8997.instastylegallery.transitions

import android.content.Context
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.ui.gallery.GalleryDetailView

internal class ShowDetailTransitionSet(
    private val context: Context,
    private val transitionName: String,
    private val from: View,
    private val to: GalleryDetailView
) : TransitionSet() {

    companion object {
        private const val TITLE_TEXT_VIEW_TRANSITION_NAME = "titleTextView"
        private const val CARD_VIEW_TRANSITION_NAME = "cardView"
    }

    init {
//        addTransition(textResize())
        addTransition(slide())
        addTransition(shared())
    }

    private fun titleTransitionName(): String {
        return transitionName + TITLE_TEXT_VIEW_TRANSITION_NAME
    }

    private fun cardViewTransitionName(): String {
        return transitionName + CARD_VIEW_TRANSITION_NAME
    }

    private fun textResize(): Transition {
        return TransitionBuilder(TextResizeTransition())
            .link(from.findViewById(R.id.gallery_title), to.findViewById(R.id.gallery_title), titleTransitionName())
            .build()
    }

    private fun slide(): Transition {
        return TransitionBuilder(TransitionInflater.from(context).inflateTransition(R.transition.bali_details_enter_transition))
            .excludeTarget(transitionName, true)
            .excludeTarget(to.findViewById<TextView>(R.id.gallery_title), true)
            .excludeTarget(to.findViewById<CardView>(R.id.cardview_container), true)
            .build()
    }

    private fun shared(): Transition {
        return TransitionBuilder(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
            .link(from.findViewById(R.id.img_photo), to.findViewById(R.id.gallery_image), transitionName)
            .link(from, to.findViewById(R.id.cardview_container), cardViewTransitionName())
            .build()
    }
}
