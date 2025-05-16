package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import callbacks.SerieBriefDiffCallback
import helpers.ImageHelper
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieBinding
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieGridBinding
import retrofit.models.SerieBrief

class AdapterRecyclerSerieGrid() : ListAdapter<SerieBrief, AdapterRecyclerSerieGrid.MyViewHolder>(SerieBriefDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerMovieSerieGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val serie = getItem(position)
        ImageHelper(serie.thumbnail, R.mipmap.logo).loadInto(holder.b.ivThumbnail)
        holder.b.tvTitle.text = serie.title
        holder.b.tvTitle.isSelected = true
        holder.b.tvVisits.text = serie.visits.toString()
        holder.b.tvRating.text = serie.rating.toString()
    }

    inner class MyViewHolder(val b: AdapterRecyclerMovieSerieGridBinding) : RecyclerView.ViewHolder(b.root)
}