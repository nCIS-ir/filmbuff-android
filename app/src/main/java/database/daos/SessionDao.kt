package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Session

@Dao
interface SessionDao {
    @Query("SELECT * FROM sessions")
    fun all(): List<Session>

    @Query("SELECT * FROM sessions WHERE (token=:token)")
    fun one(token: String): Session

    @Insert
    fun insert(vararg sessions: Session)

    @Update
    fun update(session: Session)

    @Query("DELETE FROM sessions")
    fun clear()
}