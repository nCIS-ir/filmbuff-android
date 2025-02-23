package ir.ncis.filmbuff

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import com.orhanobut.hawk.Hawk

@SuppressLint("StaticFieldLeak")
class App : Application() {
    companion object {
        lateinit var ACTIVITY: ActivityEnhanced
        lateinit var INFLATER: LayoutInflater
        lateinit var CONTEXT: Context
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        Hawk.init(CONTEXT).build()
    }
}