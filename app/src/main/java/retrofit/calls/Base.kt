package retrofit.calls

import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import retrofit.ApiClient
import retrofit.models.BaseGenre

object Base {
    suspend fun genres(): Result<List<BaseGenre>> {
        return try {
            val response = ApiClient.API.baseGenres()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.result != null) {
                        Result.success(body.result)
                    } else {
                        Result.failure(Exception(body.message))
                    }
                } else {
                    Result.failure(Exception(response.message()))
                }
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}