package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun all(): List<Favorite>

    @Query("SELECT * FROM favorites WHERE (id=:id)")
    fun one(id: String): Favorite

    @Insert
    fun insert(vararg favorites: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Query("DELETE FROM favorites")
    fun clear()
}