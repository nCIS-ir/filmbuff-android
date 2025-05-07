package retrofit.calls

import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import enums.Direction
import enums.Sort
import helpers.KeyString
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import kotlinx.coroutines.launch
import retrofit.ApiClient
import retrofit.calls.Auth.refresh
import retrofit.models.Favorite
import retrofit.models.SerieBrief

object Serie {
    suspend fun favorite(onSuccess: (List<Favorite>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        try {
            val response = ApiClient.API.serieFavorite()
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
    suspend fun genre(genreId: String, page: Int = 1, perPage: Int = 10, sort: Sort = Sort.POPULARITY, direction: Direction = Direction.DESCENDING, onSuccess: (List<SerieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        try {
            val response = ApiClient.API.serieGenre(genreId, page, perPage, sort.value, direction.value)
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

    suspend fun recent(onSuccess: (List<SerieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        try {
            val response = ApiClient.API.serieRecent()
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
}