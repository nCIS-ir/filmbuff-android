package dialogs

import android.content.Context
import android.os.Bundle
import enums.Direction
import enums.Sort
import helpers.ContextHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.DialogEnhanced
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.DialogSortingBinding
import view_models.MainViewModel

class SortingDialog(context: Context, private val mainViewModel: MainViewModel) : DialogEnhanced(context) {
    private lateinit var b: DialogSortingBinding
    private val colorOrange300 = ContextHelper.getColor(R.color.orange_300)
    private val colorWhite = ContextHelper.getColor(R.color.white)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DialogSortingBinding.inflate(layoutInflater)
        setContentView(b.root)

        observe()

        b.ivAscending.setOnClickListener {
            b.ivAscending.setColorFilter(colorOrange300)
            b.ivDescending.setColorFilter(colorWhite)
            b.ivAscending.setTag(b.ivAscending.id, true)
            b.ivDescending.setTag(b.ivDescending.id, false)
        }

        b.ivDescending.setOnClickListener {
            b.ivAscending.setColorFilter(colorWhite)
            b.ivDescending.setColorFilter(colorOrange300)
            b.ivAscending.setTag(b.ivAscending.id, false)
            b.ivDescending.setTag(b.ivDescending.id, true)
        }

        b.btApply.setOnClickListener {
            mainViewModel.setSort(
                when {
                    b.rbPopularity.isChecked -> Sort.POPULARITY
                    b.rbRating.isChecked     -> Sort.RATING
                    b.rbRelease.isChecked    -> Sort.RELEASE
                    else                     -> Sort.POPULARITY
                }
            )
            mainViewModel.setDirection(if (b.ivAscending.getTag(b.ivAscending.id) as Boolean) Direction.ASCENDING else Direction.DESCENDING)
            dismiss()
        }
    }

    private fun observe() {
        mainViewModel.sort.observe(App.ACTIVITY) { sort ->
            when (sort) {
                Sort.POPULARITY -> b.rbPopularity.isChecked = true
                Sort.RATING     -> b.rbRating.isChecked = true
                Sort.RELEASE    -> b.rbRelease.isChecked = true
            }
        }

        mainViewModel.direction.observe(App.ACTIVITY) { direction ->
            when (direction) {
                Direction.ASCENDING  -> {
                    b.ivAscending.setColorFilter(colorOrange300)
                    b.ivDescending.setColorFilter(colorWhite)
                    b.ivAscending.setTag(b.ivAscending.id, true)
                    b.ivDescending.setTag(b.ivDescending.id, false)
                }

                Direction.DESCENDING -> {
                    b.ivAscending.setColorFilter(colorWhite)
                    b.ivDescending.setColorFilter(colorOrange300)
                    b.ivAscending.setTag(b.ivAscending.id, false)
                    b.ivDescending.setTag(b.ivDescending.id, true)
                }
            }
        }
    }
}