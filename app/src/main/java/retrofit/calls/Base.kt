package retrofit.calls

import ir.ncis.filmbuff.App
import ir.ncis.filmbuff.R
import retrofit.ApiClient
import retrofit.models.BaseActivity
import retrofit.models.BaseArtist
import retrofit.models.BaseCountry
import retrofit.models.BaseGenre
import retrofit.models.BaseLanguage
import retrofit.models.BasePack
import retrofit.models.BasePlan
import retrofit.models.BaseQuality
import retrofit.models.BaseRole

object Base {
    suspend fun activities(): Result<List<BaseActivity>> {
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
                Result.failure(Exception(response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun artists(): Result<List<BaseArtist>> {
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
                Result.failure(Exception(response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun countries(): Result<List<BaseCountry>> {
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
                Result.failure(Exception(response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

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

    suspend fun languages(): Result<List<BaseLanguage>> {
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
                Result.failure(Exception(response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun packs(): Result<List<BasePack>> {
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
                Result.failure(Exception(response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun plans(): Result<List<BasePlan>> {
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
                Result.failure(Exception(response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun qualities(): Result<List<BaseQuality>> {
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
                Result.failure(Exception(response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun roles(): Result<List<BaseRole>> {
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
                Result.failure(Exception(response.errorBody()?.string() ?: App.CONTEXT.getString(R.string.unknown_error)))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}