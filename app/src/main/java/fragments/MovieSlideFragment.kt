package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import helpers.ImageHelper
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.AdapterPagerSliderBinding
import retrofit.models.MovieBrief

class MovieSlideFragment : Fragment() {
    private lateinit var b: AdapterPagerSliderBinding

    companion object {
        private const val ARG_IMAGE = "movie_image"
        private const val ARG_VIP = "movie_vip"

        fun newInstance(movie: MovieBrief): MovieSlideFragment {
            val fragment = MovieSlideFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE, movie.cover)
            args.putBoolean(ARG_VIP, movie.isVip)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = AdapterPagerSliderBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImageHelper(arguments?.getString(ARG_IMAGE)!!, R.mipmap.logo).loadInto(b.ivSlider)
        b.ivVip.visibility = if (arguments?.getBoolean(ARG_VIP)!!) View.VISIBLE else View.GONE
    }
}