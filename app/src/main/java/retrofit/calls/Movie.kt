package retrofit.calls

import androidx.lifecycle.lifecycleScope
import com.orhanobut.hawk.Hawk
import dialogs.LoadingDialog
import enums.Direction
import enums.Sort
import helpers.KeyHelper
import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import kotlinx.coroutines.launch
import retrofit.ApiClient
import retrofit.calls.Auth.refresh
import retrofit.models.MovieBrief
import retrofit.models.MovieFull
import retrofit.models.MovieGenre
import retrofit.models.Review

object Movie {
    suspend fun allGenres(sort: Sort = Sort.POPULARITY, direction: Direction = Direction.DESCENDING, onSuccess: (List<MovieGenre>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_loading_data))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.movieAllGenres(sort.value, direction.value)
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
                            App.ACTIVITY.lifecycleScope.launch { allGenres(sort, direction, onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
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
            if (response.isSuccessful) {
                onSuccess?.invoke()
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun details(movieId: String, onSuccess: ((MovieFull) -> Unit), onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_loading_data))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.movieDetails(movieId)
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
                            App.ACTIVITY.lifecycleScope.launch { details(movieId, onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
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
            if (response.isSuccessful) {
                onSuccess?.invoke()
            } else {
                val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun favoriteGet(onSuccess: (List<MovieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_loading_data))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.movieFavoriteGet()
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
                            Hawk.put(KeyHelper.TOKEN, it.token)
                            Hawk.put(KeyHelper.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { favoriteGet(onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun favoriteSet(movie: MovieFull, onSuccess: () -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(if (movie.isFavorite) R.string.api_favorite_delete else R.string.api_favorite_set))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.movieFavoriteSet(movie.id)
            if (response.isSuccessful) {
                onSuccess()
            } else {
                if (response.code() == 401) {
                    refresh(
                        {
                            Hawk.put(KeyHelper.TOKEN, it.token)
                            Hawk.put(KeyHelper.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { favoriteSet(movie, onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun genre(genreId: String, page: Int = 1, perPage: Int = 10, sort: Sort = Sort.POPULARITY, direction: Direction = Direction.DESCENDING, onSuccess: (List<MovieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_loading_data))
            loadingDialog.show()
        }
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
                            Hawk.put(KeyHelper.TOKEN, it.token)
                            Hawk.put(KeyHelper.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { genre(genreId, page, perPage, sort, direction, onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun recent(onSuccess: (List<MovieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_loading_data))
            loadingDialog.show()
        }
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
                            Hawk.put(KeyHelper.TOKEN, it.token)
                            Hawk.put(KeyHelper.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { recent(onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun reviewGet(movieId: String, onSuccess: (List<Review>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_review_get))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.movieReviewGet(movieId)
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
                            App.ACTIVITY.lifecycleScope.launch { reviewGet(movieId, onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun reviewSubmit(movieId: String, score: Int, content: String, onSuccess: () -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_review_submit))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.movieReviewSubmit(movieId, score, content)
            if (response.isSuccessful) {
                onSuccess()
            } else {
                if (response.code() == 401) {
                    refresh(
                        {
                            Hawk.put(KeyHelper.TOKEN, it.token)
                            Hawk.put(KeyHelper.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { reviewSubmit(movieId, score, content, onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }

    suspend fun search(term: String, genreIds: List<String> = listOf(), onSuccess: (List<MovieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_loading_data))
            loadingDialog.show()
        }
        try {
            val response = ApiClient.API.movieSearch(term, genreIds.joinToString(","))
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
                            App.ACTIVITY.lifecycleScope.launch { search(term, genreIds, onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }


    suspend fun slider(onSuccess: (List<MovieBrief>) -> Unit, onError: ((Exception) -> Unit)? = null, showLoading: Boolean = true) {
        var loadingDialog: LoadingDialog? = null
        if (showLoading) {
            loadingDialog = LoadingDialog(App.ACTIVITY, App.ACTIVITY.getString(R.string.api_loading_data))
            loadingDialog.show()
        }
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
                            Hawk.put(KeyHelper.TOKEN, it.token)
                            Hawk.put(KeyHelper.REFRESH, it.refresh)
                            App.ACTIVITY.lifecycleScope.launch { slider(onSuccess, onError, showLoading) }
                        },
                        {
                            onError?.invoke(it)
                        })
                } else {
                    val errorMessage = response.errorBody()?.string() ?: App.ACTIVITY.getString(R.string.error_unknown)
                    onError?.invoke(Exception("HTTP ${response.code()}: $errorMessage"))
                }
            }
        } catch (e: Exception) {
            onError?.invoke(e)
        } finally {
            loadingDialog?.dismiss()
        }
    }
}