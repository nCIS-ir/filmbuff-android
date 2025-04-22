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

class FragmentMainHome : Fragment() {
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
                b.rvGenres.adapter = AdapterRecyclerGenreMovie(App.DB.genreDao().all())
            } else {
                Serie.recent().onSuccess { b.rvRecents.adapter = AdapterRecyclerSerie(it) }
                b.rvGenres.adapter = AdapterRecyclerGenreSerie(App.DB.genreDao().all())
            }
        }

        b.cvProfile.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(b.root.id, FragmentMainProfile()).commit()
        }

        b.cvSort.setOnClickListener {
            SortingDialog(requireContext()).show()
        }

        b.cvSearch.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(b.root.id, FargmentHomeSearch()).commit()

        }
    }
}