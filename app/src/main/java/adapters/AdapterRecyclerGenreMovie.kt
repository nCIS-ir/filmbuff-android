package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.models.Genre
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieGenreBinding
import retrofit.models.Movie

class AdapterRecyclerGenreMovie(private val genres: List<Genre>, private val movieGenres: Map<String, List<Movie>>) : RecyclerView.Adapter<AdapterRecyclerGenreMovie.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerMovieSerieGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val genre = genres[position]
        holder.b.tvTitle.text = genre.title
        holder.b.rvItems.layoutManager = LinearLayoutManager(holder.b.root.context, LinearLayoutManager.HORIZONTAL, false)
        holder.b.rvItems.adapter = AdapterRecyclerMovie(movieGenres[genre.id] ?: emptyList())
    }

    override fun getItemCount(): Int = genres.size

    class MyViewHolder(val b: AdapterRecyclerMovieSerieGenreBinding) : RecyclerView.ViewHolder(b.root)
}