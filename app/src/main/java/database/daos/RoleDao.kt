package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Role

@Dao
interface RoleDao {
    @Query("SELECT * FROM roles")
    fun all(): List<Role>

    @Query("SELECT * FROM roles WHERE (id=:id)")
    fun one(id: String): Role

    @Insert
    fun insert(vararg roles: Role)

    @Update
    fun update(role: Role)

    @Query("DELETE FROM roles")
    fun clear()
}