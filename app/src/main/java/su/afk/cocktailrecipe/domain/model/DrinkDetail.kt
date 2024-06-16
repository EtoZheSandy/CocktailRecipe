package su.afk.cocktailrecipe.domain.model

data class DrinkDetail(
    val idDrink: Int,
    val nameDrink: String,
    val urlDrink: String,
    val recipe: String,
    val categoryAlcoholic: String,
    val ingredientList: List<String>,
    val measureList: List<String>,
)
