package me.dlkanth.androidinappupdate

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

import me.dlkanth.androidinappupdate.di.ActivityContributor

@Component(modules = [ActivityContributor::class, TestAppModule::class, AndroidInjectionModule::class])
interface TestAppComponent: AndroidInjector<TestApplication> {

    @Component.Builder
    interface Builder {

        fun appModule(appModule: TestAppModule): Builder

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AndroidInjector<TestApplication>

    }

}