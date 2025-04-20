package helpers

import androidx.core.content.ContextCompat
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R

object ContextHelper {
    fun getColor(colorResId: Int): Int = ContextCompat.getColor(App.CONTEXT, colorResId)

    fun getHttpStatus(e: Throwable): Int? {
        val errorMessage = e.localizedMessage ?: App.CONTEXT.getString(R.string.unknown_error)
        return errorMessage.substringAfter("HTTP ").substringBefore(":").toIntOrNull()
    }

    fun getLanguage(): String = App.CONTEXT.resources.configuration.locales[0].language
}