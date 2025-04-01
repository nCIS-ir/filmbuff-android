package database

import androidx.room.Database
import androidx.room.RoomDatabase
import database.daos.ActivityDao
import database.daos.ArtistDao
import database.daos.CommentDao
import database.daos.CountryDao
import database.daos.EpisodeDao
import database.daos.EpisodeFileDao
import database.daos.FavoriteDao
import database.daos.GenreDao
import database.daos.LanguageDao
import database.daos.MovieCastDao
import database.daos.MovieDao
import database.daos.MovieFileDao
import database.daos.PackDao
import database.daos.PlanDao
import database.daos.PurchaseDao
import database.daos.QualityDao
import database.daos.RoleDao
import database.daos.SeasonDao
import database.daos.SerieCastDao
import database.daos.SerieDao
import database.daos.SessionDao
import database.daos.SubscriptionDao
import database.daos.UserActivityDao
import database.daos.UserDao
import database.models.Activity
import database.models.Artist
import database.models.Comment
import database.models.Country
import database.models.Episode
import database.models.EpisodeFile
import database.models.Favorite
import database.models.Genre
import database.models.Language
import database.models.Movie
import database.models.MovieCast
import database.models.MovieFile
import database.models.Pack
import database.models.Plan
import database.models.Purchase
import database.models.Quality
import database.models.Role
import database.models.Season
import database.models.Serie
import database.models.SerieCast
import database.models.Session
import database.models.Subscription
import database.models.User
import database.models.UserActivity

@Database(
    entities = [
        Activity::class,
        Artist::class,
        Comment::class,
        Country::class,
        Episode::class,
        EpisodeFile::class,
        Favorite::class,
        Genre::class,
        Language::class,
        Movie::class,
        MovieCast::class,
        MovieFile::class,
        Pack::class,
        Plan::class,
        Purchase::class,
        Quality::class,
        Role::class,
        Season::class,
        Serie::class,
        SerieCast::class,
        Session::class,
        Subscription::class,
        User::class,
        UserActivity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun artistDao(): ArtistDao
    abstract fun commentDao(): CommentDao
    abstract fun countryDao(): CountryDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun episodeFileDao(): EpisodeFileDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun genreDao(): GenreDao
    abstract fun languageDao(): LanguageDao
    abstract fun movieCastDao(): MovieCastDao
    abstract fun movieDao(): MovieDao
    abstract fun movieFileDao(): MovieFileDao
    abstract fun packDao(): PackDao
    abstract fun planDao(): PlanDao
    abstract fun purchaseDao(): PurchaseDao
    abstract fun qualityDao(): QualityDao
    abstract fun roleDao(): RoleDao
    abstract fun seasonDao(): SeasonDao
    abstract fun serieCastDao(): SerieCastDao
    abstract fun serieDao(): SerieDao
    abstract fun sessionDao(): SessionDao
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun userActivityDao(): UserActivityDao
    abstract fun userDao(): UserDao
}