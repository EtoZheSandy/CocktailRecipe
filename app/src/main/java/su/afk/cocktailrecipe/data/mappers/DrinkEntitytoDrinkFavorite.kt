package su.afk.cocktailrecipe.data.mappers

import su.afk.cocktailrecipe.data.local.entity.DrinkEntity
import su.afk.cocktailrecipe.domain.model.Drink
import su.afk.cocktailrecipe.domain.model.DrinkFavorite

fun DrinkEntity.toDrinkFavorite(): DrinkFavorite {
    return DrinkFavorite(
        id = id!!,
        nameDrink = nameDrink,
        urlDrink = urlDrink
    )
}

fun DrinkEntity.toDrink(): Drink {
    return Drink(
        idDrink = id!!,
        nameDrink = nameDrink,
        urlDrink = urlDrink
    )
}