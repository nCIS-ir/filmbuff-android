package adapters

import activities.MovieActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import callbacks.MovieBriefDiffCallback
import helpers.ImageHelper
import helpers.KeyHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieBinding
import retrofit.models.MovieBrief

class AdapterRecyclerMovie() : ListAdapter<MovieBrief, AdapterRecyclerMovie.MyViewHolder>(MovieBriefDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerMovieSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)
        ImageHelper(movie.thumbnail, R.mipmap.placeholder).loadInto(holder.b.ivThumbnail)
        holder.b.tvTitle.text = movie.title
        holder.b.tvTitle.isSelected = true
        holder.b.tvVisits.text = movie.visits.toString()
        holder.b.tvRating.text = movie.rating.toString()
        holder.b.vgRoot.setOnClickListener { App.ACTIVITY.runActivity(MovieActivity::class.java, bundleOf(Pair(KeyHelper.ID, movie.id))) }
    }

    inner class MyViewHolder(val b: AdapterRecyclerMovieSerieBinding) : RecyclerView.ViewHolder(b.root)
}