package helpers

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
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

    fun getPasswordVisibility(editText: EditText): Boolean = editText.transformationMethod !is PasswordTransformationMethod

    fun togglePassword(imageView: ImageView, editText: EditText) {
        imageView.setImageResource(if (getPasswordVisibility(editText)) R.drawable.ic_eye_open else R.drawable.ic_eye_close)
        imageView.setOnClickListener {
            val selection = editText.selectionStart
            if (getPasswordVisibility(editText)) {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                imageView.setImageResource(R.drawable.ic_eye_close)
            } else {
                editText.transformationMethod = null
                imageView.setImageResource(R.drawable.ic_eye_open)
            }
            editText.setSelection(selection)
        }
    }
}