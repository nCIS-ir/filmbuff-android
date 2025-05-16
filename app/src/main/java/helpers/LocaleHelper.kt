package helpers

import activities.SplashActivity
import android.content.Context
import android.content.Intent
import com.orhanobut.hawk.Hawk
import ir.ncis.filmbuff.App
import java.util.Locale

object LocaleHelper {
    const val EN = "en"
    const val FA = "fa"

    fun changeLocale(context: Context, language: String) {
        Hawk.put(KeyHelper.LOCALE, language)
        val intent = Intent(context, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        setLocale(context, language)
        App.ACTIVITY.startActivity(intent)
        App.ACTIVITY.finishAffinity()
    }

    fun setLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        Hawk.put(KeyHelper.LOCALE, language)
        return context.createConfigurationContext(config)
    }

    fun getCurrentLocale(): String = Hawk.get(KeyHelper.LOCALE, Locale.getDefault().language)
}