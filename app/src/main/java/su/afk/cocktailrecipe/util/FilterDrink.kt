package su.afk.cocktailrecipe.util

object FilterDrink {
    fun compare(value: String): String {
        return when (value) {
            "Любой" -> "ANY"
            "Алкогольный" -> "Alcoholic"
            "Безалкогольный" -> "Non alcoholic"
            else -> "ANY"
        }
    }
}