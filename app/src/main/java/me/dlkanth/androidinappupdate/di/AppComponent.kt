package me.dlkanth.androidinappupdate.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import me.dlkanth.androidinappupdate.CustomApp

@Component(modules = [AppModule::class, ActivityContributor::class, AndroidInjectionModule::class])
interface AppComponent: AndroidInjector<CustomApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AndroidInjector<CustomApp>

    }

}