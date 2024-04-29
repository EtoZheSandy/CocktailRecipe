package su.afk.cocktailrecipe.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import su.afk.cocktailrecipe.data.models.MainCoctails
import su.afk.cocktailrecipe.data.models.ThecocktaildbModels

interface ApiCocktail {

    //    Найдите случайный коктейль
    //    www.thecocktaildb.com/api/json/v1/1/random.php
    @GET("api/json/v1/1/random.php")
    suspend fun getRandom(): ThecocktaildbModels

    //  Поиск коктейля по имени
    //    www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita
    @GET("api/json/v1/1/search.php")
    suspend fun getNameDrink(
        @Query("s") name: String
    ): ThecocktaildbModels

    //  Поиск картинке по имени игридиента
    //   https://www.thecocktaildb.com/images/ingredients/vodka.png
    @GET("images/ingredients/{name}")
    suspend fun gatImgIngridient(
        @Path("name") name: String
    )

    //  Поиск картинке по имени игридиента
    //   https://www.thecocktaildb.com/images/ingredients/vodka.png
    @GET("images/ingredients/{name}-medium")
    suspend fun gatImgIngridientMedium(
        @Path("name") name: String
    )

//    Перечислить все коктейли по первой букве
//    www.thecocktaildb.com/api/json/v1/1/search.php?f=a
//
//    Поиск ингредиента по названию
//    www.thecocktaildb.com/api/json/v1/1/search.php?i=vodka
//
//    Поиск полной информации о коктейле по идентификатору
//    www.thecocktaildb.com/api/json/v1/1/lookup.php?i=11007
    @GET("api/json/v1/1/lookup.php")
    suspend fun getIdDrink(
        @Query("i") name: Int
    ): ThecocktaildbModels

//    Поиск ингредиента по идентификатору
//    www.thecocktaildb.com/api/json/v1/1/lookup.php?iid=552
    @GET("api/json/v1/1/lookup.php")
    suspend fun getIngridientIntsDrink(
        @Query("iid") name: Int
    ): ThecocktaildbModels


//    Поиск по ингредиенту
//    www.thecocktaildb.com/api/json/v1/1/filter.php?i=Gin
//    www.thecocktaildb.com/api/json/v1/1/filter.php?i=Vodka
    @GET("api/json/v1/1/filter.php")
    suspend fun getIngridientDrink(
    @Query("i") name: String
    ): ThecocktaildbModels


    @GET("api/json/v1/1/filter.php")
    suspend fun getMainDrink(
        @Query("a") name: String = "Alcoholic"
    ): MainCoctails

//    Фильтр по алкоголю
//    www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic
//    www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic

//    Фильтр по категории
//    www.thecocktaildb.com/api/json/v1/1/filter.php?c=Ordinary_Drink
//    www.thecocktaildb.com/api/json/v1/1/filter.php?c=Cocktail


}