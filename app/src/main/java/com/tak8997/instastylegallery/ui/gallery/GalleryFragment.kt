package com.tak8997.instastylegallery.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.databinding.FragmentGalleryBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

internal class GalleryFragment : DaggerFragment() {

    companion object {
        fun newInstance(): GalleryFragment {
            return GalleryFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel by viewModels<GalleryViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_gallery,
            container,
            false
        )

        return binding.root
    }
}
