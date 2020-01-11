package com.tak8997.instastylegallery.util

internal class TransitionUtils {

    companion object {
        private const val DEFAULT_TRANSITION_NAME = "transition"

        fun getItemPositionFromTransition(transitionName: String): Int {
            return transitionName.substring(transitionName.length - 1).toInt()
        }

        fun getRecyclerViewTransitionName(position: Int): String {
            return DEFAULT_TRANSITION_NAME + position
        }
    }
}
