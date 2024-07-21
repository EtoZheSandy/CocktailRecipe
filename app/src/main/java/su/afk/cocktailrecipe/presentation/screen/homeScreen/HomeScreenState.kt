package su.afk.cocktailrecipe.presentation.screen.homeScreen

import su.afk.cocktailrecipe.domain.model.Drink

data class HomeScreenState(
    val isLoading: Boolean = false,
    val cocktailList: List<Drink> = listOf(),
    val loadErrorMessage: String = "",
    val isSearchStarting: Boolean = true,
    val searchInput: String = ""
)

sealed class HomeScreenEvent {
    data class Search(val query: String): HomeScreenEvent()
    object Retry: HomeScreenEvent()
}