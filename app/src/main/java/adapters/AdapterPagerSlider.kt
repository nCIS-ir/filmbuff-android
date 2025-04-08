package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import ir.ncis.filmbuff.databinding.AdapterPagerSliderBinding
import retrofit.models.Movie

class AdapterPagerSlider(private val images: List<Movie>) : PagerAdapter() {
    override fun getCount(): Int = images.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val b = AdapterPagerSliderBinding.inflate(LayoutInflater.from(container.context), container, false)
        container.addView(b.root)
        return b.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}