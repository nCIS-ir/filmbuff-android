package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Artist

@Dao
interface ArtistDao {
    @Query("SELECT * FROM artists")
    fun all(): List<Artist>

    @Query("SELECT * FROM artists WHERE (id=:id)")
    fun one(id: String): Artist

    @Insert
    fun insert(vararg artists: Artist)

    @Update
    fun update(artist: Artist)

    @Query("DELETE FROM artists")
    fun clear()
}