package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import database.models.Genre
import ir.ncis.filmbuff.databinding.AdapterRecyclerGenreSearchBinding

class AdapterRecyclerGenreSearch(private val genres: List<Genre>, private val cbAll: CheckBox) : RecyclerView.Adapter<AdapterRecyclerGenreSearch.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerGenreSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val genre = genres[position]
        holder.b.cbGenre.text = genre.title
        holder.b.cbGenre.isChecked = genre.isSelected
        holder.b.cbGenre.setOnClickListener { checkBox ->
            checkBox as CheckBox
            genre.isSelected = checkBox.isChecked
            cbAll.isChecked = genres.all { it.isSelected }
        }
    }

    override fun getItemCount(): Int = genres.size

    class MyViewHolder(val b: AdapterRecyclerGenreSearchBinding) : RecyclerView.ViewHolder(b.root)
}