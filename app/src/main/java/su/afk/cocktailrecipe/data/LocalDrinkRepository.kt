package su.afk.cocktailrecipe.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import su.afk.cocktailrecipe.data.local.dao.CocktailDao
import su.afk.cocktailrecipe.data.local.entity.DrinkEntity
import su.afk.cocktailrecipe.domain.repository.LocalDrink
import javax.inject.Inject

class LocalDrinkRepository @Inject constructor(
    private val daoCocktail: CocktailDao
): LocalDrink {

    override suspend fun checkFavoriteCocktail(cocktailId: Int): DrinkEntity? {
        return withContext(Dispatchers.IO) {
            daoCocktail.checkDrink(cocktailId)
        }
    }

    override suspend fun saveCocktail(cocktail: DrinkEntity) {
        withContext(Dispatchers.IO) {
            daoCocktail.saveDrink(cocktail)
        }
    }

    override suspend fun deleteCocktail(cocktail: DrinkEntity) {
        withContext(Dispatchers.IO) {
            daoCocktail.deleteDrink(cocktail)
        }
    }

    override fun getAllCocktail(): Flow<List<DrinkEntity>> {
        return daoCocktail.queryAllDrink()
    }
}