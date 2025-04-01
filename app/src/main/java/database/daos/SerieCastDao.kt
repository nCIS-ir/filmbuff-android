package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.SerieCast

@Dao
interface SerieCastDao {
    @Query("SELECT * FROM serie_casts")
    fun all(): List<SerieCast>

    @Query("SELECT * FROM serie_casts WHERE (id=:id)")
    fun one(id: String): SerieCast

    @Insert
    fun insert(vararg serieCasts: SerieCast)

    @Update
    fun update(serieCast: SerieCast)

    @Query("DELETE FROM serie_casts")
    fun clear()
}