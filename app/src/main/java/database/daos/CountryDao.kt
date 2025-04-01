package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Country

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries")
    fun all(): List<Country>

    @Query("SELECT * FROM countries WHERE (id=:id)")
    fun one(id: String): Country

    @Insert
    fun insert(vararg comments: Country)

    @Update
    fun update(comment: Country)

    @Query("DELETE FROM countries")
    fun clear()
}