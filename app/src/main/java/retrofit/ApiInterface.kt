package retrofit

import retrofit.models.Activity
import retrofit.models.Artist
import retrofit.models.Country
import retrofit.models.Favorite
import retrofit.models.Genre
import retrofit.models.Language
import retrofit.models.Movie
import retrofit.models.Pack
import retrofit.models.Plan
import retrofit.models.Quality
import retrofit.models.ResponseWrapper
import retrofit.models.Role
import retrofit.models.Serie
import retrofit.models.Session
import retrofit.models.User
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    //region Auth
    @POST("auth/info")
    @FormUrlEncoded
    suspend fun authInfo(): Response<ResponseWrapper<User>>

    @POST("auth/login")
    @FormUrlEncoded
    suspend fun authLogin(@Field("username") username: String, @Field("password") password: String): Response<ResponseWrapper<Session>>

    @DELETE("auth/logout")
    suspend fun authLogout(): Response<Void?>

    @POST("auth/refresh")
    @FormUrlEncoded
    suspend fun authRefresh(): Response<ResponseWrapper<Session>>

    @POST("auth/register")
    @FormUrlEncoded
    suspend fun authRegister(@Field("username") username: String, @Field("password") password: String, @Field("email") email: String): Response<Void?>

    @POST("auth/resend")
    @FormUrlEncoded
    suspend fun authResend(@Field("username") username: String): Response<Void?>

    @POST("auth/verify")
    @FormUrlEncoded
    suspend fun authVerify(@Field("username") username: String, @Field("otp") otp: Int): Response<ResponseWrapper<Session>>
    //endregion

    //region Base
    @GET("base/activities")
    suspend fun baseActivities(): Response<ResponseWrapper<List<Activity>>>

    @GET("base/artists")
    suspend fun baseArtists(): Response<ResponseWrapper<List<Artist>>>

    @GET("base/countries")
    suspend fun baseCountries(): Response<ResponseWrapper<List<Country>>>

    @GET("base/genres")
    suspend fun baseGenres(): Response<ResponseWrapper<List<Genre>>>

    @GET("base/languages")
    suspend fun baseLanguages(): Response<ResponseWrapper<List<Language>>>

    @GET("base/packs")
    suspend fun basePacks(): Response<ResponseWrapper<List<Pack>>>

    @GET("base/plans")
    suspend fun basePlans(): Response<ResponseWrapper<List<Plan>>>

    @GET("base/qualities")
    suspend fun baseQualities(): Response<ResponseWrapper<List<Quality>>>

    @GET("base/roles")
    suspend fun baseRoles(): Response<ResponseWrapper<List<Role>>>
    //endregion

    //region Movie
    @GET("movie/recent")
    suspend fun movieRecent(): Response<ResponseWrapper<List<Movie>>>

    @GET("movie/{genre_id}")
    suspend fun movieGenre(@Path("genre_id") genreId: String, @Query("page") page: Int, @Query("perPage") perPage: Int, @Query("sort") sort: String, @Query("direction") direction: String): Response<ResponseWrapper<List<Movie>>>

    @GET("movie/slider")
    suspend fun movieSlider(): Response<ResponseWrapper<List<Movie>>>

    @GET("movie/favorite")
    suspend fun movieFavorite(): Response<List<Favorite>>

    @POST("movie/favorite/{movie_id}")
    suspend fun addFavoriteMovie(@Path("movie_id") movieId: String): Response<Void>

    @DELETE("movie/favorite/{movie_id}")
    suspend fun deleteFavoriteMovie(@Path("movie_id") movieId: String): Response<Void>

    @POST("movie/visit/{movie_file_id}")
    suspend fun editVisitMovie(@Path("movie_file_id") movieFileId: String): Response<Void>

    @DELETE("movie/visit/{movie_file_id}")
    suspend fun deleteVisitMovie(@Path("movie_file_id") movieFileId: String): Response<Void>
    //endregion

    //region Serie
    @GET("serie/recent")
    suspend fun serieRecent(): Response<ResponseWrapper<List<Serie>>>

    @GET("serie/{genre_id}")
    suspend fun serieGenre(@Path("genre_id") genreId: String, @Query("page") page: Int, @Query("perPage") perPage: Int, @Query("sort") sort: String, @Query("direction") direction: String): Response<ResponseWrapper<List<Serie>>>

    @GET("serie/slider")
    suspend fun serieSlider(): Response<ResponseWrapper<List<Serie>>>

    @GET("serie/favorite")
    suspend fun serieFavorite(): Response<List<Favorite>>

    @POST("serie/favorite/{serie_id}")
    suspend fun addFavoriteSerie(@Path("serie_id") serieId: String): Response<Void>

    @DELETE("serie/favorite/{serie_id}")
    suspend fun deleteFavoriteSerie(@Path("serie_id") serieId: String): Response<Void>

    @POST("serie/visit/{episode_file_id}")
    suspend fun editVisitSerie(@Path("episode_file_id") episodeFileId: String): Response<Void>

    @DELETE("serie/visit/{episode_file_id}")
    suspend fun deleteVisitSerie(@Path("episode_file_id") episodeFileId: String): Response<Void>
    //endregion
}