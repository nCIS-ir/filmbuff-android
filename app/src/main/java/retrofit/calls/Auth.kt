package retrofit.calls

import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import retrofit.ApiClient
import retrofit.models.User
import retrofit.models.Session

object Auth {
    suspend fun info(): Result<User> {
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
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                Result.failure(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(username: String, password: String): Result<Session> {
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
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                Result.failure(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Void?> {
        return try {
            val response = ApiClient.API.authLogout()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                Result.failure(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun refresh(): Result<Session> {
        return try {
            val response = ApiClient.API.authRefresh()
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
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                Result.failure(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(username: String, password: String, email: String): Result<Void?> {
        return try {
            val response = ApiClient.API.authRegister(username, password, email)
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                Result.failure(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resend(username: String): Result<Void?> {
        return try {
            val response = ApiClient.API.authResend(username)
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                Result.failure(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verify(username: String, otp: Int): Result<Session> {
        return try {
            val response = ApiClient.API.authVerify(username, otp)
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
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                Result.failure(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}