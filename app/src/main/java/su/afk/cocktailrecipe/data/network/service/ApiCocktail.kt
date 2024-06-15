package su.afk.cocktailrecipe.data.network.service

import retrofit2.http.GET
import retrofit2.http.Query
import su.afk.cocktailrecipe.data.network.dto.ListDrinkResponseDto

interface ApiCocktail {


    @GET("api/json/v1/1/random.php")
    suspend fun getRandomDrink(): ListDrinkResponseDto

    @GET("api/json/v1/1/search.php")
    suspend fun getNameDrinkDetail(
        @Query("s") name: String
    ): ListDrinkResponseDto

    @GET("api/json/v1/1/lookup.php")
    suspend fun getIdDrinkDetail(
        @Query("i") name: Int
    ): ListDrinkResponseDto

    @GET("api/json/v1/1/lookup.php")
    suspend fun getIdIngredientDrink(
        @Query("iid") name: Int
    ): ListDrinkResponseDto

    @GET("api/json/v1/1/filter.php")
    suspend fun getIngredientDrink(
    @Query("i") name: String
    ): ListDrinkResponseDto

    @GET("api/json/v1/1/filter.php")
    suspend fun getMainDrink(
        @Query("a") name: String
    ): ListDrinkResponseDto
}