package su.afk.cocktailrecipe.presentation.screen.homeScreen

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import su.afk.cocktailrecipe.util.RegistrationUtil

/**
 * Тестовый пример юнит теста формы регистрации
 */
class ExampleAuthLoginPasswordTest {

    /**
     * Пустое имя юзера возвращает false
     */
    @Test
    fun `empty username return false`() {
        val result = RegistrationUtil.registration(
            "", "123", "123"
        )
        assertFalse(result)
    }

    @Test
    fun `valid username and corrected password return true`() {
        val result = RegistrationUtil.registration(
            "MyNick", "123", "123"
        )
        assertTrue(result)
    }

    @Test
    fun `username already exists return false`() {
        val result = RegistrationUtil.registration(
            "admin", "123", "123"
        )
        assertFalse(result)
    }

    @Test
    fun `empty password return false`() {
        val result = RegistrationUtil.registration(
            "MyNick", "", ""
        )
        assertFalse(result)
    }

    @Test
    fun `not confirmed password return false`() {
        val result = RegistrationUtil.registration(
            "MyNick", "123", "111"
        )
        assertFalse(result)
    }

    @Test
    fun `empty low 2 digit password return false`() {
        val result = RegistrationUtil.registration(
            "MyNick", "12", "12"
        )
        assertFalse(result)
    }


}