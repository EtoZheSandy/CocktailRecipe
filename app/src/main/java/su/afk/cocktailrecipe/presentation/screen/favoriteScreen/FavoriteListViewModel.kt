package su.afk.cocktailrecipe.presentation.screen.favoriteScreen

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import su.afk.cocktailrecipe.data.LocalDrinkRepository
import su.afk.cocktailrecipe.domain.model.DrinkListEntry
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val localRepository: LocalDrinkRepository
) : ViewModel() {

    init {
        getFavoriteCocktail()
    }

    private val _favoriteListCocktail = MutableStateFlow<List<DrinkListEntry>>(emptyList())
    val favoriteListCocktail: StateFlow<List<DrinkListEntry>> = _favoriteListCocktail

    private fun getFavoriteCocktail() {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.getAllCocktail().collect { listFavorite ->
                val mappedList = listFavorite.map { entity ->
                    DrinkListEntry(
                        nameDrink = entity.nameDrink,
                        imageUrl = entity.urlDrink,
                        idDrink = entity.id ?: 0
                    )
                }

                withContext(Dispatchers.Main) {
                    _favoriteListCocktail.value = mappedList
                }
            }
        }
    }


    fun calcDominateColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}