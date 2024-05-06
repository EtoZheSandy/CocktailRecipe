package su.afk.cocktailrecipe.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import su.afk.cocktailrecipe.data.ConnectivityRepository
import su.afk.cocktailrecipe.data.DrinkRepository
import su.afk.cocktailrecipe.data.retrofit.ApiCocktail
import su.afk.cocktailrecipe.data.room.MainDB
import su.afk.cocktailrecipe.di.Constans.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
//    fun provideRepositoryDrink(api: ApiCocktail) = DrinkRepository(api)
    fun provideRepositoryDrink(api: ApiCocktail): DrinkRepository {
        return DrinkRepository(api)
    }

    @Singleton
    @Provides
    fun provideConnectivityRepository(@ApplicationContext app: Context): ConnectivityRepository {
        return ConnectivityRepository(context = app)
    }

    @Singleton
    @Provides
    fun provideApiCocktail(): ApiCocktail {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiCocktail::class.java)
    }


    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        context = app,
        MainDB::class.java,
        "MyDataBase.db"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideDao(db: MainDB) = db.daoCocktail() // The reason we can implement a Dao for the database
}