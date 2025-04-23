package activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import database.models.Activity
import database.models.Country
import database.models.Genre
import database.models.Language
import database.models.Pack
import database.models.Plan
import database.models.Quality
import database.models.Role
import helpers.ContextHelper
import helpers.KeyString
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.ActivitySplashBinding
import kotlinx.coroutines.launch
import retrofit.calls.Auth
import retrofit.calls.Base

@SuppressLint("CustomSplashScreen")
class SplashActivity : ActivityEnhanced() {
    private val retries = 3
    private lateinit var b: ActivitySplashBinding
    private lateinit var nextActivity: Class<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(b.root)

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
                            nextActivity = AuthActivity::class.java
                        } else {
                            getUserInfo()
                        }
                        runActivity(nextActivity, shouldFinish = true)
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

    private suspend fun getUserInfo() {
        Auth.info(
            { user ->
                App.USER = user
                nextActivity = MainActivity::class.java
            },
            { exception ->
                val statusCode = ContextHelper.getHttpStatus(exception)
                if (statusCode != null) {
                    when (statusCode) {
                        401 -> lifecycleScope.launch { refreshToken() }
                        403 -> nextActivity = AuthActivity::class.java
                        else -> App.exit()
                    }
                }
            }
        )
    }

    private suspend fun refreshToken() {
        Hawk.put(KeyString.TOKEN, Hawk.get(KeyString.REFRESH, ""))
        Auth.refresh(
            {
                Hawk.put(KeyString.TOKEN, it.token)
                Hawk.put(KeyString.REFRESH, it.refresh)
                lifecycleScope.launch { getUserInfo() }
            },
            {
                App.exit()
            }
        )
    }
}