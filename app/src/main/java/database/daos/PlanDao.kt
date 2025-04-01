package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Plan

@Dao
interface PlanDao {
    @Query("SELECT * FROM plans")
    fun all(): List<Plan>

    @Query("SELECT * FROM plans WHERE (id=:id)")
    fun one(id: String): Plan

    @Insert
    fun insert(vararg plans: Plan)

    @Update
    fun update(plan: Plan)

    @Query("DELETE FROM plans")
    fun clear()
}