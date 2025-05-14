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
import retrofit.models.SerieBrief
import retrofit.models.SerieGenre

object Serie {
    suspend fun favorite(onSuccess: (List<SerieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        try {
            val response = ApiClient.API.serieFavorite()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    if (body.result != null) {
                        val result = mutableListOf<SerieBrief>()
                        body.result.forEach { result += it.serie!! }
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

    suspend fun allGenres(sort: Sort = Sort.POPULARITY, direction: Direction = Direction.DESCENDING, onSuccess: (List<SerieGenre>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        try {
            val response = ApiClient.API.serieAllGenres(sort.value, direction.value)
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
                            App.ACTIVITY.lifecycleScope.launch { allGenres(sort, direction, onSuccess, onError, showLoading) }
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

    suspend fun slider(onSuccess: (List<SerieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        try {
            val response = ApiClient.API.serieSlider()
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

    suspend fun addFavorite(serieId: String, onSuccess: (() -> Unit)? = null, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.favorite_add))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.addFavoriteSerie(serieId)
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

    suspend fun deleteFavorite(serieId: String, onSuccess: (() -> Unit)? = null, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.favorite_delete))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.deleteFavoriteSerie(serieId)
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

    suspend fun editVisit(episodeFileId: String, onSuccess: (() -> Unit)? = null, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.visit_save))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.editVisitSerie(episodeFileId)
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

    suspend fun deleteVisit(episodeFileId: String, onSuccess: (() -> Unit)? = null, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.visit_delete))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.deleteVisitSerie(episodeFileId)
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