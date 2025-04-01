package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.EpisodeFile

@Dao
interface EpisodeFileDao {
    @Query("SELECT * FROM episode_files")
    fun all(): List<EpisodeFile>

    @Query("SELECT * FROM episode_files WHERE (id=:id)")
    fun one(id: String): EpisodeFile

    @Insert
    fun insert(vararg episodeFiless: EpisodeFile)

    @Update
    fun update(episodeFile: EpisodeFile)

    @Query("DELETE FROM episode_files")
    fun clear()
}