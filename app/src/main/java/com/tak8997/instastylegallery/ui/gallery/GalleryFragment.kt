package com.tak8997.instastylegallery.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.recyclerview.widget.GridLayoutManager
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.databinding.FragmentGalleryBinding
import com.tak8997.instastylegallery.ui.gallery.gallery.GalleryItemAdapter
import com.tak8997.instastylegallery.widget.GalleryItemDecoration
import dagger.android.support.DaggerFragment
import javax.inject.Inject

internal class GalleryFragment : DaggerFragment(), LifecycleOwner {

    companion object {
        const val TAG = "GalleryFragment"

        fun newInstance() = GalleryFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel by viewModels<GalleryViewModel> { viewModelFactory }
    private val galleryAdapter by lazy { GalleryItemAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentGalleryBinding>(
            inflater,
            R.layout.fragment_gallery,
            container,
            false
        ).apply {
            lifecycleOwner = this@GalleryFragment.viewLifecycleOwner
            viewmodel = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.run {
            permissionChecked.observe(viewLifecycleOwner, Observer {
                if (it == true) {
                    viewModel.fetchGalleryItems(LoaderManager.getInstance(requireActivity()))
                }
            })
            galleryItems.observe(viewLifecycleOwner, Observer {
                if (it.isNotEmpty()) {
                    galleryAdapter.submitList(it)
                }
            })
        }
    }

    private fun setupRecycler() {
        with(binding.recyclerGallery) {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = galleryAdapter
            addItemDecoration(GalleryItemDecoration())
            setHasFixedSize(true)
        }
    }
}
