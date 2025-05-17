package activities

import adapters.AdapterRecyclerMovieGrid
import adapters.AdapterRecyclerSerieGrid
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import enums.Mode
import helpers.KeyHelper
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.ActivityGenreBinding
import kotlinx.coroutines.launch
import retrofit.calls.Movie
import retrofit.calls.Serie

private val adapterRecyclerMovieGrid = AdapterRecyclerMovieGrid()

class GenreActivity : ActivityEnhanced() {
    private lateinit var b: ActivityGenreBinding
    private lateinit var genreId: String
    private var loading = true
    private var page = 1
    private var previousTotalItemCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(b.root)

        genreId = intent.getStringExtra(KeyHelper.GENRE_ID) ?: ""
        val mode = intent.getStringExtra(KeyHelper.MODE) ?: Mode.MOVIE.value
        if (genreId.isBlank()) finish()

        b.rvItems.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        b.rvItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) {
                    return
                }
                val layoutManager = b.rvItems.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount < previousTotalItemCount) {
                    page = 1
                    previousTotalItemCount = totalItemCount
                    loading = true
                }
                if (loading && totalItemCount > previousTotalItemCount) {
                    loading = false
                    previousTotalItemCount = totalItemCount
                }
                if (!loading && lastVisibleItemPosition >= totalItemCount) {
                    page++
                    lifecycleScope.launch {
                        if (mode == Mode.MOVIE.value) {
                            loadCurrentPageMovies()
                        } else {
                            loadCurrentPageSeries()
                        }
                    }
                }
            }
        })
        if (mode == Mode.MOVIE.value) {
            b.rvItems.adapter = AdapterRecyclerMovieGrid()
        } else {
            b.rvItems.adapter = AdapterRecyclerSerieGrid()
        }

        b.ivBack.setOnClickListener { finish() }

        b.tvGenre.text = App.DB.genreDao().one(genreId).title

        lifecycleScope.launch {
            if (mode == Mode.MOVIE.value) {
                loadCurrentPageMovies()
            } else {
                loadCurrentPageSeries()
            }
        }
    }

    private suspend fun loadCurrentPageMovies() {
        loading = true
        Movie.genre(
            genreId,
            page,
            24,
            onSuccess = { movies ->
                loading = false
                b.shimmerItems.visibility = View.INVISIBLE
                b.rvItems.visibility = View.VISIBLE
                (b.rvItems.adapter as AdapterRecyclerMovieGrid).apply { submitList(currentList.toMutableList().apply { addAll(movies) }) }
            },
            onError = { finish() },
            showLoading = true
        )
    }

    private suspend fun loadCurrentPageSeries() {
        loading = true
        Serie.genre(
            genreId,
            page,
            24,
            onSuccess = { series ->
                loading = false
                b.shimmerItems.visibility = View.INVISIBLE
                b.rvItems.visibility = View.VISIBLE
                (b.rvItems.adapter as AdapterRecyclerSerieGrid).apply { submitList(currentList.toMutableList().apply { addAll(series) }) }
            },
            onError = { finish() },
            showLoading = true
        )
    }
}