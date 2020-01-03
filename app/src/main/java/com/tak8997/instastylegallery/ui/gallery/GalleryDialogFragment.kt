package com.tak8997.instastylegallery.ui.gallery

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tak8997.instastylegallery.GlideApp
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.data.model.GalleryItem
import com.tak8997.instastylegallery.databinding.DialogFragmentGalleryBinding
import com.tak8997.instastylegallery.util.toPx

internal class GalleryDialogFragment : DialogFragment() {

    companion object {

        private const val ITEM_KEY_GALLERY = "item_gallery"
        private const val ANIMATION_DURATION = 1500L

        fun newInstance(galleryItem: GalleryItem): GalleryDialogFragment {
            return GalleryDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ITEM_KEY_GALLERY, galleryItem)
                }
            }
        }
    }

    private lateinit var binding: DialogFragmentGalleryBinding

    private var galleryItem: GalleryItem? = null

    private var posEndX: Float = 0.0f
    private var posEndY: Float = 0.0f

    private val photoDetailAnimator by lazy {
//        val scaleX = ObjectAnimator.ofFloat(binding.container, "ScaleX", 0.6f, 1f).apply {
//            duration = ANIMATION_DURATION
//        }
//        val scaleY = ObjectAnimator.ofFloat(binding.container, "ScaleY", 0.6f, 1f).apply {
//            duration = ANIMATION_DURATION
//        }
        Log.d("MY_LOG", "x : ${galleryItem?.posX} / ${galleryItem?.posX?.toPx}  , y : ${galleryItem?.posY}")
        val moveX = ObjectAnimator.ofFloat(binding.container, "x", galleryItem?.posX?.toFloat() ?: 0.0f, posEndX).apply {
            duration = ANIMATION_DURATION
        }
        val moveY = ObjectAnimator.ofFloat(binding.container, "y", galleryItem?.posY?.toFloat() ?: 0.0f, posEndY).apply {
            duration = ANIMATION_DURATION
        }

        AnimatorSet().apply {
            interpolator = AccelerateInterpolator()
            playTogether(
                moveX,
                moveY
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme)

        galleryItem = arguments?.getParcelable(ITEM_KEY_GALLERY)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_fragment_gallery,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlideApp.with(this)
            .load(galleryItem?.imagePath)
            .fitCenter()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.imgPhotoDetail)

        binding.container.viewTreeObserver.addOnGlobalLayoutListener {
            posEndX = binding.imgPhotoDetail.x
            posEndY = binding.imgPhotoDetail.y
            Log.d("MY_LOG", binding.imgPhotoDetail.x.toString())
            Log.d("MY_LOG", binding.imgPhotoDetail.y.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        photoDetailAnimator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        photoDetailAnimator.cancel()
    }
}
