package com.tak8997.instastylegallery.di

import com.tak8997.instastylegallery.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainFragment(): MainFragment

}
