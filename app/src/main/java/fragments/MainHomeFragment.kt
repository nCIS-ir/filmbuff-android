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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

        viewLifecycleOwner.lifecycleScope.launch {
            b.rvRecents.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
            if ((activity as MainActivity).mode == MainActivity.Mode.MOVIE) {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    Movie.slider(
                        {
                            b.shimmerSlider.visibility = View.GONE
                            b.vpSlider.visibility = View.VISIBLE
                            b.vpSlider.adapter = AdapterPagerMovieSlider(requireActivity() as MainActivity, it)
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
                                    view.shimmerItems.visibility = View.GONE
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

        b.cvProfile.setOnClickListener { (requireActivity() as MainActivity).showFragment(MainProfileFragment()) }
        b.cvSort.setOnClickListener { SortingDialog(requireActivity()).show() }
        b.cvSearch.setOnClickListener { (requireActivity() as MainActivity).showFragment(MainSearchFargment()) }
    }
}