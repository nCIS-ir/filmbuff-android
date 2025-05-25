package activities

import android.os.Bundle
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import fragments.MoviePlayFragment
import fragments.MovieReviewsFragment
import fragments.SerieAboutFragment
import helpers.ContextHelper
import helpers.KeyHelper
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.ActivitySerieBinding
import kotlinx.coroutines.launch
import retrofit.calls.Serie

class SerieActivity : ActivityEnhanced() {
    private lateinit var b: ActivitySerieBinding
    private val blue100 = ContextHelper.getColor(R.color.blue_100)
    private val blue400 = ContextHelper.getColor(R.color.blue_400)
    private val dp2 = ContextHelper.dpToPx(2)
    private val dp4 = ContextHelper.dpToPx(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySerieBinding.inflate(layoutInflater)
        setContentView(b.root)

        val serieId = intent.getStringExtra(KeyHelper.ID)
        if (serieId == null) finish()

        lifecycleScope.launch {
            Serie.details(
                serieId!!,
                { serie ->
                    showFragment(SerieAboutFragment(serie))
                    b.tvAbout.setOnClickListener { showFragment(SerieAboutFragment(serie)) }
                    b.tvReviews.setOnClickListener { showFragment(MovieReviewsFragment()) }
                    b.tvPlay.setOnClickListener { showFragment(MoviePlayFragment()) }
                },
                { finish() },
            )
        }
    }

    fun showFragment(fragment: Fragment) {
        when (fragment) {
            is SerieAboutFragment   -> {
                b.tvAbout.setTextColor(blue100)
                b.tvReviews.setTextColor(blue400)
                b.tvPlay.setTextColor(blue400)
                b.ivAbout.setColorFilter(blue100)
                b.ivAbout.updateLayoutParams { height = dp4 }
                b.ivReviews.setColorFilter(blue400)
                b.ivReviews.updateLayoutParams { height = dp2 }
                b.ivPlay.setColorFilter(blue400)
                b.ivPlay.updateLayoutParams { height = dp2 }
            }

            is MovieReviewsFragment -> {
                b.tvAbout.setTextColor(blue400)
                b.tvReviews.setTextColor(blue100)
                b.tvPlay.setTextColor(blue400)
                b.ivAbout.setColorFilter(blue400)
                b.ivAbout.updateLayoutParams { height = dp2 }
                b.ivReviews.setColorFilter(blue100)
                b.ivReviews.updateLayoutParams { height = dp4 }
                b.ivPlay.setColorFilter(blue400)
                b.ivPlay.updateLayoutParams { height = dp2 }
            }

            is MoviePlayFragment    -> {
                b.tvAbout.setTextColor(blue400)
                b.tvReviews.setTextColor(blue400)
                b.tvPlay.setTextColor(blue100)
                b.ivAbout.setColorFilter(blue400)
                b.ivAbout.updateLayoutParams { height = dp2 }
                b.ivReviews.setColorFilter(blue400)
                b.ivReviews.updateLayoutParams { height = dp2 }
                b.ivPlay.setColorFilter(blue100)
                b.ivPlay.updateLayoutParams { height = dp4 }
            }
        }
        supportFragmentManager
            .beginTransaction()
            .replace(b.fragment.id, fragment)
            .commit()
    }
}