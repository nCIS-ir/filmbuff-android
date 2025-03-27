package retrofit

import retrofit.models.AuthLogin
import retrofit.models.BaseGenre
import retrofit.models.ResponseWrapper
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    //region Auth
    @POST("auth/login")
    @FormUrlEncoded
    suspend fun authLogin(@Field("username") username: String, @Field("password") password: String): Response<ResponseWrapper<AuthLogin>>
    //endregion

    //region Base
    @GET("base/genres")
    suspend fun baseGenres(): Response<ResponseWrapper<List<BaseGenre>>>
    //endregion
}