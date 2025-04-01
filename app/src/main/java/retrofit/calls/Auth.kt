package retrofit.calls

import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import retrofit.ApiClient
import retrofit.models.AuthInfo
import retrofit.models.AuthLogin

object Auth {
    suspend fun info(): Result<AuthInfo> {
        return try {
            val response = ApiClient.API.authInfo()
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

    suspend fun login(username: String, password: String): Result<AuthLogin> {
        return try {
            val response = ApiClient.API.authLogin(username, password)
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