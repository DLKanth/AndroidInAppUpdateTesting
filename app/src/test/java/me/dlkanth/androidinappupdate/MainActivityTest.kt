package me.dlkanth.androidinappupdate

import androidx.test.core.app.ApplicationProvider
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.android.controller.ComponentController
import android.app.Activity


@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class MainActivityTest {

    lateinit var fakeUpdateManager: FakeAppUpdateManager
    lateinit var mainActivityController: ActivityController<MainActivity>
    lateinit var mainActivity: MainActivity

    @Before
    fun setup() {
        mainActivityController = Robolectric.buildActivity(MainActivity::class.java)
        mainActivity = Mockito.spy(mainActivityController.get())
        fakeUpdateManager = ApplicationProvider.getApplicationContext<TestApplication>().fakeAppUpdateManager
        replaceComponentInActivityController(mainActivityController, mainActivity)
    }

    @Test
    fun `immediate update flow`() {
        fakeUpdateManager.setUpdateAvailable(2)
        assert(fakeUpdateManager.isImmediateFlowVisible)
    }

    @Test
    fun `update not available`() {
        mainActivityController.create()
        Mockito.verify(mainActivity).showNoUpdateInfo()
    }


    @Test
    fun `flexible update flow`() {
        fakeUpdateManager.setUpdateAvailable(2)
        mainActivityController.create()
        assert(fakeUpdateManager.isConfirmationDialogVisible)

        fakeUpdateManager.userAcceptsUpdate()
        fakeUpdateManager.downloadStarts()
        fakeUpdateManager.downloadCompletes()

        Mockito.verify(fakeUpdateManager).completeUpdate()
    }


    private fun replaceComponentInActivityController(activityController: ActivityController<*>, activity: Activity) {
        val componentField = ComponentController::class.java.getDeclaredField("component")
        componentField.isAccessible = true
        componentField.set(activityController, activity)
    }



}