package helpers

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

object ViewHelper {
    private var autoScrollJobs: MutableMap<Int, Job> = mutableMapOf()

    fun hideKeyboard(view: View) {
        val imm = App.CONTEXT.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun startAutoScroll(viewPager: ViewPager2, interval: Long = 5000L) {
        stopAutoScroll(viewPager)
        autoScrollJobs[viewPager.id] = App.ACTIVITY.lifecycleScope.launch {
            while (isActive) {
                delay(interval)
                val nextItem = if (viewPager.currentItem == (viewPager.adapter?.itemCount ?: 1) - 1) 0 else viewPager.currentItem + 1
                viewPager.setCurrentItem(nextItem, true)
            }
        }
    }

    fun stopAutoScroll(viewPager: ViewPager2) {
        autoScrollJobs[viewPager.id]?.cancel()
        autoScrollJobs.remove(viewPager.id)
    }

    fun togglePasswordVisibility(imageView: ImageView, editText: EditText) {
        imageView.setImageResource(if (isPasswordVisible(editText)) R.drawable.ic_eye_open else R.drawable.ic_eye_close)
        imageView.setOnClickListener {
            val selection = editText.selectionStart
            if (isPasswordVisible(editText)) {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                imageView.setImageResource(R.drawable.ic_eye_close)
            } else {
                editText.transformationMethod = null
                imageView.setImageResource(R.drawable.ic_eye_open)
            }
            editText.setSelection(selection)
        }
    }

    private fun isPasswordVisible(editText: EditText): Boolean = editText.transformationMethod !is PasswordTransformationMethod
}