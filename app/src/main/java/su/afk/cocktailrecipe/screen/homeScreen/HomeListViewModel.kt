package su.afk.cocktailrecipe.screen.homeScreen

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import su.afk.cocktailrecipe.data.DrinkRepository
import su.afk.cocktailrecipe.data.models.DrinkListEntry
import su.afk.cocktailrecipe.di.Constans.PAGE_SIZE
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val repository: DrinkRepository
) : ViewModel() {


    private var currencyPage = 0

    var cocktailList = mutableStateOf<List<DrinkListEntry>>(listOf()) // наш список коктелей
    var loadError = mutableStateOf("") // для ошибок загрузки
    var isLoading = mutableStateOf(true) // для статуса загрузки
//    var endReached = mutableStateOf(false) // для показа - последняя ли это страница

    private var cacheCocktailList = listOf<DrinkListEntry>()
    private var isSearchStarting = true
//    var isSearching = mutableStateOf(false)
    var randomCocktailId = mutableStateOf("1")


    init {
        loadCocktailPaginated()
        loadRandomCocktail()
    }

    fun searchDrinkName(query: String) {
        viewModelScope.launch {
            val result = repository.getDrinkName(query)
            when(result) {
                is Resource.Success -> {
                    val cocktailEntries: List<DrinkListEntry> = result.data!!.drinks!!.map {
                        DrinkListEntry(nameDrink = it.strDrink, imageUrl = it.strDrinkThumb, idDrink = it.idDrink.toInt())
                    }
                    currencyPage + 1

                    loadError.value = ""
                    isLoading.value = false
                    cocktailList.value = cocktailEntries //.shuffled() // рандомим вывод
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                }
            }
        }
    }

    private suspend fun searchOnline(query: String) {

    }

    private suspend fun searchOffline(query: String) {

    }
//        val listToSearch = if (isSearchStarting) {
//            cocktailList.value
//        } else {
//            cacheCocktailList
//        }
//        viewModelScope.launch(Dispatchers.Default) {
//            if(query.isEmpty()) { //если перестали искать и очиситили поле ввода
//                cocktailList.value = cacheCocktailList // если строка поиска пуста или удалена мы возвращаем ее из кэша
//                isSearchStarting = true
//                return@launch
//            }
//
//            val results = listToSearch.filter {
//                // поиск по названию с удалением пробелов и регистров
//                it.nameDrink.contains(query.trim(), ignoreCase = true) ||
//                        it.idDrink.toString() == query.trim() // поиск по id
//            }
//            //сработает при первом запуске поиска
//            if(isSearchStarting) {
//                cacheCocktailList = cocktailList.value
//                isSearchStarting = false
//            }
//            cocktailList.value = results
//        }


    fun loadCocktailPaginated() {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getDrinkHome(PAGE_SIZE, currencyPage * PAGE_SIZE)
            when(result) {
                is Resource.Success -> {
//                    endReached.value = currencyPage * PAGE_SIZE >= result.data!!.count

                    val cocktailEntries: List<DrinkListEntry> = result.data!!.drinks!!.map {
                        DrinkListEntry(nameDrink = it.strDrink, imageUrl = it.strDrinkThumb, idDrink = it.idDrink.toInt())
                    }
                    currencyPage + 1

                    loadError.value = ""
                    isLoading.value = false
                    cocktailList.value += cocktailEntries.shuffled() // рандомим вывод
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                }
            }
        }
    }

    fun loadRandomCocktail() {
        viewModelScope.launch(Dispatchers.IO) {
            val resultRandom = repository.getDrinkRandom()
            withContext(Dispatchers.Main) {
                when (resultRandom) {
                    is Resource.Success -> {
                        randomCocktailId.value =
                            resultRandom.data!!.drinks!!.firstOrNull()!!.idDrink!!
                    }

                    is Resource.Error -> {
                        randomCocktailId.value = "1"
                    }

                    is Resource.Loading -> {
                    }
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

    //TODO: В идеале реализовать поиск по локальному хранилищу (сохранить все коктейли)
    //TODO: И кэшировать се эти данные в Room


    //TODO: Кнопка показать рандомный коктель
//    private val apiService = RepositoryDrink.
//    val posts: MutableState<ThecocktaildbModels?> = mutableStateOf(null)
//    fun getPosts() {
//        viewModelScope.launch {
////            try {
//                val response = apiService.getRandom()
////                if (response.nUL) {
//                    posts.value = response
////                }
////            } catch (e: Exception) {
//                // Handle errors here
////            }
//        }
//    }
}