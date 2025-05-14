package activities

import adapters.AdapterPagerMovieSlider
import adapters.AdapterPagerSerieSlider
import adapters.AdapterRecyclerMovie
import adapters.AdapterRecyclerSerie
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dialogs.SortingDialog
import enums.Mode
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.ActivityMainBinding
import ir.ncis.filmbuff.databinding.LayoutGenreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit.calls.Movie
import retrofit.calls.Serie
import view_models.MainViewModel

class MainActivity : ActivityEnhanced() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var b: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.rvRecents.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)

        observe()

        b.btMovie.setOnClickListener {
            if (mainViewModel.mode.value != Mode.MOVIE) {
                mainViewModel.setMode(Mode.MOVIE)
            }
        }

        b.btSerie.setOnClickListener {
            if (mainViewModel.mode.value != Mode.SERIE) {
                mainViewModel.setMode(Mode.SERIE)
            }
        }

        b.cvProfile.setOnClickListener { runActivity(ProfileActivity::class.java) }
        b.cvSort.setOnClickListener { SortingDialog(this, mainViewModel).show() }
        b.cvSearch.setOnClickListener { runActivity(SearchActivity::class.java) }

        b.ivLogo.setOnClickListener { runActivity(PlayActivity::class.java) }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                checkedExit()
            }
        })
    }


    private fun observe() {
        mainViewModel.sort.observe(this) { mainViewModel.setShouldReload(true) }
        mainViewModel.direction.observe(this) { mainViewModel.setShouldReload(true) }
        mainViewModel.mode.observe(this) { mode ->
            mainViewModel.setShouldReload(true)
            if (mode == Mode.MOVIE) {
                b.btMovie.showBackground = true
                b.btSerie.showBackground = false
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        b.shimmerSlider.visibility = View.VISIBLE
                        b.vpSlider.visibility = View.GONE
                        b.shimmerRecent.visibility = View.VISIBLE
                        b.rvRecents.visibility = View.GONE
                        async(Dispatchers.IO) {
                            Movie.slider(
                                { movies ->
                                    App.HANDLER.post {
                                        b.shimmerSlider.visibility = View.GONE
                                        b.vpSlider.visibility = View.VISIBLE
                                        b.vpSlider.adapter = AdapterPagerMovieSlider(this@MainActivity, movies)
                                    }
                                },
                                { App.HANDLER.post { b.shimmerSlider.visibility = View.GONE } },
                                showLoading = false
                            )
                        }
                        async(Dispatchers.IO) {
                            Movie.recent(
                                { movies ->
                                    App.HANDLER.post {
                                        b.shimmerRecent.visibility = View.GONE
                                        b.rvRecents.visibility = View.VISIBLE
                                        val adapter = AdapterRecyclerMovie()
                                        b.rvRecents.adapter = adapter
                                        adapter.submitList(movies)
                                    }
                                },
                                { App.HANDLER.post { b.shimmerRecent.visibility = View.GONE } },
                                showLoading = false
                            )
                        }
                    }
                }
            } else {
                mainViewModel.setShouldReload(true)
                b.btMovie.showBackground = false
                b.btSerie.showBackground = true
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        b.shimmerSlider.visibility = View.VISIBLE
                        b.vpSlider.visibility = View.GONE
                        b.shimmerRecent.visibility = View.VISIBLE
                        b.rvRecents.visibility = View.GONE
                        async(Dispatchers.IO) {
                            Serie.slider(
                                { series ->
                                    App.HANDLER.post {
                                        b.shimmerSlider.visibility = View.GONE
                                        b.vpSlider.visibility = View.VISIBLE
                                        b.vpSlider.adapter = AdapterPagerSerieSlider(this@MainActivity, series)
                                    }
                                },
                                { App.HANDLER.post { b.shimmerSlider.visibility = View.GONE } },
                                showLoading = false
                            )
                        }
                        async(Dispatchers.IO) {
                            Serie.recent(
                                { series ->
                                    App.HANDLER.post {
                                        b.shimmerRecent.visibility = View.GONE
                                        b.rvRecents.visibility = View.VISIBLE
                                        val adapter = AdapterRecyclerSerie()
                                        b.rvRecents.adapter = adapter
                                        adapter.submitList(series)
                                    }
                                },
                                { App.HANDLER.post { b.shimmerRecent.visibility = View.GONE } },
                                showLoading = false
                            )
                        }
                    }
                }
            }
        }
        mainViewModel.shouldReload.observe(this) { shouldReload ->
            if (shouldReload) {
                if (mainViewModel.mode.value == Mode.MOVIE) {
                    loadMovieGenres()
                } else {
                    loadSerieGenres()
                }
                mainViewModel.setShouldReload(false)
            }
        }
    }

    private fun loadMovieGenres() {
        lifecycleScope.launch {
            b.vgGenres.removeAllViews()
            App.DB.genreDao().all().forEach { genre ->
                val view = LayoutGenreBinding.inflate(layoutInflater)
                view.root.tag = genre.id
                view.tvTitle.text = genre.title
                view.rvItems.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
                b.vgGenres.addView(view.root)
            }
            async(Dispatchers.IO) {
                Movie.allGenres(
                    sort = mainViewModel.sort.value!!,
                    direction = mainViewModel.direction.value!!,
                    onSuccess = { movieGenres ->
                        movieGenres.forEach { movieGenre ->
                            App.HANDLER.post {
                                val view = b.vgGenres.children.find { view -> view.tag == movieGenre.genreId }
                                if (view != null) {
                                    val bindingView = LayoutGenreBinding.bind(view)
                                    bindingView.shimmerItems.visibility = View.GONE
                                    bindingView.rvItems.visibility = View.VISIBLE
                                    val adapter = AdapterRecyclerMovie()
                                    bindingView.rvItems.adapter = adapter
                                    adapter.submitList(movieGenre.movies)
                                }
                            }
                        }
                    },
                    showLoading = false
                )
            }
        }
    }

    private fun loadSerieGenres() {
        lifecycleScope.launch {
            b.vgGenres.removeAllViews()
            App.DB.genreDao().all().forEach { genre ->
                val view = LayoutGenreBinding.inflate(layoutInflater)
                view.root.tag = genre.id
                view.tvTitle.text = genre.title
                view.rvItems.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
                b.vgGenres.addView(view.root)
            }
            async(Dispatchers.IO) {
                Serie.allGenres(
                    sort = mainViewModel.sort.value!!,
                    direction = mainViewModel.direction.value!!,
                    onSuccess = { serieGenres ->
                        serieGenres.forEach { serieGenre ->
                            App.HANDLER.post {
                                val view = b.vgGenres.children.find { view -> view.tag == serieGenre.genreId }
                                if (view != null) {
                                    val bindingView = LayoutGenreBinding.bind(view)
                                    bindingView.shimmerItems.visibility = View.GONE
                                    bindingView.rvItems.visibility = View.VISIBLE
                                    val adapter = AdapterRecyclerSerie()
                                    bindingView.rvItems.adapter = adapter
                                    adapter.submitList(serieGenre.series)
                                }
                            }
                        }
                    },
                    showLoading = false
                )
            }
        }
    }
}