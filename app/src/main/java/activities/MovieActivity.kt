package activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import fragments.MovieAboutFragment
import fragments.MoviePlayFragment
import fragments.MovieReviewsFragment
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.databinding.ActivityMovieBinding

class MovieActivity : ActivityEnhanced() {
    private lateinit var b: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.tvAbout.setOnClickListener {
            showFragment(MovieAboutFragment())
        }

        b.tvReviews.setOnClickListener {
            showFragment(MovieReviewsFragment())
        }

        b.tvPlay.setOnClickListener {
            showFragment(MoviePlayFragment())
        }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(b.fragment.id, fragment)
            .commit()
    }
}