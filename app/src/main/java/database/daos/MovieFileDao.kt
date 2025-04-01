package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.MovieFile

@Dao
interface MovieFileDao {
    @Query("SELECT * FROM movie_files")
    fun all(): List<MovieFile>

    @Query("SELECT * FROM movie_files WHERE (id=:id)")
    fun one(id: String): MovieFile

    @Insert
    fun insert(vararg movieFiles: MovieFile)

    @Update
    fun update(movieFile: MovieFile)

    @Query("DELETE FROM movie_files")
    fun clear()
}