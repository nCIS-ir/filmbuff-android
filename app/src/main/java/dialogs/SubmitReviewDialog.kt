package dialogs

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import enums.Mode
import helpers.ViewHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.DialogEnhanced
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.DialogSubmitReviewBinding
import kotlinx.coroutines.launch
import retrofit.calls.Movie
import retrofit.calls.Serie
import retrofit.models.Review

class SubmitReviewDialog(context: Context, private val id: String, private val mode: Mode, private val currentUserReview: Review? = null, private val onSubmit: (() -> Unit)? = null) : DialogEnhanced(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = DialogSubmitReviewBinding.inflate(layoutInflater)
        setContentView(b.root)
        setCancelable(false)

        if (currentUserReview != null) {
            b.rbRating.rating = currentUserReview.score.toFloat()
            b.etContent.setText(currentUserReview.content)
        }

        b.cvConfirm.setOnClickListener {
            ViewHelper.hideKeyboard(b.etContent)
            val content = b.etContent.editableText.toString()
            if (content.isEmpty()) {
                b.tvError.text = context.getString(R.string.error_empty_comment)
            } else {
                b.tvError.text = null
                App.ACTIVITY.lifecycleScope.launch {
                    if (mode == Mode.MOVIE) {
                        Movie.reviewSubmit(
                            id,
                            b.rbRating.rating.toInt(),
                            content,
                            {
                                onSubmit?.invoke()
                                dismiss()
                            },
                            { error -> b.tvError.error = error.message }
                        )
                    } else {
                        Serie.reviewSubmit(
                            id,
                            b.rbRating.rating.toInt(),
                            content,
                            {
                                onSubmit?.invoke()
                                dismiss()
                            },
                            { error -> b.tvError.error = error.message }
                        )
                    }
                }
            }
        }

        b.cvReject.setOnClickListener { dismiss() }
    }
}