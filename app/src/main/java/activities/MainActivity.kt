package activities

import adapters.AdapterPagerMovieSlider
import adapters.AdapterRecyclerMovie
import adapters.AdapterRecyclerSerie
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dialogs.SortingDialog
import enums.Direction
import enums.Sort
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.ActivityMainBinding
import ir.ncis.filmbuff.databinding.LayoutGenreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit.calls.Movie
import retrofit.calls.Serie

class MainActivity : ActivityEnhanced() {
    companion object {
        var sort = Sort.POPULARITY
        var direction = Direction.DESCENDING
    }

    private lateinit var b: ActivityMainBinding
    var mode = Mode.MOVIE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        lifecycleScope.launch {
            b.rvRecents.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
            if (mode == Mode.MOVIE) {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    Movie.slider(
                        {
                            b.shimmerSlider.visibility = View.GONE
                            b.vpSlider.visibility = View.VISIBLE
                            b.vpSlider.adapter = AdapterPagerMovieSlider(this@MainActivity, it)
                        },
                        {
                            b.shimmerSlider.visibility = View.GONE
                        },
                        showLoading = false
                    )
                    Movie.recent(
                        {
                            b.shimmerRecent.visibility = View.GONE
                            b.rvRecents.visibility = View.VISIBLE
                            val adapter = AdapterRecyclerMovie()
                            b.rvRecents.adapter = adapter
                            adapter.submitList(it)
                        },
                        {
                            b.shimmerRecent.visibility = View.GONE
                        },
                        showLoading = false
                    )
                    b.vgGenres.removeAllViews()
                    App.DB.genreDao().all().forEach { genre ->
                        val view = LayoutGenreBinding.inflate(layoutInflater)
                        view.tvTitle.text = genre.title
                        view.rvItems.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
                        val adapter = AdapterRecyclerMovie()
                        view.rvItems.adapter = adapter
                        async(Dispatchers.IO) {
                            Movie.genre(
                                genre.id,
                                onSuccess = {
                                    App.HANDLER.post {
                                        view.shimmerItems.visibility = View.GONE
                                        view.rvItems.visibility = View.VISIBLE
                                        adapter.submitList(it)
                                    }
                                },
                                onError = {
                                    App.HANDLER.post {
                                        view.shimmerItems.visibility = View.INVISIBLE
                                    }
                                },
                                showLoading = false
                            )
                        }
                        b.vgGenres.addView(view.root)
                    }
                }
            } else {
                Serie.recent(
                    {
                        val adapter = AdapterRecyclerSerie()
                        b.rvRecents.adapter = adapter
                        adapter.submitList(it)
                    },
                    showLoading = false
                )
            }
        }

        b.cvProfile.setOnClickListener { runActivity(ProfileActivity::class.java) }
        b.cvSort.setOnClickListener { SortingDialog(this).show() }
        b.cvSearch.setOnClickListener { runActivity(SearchActivity::class.java) }

        b.ivLogo.setOnClickListener { runActivity(PlayActivity::class.java) }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                checkedExit()
            }
        })
    }

    enum class Mode {
        MOVIE,
        SERIE,
    }
}