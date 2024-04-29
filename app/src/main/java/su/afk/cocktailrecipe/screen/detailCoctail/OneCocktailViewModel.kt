package su.afk.cocktailrecipe.screen.detailCoctail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import su.afk.cocktailrecipe.data.DrinkRepository
import su.afk.cocktailrecipe.data.models.ThecocktaildbModels
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

@HiltViewModel
class OneCocktailViewModel @Inject constructor(
    private val repository: DrinkRepository
) : ViewModel() {

    suspend fun getDrinkInfo(drinkId: String) : Resource<ThecocktaildbModels> {
        return repository.getDrinkDetail(drinkId.toInt())
    }
}