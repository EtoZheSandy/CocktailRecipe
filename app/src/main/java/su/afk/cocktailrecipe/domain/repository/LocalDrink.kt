package su.afk.cocktailrecipe.domain.repository

import kotlinx.coroutines.flow.Flow
import su.afk.cocktailrecipe.data.local.entity.DrinkEntity
import su.afk.cocktailrecipe.domain.model.Drink

interface LocalDrink {

    suspend fun checkFavoriteCocktail(cocktailId: Int): DrinkEntity?

    suspend fun saveCocktail(cocktail: DrinkEntity)

    suspend fun deleteCocktail(cocktail: DrinkEntity)

    fun getAllCocktail(): Flow<List<Drink>>
}