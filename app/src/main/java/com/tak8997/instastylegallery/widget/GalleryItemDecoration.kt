package com.tak8997.instastylegallery.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tak8997.instastylegallery.util.toPx

internal class GalleryItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position == 0 || position == 1 || position == 2) {
            outRect.top = 10.toPx
            outRect.bottom = 10.toPx
        } else {
            outRect.bottom = 10.toPx
        }

        val lp = view.layoutParams as? GridLayoutManager.LayoutParams
        val spanIndex = lp?.spanIndex

//        Log.d("MY_LOG", "deco : ${position}, ${itemCount}, ${spanIndex}")

        when (spanIndex) {
            0 -> {
                outRect.left = 10.toPx
                outRect.right = 5.toPx
            }
            1 -> {
                outRect.left = 5.toPx
                outRect.right = 10.toPx
            }
            2 -> {
            }
        }
    }
}
