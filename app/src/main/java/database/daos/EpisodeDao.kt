package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Episode

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM episodes")
    fun all(): List<Episode>

    @Query("SELECT * FROM episodes WHERE (id=:id)")
    fun one(id: String): Episode

    @Insert
    fun insert(vararg episodes: Episode)

    @Update
    fun update(episode: Episode)

    @Query("DELETE FROM episodes")
    fun clear()
}