package adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fragments.MovieSlideFragment
import ir.ncis.filmbuff.ActivityEnhanced
import retrofit.models.Movie

class AdapterPagerMovieSlider(activity: ActivityEnhanced, private val movies: List<Movie>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = movies.size

    override fun createFragment(position: Int): Fragment = MovieSlideFragment.newInstance(movies[position])
}