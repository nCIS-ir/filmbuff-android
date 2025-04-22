package fragments

import activities.MainActivity
import adapters.AdapterRecyclerGenreMovie
import adapters.AdapterRecyclerGenreSerie
import adapters.AdapterRecyclerMovie
import adapters.AdapterRecyclerSerie
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dialogs.SortingDialog
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.FragmentMainHomeBinding
import kotlinx.coroutines.launch
import retrofit.calls.Movie
import retrofit.calls.Serie
import retrofit.models.Movie as MovieModel

class MainHomeFragment : Fragment() {
    private lateinit var b: FragmentMainHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentMainHomeBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            b.rvRecents.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
            b.rvGenres.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.VERTICAL, false)
            if ((activity as MainActivity).mode == MainActivity.Mode.MOVIE) {
                Movie.recent().onSuccess { b.rvRecents.adapter = AdapterRecyclerMovie(it) }
                lifecycleScope.launch {
                    Movie.recent()
                        .onSuccess {
                            b.rvRecents.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
                            b.rvRecents.adapter = AdapterRecyclerMovie(it)
                        }
                    val genres = App.DB.genreDao().all()
                    var genreMovies = mutableMapOf<String, List<MovieModel>>()
                    genres.forEach { genre ->
                        Movie.genre(genre.id).onSuccess { movies -> genreMovies[genre.title] = movies }
                    }
                    b.rvGenres.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.VERTICAL, false)
                    b.rvGenres.adapter = AdapterRecyclerGenreMovie(genres, genreMovies)
                }
            } else {
                Serie.recent().onSuccess { b.rvRecents.adapter = AdapterRecyclerSerie(it) }
                b.rvGenres.adapter = AdapterRecyclerGenreSerie(App.DB.genreDao().all())
            }
        }

        b.cvProfile.setOnClickListener {
            (activity as MainActivity).showFragment(MainProfileFragment())
        }

        b.cvSort.setOnClickListener {
            SortingDialog(requireContext()).show()
        }

        b.cvSearch.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(b.root.id, MainSearchFargment()).commit()
        }
    }
}