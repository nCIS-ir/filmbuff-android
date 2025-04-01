package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Serie

@Dao
interface SerieDao {
    @Query("SELECT * FROM series")
    fun all(): List<Serie>

    @Query("SELECT * FROM series WHERE (id=:id)")
    fun one(id: String): Serie

    @Insert
    fun insert(vararg series: Serie)

    @Update
    fun update(serie: Serie)

    @Query("DELETE FROM series")
    fun clear()
}