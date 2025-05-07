package retrofit.calls

import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import dialogs.LoadingDialog
import enums.Direction
import enums.Sort
import helpers.KeyString
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import kotlinx.coroutines.launch
import retrofit.ApiClient
import retrofit.calls.Auth.refresh
import retrofit.models.MovieBrief

object Movie {
    suspend fun favorite(onSuccess: (List<MovieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        try {
            val response = ApiClient.API.movieFavorite()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.result != null) {
                        val result = mutableListOf<MovieBrief>()
                        body.result.forEach { result += it.movie!! }
                        onSuccess(result)
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
                            Hawk.put(KeyString.TOKEN, it.token)
                            Hawk.put(KeyString.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { favorite(onSuccess, onError, showLoading) }
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
        }
    }

    suspend fun genre(genreId: String, page: Int = 1, perPage: Int = 10, sort: Sort = Sort.POPULARITY, direction: Direction = Direction.DESCENDING, onSuccess: (List<MovieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        try {
            val response = ApiClient.API.movieGenre(genreId, page, perPage, sort.value, direction.value)
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
                            Hawk.put(KeyString.TOKEN, it.token)
                            Hawk.put(KeyString.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { genre(genreId, page, perPage, sort, direction, onSuccess, onError, showLoading) }
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
        }
    }

    suspend fun recent(onSuccess: (List<MovieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        try {
            val response = ApiClient.API.movieRecent()
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
                            Hawk.put(KeyString.TOKEN, it.token)
                            Hawk.put(KeyString.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { recent(onSuccess, onError, showLoading) }
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
        }
    }

    suspend fun slider(onSuccess: (List<MovieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        try {
            val response = ApiClient.API.movieSlider()
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
                            Hawk.put(KeyString.TOKEN, it.token)
                            Hawk.put(KeyString.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { slider(onSuccess, onError, showLoading) }
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
        }
    }

    suspend fun addFavorite(movieId: String, onSuccess: (() -> Unit)? = null, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.favorite_add))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.addFavoriteMovie(movieId)
            loadingDialog?.dismiss()
            if (response.isSuccessful) {
                onSuccess?.invoke()
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    suspend fun deleteFavorite(movieId: String, onSuccess: (() -> Unit)? = null, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.favorite_delete))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.deleteFavoriteMovie(movieId)
            loadingDialog?.dismiss()
            if (response.isSuccessful) {
                onSuccess?.invoke()
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    suspend fun editVisit(movieFileId: String, onSuccess: (() -> Unit)? = null, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.visit_save))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.editVisitMovie(movieFileId)
            loadingDialog?.dismiss()
            if (response.isSuccessful) {
                onSuccess?.invoke()
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }

    suspend fun deleteVisit(movieFileId: String, onSuccess: (() -> Unit)? = null, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.visit_delete))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.deleteVisitMovie(movieFileId)
            loadingDialog?.dismiss()
            if (response.isSuccessful) {
                onSuccess?.invoke()
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.unknown_error)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        }
    }
}