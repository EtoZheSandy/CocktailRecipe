package su.afk.cocktailrecipe.screen.detailScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import su.afk.cocktailrecipe.data.DrinkRepository
import su.afk.cocktailrecipe.data.LocalRepository
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

    private val _drinkInfo = MutableStateFlow<Resource<ThecocktaildbModels>>(Resource.Loading())
    val drinkInfo: StateFlow<Resource<ThecocktaildbModels>> = _drinkInfo

    suspend fun getDrinkInfo(drinkId: String) {
        viewModelScope.launch(Dispatchers.IO) {
        val id = if (drinkId == "1") {
            loadRandomCocktail()
        } else {
            drinkId
        }
        _drinkInfo.value = repository.getDrinkDetail(id.toInt())
        }
    }

    fun checkFavoriteCocktail(cocktail: ThecocktaildbModels) {
        viewModelScope.launch(Dispatchers.IO) {
            val cocktailId = cocktail.drinks?.firstOrNull()?.idDrink?.toIntOrNull()
            if (cocktailId != null) {
                val isFavorite = cocktailRepository.checkFavoriteCocktail(cocktailId)
                favorite.value = isFavorite != null
            }
        }
    }

    fun saveCocktail(cocktail: ThecocktaildbModels) {
        viewModelScope.launch(Dispatchers.IO) {
            cocktailRepository.saveCocktail(
                DrinkEntity(
                    id = cocktail.drinks?.firstOrNull()!!.idDrink.toInt(),
                    nameDrink = cocktail.drinks?.firstOrNull()!!.strDrink,
                    urlDrink = cocktail.drinks?.firstOrNull()!!.strDrinkThumb
                )
            )
            favorite.value = true
        }
    }

    fun deleteCocktail(cocktail: ThecocktaildbModels) {
        viewModelScope.launch(Dispatchers.IO) {
            cocktailRepository.deleteCocktail(
                DrinkEntity(
                    id = cocktail.drinks?.firstOrNull()!!.idDrink.toInt(),
                    nameDrink = cocktail.drinks?.firstOrNull()!!.strDrink,
                    urlDrink = cocktail.drinks?.firstOrNull()!!.strDrinkThumb
                )
            )
            favorite.value = false
        }
    }

    private suspend fun loadRandomCocktail(): String {
        var randomCocktailId = "1"

        val resultRandom = repository.getDrinkRandom()
        when (resultRandom) {
            is Resource.Success -> {
                randomCocktailId =
                    resultRandom.data!!.drinks!!.firstOrNull()?.idDrink ?: "1"
            }

            is Resource.Error -> {
                randomCocktailId = "1"
            }

            is Resource.Loading -> {
            }
        }
        return randomCocktailId
    }

}