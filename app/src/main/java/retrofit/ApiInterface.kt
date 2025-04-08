package retrofit

import retrofit.models.User
import retrofit.models.Session
import retrofit.models.Activity
import retrofit.models.Artist
import retrofit.models.Country
import retrofit.models.Genre
import retrofit.models.Language
import retrofit.models.Pack
import retrofit.models.Plan
import retrofit.models.Quality
import retrofit.models.Role
import retrofit.models.ResponseWrapper
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

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
}