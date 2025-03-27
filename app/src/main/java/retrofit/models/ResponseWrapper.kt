package retrofit.models

data class ResponseWrapper<T>(
    val result: T?,
    val meta: String?,
    val message: String?,
    val errors: String?,
)
