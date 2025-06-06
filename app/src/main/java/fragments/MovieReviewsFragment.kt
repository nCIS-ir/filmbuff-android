package fragments

import adapters.AdapterRecyclerReview
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dialogs.SubmitReviewDialog
import enums.Mode
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.FragmentMovieSerieReviewsBinding
import kotlinx.coroutines.launch
import retrofit.calls.Movie

class MovieReviewsFragment(private val movieId: String) : Fragment() {
    private lateinit var b: FragmentMovieSerieReviewsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentMovieSerieReviewsBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadReviews()
    }

    private fun loadReviews() {
        lifecycleScope.launch {
            Movie.reviewGet(
                movieId,
                { reviews ->
                    val currentUserReview = reviews.find { it.username == App.USER.username }
                    b.rvReviews.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                    b.rvReviews.adapter = AdapterRecyclerReview(reviews)
                    b.cvSubmitReview.setOnClickListener { SubmitReviewDialog(requireActivity(), movieId, Mode.MOVIE, currentUserReview) { loadReviews() }.show() }
                }
            )
        }
    }
}