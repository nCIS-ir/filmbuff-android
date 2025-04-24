package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import callbacks.SerieBriefDiffCallback
import helpers.ImageHelper
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieBinding
import retrofit.models.SerieBrief

class AdapterRecyclerSerie() : ListAdapter<SerieBrief, AdapterRecyclerSerie.MyViewHolder>(SerieBriefDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerMovieSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val serie = getItem(position)
        ImageHelper(serie.thumbnail, R.mipmap.logo).loadInto(holder.b.ivThumbnail)
        holder.b.tvTitle.text = serie.title
        holder.b.tvTitle.isSelected = true
        holder.b.tvVisits.text = serie.visits.toString()
        holder.b.tvRating.text = serie.rating.toString()
    }

    inner class MyViewHolder(val b: AdapterRecyclerMovieSerieBinding) : RecyclerView.ViewHolder(b.root)
}