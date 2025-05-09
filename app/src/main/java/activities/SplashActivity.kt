package activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar.make
import com.orhanobut.hawk.Hawk
import database.models.Activity
import database.models.Country
import database.models.Genre
import database.models.Language
import database.models.Pack
import database.models.Plan
import database.models.Quality
import database.models.Role
import helpers.KeyString
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R.string.permission_action
import ir.ncis.filmbuff.R.string.permission_message
import ir.ncis.filmbuff.databinding.ActivitySplashBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth
import retrofit.calls.Base

@SuppressLint("CustomSplashScreen")
class SplashActivity : ActivityEnhanced() {
    private val retries = 3
    private lateinit var b: ActivitySplashBinding
    private val permissionActivityResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            delayedDismiss()
        } else {
            make(b.vgRoot, this.getString(permission_message), LENGTH_INDEFINITE)
                .setAction(this.getString(permission_action)) { checkForPermissions() }
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(b.root)

        checkForPermissions()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                checkedExit()
            }
        })
    }

    private fun checkForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                delayedDismiss()
            } else {
                permissionActivityResult.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            delayedDismiss()
        }
    }

    private fun delayedDismiss() {
        App.HANDLER.post {
            lifecycleScope.launch {
                Base.info(
                    { info ->
                        lifecycleScope.launch {
                            App.HANDLER.post {
                                b.pbLoading.max = 9
                                b.pbLoading.progress = 0
                            }
                            App.DB.clearAllTables()
                            App.HANDLER.post { b.pbLoading.progress++ }
                            App.DB.activityDao().insert(*Activity.from(info.activities).toTypedArray())
                            App.HANDLER.post { b.pbLoading.progress++ }
                            App.DB.countryDao().insert(*Country.from(info.countries).toTypedArray())
                            App.HANDLER.post { b.pbLoading.progress++ }
                            App.DB.genreDao().insert(*Genre.from(info.genres).toTypedArray())
                            App.HANDLER.post { b.pbLoading.progress++ }
                            App.DB.languageDao().insert(*Language.from(info.languages).toTypedArray())
                            App.HANDLER.post { b.pbLoading.progress++ }
                            App.DB.packDao().insert(*Pack.from(info.packs).toTypedArray())
                            App.HANDLER.post { b.pbLoading.progress++ }
                            App.DB.planDao().insert(*Plan.from(info.plans).toTypedArray())
                            App.HANDLER.post { b.pbLoading.progress++ }
                            App.DB.qualityDao().insert(*Quality.from(info.qualities).toTypedArray())
                            App.HANDLER.post { b.pbLoading.progress++ }
                            App.DB.roleDao().insert(*Role.from(info.roles).toTypedArray())
                            App.HANDLER.post { b.pbLoading.progress++ }
                            if (!Hawk.contains(KeyString.TOKEN)) {
                                runActivity(AuthActivity::class.java, shouldFinish = true)
                            } else {
                                Auth.info(
                                    { user ->
                                        App.USER = user
                                        runActivity(MainActivity::class.java, shouldFinish = true)
                                    },
                                    {
                                        runActivity(AuthActivity::class.java, shouldFinish = true)
                                    }
                                )
                            }
                        }
                    },
                    {
                        Toast.makeText(this@SplashActivity, it.message, Toast.LENGTH_SHORT).show()
                        App.HANDLER.postDelayed({ App.exit() }, 2000)
                    },
                    false
                )
            }
        }
    }
}