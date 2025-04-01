package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    fun all(): List<Genre>

    @Query("SELECT * FROM genres WHERE (id=:id)")
    fun one(id: String): Genre

    @Insert
    fun insert(vararg genres: Genre)

    @Update
    fun update(genre: Genre)

    @Query("DELETE FROM genres")
    fun clear()
}