package helpers

import com.google.gson.GsonBuilder
import database.models.User
import ir.ncis.filmbuff.App
import java.util.Base64

object JwtHelper {
    fun getUser(token: String): User? {
        return try {
            val gson = GsonBuilder().create()
            val payload = token.split('.')[1]
            val decoded = String(Base64.getDecoder().decode(payload.toByteArray()), Charsets.UTF_8)
            val jwt = gson.fromJson(decoded, Jwt::class.java)
            return App.DB.userDao().one(jwt.sub)
        } catch (_: Exception) {
            null
        }
    }

    private data class Jwt(
        val iss: String,
        val aud: String,
        val iat: String,
        val nbf: String,
        val exp: String,
        val jti: String,
        val sub: String,
    )
}