package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Pack

@Dao
interface PackDao {
    @Query("SELECT * FROM packs")
    fun all(): List<Pack>

    @Query("SELECT * FROM packs WHERE (id=:id)")
    fun one(id: String): Pack

    @Insert
    fun insert(vararg packs: Pack)

    @Update
    fun update(pack: Pack)

    @Query("DELETE FROM packs")
    fun clear()
}