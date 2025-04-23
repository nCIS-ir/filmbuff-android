package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import callbacks.MovieDiffCallback
import helpers.ImageHelper
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieBinding
import retrofit.models.Movie

class AdapterRecyclerMovie() : ListAdapter<Movie, AdapterRecyclerMovie.MyViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerMovieSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)
        ImageHelper(movie.thumbnail, R.mipmap.logo).loadInto(holder.b.ivThumbnail)
        holder.b.tvTitle.text = movie.title
        holder.b.tvTitle.isSelected = true
        holder.b.tvVisits.text = movie.visits.toString()
        holder.b.tvRating.text = movie.rating.toString()
    }

    inner class MyViewHolder(val b: AdapterRecyclerMovieSerieBinding) : RecyclerView.ViewHolder(b.root)
}