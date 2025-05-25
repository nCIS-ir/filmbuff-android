package helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.widget.ImageView
import androidx.core.graphics.scale
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import database.daos.ImageCacheDao
import database.models.ImageCache
import ir.ncis.filmbuff.App
import java.io.ByteArrayOutputStream

class ImageHelper(private val url: String, private val placeHolder: Int) {
    private var listener: ImageProcessListener? = null
    private var centerCrop = false
    private var fit = false
    private var height = -1
    private var width = -1

    fun loadInto(imageView: ImageView?) {
        if (imageView != null) {
            val imageCacheDao: ImageCacheDao = App.DB.imageCacheDao()
            if (imageCacheDao.exists(url)) {
                if (listener != null) {
                    listener!!.onPrepare()
                }
                try {
                    setImageViewFromBase64(imageCacheDao.one(url).photo!!, imageView)
                    if (listener != null) {
                        listener!!.onSuccess()
                    }
                } catch (e: Exception) {
                    if (listener != null) {
                        listener!!.onError(e)
                    }
                }
            } else {
                if (listener != null) {
                    listener!!.onPrepare()
                }
                val picasso = Picasso.get()
                    .load(url)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                if (width != -1 || height != -1) {
                    picasso.resize(width, height)
                }
                if (centerCrop) {
                    picasso.centerCrop()
                }
                if (fit) {
                    picasso.fit()
                }
                picasso
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            if (!imageCacheDao.exists(url)) {
                                imageCacheDao.insert(ImageCache(url, getBase64FromImageView(imageView)))
                            }
                            if (listener != null) {
                                listener!!.onSuccess()
                            }
                        }

                        override fun onError(e: Exception) {
                            if (listener != null) {
                                listener!!.onError(e)
                            }
                        }
                    })
            }
        }
    }

    fun resize(width: Int, height: Int): ImageHelper {
        this.width = width
        this.height = height
        return this
    }

    fun centerCrop(): ImageHelper {
        centerCrop = true
        return this
    }

    fun fit(): ImageHelper {
        fit = true
        return this
    }

    fun setListener(listener: ImageProcessListener?): ImageHelper {
        this.listener = listener
        return this
    }

    private fun getBase64FromImageView(imageView: ImageView): String? {
        val drawable = imageView.drawable as BitmapDrawable?
        if (drawable != null) {
            val bitmap = drawable.bitmap
            val stream = ByteArrayOutputStream()
            resizeBitmapMaintainingAspectRatio(bitmap).compress(Bitmap.CompressFormat.JPEG, 75, stream)
            return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)
        }
        return null
    }

    private fun setImageViewFromBase64(base64: String, imageView: ImageView) {
        val imageAsBytes = Base64.decode(base64.toByteArray(), Base64.DEFAULT)
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size))
    }

    private fun resizeBitmapMaintainingAspectRatio(bitmap: Bitmap, maxSize: Int = 512): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        if (width <= maxSize && height <= maxSize) return bitmap
        val aspectRatio: Float = width.toFloat() / height.toFloat()
        val newWidth: Int
        val newHeight: Int
        if (aspectRatio > 1) {
            newWidth = maxSize
            newHeight = (maxSize / aspectRatio).toInt()
        } else {
            newHeight = maxSize
            newWidth = (maxSize * aspectRatio).toInt()
        }
        return bitmap.scale(newWidth, newHeight)
    }

    interface ImageProcessListener {
        fun onPrepare()

        fun onSuccess()

        fun onError(e: Exception)
    }
}