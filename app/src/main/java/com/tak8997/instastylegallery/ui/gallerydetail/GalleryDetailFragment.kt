package com.tak8997.instastylegallery.ui.gallerydetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.databinding.FragmentGalleryDetailBinding
import com.tak8997.instastylegallery.ui.gallerydetail.GalleryDetailFragmentArgs.Companion.fromBundle
import dagger.android.support.DaggerFragment
import javax.inject.Inject

internal class GalleryDetailFragment : DaggerFragment() {

    companion object {
        const val GALLERY_ITEM = "galleryItem"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentGalleryDetailBinding

    private val viewModel by viewModels<GalleryDetailViewModel> { viewModelFactory }

    private val galleryItem by lazy {
        arguments?.let {
            fromBundle(it).galleryItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentGalleryDetailBinding>(
            inflater,
            R.layout.fragment_gallery_detail,
            container,
            false
        ).apply {
            lifecycleOwner = this@GalleryDetailFragment.viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MY_LOG", "path : ${galleryItem?.imagePath}")
    }
}
