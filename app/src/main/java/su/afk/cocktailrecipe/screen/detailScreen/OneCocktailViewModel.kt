package su.afk.cocktailrecipe.screen.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import su.afk.cocktailrecipe.data.LocalRepository
import su.afk.cocktailrecipe.data.DrinkRepository
import su.afk.cocktailrecipe.data.models.ThecocktaildbModels
import su.afk.cocktailrecipe.data.room.entity.DrinkEntity
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

@HiltViewModel
class OneCocktailViewModel @Inject constructor(
    private val repository: DrinkRepository,
    private val cocktailRepository: LocalRepository,
) : ViewModel() {

    val favorite = MutableStateFlow(false)

    suspend fun getDrinkInfo(drinkId: String) : Resource<ThecocktaildbModels> {
        return repository.getDrinkDetail(drinkId.toInt())
    }

    fun checkFavoriteCocktail(cocktail: ThecocktaildbModels){
        viewModelScope.launch {
            val cocktailId = cocktail.drinks?.firstOrNull()?.idDrink?.toIntOrNull()
            if (cocktailId != null) {
                val isFavorite = cocktailRepository.checkFavoriteCocktail(cocktailId)
                favorite.value = isFavorite != null
            }
        }
    }

    fun saveCocktail(cocktail: ThecocktaildbModels){
        viewModelScope.launch {
            cocktailRepository.saveCocktail(
                DrinkEntity(id = cocktail.drinks?.firstOrNull()!!.idDrink.toInt(),
                    nameDrink = cocktail.drinks?.firstOrNull()!!.strDrink,
                    urlDrink = cocktail.drinks?.firstOrNull()!!.strDrinkThumb)
            )
            favorite.value = true
        }
    }

    fun deleteCocktail(cocktail: ThecocktaildbModels){
        viewModelScope.launch {
            cocktailRepository.deleteCocktail(
                DrinkEntity(id = cocktail.drinks?.firstOrNull()!!.idDrink.toInt(),
                    nameDrink = cocktail.drinks?.firstOrNull()!!.strDrink,
                    urlDrink = cocktail.drinks?.firstOrNull()!!.strDrinkThumb)
            )
            favorite.value = false
        }
    }


}