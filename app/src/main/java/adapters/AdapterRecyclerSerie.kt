package adapters

import activities.SerieActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import callbacks.SerieBriefDiffCallback
import helpers.ImageHelper
import helpers.KeyHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieBinding
import retrofit.models.SerieBrief

class AdapterRecyclerSerie() : ListAdapter<SerieBrief, AdapterRecyclerSerie.MyViewHolder>(SerieBriefDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerMovieSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val serie = getItem(position)
        ImageHelper(serie.thumbnail, R.mipmap.placeholder).loadInto(holder.b.ivThumbnail)
        holder.b.tvTitle.text = serie.title
        holder.b.tvTitle.isSelected = true
        holder.b.tvVisits.text = serie.visits.toString()
        holder.b.tvRating.text = serie.rating.toString()
        holder.b.vgRoot.setOnClickListener { App.ACTIVITY.runActivity(SerieActivity::class.java, bundleOf(Pair(KeyHelper.ID, serie.id))) }
    }

    inner class MyViewHolder(val b: AdapterRecyclerMovieSerieBinding) : RecyclerView.ViewHolder(b.root)
}