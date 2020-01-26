package com.tak8997.instastylegallery.ui.gallery

import android.os.Bundle
import android.transition.Scene
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tak8997.instastylegallery.GlideApp
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.databinding.FragmentGalleryBinding
import com.tak8997.instastylegallery.ui.BaseView
import com.tak8997.instastylegallery.ui.MainActivity
import com.tak8997.instastylegallery.ui.gallery.gallery.GalleryItemAdapter
import com.tak8997.instastylegallery.util.TransitionUtils
import com.tak8997.instastylegallery.widget.GalleryItemDecoration
import dagger.android.support.DaggerFragment
import javax.inject.Inject

internal class GalleryFragment : DaggerFragment(), LifecycleOwner, BaseView {

    companion object {
        const val TAG = "GalleryFragment"

        fun newInstance() = GalleryFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentGalleryBinding

    private val viewModel by viewModels<GalleryViewModel> { viewModelFactory }
    private val galleryAdapter by lazy {
        GalleryItemAdapter(GlideApp.with(this), viewModel::onItemLongClick)
    }

    private var detailScene: Scene? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                    viewModel.fetchGalleryItems()
                }
            })
            galleryItems.observe(viewLifecycleOwner, Observer {
                galleryAdapter.submitList(it)
            })
            detailScene.observe(viewLifecycleOwner, Observer { (itemView, transitionName, galleryItem) ->
                this@GalleryFragment.detailScene = GalleryDetailView.showScene(
                    requireActivity(),
                    binding.container,
                    itemView,
                    transitionName,
                    galleryItem
                )
            })
        }
    }

    override fun onBackPressed() {
        if (detailScene != null) {
            val currentTransitionName = viewModel.transitionName.value
            val childPosition = TransitionUtils.getItemPositionFromTransition(currentTransitionName)
            GalleryDetailView.hideScene(requireActivity(), binding.container, getSharedViewByPosition(childPosition), currentTransitionName)
            notifyLayoutOnBackPressed()
            detailScene = null
        } else {
            (requireActivity() as? MainActivity)?.superOnBackPressed()
        }
    }

    private fun notifyLayoutOnBackPressed() {
        binding.container.removeAllViews()
        binding.container.addView(binding.recyclerGallery)
        binding.recyclerGallery.requestLayout()
    }

    private fun getSharedViewByPosition(childPosition: Int): View? {
        for (i in 0 until binding.recyclerGallery.childCount) {
            if (childPosition == binding.recyclerGallery.getChildAdapterPosition(binding.recyclerGallery.getChildAt(i))) {
                return binding.recyclerGallery.getChildAt(i)
            }
        }
        return null
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
