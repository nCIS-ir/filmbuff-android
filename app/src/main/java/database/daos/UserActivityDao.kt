package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.UserActivity

@Dao
interface UserActivityDao {
    @Query("SELECT * FROM user_activities")
    fun all(): List<UserActivity>

    @Query("SELECT * FROM user_activities WHERE (id=:id)")
    fun one(id: String): UserActivity

    @Insert
    fun insert(vararg userActivities: UserActivity)

    @Update
    fun update(userActivity: UserActivity)

    @Query("DELETE FROM user_activities")
    fun clear()
}