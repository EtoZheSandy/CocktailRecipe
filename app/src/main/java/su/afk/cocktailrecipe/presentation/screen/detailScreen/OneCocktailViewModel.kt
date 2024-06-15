package su.afk.cocktailrecipe.presentation.screen.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import su.afk.cocktailrecipe.data.NetworkDrinkRepository
import su.afk.cocktailrecipe.data.LocalDrinkRepository
import su.afk.cocktailrecipe.data.network.dto.ListDrinkResponseDto
import su.afk.cocktailrecipe.data.local.entity.DrinkEntity
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

@HiltViewModel
class OneCocktailViewModel @Inject constructor(
    private val repository: NetworkDrinkRepository,
    private val cocktailRepository: LocalDrinkRepository,
) : ViewModel() {

    val favorite = MutableStateFlow(false)

    private val _drinkInfo = MutableStateFlow<Resource<ListDrinkResponseDto>>(Resource.Loading())
    val drinkInfo: StateFlow<Resource<ListDrinkResponseDto>> = _drinkInfo

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

    fun checkFavoriteCocktail(cocktail: ListDrinkResponseDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val cocktailId = cocktail.drinks?.firstOrNull()?.idDrink?.toIntOrNull()
            if (cocktailId != null) {
                val isFavorite = cocktailRepository.checkFavoriteCocktail(cocktailId)
                favorite.value = isFavorite != null
            }
        }
    }

    fun saveCocktail(cocktail: ListDrinkResponseDto) {
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

    fun deleteCocktail(cocktail: ListDrinkResponseDto) {
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