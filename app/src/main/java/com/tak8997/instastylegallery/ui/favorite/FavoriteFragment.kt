package com.tak8997.instastylegallery.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.databinding.FragmentFavoriteBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

internal class FavoriteFragment : DaggerFragment() {

    companion object {
        const val TAG = "FavoriteFragment"

        fun newInstance() = FavoriteFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorite,
            container,
            false
        )

        return binding.root
    }
}