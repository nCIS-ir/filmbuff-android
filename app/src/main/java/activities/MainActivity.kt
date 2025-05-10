package activities

import adapters.AdapterPagerMovieSlider
import adapters.AdapterPagerSerieSlider
import adapters.AdapterRecyclerMovie
import adapters.AdapterRecyclerSerie
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
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

        observe()

        b.btMovie.setOnClickListener {
            mainViewModel.setMode(Mode.MOVIE)
        }

        b.btSerie.setOnClickListener {
            mainViewModel.setMode(Mode.SERIE)
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

    //     b.rvRecents.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)

    private fun observe() {
        mainViewModel.sort.observe(this) { mainViewModel.setShouldReload(true) }
        mainViewModel.direction.observe(this) { mainViewModel.setShouldReload(true) }
        mainViewModel.mode.observe(this) { mode ->
            if (mode == Mode.MOVIE) {
                b.btMovie.showBackground = true
                b.btSerie.showBackground = false
                lifecycleScope.launch {
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
                        loadMovieGenres()
                    }
                }
            } else {
                b.btMovie.showBackground = false
                b.btSerie.showBackground = true
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        Serie.recent(
                            {
                                val adapter = AdapterRecyclerSerie()
                                b.rvRecents.adapter = adapter
                                adapter.submitList(it)
                            },
                            showLoading = false
                        )
                        Serie.slider(
                            {
                                b.shimmerSlider.visibility = View.GONE
                                b.vpSlider.visibility = View.VISIBLE
                                b.vpSlider.adapter = AdapterPagerSerieSlider(this@MainActivity, it)
                            },
                            {
                                b.shimmerSlider.visibility = View.GONE
                            },
                            showLoading = false
                        )
                        loadSerieGenres()
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
                view.tvTitle.text = genre.title
                view.rvItems.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
                val adapter = AdapterRecyclerMovie()
                view.rvItems.adapter = adapter
                async(Dispatchers.IO) {
                    Movie.genre(
                        genreId = genre.id,
                        sort = mainViewModel.sort.value!!,
                        direction = mainViewModel.direction.value!!,
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
    }

    private fun loadSerieGenres() {
        lifecycleScope.launch {
            b.vgGenres.removeAllViews()
            App.DB.genreDao().all().forEach { genre ->
                val view = LayoutGenreBinding.inflate(layoutInflater)
                view.tvTitle.text = genre.title
                view.rvItems.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
                val adapter = AdapterRecyclerSerie()
                view.rvItems.adapter = adapter
                async(Dispatchers.IO) {
                    Serie.genre(
                        genreId = genre.id,
                        sort = mainViewModel.sort.value!!,
                        direction = mainViewModel.direction.value!!,
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
    }
}