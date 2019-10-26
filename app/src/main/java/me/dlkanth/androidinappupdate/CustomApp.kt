package me.dlkanth.androidinappupdate

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import me.dlkanth.androidinappupdate.di.DaggerAppComponent
import javax.inject.Inject

class CustomApp: Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build().inject(this)

    }

    override fun activityInjector() = activityDispatchingInjector


}