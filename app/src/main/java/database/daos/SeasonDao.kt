package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Season

@Dao
interface SeasonDao {
    @Query("SELECT * FROM seasons")
    fun all(): List<Season>

    @Query("SELECT * FROM seasons WHERE (id=:id)")
    fun one(id: String): Season

    @Insert
    fun insert(vararg seasons: Season)

    @Update
    fun update(season: Season)

    @Query("DELETE FROM seasons")
    fun clear()
}