package com.tak8997.instastylegallery.di

import androidx.lifecycle.ViewModel
import com.tak8997.instastylegallery.ui.favorite.FavoriteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface FavoriteModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    fun bindViewModel(viewModel: FavoriteViewModel): ViewModel
}
