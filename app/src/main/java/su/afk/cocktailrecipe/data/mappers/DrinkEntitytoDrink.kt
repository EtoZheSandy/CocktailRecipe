package su.afk.cocktailrecipe.data.mappers

import su.afk.cocktailrecipe.data.local.entity.DrinkEntity
import su.afk.cocktailrecipe.domain.model.Drink

fun DrinkEntity.toDrink(): Drink {
    return Drink(
        idDrink = id!!,
        nameDrink = nameDrink,
        urlDrink = urlDrink
    )
}