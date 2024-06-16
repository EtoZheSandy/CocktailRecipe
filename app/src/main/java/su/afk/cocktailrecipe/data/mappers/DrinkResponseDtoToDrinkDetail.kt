package su.afk.cocktailrecipe.data.mappers

import su.afk.cocktailrecipe.data.network.dto.DrinkResponseDto
import su.afk.cocktailrecipe.domain.model.DrinkDetail

fun DrinkResponseDto.toDrinkDetail(): DrinkDetail {
    // Collect non-null ingredient strings into a list
    val ingredients = listOfNotNull(
        strIngredient1,
        strIngredient2,
        strIngredient3,
        strIngredient4,
        strIngredient5,
        strIngredient6,
        strIngredient7,
        strIngredient8,
        strIngredient9,
        strIngredient10,
        strIngredient11,
        strIngredient12,
        strIngredient13,
        strIngredient14,
        strIngredient15
    )
    val measures = listOfNotNull(
        strMeasure1,
        strMeasure2,
        strMeasure3,
        strMeasure4,
        strMeasure5,
        strMeasure6,
        strMeasure7,
        strMeasure8,
        strMeasure9,
        strMeasure10,
        strMeasure11,
        strMeasure12,
        strMeasure13,
        strMeasure14,
        strMeasure15
    )

    return DrinkDetail(
        idDrink = idDrink.toInt(),
        nameDrink = strDrink,
        urlDrink = strDrinkThumb,
        recipe = strInstructions,
        categoryAlcoholic = strAlcoholic,
        ingredientList = ingredients,
        measureList = measures
    )
}