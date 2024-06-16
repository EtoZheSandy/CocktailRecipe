package su.afk.cocktailrecipe.presentation.screen.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import su.afk.cocktailrecipe.data.local.entity.DrinkEntity
import su.afk.cocktailrecipe.domain.model.DrinkDetail
import su.afk.cocktailrecipe.domain.repository.LocalDrink
import su.afk.cocktailrecipe.domain.repository.NetworkDrink
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val networkDrink: NetworkDrink,
    private val localDrink: LocalDrink,
) : ViewModel() {

    private val _favorite = MutableStateFlow(false)
    val favorite = _favorite.asStateFlow()

    private val _cocktailDetail = MutableStateFlow<Resource<DrinkDetail>>(Resource.Loading())
    val cocktailDetail: StateFlow<Resource<DrinkDetail>> = _cocktailDetail

    fun getDrinkDetail(drinkId: String) {
        viewModelScope.launch(Dispatchers.IO) {
        val id = if (drinkId == "1") loadRandomCocktail() else drinkId
            _cocktailDetail.value = networkDrink.getDrinkDetail(id.toInt())
        }
    }

    fun checkFavoriteDrink(cocktailId: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (cocktailId != null) {
                val isFavorite = localDrink.checkFavoriteCocktail(cocktailId)
                _favorite.value = isFavorite != null
            }
        }
    }

    fun saveCocktail(cocktail: DrinkDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            localDrink.saveCocktail(
                DrinkEntity(
                    id = cocktail.idDrink,
                    nameDrink = cocktail.nameDrink,
                    urlDrink = cocktail.urlDrink
                )
            )
            _favorite.value = true
        }
    }

    fun deleteCocktail(cocktail: DrinkDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            localDrink.deleteCocktail(
                DrinkEntity(
                    id = cocktail.idDrink,
                    nameDrink = cocktail.nameDrink,
                    urlDrink = cocktail.urlDrink
                )
            )
            _favorite.value = false
        }
    }

    private suspend fun loadRandomCocktail(): String {
        var randomCocktailId = "1"

        val resultRandom = networkDrink.getDrinkRandom()
        when (resultRandom) {
            is Resource.Success -> {
                randomCocktailId =
                    resultRandom.data?.drinks?.firstOrNull()?.idDrink ?: "1"
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