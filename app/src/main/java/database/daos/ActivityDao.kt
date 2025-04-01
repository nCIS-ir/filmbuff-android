package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Activity

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities")
    fun all(): List<Activity>

    @Query("SELECT * FROM activities WHERE (id=:id)")
    fun one(id: String): Activity

    @Insert
    fun insert(vararg activities: Activity)

    @Update
    fun update(activity: Activity)

    @Query("DELETE FROM activities")
    fun clear()
}