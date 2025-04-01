package database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit.models.BaseLanguage

@Entity(tableName = "languages")
data class Language(
    @PrimaryKey
    val id: String,
    val code: String,
    val name: String,
) {
    companion object {
        fun from(item: BaseLanguage): Language {
            return Language(
                id = item.id,
                code = item.code,
                name = item.name,
            )
        }

        fun from(items: List<BaseLanguage>): List<Language> {
            val result = mutableListOf<Language>()
            items.forEach { item -> result.add(from(item)) }
            return result.toList()
        }
    }

}
