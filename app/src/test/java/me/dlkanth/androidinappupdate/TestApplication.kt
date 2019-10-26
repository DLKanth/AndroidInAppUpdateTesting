package me.dlkanth.androidinappupdate

import android.app.Activity
import androidx.multidex.MultiDexApplication
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import org.mockito.Mockito
import javax.inject.Inject

class TestApplication: MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>


    val fakeAppUpdateManager by lazy {
        Mockito.spy(FakeAppUpdateManager(this))
    }

    override fun onCreate() {
        super.onCreate()

        DaggerTestAppComponent.builder()
            .appModule(TestAppModule(fakeAppUpdateManager))
            .application(this)
            .build().inject(this)
    }

    override fun activityInjector() = activityDispatchingInjector
}