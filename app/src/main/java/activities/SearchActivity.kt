package activities

import adapters.AdapterRecyclerGenreSearch
import adapters.AdapterRecyclerMovieGrid
import adapters.AdapterRecyclerSerieGrid
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import enums.Mode
import helpers.KeyHelper
import helpers.ViewHelper
import ir.ncis.filmbuff.ActivityEnhanced
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.ActivitySearchBinding
import kotlinx.coroutines.launch
import retrofit.calls.Movie
import retrofit.calls.Serie

class SearchActivity : ActivityEnhanced() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(b.root)

        val mode = Mode.valueOf(intent.getStringExtra(KeyHelper.MODE) ?: Mode.MOVIE.name)

        b.ivBack.setOnClickListener { finish() }

        val genres = App.DB.genreDao().all()
        val adapter = AdapterRecyclerGenreSearch(genres, b.cbAll)
        b.rvGenre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        b.rvGenre.adapter = adapter
        b.rvResult.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)

        b.cbAll.setOnClickListener {
            genres.forEachIndexed { index, genre ->
                genre.isSelected = b.cbAll.isChecked
                adapter.notifyItemChanged(index)
            }
        }

        b.btSearch.setOnClickListener {
            ViewHelper.hideKeyboard(b.etSearch)
            val term = b.etSearch.editableText.toString()
            if (term.isNotEmpty()) {
                b.shimmerResult.visibility = View.VISIBLE
                b.rvResult.visibility = View.INVISIBLE
                lifecycleScope.launch {
                    if (mode == Mode.MOVIE) {
                        Movie.search(
                            term,
                            if (genres.any { !it.isSelected }) genres.filter { it.isSelected }.map { it.id } else listOf(),
                            {
                                b.rvResult.adapter = AdapterRecyclerMovieGrid().apply { submitList(it) }
                                b.shimmerResult.visibility = View.INVISIBLE
                                b.rvResult.visibility = View.VISIBLE
                            },
                            {
                                b.shimmerResult.visibility = View.INVISIBLE
                                b.rvResult.visibility = View.VISIBLE
                            }
                        )
                    } else {
                        Serie.search(
                            term,
                            if (genres.any { !it.isSelected }) genres.filter { it.isSelected }.map { it.id } else listOf(),
                            {
                                b.rvResult.adapter = AdapterRecyclerSerieGrid().apply { submitList(it) }
                                b.shimmerResult.visibility = View.INVISIBLE
                                b.rvResult.visibility = View.VISIBLE
                            },
                            {
                                b.shimmerResult.visibility = View.INVISIBLE
                                b.rvResult.visibility = View.VISIBLE
                            }
                        )
                    }
                }
            }
        }

        b.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                b.btSearch.performClick()
                true
            }
            false
        }

        b.ivSearch.setOnClickListener { b.btSearch.performClick() }
    }
}