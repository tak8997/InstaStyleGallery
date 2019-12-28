package com.tak8997.instastylegallery.di.uimodule

import androidx.lifecycle.ViewModel
import com.tak8997.instastylegallery.di.ViewModelKey
import com.tak8997.instastylegallery.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindViewModel(viewModel: MainViewModel): ViewModel
}
