package su.afk.cocktailrecipe.util

object RegistrationUtil {

    private val existingUsers = listOf("admin", "123")

    fun registration(
        username: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if(username.isEmpty() || password.isEmpty()) return false
        if(username in existingUsers) return false
        if(password != confirmedPassword) return false
        if(password.length < 0) return false
        return true
    }
}