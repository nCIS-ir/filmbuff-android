package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import database.models.ImageCache

@Dao
interface ImageCacheDao {
    @Query("SELECT * FROM image_caches")
    fun all(): List<ImageCache>

    @Query("SELECT EXISTS (SELECT * FROM image_caches WHERE (url=:url))")
    fun exists(url: String?): Boolean

    @Query("SELECT * FROM image_caches WHERE (url=:url)")
    fun one(url: String?): ImageCache

    @Query("DELETE FROM image_caches")
    fun clear()

    @Insert
    fun insert(vararg imageCaches: ImageCache)
}