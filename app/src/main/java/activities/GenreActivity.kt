package activities

import adapters.AdapterRecyclerMovieGrid
import adapters.AdapterRecyclerSerieGrid
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import enums.Mode
import helpers.KeyHelper
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.ActivityGenreBinding
import kotlinx.coroutines.launch
import retrofit.calls.Movie
import retrofit.calls.Serie

class GenreActivity : ActivityEnhanced() {
    private lateinit var b: ActivityGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(b.root)

        val genreId = intent.getStringExtra(KeyHelper.GENRE_ID) ?: ""
        val mode = intent.getStringExtra(KeyHelper.MODE) ?: Mode.MOVIE.value
        if (genreId.isBlank()) finish()

        b.rvItems.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)

        b.ivBack.setOnClickListener { finish() }

        b.tvGenre.text = App.DB.genreDao().one(genreId).title

        lifecycleScope.launch {
            if (mode == Mode.MOVIE.value) {
                Movie.genre(
                    genreId,
                    1,
                    24,
                    onSuccess = { movies ->
                        b.shimmerItems.visibility = View.INVISIBLE
                        b.rvItems.visibility = View.VISIBLE
                        b.rvItems.adapter = AdapterRecyclerMovieGrid().apply { submitList(movies) }
                    },
                    onError = { finish() },
                    showLoading = true
                )
            } else {
                Serie.genre(
                    genreId,
                    1,
                    24,
                    onSuccess = { series ->
                        b.shimmerItems.visibility = View.INVISIBLE
                        b.rvItems.visibility = View.VISIBLE
                        b.rvItems.adapter = AdapterRecyclerSerieGrid().apply { submitList(series) }
                    },
                    onError = { finish() },
                    showLoading = true
                )
            }
        }
    }
}