package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.MovieCast

@Dao
interface MovieCastDao {
    @Query("SELECT * FROM movie_casts")
    fun all(): List<MovieCast>

    @Query("SELECT * FROM movie_casts WHERE (id=:id)")
    fun one(id: String): MovieCast

    @Insert
    fun insert(vararg movieCasts: MovieCast)

    @Update
    fun update(movieCast: MovieCast)

    @Query("DELETE FROM movie_casts")
    fun clear()
}