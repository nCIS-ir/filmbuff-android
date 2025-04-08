package retrofit.calls

import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import retrofit.ApiClient
import retrofit.models.Activity
import retrofit.models.Artist
import retrofit.models.Country
import retrofit.models.Genre
import retrofit.models.Language
import retrofit.models.Pack
import retrofit.models.Plan
import retrofit.models.Quality
import retrofit.models.Role

object Base {
    suspend fun activities(): Result<List<Activity>> {
        return try {
            val response = ApiClient.API.baseActivities()
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

    suspend fun artists(): Result<List<Artist>> {
        return try {
            val response = ApiClient.API.baseArtists()
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

    suspend fun countries(): Result<List<Country>> {
        return try {
            val response = ApiClient.API.baseCountries()
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

    suspend fun genres(): Result<List<Genre>> {
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
                val errorMessage = response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)
                Result.failure(Exception("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun languages(): Result<List<Language>> {
        return try {
            val response = ApiClient.API.baseLanguages()
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

    suspend fun packs(): Result<List<Pack>> {
        return try {
            val response = ApiClient.API.basePacks()
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

    suspend fun plans(): Result<List<Plan>> {
        return try {
            val response = ApiClient.API.basePlans()
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

    suspend fun qualities(): Result<List<Quality>> {
        return try {
            val response = ApiClient.API.baseQualities()
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

    suspend fun roles(): Result<List<Role>> {
        return try {
            val response = ApiClient.API.baseRoles()
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