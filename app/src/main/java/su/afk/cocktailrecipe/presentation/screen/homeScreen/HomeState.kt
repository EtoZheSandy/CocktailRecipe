package su.afk.cocktailrecipe.presentation.screen.homeScreen

import su.afk.cocktailrecipe.domain.model.DrinkListEntry

data class HomeState(
    val isLoading: Boolean = false,
//    val cocktailList: List<Drink>? = emptyList(),
    val cocktailList: List<DrinkListEntry> = listOf(),
    val loadErrorMessage: String = "",
    val isSearchStarting: Boolean = true,
    val searchText: String = ""
)