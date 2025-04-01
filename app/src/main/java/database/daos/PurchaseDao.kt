package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Purchase

@Dao
interface PurchaseDao {
    @Query("SELECT * FROM purchases")
    fun all(): List<Purchase>

    @Query("SELECT * FROM purchases WHERE (id=:id)")
    fun one(id: String): Purchase

    @Insert
    fun insert(vararg purchases: Purchase)

    @Update
    fun update(purchase: Purchase)

    @Query("DELETE FROM purchases")
    fun clear()
}