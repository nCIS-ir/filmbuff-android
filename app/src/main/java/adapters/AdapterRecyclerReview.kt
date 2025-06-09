package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import helpers.ContextHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import ir.ncis.filmbuff.databinding.AdapterRecyclerReviewBinding
import retrofit.models.Review

class AdapterRecyclerReview(private val reviews: List<Review>) : RecyclerView.Adapter<AdapterRecyclerReview.MyViewHolder>() {
    private val colorGreen400 = ContextHelper.getColor(R.color.green_400)
    private val colorWhite = ContextHelper.getColor(R.color.white)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = reviews[position]
        holder.b.tvPending.visibility = if (review.isActive) View.INVISIBLE else View.VISIBLE
        holder.b.rbRating.rating = review.score.toFloat()
        holder.b.tvUsername.setTextColor(if (review.username == App.USER.username) colorGreen400 else colorWhite)
        holder.b.tvUsername.text = buildString {
            append("@")
            append(review.username)
        }
        holder.b.tvContent.text = review.content
    }

    override fun getItemCount(): Int = reviews.size

    class MyViewHolder(val b: AdapterRecyclerReviewBinding) : RecyclerView.ViewHolder(b.root)
}