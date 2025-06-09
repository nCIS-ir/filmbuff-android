package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dialogs.CastDetailsDialog
import helpers.ImageHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.AdapterRecyclerCastBinding
import retrofit.models.Cast

class AdapterRecyclerCast(private val casts: List<Cast>) : RecyclerView.Adapter<AdapterRecyclerCast.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerCastBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cast = casts[position]
        ImageHelper(cast.artist.photo, R.mipmap.placeholder).loadInto(holder.b.ivPhoto)
        holder.b.cvPhoto.setOnClickListener { CastDetailsDialog(App.ACTIVITY, cast).show() }
    }

    override fun getItemCount(): Int = casts.size

    class MyViewHolder(val b: AdapterRecyclerCastBinding) : RecyclerView.ViewHolder(b.root)
}