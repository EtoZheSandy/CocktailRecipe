package su.afk.cocktailrecipe.presentation.screen.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.afk.cocktailrecipe.data.ConnectivityRepository
import su.afk.cocktailrecipe.domain.model.DrinkListEntry
import su.afk.cocktailrecipe.domain.repository.NetworkDrink
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val repository: NetworkDrink,
    private val connectivityRepository: ConnectivityRepository,
) : ViewModel() {

    var homeState by mutableStateOf(HomeState())
        private set

    private val isOnline = connectivityRepository.isConnected // проверка на подключение к интернету

    init {
        loadCocktailPaginated()
    }

    fun loadCocktailPaginated() {
        homeState = homeState.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getDrinkHome()
            when (result) {
                is Resource.Success -> {
                    val cocktailEntries: List<DrinkListEntry> = result.data!!.drinks!!.map {
                        DrinkListEntry(
                            nameDrink = it.strDrink,
                            imageUrl = it.strDrinkThumb,
                            idDrink = it.idDrink.toInt()
                        )
                    }
                    homeState = homeState.copy(isLoading = false, loadErrorMessage = "",
                        cocktailList = cocktailEntries.shuffled())
                }
                is Resource.Error -> {
                    homeState = homeState.copy(isLoading = false, loadErrorMessage = result.message!!)
                }
                is Resource.Loading -> {
                }
            }
        }
    }

    fun setSearchText(newText: String) {
        homeState = homeState.copy(searchText = newText)
    }

    fun searchDrinkName(query: String) {
        viewModelScope.launch(Dispatchers.IO) {

            isOnline.collect { isConnected ->
                if (isConnected) {
                    searchOnline(query)
                } else {
                    searchOffline(query)
                }
            }
        }
    }

    private suspend fun searchOnline(query: String) {
        val result = repository.getDrinkName(query)
        when (result) {
            is Resource.Success -> {
                val cocktailEntries: List<DrinkListEntry> = result.data?.drinks?.map {
                    DrinkListEntry(
                        nameDrink = it.strDrink,
                        imageUrl = it.strDrinkThumb,
                        idDrink = it.idDrink.toInt()
                    )
                } ?: emptyList()

                homeState = homeState.copy(isLoading = false, loadErrorMessage = "", cocktailList = cocktailEntries)
            }
            is Resource.Error -> {
                homeState = homeState.copy(isLoading = false, loadErrorMessage = result.message!!)
            }
            is Resource.Loading -> {
                homeState = homeState.copy(isLoading = true)
            }
        }
    }

    private var cacheCocktailList = listOf<DrinkListEntry>()

    private fun searchOffline(query: String) {
        val listToSearch = if (homeState.isSearchStarting) {
            homeState.cocktailList
        } else {
            cacheCocktailList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) { //если перестали искать и очиситили поле ввода
                homeState = homeState.copy(cocktailList = cacheCocktailList, isSearchStarting = true) // если строка поиска пуста или удалена мы возвращаем ее из кэша
                return@launch
            }

            val results = listToSearch?.filter {
                // поиск по названию с удалением пробелов и регистров
                it.nameDrink.contains(query.trim(), ignoreCase = true) ||
                        it.idDrink.toString() == query.trim() // поиск по id
            }
            //сработает при первом запуске поиска
            if (homeState.isSearchStarting) {
                cacheCocktailList = homeState.cocktailList
                homeState = homeState.copy(isSearchStarting = false)
            }
            homeState = homeState.copy(cocktailList = results!!)
        }
    }
}