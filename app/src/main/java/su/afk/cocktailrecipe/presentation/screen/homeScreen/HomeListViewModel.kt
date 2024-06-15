package su.afk.cocktailrecipe.presentation.screen.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.afk.cocktailrecipe.domain.repository.NetworkDrink
import su.afk.cocktailrecipe.util.Resource
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val repository: NetworkDrink,
) : ViewModel() {

    var homeState by mutableStateOf(HomeState())
        private set

    init {
        loadCocktailPaginated()
    }

    fun loadCocktailPaginated() {
        homeState = homeState.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getDrinkHome()
            when (result) {
                is Resource.Success -> {
                    homeState = homeState.copy(
                        isLoading = false,
                        loadErrorMessage = "",
                        cocktailList = result.data!!.shuffled()
                    )
                }
                is Resource.Error -> {
                    homeState =
                        homeState.copy(isLoading = false, loadErrorMessage = result.message!!)
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
        viewModelScope.launch {
            searchOnline(query)
        }
    }

    private suspend fun searchOnline(query: String) {
        if (query.isEmpty()) { loadCocktailPaginated() }

        when (val result = repository.getDrinkName(query)) {
            is Resource.Success -> {
                val cocktailEntries = result.data ?: listOf()
                homeState = homeState.copy(
                    isLoading = false,
                    loadErrorMessage = "",
                    cocktailList = cocktailEntries
                )
            }
            is Resource.Error -> {
                homeState = homeState.copy(
                    isLoading = false,
                    loadErrorMessage = result.message ?: "Неизвестная ошибка"
                )
            }
            is Resource.Loading -> {
                homeState = homeState.copy(isLoading = true)
            }
        }
    }


}