package fragments

import activities.MainActivity
import adapters.AdapterPagerMovieSlider
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
import ir.ncis.filmbuff.databinding.LayoutGenreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit.calls.Movie
import retrofit.calls.Serie

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
            if ((activity as MainActivity).mode == MainActivity.Mode.MOVIE) {
                Movie.slider().onSuccess { b.vpSlider.adapter = AdapterPagerMovieSlider(requireActivity() as MainActivity, it) }
                Movie.recent()
                    .onSuccess {
                        val adapter = AdapterRecyclerMovie()
                        b.rvRecents.adapter = adapter
                        adapter.submitList(it)
                    }
                lifecycleScope.launch {
                    Movie.recent()
                        .onSuccess {
                            val adapter = AdapterRecyclerMovie()
                            b.rvRecents.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
                            b.rvRecents.adapter = adapter
                            adapter.submitList(it)
                        }
                    b.vgGenres.removeAllViews()
                    App.DB.genreDao().all().forEach { genre ->
                        val view = LayoutGenreBinding.inflate(layoutInflater)
                        view.tvTitle.text = genre.title
                        view.rvItems.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
                        val adapter = AdapterRecyclerMovie()
                        view.rvItems.adapter = adapter
                        async(Dispatchers.IO) { Movie.genre(genre.id).onSuccess { App.HANDLER.post { adapter.submitList(it) } } }
                        b.vgGenres.addView(view.root)
                    }
                }
            } else {
                Serie.recent().onSuccess { b.rvRecents.adapter = AdapterRecyclerSerie(it) }
            }
        }

        b.cvProfile.setOnClickListener { (requireActivity() as MainActivity).showFragment(MainProfileFragment()) }
        b.cvSort.setOnClickListener { SortingDialog(requireActivity()).show() }
        b.cvSearch.setOnClickListener { (requireActivity() as MainActivity).showFragment(MainSearchFargment()) }
    }
}