package helpers

import androidx.core.content.ContextCompat
import com.google.gson.GsonBuilder
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import retrofit.models.ResponseWrapper
import java.io.File

object ContextHelper {
    fun copyAssetToFile(assetName: String): File {
        val outFile = File(App.CONTEXT.cacheDir, assetName)
        if (!outFile.exists()) {
            App.CONTEXT.assets.open(assetName).use { input ->
                outFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
        return outFile
    }

    fun dpToPx(dp: Int) = (dp * App.CONTEXT.resources.displayMetrics.density).toInt()

    fun getColor(colorResId: Int): Int = ContextCompat.getColor(App.CONTEXT, colorResId)

    fun getHttpStatus(e: Throwable): Int? {
        val errorMessage = e.localizedMessage ?: App.CONTEXT.getString(R.string.error_unknown)
        return errorMessage.substringAfter("HTTP ").substringBefore(":").toIntOrNull()
    }

    fun getHttpMessage(e: Throwable): String? {
        val errorMessage = e.localizedMessage ?: App.CONTEXT.getString(R.string.error_unknown)
        return GsonBuilder().create().fromJson(errorMessage.substringAfter(": "), ResponseWrapper::class.java).message
    }

    fun getLanguage(): String = App.CONTEXT.resources.configuration.locales[0].language
}