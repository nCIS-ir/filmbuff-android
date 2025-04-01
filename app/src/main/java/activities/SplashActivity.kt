package activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import database.models.Activity
import database.models.Artist
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
import ir.ncis.filmbuff.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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

        lifecycleScope.launch { App.DB.clearAllTables() }

        nextActivity = MainActivity::class.java
        if (!Hawk.contains(KeyString.TOKEN)) {
            nextActivity = AuthActivity::class.java
        } else {
            refreshToken()
        }

        lifecycleScope.launch {
            val apiCalls = listOf<suspend () -> Unit>(
                //region Load Activities
                {
                    var shouldStop = false
                    for (i in 0 until retries) {
                        if (shouldStop) break
                        Base.activities()
                            .onSuccess {
                                App.DB.activityDao().insert(*Activity.from(it).toTypedArray())
                                App.HANDLER.post { b.pbLoading.progress++ }
                                shouldStop = true
                            }
                            .onFailure {
                                if (i == retries - 1) {
                                    App.exit()
                                }
                            }
                    }
                },
                //endregion
                //region Load Artists
                {
                    var shouldStop = false
                    for (i in 0 until retries) {
                        if (shouldStop) break
                        Base.artists()
                            .onSuccess {
                                App.DB.artistDao().insert(*Artist.from(it).toTypedArray())
                                App.HANDLER.post { b.pbLoading.progress++ }
                                shouldStop = true
                            }
                            .onFailure {
                                if (i == retries - 1) {
                                    App.exit()
                                }
                            }
                    }
                },
                //endregion
                //region Load Countries
                {
                    var shouldStop = false
                    for (i in 0 until retries) {
                        if (shouldStop) break
                        Base.countries()
                            .onSuccess {
                                App.DB.countryDao().insert(*Country.from(it).toTypedArray())
                                App.HANDLER.post { b.pbLoading.progress++ }
                                shouldStop = true
                            }
                            .onFailure {
                                if (i == retries - 1) {
                                    App.exit()
                                }
                            }
                    }
                },
                //endregion
                //region Load Genres
                {
                    var shouldStop = false
                    for (i in 0 until retries) {
                        if (shouldStop) break
                        Base.genres()
                            .onSuccess {
                                App.DB.genreDao().insert(*Genre.from(it).toTypedArray())
                                App.HANDLER.post { b.pbLoading.progress++ }
                                shouldStop = true
                            }
                            .onFailure {
                                if (i == retries - 1) {
                                    App.exit()
                                }
                            }
                    }
                },
                //endregion
                //region Load Languages
                {
                    var shouldStop = false
                    for (i in 0 until retries) {
                        if (shouldStop) break
                        Base.languages()
                            .onSuccess {
                                App.DB.languageDao().insert(*Language.from(it).toTypedArray())
                                App.HANDLER.post { b.pbLoading.progress++ }
                                shouldStop = true
                            }
                            .onFailure {
                                if (i == retries - 1) {
                                    App.exit()
                                }
                            }
                    }
                },
                //endregion
                //region Load Packs
                {
                    var shouldStop = false
                    for (i in 0 until retries) {
                        if (shouldStop) break
                        Base.packs()
                            .onSuccess {
                                App.DB.packDao().insert(*Pack.from(it).toTypedArray())
                                App.HANDLER.post { b.pbLoading.progress++ }
                                shouldStop = true
                            }
                            .onFailure {
                                if (i == retries - 1) {
                                    App.exit()
                                }
                            }
                    }
                },
                //endregion
                //region Load Plans
                {
                    var shouldStop = false
                    for (i in 0 until retries) {
                        if (shouldStop) break
                        Base.plans()
                            .onSuccess {
                                App.DB.planDao().insert(*Plan.from(it).toTypedArray())
                                App.HANDLER.post { b.pbLoading.progress++ }
                                shouldStop = true
                            }
                            .onFailure {
                                if (i == retries - 1) {
                                    App.exit()
                                }
                            }
                    }
                },
                //endregion
                //region Load Qualities
                {
                    var shouldStop = false
                    for (i in 0 until retries) {
                        if (shouldStop) break
                        Base.qualities()
                            .onSuccess {
                                App.DB.qualityDao().insert(*Quality.from(it).toTypedArray())
                                App.HANDLER.post { b.pbLoading.progress++ }
                                shouldStop = true
                            }
                            .onFailure {
                                if (i == retries - 1) {
                                    App.exit()
                                }
                            }
                    }
                },
                //endregion
                //region Load Roles
                {
                    var shouldStop = false
                    for (i in 0 until retries) {
                        if (shouldStop) break
                        Base.roles()
                            .onSuccess {
                                App.DB.roleDao().insert(*Role.from(it).toTypedArray())
                                App.HANDLER.post { b.pbLoading.progress++ }
                                shouldStop = true
                            }
                            .onFailure {
                                if (i == retries - 1) {
                                    App.exit()
                                }
                            }
                    }
                },
                //endregion
            )
            b.pbLoading.max = apiCalls.size
            b.pbLoading.progress = 0
            async(Dispatchers.IO) { apiCalls.forEach { it.invoke() } }.await()
            runActivity(nextActivity, shouldFinishCurrentActivity = true)
        }
    }

    private fun refreshToken() {
        TODO("Not yet implemented")
    }
}