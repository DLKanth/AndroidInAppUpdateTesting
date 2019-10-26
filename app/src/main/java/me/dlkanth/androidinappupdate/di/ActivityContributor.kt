package me.dlkanth.androidinappupdate.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.dlkanth.androidinappupdate.MainActivity

@Module
abstract class ActivityContributor {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}