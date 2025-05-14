package retrofit

import retrofit.models.Favorite
import retrofit.models.Info
import retrofit.models.MovieBrief
import retrofit.models.MovieGenre
import retrofit.models.Purchase
import retrofit.models.ResponseWrapper
import retrofit.models.SerieBrief
import retrofit.models.SerieGenre
import retrofit.models.Session
import retrofit.models.Subscription
import retrofit.models.User
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    //region Auth
    @GET("auth/info")
    suspend fun authInfo(): Response<ResponseWrapper<User>>

    @POST("auth/login")
    @FormUrlEncoded
    suspend fun authLogin(@Field("username") username: String, @Field("password") password: String): Response<ResponseWrapper<Session>>

    @DELETE("auth/logout")
    suspend fun authLogout(): Response<Void?>

    @PATCH("auth/refresh")
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
    @GET("base/info")
    suspend fun baseInfo(): Response<ResponseWrapper<Info>>
    //endregion

    //region Movie
    @GET("movie/recent")
    suspend fun movieRecent(): Response<ResponseWrapper<List<MovieBrief>>>

    @GET("movie/genre")
    suspend fun movieAllGenres(@Query("sort") sort: String, @Query("direction") direction: String): Response<ResponseWrapper<List<MovieGenre>>>

    @GET("movie/{genre_id}")
    suspend fun movieGenre(@Path("genre_id") genreId: String, @Query("page") page: Int, @Query("perPage") perPage: Int, @Query("sort") sort: String, @Query("direction") direction: String): Response<ResponseWrapper<List<MovieBrief>>>

    @GET("movie/slider")
    suspend fun movieSlider(): Response<ResponseWrapper<List<MovieBrief>>>

    @GET("movie/favorite")
    suspend fun movieFavorite(): Response<ResponseWrapper<List<Favorite>>>

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
    suspend fun serieRecent(): Response<ResponseWrapper<List<SerieBrief>>>

    @GET("serie/genre")
    suspend fun serieAllGenres(@Query("sort") sort: String, @Query("direction") direction: String): Response<ResponseWrapper<List<SerieGenre>>>

    @GET("serie/{genre_id}")
    suspend fun serieGenre(@Path("genre_id") genreId: String, @Query("page") page: Int, @Query("perPage") perPage: Int, @Query("sort") sort: String, @Query("direction") direction: String): Response<ResponseWrapper<List<SerieBrief>>>

    @GET("serie/slider")
    suspend fun serieSlider(): Response<ResponseWrapper<List<SerieBrief>>>

    @GET("serie/favorite")
    suspend fun serieFavorite(): Response<ResponseWrapper<List<Favorite>>>

    @POST("serie/favorite/{serie_id}")
    suspend fun addFavoriteSerie(@Path("serie_id") serieId: String): Response<Void>

    @DELETE("serie/favorite/{serie_id}")
    suspend fun deleteFavoriteSerie(@Path("serie_id") serieId: String): Response<Void>

    @POST("serie/visit/{episode_file_id}")
    suspend fun editVisitSerie(@Path("episode_file_id") episodeFileId: String): Response<Void>

    @DELETE("serie/visit/{episode_file_id}")
    suspend fun deleteVisitSerie(@Path("episode_file_id") episodeFileId: String): Response<Void>
    //endregion

    //region User
    @PATCH("user/password")
    @FormUrlEncoded
    suspend fun userPassword(@Field("old_pass") oldPass: String, @Field("new_pass") newPass: String, @Field("repeat_pass") repeatPass: String): Response<Void>

    @GET("user/purchases")
    suspend fun userPurchases(): Response<ResponseWrapper<List<Purchase>>>

    @GET("user/subscriptions")
    suspend fun userSubscriptions(): Response<ResponseWrapper<List<Subscription>>>
    //endregion
}