package su.afk.cocktailrecipe.presentation.screen.favoriteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import su.afk.cocktailrecipe.domain.model.Drink
import su.afk.cocktailrecipe.domain.model.DrinkFavorite
import su.afk.cocktailrecipe.domain.repository.LocalDrink
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val localRepository: LocalDrink
) : ViewModel() {

    private val _favoriteListCocktail = MutableStateFlow<List<Drink>>(emptyList())
    val favoriteListCocktail: StateFlow<List<Drink>> = _favoriteListCocktail.asStateFlow()

    private fun getAllFavoriteCocktail() = viewModelScope.launch(Dispatchers.IO) {
        localRepository.getAllCocktail().collect { listFavorite ->

            withContext(Dispatchers.Main) {
                _favoriteListCocktail.value = listFavorite
            }
        }
    }

    init {
        getAllFavoriteCocktail()
    }
}