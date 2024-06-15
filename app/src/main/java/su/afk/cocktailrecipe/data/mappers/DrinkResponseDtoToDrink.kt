package su.afk.cocktailrecipe.data.mappers

import su.afk.cocktailrecipe.data.network.dto.DrinkResponseDto
import su.afk.cocktailrecipe.domain.model.Drink

fun DrinkResponseDto.toDrink(): Drink {
    return Drink(
        idDrink = idDrink.toInt(),
        nameDrink = strDrink,
        urlDrink = strDrinkThumb
    )
}