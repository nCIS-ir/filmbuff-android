package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.ncis.filmbuff.databinding.AdapterRecyclerPurchaseBinding
import retrofit.models.Purchase

class AdapterRecyclerPurchase(private val purchases: List<Purchase>) : RecyclerView.Adapter<AdapterRecyclerPurchase.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(AdapterRecyclerPurchaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val purchase = purchases[position]
        holder.b.tvId.text = buildString { append(purchases.size - position) }
        holder.b.tvCoins.text = purchase.coins.toString()
        holder.b.tvReference.text = purchase.reference
        holder.b.tvDate.text = purchase.paidAt
    }

    override fun getItemCount(): Int = purchases.size

    class MyViewHolder(val b: AdapterRecyclerPurchaseBinding) : RecyclerView.ViewHolder(b.root)
}