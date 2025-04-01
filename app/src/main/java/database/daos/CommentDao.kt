package database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import database.models.Comment

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments")
    fun all(): List<Comment>

    @Query("SELECT * FROM comments WHERE (id=:id)")
    fun one(id: String): Comment

    @Insert
    fun insert(vararg comments: Comment)

    @Update
    fun update(comment: Comment)

    @Query("DELETE FROM comments")
    fun clear()
}