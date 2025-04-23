package helpers

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import retrofit.models.ResponseWrapper

object ContextHelper {
    fun hideKeyboard(view: View) {
        val imm = App.CONTEXT.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getColor(colorResId: Int): Int = ContextCompat.getColor(App.CONTEXT, colorResId)

    fun getHttpStatus(e: Throwable): Int? {
        val errorMessage = e.localizedMessage ?: App.CONTEXT.getString(R.string.unknown_error)
        return errorMessage.substringAfter("HTTP ").substringBefore(":").toIntOrNull()
    }

    fun getHttpMessage(e: Throwable): String? {
        val errorMessage = e.localizedMessage ?: App.CONTEXT.getString(R.string.unknown_error)
        return GsonBuilder().create().fromJson(errorMessage.substringAfter(": "), ResponseWrapper::class.java).message
    }

    fun getLanguage(): String = App.CONTEXT.resources.configuration.locales[0].language
}