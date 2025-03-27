package retrofit

import com.orhanobut.hawk.Hawk
import helpers.KeyString
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    val API: ApiInterface = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(object: Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        return chain.proceed(
                            chain
                                .request()
                                .newBuilder()
                                .headers(
                                    Headers.Builder()
                                        .add("Accept-Language", Hawk.get(KeyString.LANGUAGE, "en"))
                                        .add("Authorization", "Bearer ${Hawk.get(KeyString.TOKEN, "")}")
                                        .build()
                                )
                                .build()
                        )
                    }
                })
                .build()
        )
        .baseUrl("https://film-buff.ir/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)
}