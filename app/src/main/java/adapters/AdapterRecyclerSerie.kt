package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import helpers.ImageHelper
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieBinding
import retrofit.models.Serie

class AdapterRecyclerSerie(private val series: List<Serie>) : RecyclerView.Adapter<AdapterRecyclerSerie.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerMovieSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val serie = series[position]
        ImageHelper(serie.thumbnail, R.mipmap.logo).loadInto(holder.b.ivThumbnail)
        holder.b.tvTitle.text = serie.title
    }

    override fun getItemCount(): Int = series.size

    class MyViewHolder(val b: AdapterRecyclerMovieSerieBinding) : RecyclerView.ViewHolder(b.root)
}