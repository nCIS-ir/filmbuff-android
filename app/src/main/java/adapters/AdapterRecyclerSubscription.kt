package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.ncis.filmbuff.databinding.AdapterRecyclerSubscriptionBinding
import retrofit.models.Subscription

class AdapterRecyclerSubscription(private val subscriptions: List<Subscription>) : RecyclerView.Adapter<AdapterRecyclerSubscription.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerSubscriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val subscription = subscriptions[position]
        holder.b.tvId.text = subscription.id
        holder.b.tvDuration.text = subscription.duration.toString()
        holder.b.tvStart.text = subscription.startedAt
        holder.b.tvEnd.text = subscription.endedAt
    }

    override fun getItemCount(): Int = subscriptions.size

    class MyViewHolder(val b: AdapterRecyclerSubscriptionBinding) : RecyclerView.ViewHolder(b.root)
}