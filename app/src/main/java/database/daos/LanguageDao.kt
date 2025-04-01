package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Language

@Dao
interface LanguageDao {
    @Query("SELECT * FROM languages")
    fun all(): List<Language>

    @Query("SELECT * FROM languages WHERE (id=:id)")
    fun one(id: String): Language

    @Insert
    fun insert(vararg languages: Language)

    @Update
    fun update(language: Language)

    @Query("DELETE FROM languages")
    fun clear()
}