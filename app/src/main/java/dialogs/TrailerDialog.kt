package dialogs

import android.content.Context
import android.os.Bundle
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import ir.ncis.filmbuff.DialogEnhanced
import ir.ncis.filmbuff.databinding.DialogTrailerBinding

class TrailerDialog(context: Context, private val title: String, private val path: String) : DialogEnhanced(context) {
    private lateinit var b: DialogTrailerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DialogTrailerBinding.inflate(this.layoutInflater)
        setContentView(b.root)

        b.tvTitle.text = title
        b.tvTitle.isSelected = true

        b.pvTrailer.player = ExoPlayer.Builder(context).build().also { exoPlayer ->
            val mediaItem = MediaItem.fromUri(path.toUri())
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }

    override fun onStop() {
        super.onStop()
        b.pvTrailer.player?.stop()
        b.pvTrailer.player = null
    }
}