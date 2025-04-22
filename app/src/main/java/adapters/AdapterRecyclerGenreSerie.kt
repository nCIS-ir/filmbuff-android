package adapters

import activities.MainActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.models.Genre
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.databinding.AdapterRecyclerMovieSerieGenreBinding
import kotlinx.coroutines.launch
import retrofit.calls.Serie

class AdapterRecyclerGenreSerie(private val genres: List<Genre>) : RecyclerView.Adapter<AdapterRecyclerGenreSerie.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerMovieSerieGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val genre = genres[position]
        holder.b.tvTitle.text = genre.title
        App.ACTIVITY.lifecycleScope.launch {
            Serie.genre(genre.id, sort = MainActivity.sort, direction = MainActivity.direction)
                .onSuccess {
                    holder.b.rvItems.layoutManager = LinearLayoutManager(App.ACTIVITY, LinearLayoutManager.HORIZONTAL, false)
                    holder.b.rvItems.adapter = AdapterRecyclerSerie(it)
                }
        }
    }

    override fun getItemCount(): Int = genres.size

    class MyViewHolder(val b: AdapterRecyclerMovieSerieGenreBinding) : RecyclerView.ViewHolder(b.root)
}