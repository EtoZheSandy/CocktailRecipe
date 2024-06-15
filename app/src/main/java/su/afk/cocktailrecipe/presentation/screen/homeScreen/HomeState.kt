package su.afk.cocktailrecipe.presentation.screen.homeScreen

import su.afk.cocktailrecipe.domain.model.Drink

data class HomeState(
    val isLoading: Boolean = false,
    val cocktailList: List<Drink> = listOf(),
    val loadErrorMessage: String = "",
    val isSearchStarting: Boolean = true,
    val searchText: String = ""
)