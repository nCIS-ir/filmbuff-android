package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import helpers.ImageHelper
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieBinding
import retrofit.models.Movie

class AdapterRecyclerMovie(private val movies: List<Movie>) : RecyclerView.Adapter<AdapterRecyclerMovie.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerMovieSerieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movies[position]
        ImageHelper(movie.thumbnail, R.mipmap.logo).loadInto(holder.b.ivThumbnail)
        holder.b.tvTitle.text = movie.title
        holder.b.tvVisits.text = movie.visits.toString()
        holder.b.tvRating.text = movie.rating.toString()
    }

    override fun getItemCount(): Int = movies.size

    class MyViewHolder(val b: AdapterRecyclerMovieSerieBinding) : RecyclerView.ViewHolder(b.root)
}