package retrofit

import retrofit.models.AuthInfo
import retrofit.models.AuthLogin
import retrofit.models.BaseActivity
import retrofit.models.BaseArtist
import retrofit.models.BaseCountry
import retrofit.models.BaseGenre
import retrofit.models.BaseLanguage
import retrofit.models.BasePack
import retrofit.models.BasePlan
import retrofit.models.BaseQuality
import retrofit.models.BaseRole
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
    suspend fun authInfo(): Response<ResponseWrapper<AuthInfo>>

    @POST("auth/login")
    @FormUrlEncoded
    suspend fun authLogin(@Field("username") username: String, @Field("password") password: String): Response<ResponseWrapper<AuthLogin>>

    @DELETE("auth/logout")
    suspend fun authLogout(): Response<Void?>

    @POST("auth/refresh")
    @FormUrlEncoded
    suspend fun authRefresh(): Response<ResponseWrapper<AuthLogin>>

    @POST("auth/register")
    @FormUrlEncoded
    suspend fun authRegister(@Field("username") username: String, @Field("password") password: String, @Field("email") email: String): Response<Void?>

    @POST("auth/resend")
    @FormUrlEncoded
    suspend fun authResend(@Field("username") username: String): Response<Void?>

    @POST("auth/verify")
    @FormUrlEncoded
    suspend fun authVerify(@Field("username") username: String, @Field("otp") otp: Int): Response<ResponseWrapper<AuthLogin>>
    //endregion

    //region Base
    @GET("base/activities")
    suspend fun baseActivities(): Response<ResponseWrapper<List<BaseActivity>>>

    @GET("base/artists")
    suspend fun baseArtists(): Response<ResponseWrapper<List<BaseArtist>>>

    @GET("base/countries")
    suspend fun baseCountries(): Response<ResponseWrapper<List<BaseCountry>>>

    @GET("base/genres")
    suspend fun baseGenres(): Response<ResponseWrapper<List<BaseGenre>>>

    @GET("base/languages")
    suspend fun baseLanguages(): Response<ResponseWrapper<List<BaseLanguage>>>

    @GET("base/packs")
    suspend fun basePacks(): Response<ResponseWrapper<List<BasePack>>>

    @GET("base/plans")
    suspend fun basePlans(): Response<ResponseWrapper<List<BasePlan>>>

    @GET("base/qualities")
    suspend fun baseQualities(): Response<ResponseWrapper<List<BaseQuality>>>

    @GET("base/roles")
    suspend fun baseRoles(): Response<ResponseWrapper<List<BaseRole>>>
    //endregion
}