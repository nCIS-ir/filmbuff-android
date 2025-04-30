package activities

import android.net.Uri
import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import helpers.ContextHelper
import ir.ncis.filmbuff.ActivityEnhanced

class PlayActivity : ActivityEnhanced() {
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerView = PlayerView(this)
        setContentView(playerView)

        val videoFile = ContextHelper.copyAssetToFile("video.mp4")
        val videoUri = Uri.fromFile(videoFile)
        val mediaItem = MediaItem.fromUri(videoUri)
        player = ExoPlayer.Builder(this).build()
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        playerView.player = player
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}