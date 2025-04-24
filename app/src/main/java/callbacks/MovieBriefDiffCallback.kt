package callbacks

import androidx.recyclerview.widget.DiffUtil
import retrofit.models.MovieBrief

class MovieBriefDiffCallback : DiffUtil.ItemCallback<MovieBrief>() {
    override fun areItemsTheSame(oldItem: MovieBrief, newItem: MovieBrief): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieBrief, newItem: MovieBrief): Boolean = oldItem == newItem
}