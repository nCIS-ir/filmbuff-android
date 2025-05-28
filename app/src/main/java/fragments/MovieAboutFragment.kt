package fragments

import adapters.AdapterRecyclerCast
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import dialogs.TrailerDialog
import ir.ncis.filmbuff.databinding.FragmentMovieSerieAboutBinding
import retrofit.models.MovieFull

class MovieAboutFragment(private val movie: MovieFull) : Fragment() {
    private lateinit var b: FragmentMovieSerieAboutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentMovieSerieAboutBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.tvStory.text = Html.fromHtml(movie.description, Html.FROM_HTML_MODE_COMPACT)

        b.cvTrailer.setOnClickListener { TrailerDialog(requireActivity(), movie.title, movie.trailer).show() }

        b.rvCasts.layoutManager = GridLayoutManager(requireActivity(), 6, GridLayoutManager.HORIZONTAL, false)
        b.rvCasts.adapter = AdapterRecyclerCast(movie.casts)
    }
}