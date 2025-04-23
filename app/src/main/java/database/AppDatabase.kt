package database

import androidx.room.Database
import androidx.room.RoomDatabase
import database.daos.ActivityDao
import database.daos.CountryDao
import database.daos.GenreDao
import database.daos.ImageCacheDao
import database.daos.LanguageDao
import database.daos.PackDao
import database.daos.PlanDao
import database.daos.QualityDao
import database.daos.RoleDao
import database.models.Activity
import database.models.Country
import database.models.Genre
import database.models.ImageCache
import database.models.Language
import database.models.Pack
import database.models.Plan
import database.models.Quality
import database.models.Role

@Database(
    entities = [
        Activity::class,
        Country::class,
        Genre::class,
        ImageCache::class,
        Language::class,
        Pack::class,
        Plan::class,
        Quality::class,
        Role::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun countryDao(): CountryDao
    abstract fun genreDao(): GenreDao
    abstract fun imageCacheDao(): ImageCacheDao
    abstract fun languageDao(): LanguageDao
    abstract fun packDao(): PackDao
    abstract fun planDao(): PlanDao
    abstract fun qualityDao(): QualityDao
    abstract fun roleDao(): RoleDao
}