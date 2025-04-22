package adapters

import activities.MainActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import database.models.Genre
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieGenreBinding
import kotlinx.coroutines.launch
import retrofit.calls.Movie

class AdapterRecyclerGenreMovie(private val genres: List<Genre>) : RecyclerView.Adapter<AdapterRecyclerGenreMovie.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerMovieSerieGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val genre = genres[position]
        holder.b.tvTitle.text = genre.title
        App.ACTIVITY.lifecycleScope.launch {
            Movie.genre(genre.id, sort = MainActivity.sort, direction = MainActivity.direction).onSuccess { holder.b.rvItems.adapter = AdapterRecyclerMovie(it) }
        }
    }

    override fun getItemCount(): Int = genres.size

    class MyViewHolder(val b: AdapterRecyclerMovieSerieGenreBinding) : RecyclerView.ViewHolder(b.root)
}