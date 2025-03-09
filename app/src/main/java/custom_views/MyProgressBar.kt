package custom_views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.MyProgressBarBinding

class MyProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val b = MyProgressBarBinding.inflate(LayoutInflater.from(context))

    var progress: Int
        get() = b.progressBar.progress
        set(value) {
            b.progressBar.progress = value
        }

    init {
        initializeAttributes(attrs)
        addView(b.root)
    }

    private fun initializeAttributes(attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar, defStyleAttr, 0)
        try {
            val backgroundColor = typedArray.getColor(R.styleable.MyProgressBar_backgroundColor, Color.WHITE)
            val cornerRadius = typedArray.getDimension(R.styleable.MyProgressBar_cornerRadius, 1000f)
            val progressColor = typedArray.getColor(R.styleable.MyProgressBar_progressColor, Color.YELLOW)
            val progress = typedArray.getInt(R.styleable.MyProgressBar_progress, 50)
            val max = typedArray.getInt(R.styleable.MyProgressBar_max, 100)
            val stroke = typedArray.getDimension(R.styleable.MyProgressBar_stroke, 4f)
            val cornerRadiusInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadius, context.resources.displayMetrics)
            val strokeInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, stroke, context.resources.displayMetrics).toInt()

            b.outerCardView.setCardBackgroundColor(backgroundColor)
            b.outerCardView.setContentPadding(strokeInPixels, strokeInPixels, strokeInPixels, strokeInPixels)
            b.outerCardView.radius = cornerRadiusInPixels
            b.innerCardView.radius = cornerRadiusInPixels
            b.progressBar.max = max
            b.progressBar.progress = progress
            b.progressBar.progressDrawable.setTint(progressColor)
            b.progressBar.progressBackgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        } finally {
            typedArray.recycle()
        }
    }

    fun setProgressColor(color: Int) {
        b.progressBar.progressDrawable.setTint(color)
    }

    fun setOuterCardColor(color: Int) {
        b.outerCardView.setCardBackgroundColor(color)
    }

    fun setInnerCardColor(color: Int) {
        b.innerCardView.setCardBackgroundColor(color)
    }

    fun setOuterCornerRadius(radius: Float) {
        b.outerCardView.radius = radius
    }

    fun setInnerCornerRadius(radius: Float) {
        b.innerCardView.radius = radius
    }
}