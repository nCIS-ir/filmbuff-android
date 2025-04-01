package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun all(): List<Movie>

    @Query("SELECT * FROM movies WHERE (id=:id)")
    fun one(id: String): Movie

    @Insert
    fun insert(vararg movies: Movie)

    @Update
    fun update(movie: Movie)

    @Query("DELETE FROM movies")
    fun clear()
}