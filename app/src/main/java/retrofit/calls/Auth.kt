package retrofit.calls

import dialogs.LoadingDialog
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import retrofit.ApiClient
import retrofit.models.Session
import retrofit.models.User

object Auth {
    suspend fun info(onSuccess: (User) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.CONTEXT.getString(R.string.api_auth_info))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authInfo()
            loadingDialog?.dismiss()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.result != null) {
                        onSuccess(body.result)
                    } else {
                        onError?.invoke(Exception(body.message))
                    }
                } else {
                    onError?.invoke(Exception(response.message()))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    suspend fun login(username: String, password: String, onSuccess: (Session) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.CONTEXT.getString(R.string.api_auth_login))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authLogin(username, password)
            loadingDialog?.dismiss()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.result != null) {
                        onSuccess(body.result)
                    } else {
                        onError?.invoke(Exception(body.message))
                    }
                } else {
                    onError?.invoke(Exception(response.message()))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    suspend fun logout(onSuccess: () -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.CONTEXT.getString(R.string.api_auth_logout))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authLogout()
            loadingDialog?.dismiss()
            if (response.isSuccessful) {
                onSuccess()
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    suspend fun refresh(onSuccess: (Session) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.CONTEXT.getString(R.string.api_auth_refresh))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authRefresh()
            loadingDialog?.dismiss()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.result != null) {
                        onSuccess(body.result)
                    } else {
                        onError?.invoke(Exception(body.message))
                    }
                } else {
                    onError?.invoke(Exception(response.message()))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
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