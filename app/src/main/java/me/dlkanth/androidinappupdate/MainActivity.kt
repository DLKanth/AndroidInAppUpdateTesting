package me.dlkanth.androidinappupdate

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        appUpdateManager.registerListener { state: InstallState ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                appUpdateManager.completeUpdate()
            }
        }

        appUpdateManager.appUpdateInfo.addOnCompleteListener { appUpdateInfoTask ->

            if (appUpdateInfoTask.isSuccessful) {
                val appUpdateInfo = appUpdateInfoTask.result
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) &&
                        appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {

                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.FLEXIBLE,
                        this, 100
                    )
                } else {
                    showNoUpdateInfo()
                }

            }
        }

    }


    fun showNoUpdateInfo() {
        Toast.makeText(this, "Update not available", Toast.LENGTH_SHORT).show()
    }
}
