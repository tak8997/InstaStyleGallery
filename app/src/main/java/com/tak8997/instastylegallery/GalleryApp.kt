package com.tak8997.instastylegallery

import android.app.Application
import android.os.StrictMode
import com.tak8997.instastylegallery.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import leakcanary.AppWatcher
import leakcanary.LeakCanary
import javax.inject.Inject

internal class GalleryApp : Application(), HasAndroidInjector {

    companion object {
        lateinit var instance: GalleryApp
            private set
    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            setupStrictMode()
        }
        setupDagger()
    }

    private fun setupStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
    }

    private fun setupDagger() {
        DaggerAppComponent
            .factory()
            .create(this)
            .inject(this)
    }

}