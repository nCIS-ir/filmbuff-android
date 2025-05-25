package retrofit.calls

import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import dialogs.LoadingDialog
import helpers.KeyHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import kotlinx.coroutines.launch
import retrofit.ApiClient
import retrofit.models.Session
import retrofit.models.User

object Auth {
    suspend fun info(onSuccess: (User) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            val title = App.ACTIVITY.getString(R.string.api_auth_info)
            loadingDialog = LoadingDialog(App.ACTIVITY, title)
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authInfo()
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
                if (response.code() == 401) {
                    refresh(
                        {
                            Hawk.put(KeyHelper.TOKEN, it.token)
                            Hawk.put(KeyHelper.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { info(onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun login(username: String, password: String, onSuccess: (Session) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_auth_login))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authLogin(username, password)
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
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun logout(onSuccess: () -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_auth_logout))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authLogout()
            if (response.isSuccessful) {
                onSuccess()
            } else {
                if (response.code() == 401) {
                    refresh(
                        {
                            Hawk.put(KeyHelper.TOKEN, it.token)
                            Hawk.put(KeyHelper.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { logout(onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun refresh(onSuccess: (Session) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        Hawk.put(KeyHelper.TOKEN, Hawk.get(KeyHelper.REFRESH, ""))
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_auth_refresh))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authRefresh()
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
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun register(username: String, password: String, email: String, onSuccess: () -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_auth_register))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authRegister(username, password, email)
            if (response.isSuccessful) {
                onSuccess()
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun resend(username: String, onSuccess: () -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_auth_resend))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authResend(username)
            if (response.isSuccessful) {
                onSuccess()
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun verify(username: String, otp: Int, onSuccess: (Session) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_auth_verify))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.authVerify(username, otp)
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
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }
}