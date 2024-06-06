package su.afk.cocktailrecipe.screen.homeScreen

//import timber.log.Timber
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import su.afk.cocktailrecipe.data.ConnectivityRepository
import su.afk.cocktailrecipe.data.DrinkRepository
import su.afk.cocktailrecipe.data.models.DrinkListEntry
import su.afk.cocktailrecipe.di.Constans.PAGE_SIZE
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val repository: DrinkRepository,
    private val connectivityRepository: ConnectivityRepository,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> get() = _searchText

    fun setSearchText(newText: String) {
        _searchText.value = newText
    }

    private val isOnline = connectivityRepository.isConnected // проверка на подключение к интернету

    var cocktailList = mutableStateOf<List<DrinkListEntry>>(listOf()) // наш список коктелей
    var loadError = mutableStateOf("") // для ошибок загрузки
    var isLoading = mutableStateOf(true) // для статуса загрузки

    private var cacheCocktailList = listOf<DrinkListEntry>()
    private var isSearchStarting = true

    init {
        loadCocktailPaginated()
    }

    // Функция поиска по названию
    fun searchDrinkName(query: String) {
        viewModelScope.launch(Dispatchers.IO) {

            isOnline.collect { isConnected ->
                if (isConnected) {
//                    Timber.tag("TAG").d("true")
                    searchOnline(query)
                } else {
//                    Timber.tag("TAG").d("false")
                    searchOffline(query)
                }
            }
        }
    }

    private suspend fun searchOnline(query: String) {
        val result = repository.getDrinkName(query)
        when (result) {
            is Resource.Success -> {
//                val cocktailEntries: List<DrinkListEntry> = result.data!!.drinks!!.map {
//                    DrinkListEntry(nameDrink = it.strDrink, imageUrl = it.strDrinkThumb, idDrink = it.idDrink.toInt())
//                }
                val cocktailEntries: List<DrinkListEntry> = result.data?.drinks?.map {
                    DrinkListEntry(
                        nameDrink = it.strDrink,
                        imageUrl = it.strDrinkThumb,
                        idDrink = it.idDrink.toInt()
                    )
                } ?: emptyList()
                loadError.value = ""
                isLoading.value = false
                cocktailList.value = cocktailEntries //.shuffled() // рандомим вывод
            }

            is Resource.Error -> {
                loadError.value = result.message!!
                isLoading.value = false
            }

            is Resource.Loading -> {
                isLoading.value = true
            }
        }
    }

    private suspend fun searchOffline(query: String) {
        val listToSearch = if (isSearchStarting) {
            cocktailList.value
        } else {
            cacheCocktailList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) { //если перестали искать и очиситили поле ввода
                cocktailList.value =
                    cacheCocktailList // если строка поиска пуста или удалена мы возвращаем ее из кэша
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter {
                // поиск по названию с удалением пробелов и регистров
                it.nameDrink.contains(query.trim(), ignoreCase = true) ||
                        it.idDrink.toString() == query.trim() // поиск по id
            }
            //сработает при первом запуске поиска
            if (isSearchStarting) {
                cacheCocktailList = cocktailList.value
                isSearchStarting = false
            }
            cocktailList.value = results
        }
    }


    fun loadCocktailPaginated() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getDrinkHome(PAGE_SIZE, 0 * PAGE_SIZE)
            when (result) {
                is Resource.Success -> {
                    val cocktailEntries: List<DrinkListEntry> = result.data!!.drinks!!.map {
                        DrinkListEntry(
                            nameDrink = it.strDrink,
                            imageUrl = it.strDrinkThumb,
                            idDrink = it.idDrink.toInt()
                        )
                    }

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

    fun calcDominateColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}