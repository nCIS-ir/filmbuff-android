package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun all(): List<User>

    @Query("SELECT * FROM users WHERE (id=:id)")
    fun one(id: String): User

    @Insert
    fun insert(vararg users: User)

    @Update
    fun update(user: User)

    @Query("DELETE FROM users")
    fun clear()
}