package activities

import android.os.Bundle
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import fragments.MovieAboutFragment
import fragments.MoviePlayFragment
import fragments.MovieReviewsFragment
import helpers.ContextHelper
import helpers.ImageHelper
import helpers.KeyHelper
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.ActivityMovieBinding
import kotlinx.coroutines.launch
import retrofit.calls.Movie

class MovieActivity : ActivityEnhanced() {
    private lateinit var b: ActivityMovieBinding
    private val blue100 = ContextHelper.getColor(R.color.blue_100)
    private val blue400 = ContextHelper.getColor(R.color.blue_400)
    private val dp2 = ContextHelper.dpToPx(2)
    private val dp4 = ContextHelper.dpToPx(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(b.root)

        val movieId = intent.getStringExtra(KeyHelper.ID)
        if (movieId == null) finish()

        b.ivBack.setOnClickListener { finish() }

        lifecycleScope.launch {
            Movie.details(
                movieId!!,
                { movie ->
                    if (movie.isFavorite) {
                        b.ivFavorite.setImageResource(R.drawable.ic_favorite_full)
                        b.ivFavorite.setColorFilter(ContextHelper.getColor(R.color.red_400))
                    } else {
                        b.ivFavorite.setImageResource(R.drawable.ic_favorite_empty)
                        b.ivFavorite.setColorFilter(ContextHelper.getColor(R.color.white))
                    }
                    b.tvName.text = movie.title
                    b.tvName.isSelected = true
                    b.tvCalendar.text = movie.year.toString()
                    b.tvDuration.text = movie.duration
                    val genreDao = App.DB.genreDao()
                    val genres = mutableListOf<String>()
                    movie.genres.forEach { genreId -> genres += genreDao.one(genreId).title }
                    b.tvGenre.text = genres.joinToString(" - ")
                    b.tvGenre.isSelected = true
                    ImageHelper(movie.cover, R.mipmap.placeholder).loadInto(b.ivCover)
                    showFragment(MovieAboutFragment(movie))
                    b.tvAbout.setOnClickListener { showFragment(MovieAboutFragment(movie)) }
                    b.tvReviews.setOnClickListener { showFragment(MovieReviewsFragment()) }
                    b.tvPlay.setOnClickListener { showFragment(MoviePlayFragment()) }
                },
                { finish() },
            )
        }
    }

    private fun showFragment(fragment: Fragment) {
        when (fragment) {
            is MovieAboutFragment -> {
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

            is MoviePlayFragment -> {
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