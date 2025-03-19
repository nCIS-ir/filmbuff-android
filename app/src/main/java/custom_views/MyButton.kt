package custom_views

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.MyButtonBinding

class MyButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, private val defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private val b = MyButtonBinding.inflate(LayoutInflater.from(context))

    var showBackground: Boolean
        get() = b.ivBackground.isVisible
        set(value) {
            b.ivBackground.visibility = if (value) VISIBLE else INVISIBLE
        }

    var text: CharSequence
        get() = b.tvTitle.text
        set(value) {
            b.tvTitle.text = value
        }

    var textColor: Int
        get() = b.tvTitle.textColors.defaultColor
        set(value) {
            b.tvTitle.setTextColor(value)
        }

    var textSize: Float
        get() = b.tvTitle.textSize
        set(value) {
            b.tvTitle.textSize = value
        }

    var typeFace: Typeface?
        get() = b.tvTitle.typeface
        set(value) {
            b.tvTitle.typeface = value
        }

    init {
        initializeAttributes(attrs)
        addView(b.root)
    }

    private fun initializeAttributes(attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyButton, defStyleAttr, 0)
        try {
            val fontFamily = typedArray.getFont(R.styleable.MyButton_font)
            val showBackground = typedArray.getBoolean(R.styleable.MyButton_showBackground, true)
            val text = typedArray.getText(R.styleable.MyButton_text)
            val textColor = typedArray.getColor(R.styleable.MyButton_textColor, Color.WHITE)
            val textSize = typedArray.getDimension(R.styleable.MyButton_textSize, 16f)
            val textSizeInPixels = textSize / context.resources.displayMetrics.density

            b.ivBackground.visibility = if (showBackground) VISIBLE else INVISIBLE
            b.tvTitle.typeface = fontFamily
            b.tvTitle.text = text
            b.tvTitle.setTextColor(textColor)
            b.tvTitle.textSize = textSizeInPixels
        } finally {
            typedArray.recycle()
        }
    }
}