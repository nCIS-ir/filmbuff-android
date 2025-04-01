package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Quality

@Dao
interface QualityDao {
    @Query("SELECT * FROM qualities")
    fun all(): List<Quality>

    @Query("SELECT * FROM qualities WHERE (id=:id)")
    fun one(id: String): Quality

    @Insert
    fun insert(vararg qualities: Quality)

    @Update
    fun update(quality: Quality)

    @Query("DELETE FROM qualities")
    fun clear()
}