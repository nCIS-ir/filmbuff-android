package ir.ncis.filmbuff

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.view.LayoutInflater
import androidx.room.Room
import com.orhanobut.hawk.Hawk
import database.AppDatabase
import helpers.KeyString
import helpers.LocaleHelper
import retrofit.models.User
import kotlin.system.exitProcess

@SuppressLint("StaticFieldLeak")
class App : Application() {
    companion object {
        val HANDLER = Handler(Looper.getMainLooper())
        lateinit var ACTIVITY: ActivityEnhanced
        lateinit var INFLATER: LayoutInflater
        lateinit var CONTEXT: Context
        lateinit var DB: AppDatabase
        lateinit var USER: User

        fun exit() {
            for ((key) in Thread.getAllStackTraces()) {
                key.interrupt()
            }
            Process.killProcess(Process.myPid())
            exitProcess(0)
        }
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        Hawk.init(CONTEXT).build()
        DB = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "filmbuff.sqlite").allowMainThreadQueries().build()

        var storedLocale = Hawk.get(KeyString.LOCALE, "")
        var defaultLocale = if (storedLocale.isNotEmpty()) {
            storedLocale
        } else {
            if (LocaleHelper.getCurrentLocale() == "fa") "fa" else "en"
        }
        LocaleHelper.setLocale(this, defaultLocale)
        Hawk.put(KeyString.LOCALE, defaultLocale)
    }
}
