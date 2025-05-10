package adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fragments.SerieSlideFragment
import ir.ncis.filmbuff.ActivityEnhanced
import retrofit.models.SerieBrief

class AdapterPagerSerieSlider(activity: ActivityEnhanced, private val series: List<SerieBrief>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = series.size

    override fun createFragment(position: Int): Fragment = SerieSlideFragment.newInstance(series[position])
}