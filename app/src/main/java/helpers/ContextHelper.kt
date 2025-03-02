package helpers

import androidx.core.content.ContextCompat
import ir.ncis.filmbuff.App

object ContextHelper {
    fun getColor(colorResId: Int): Int = ContextCompat.getColor(App.CONTEXT, colorResId)
}