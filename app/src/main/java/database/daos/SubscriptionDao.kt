package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Subscription

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions")
    fun all(): List<Subscription>

    @Query("SELECT * FROM subscriptions WHERE (id=:id)")
    fun one(id: String): Subscription

    @Insert
    fun insert(vararg subscriptions: Subscription)

    @Update
    fun update(subscription: Subscription)

    @Query("DELETE FROM subscriptions")
    fun clear()
}