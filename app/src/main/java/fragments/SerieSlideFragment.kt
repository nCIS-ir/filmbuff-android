package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import helpers.ImageHelper
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.FragmentMovieSliderBinding
import retrofit.models.SerieBrief

class SerieSlideFragment : Fragment() {
    private lateinit var b: FragmentMovieSliderBinding

    companion object {
        private const val ARG_IMAGE = "serie_image"
        private const val ARG_VIP = "serie_vip"

        fun newInstance(serie: SerieBrief): SerieSlideFragment {
            val fragment = SerieSlideFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE, serie.cover)
            args.putBoolean(ARG_VIP, serie.isVip)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        b = FragmentMovieSliderBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ImageHelper(arguments?.getString(ARG_IMAGE)!!, R.mipmap.placeholder).loadInto(b.ivSlider)
        b.ivVip.visibility = if (arguments?.getBoolean(ARG_VIP)!!) View.VISIBLE else View.GONE
    }
}