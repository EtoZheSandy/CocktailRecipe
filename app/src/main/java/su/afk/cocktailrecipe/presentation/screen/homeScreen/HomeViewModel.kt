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
class HomeViewModel @Inject constructor(
    private val repository: NetworkDrink,
) : ViewModel() {

    var homeState by mutableStateOf(HomeScreenState())
        private set

    init {
        loadCocktailPaginated()
    }

    private fun loadCocktailPaginated() {
        homeState = homeState.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getDrinkHome()) {
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

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.Search -> {
                homeState = homeState.copy(searchInput = event.query)
                searchDrinkName(event.query)
            }

            is HomeScreenEvent.Retry -> loadCocktailPaginated()
        }
    }

    private fun searchDrinkName(query: String) {
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
                    loadErrorMessage = result.message ?: ERROR_CODE_101.toString()
                )
            }
            is Resource.Loading -> {
                homeState = homeState.copy(isLoading = true)
            }
        }
    }

    companion object {
        const val ERROR_CODE_101 = 101
        const val ERROR_CODE_102 = 102
    }
}