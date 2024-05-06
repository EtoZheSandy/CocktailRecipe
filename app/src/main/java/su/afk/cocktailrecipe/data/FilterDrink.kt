package su.afk.cocktailrecipe.data

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