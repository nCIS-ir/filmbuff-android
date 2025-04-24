package callbacks

import androidx.recyclerview.widget.DiffUtil
import retrofit.models.SerieBrief

class SerieBriefDiffCallback : DiffUtil.ItemCallback<SerieBrief>() {
    override fun areItemsTheSame(oldItem: SerieBrief, newItem: SerieBrief): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SerieBrief, newItem: SerieBrief): Boolean = oldItem == newItem
}