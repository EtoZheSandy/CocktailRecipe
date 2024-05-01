package su.afk.cocktailrecipe.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import su.afk.cocktailrecipe.data.room.dao.CocktailDao
import su.afk.cocktailrecipe.data.room.entity.DrinkEntity
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val daoCocktail: CocktailDao
) {

    suspend fun checkFavoriteCocktail(cocktailId: Int): DrinkEntity? {
        return withContext(Dispatchers.IO) {
            daoCocktail.checkItem(cocktailId)
        }
    }

    suspend fun saveCocktail(cocktail: DrinkEntity) {
        withContext(Dispatchers.IO) {
            daoCocktail.saveItem(cocktail)
        }
    }

    suspend fun deleteCocktail(cocktail: DrinkEntity) {
        withContext(Dispatchers.IO) {
            daoCocktail.deleteItem(cocktail)
        }
    }
}