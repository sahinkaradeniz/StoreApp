package com.skapps.fakestoreapp.data.datasource.local.favorites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductToFavorites(model: FavoritesDbModel)

    @Query("DELETE FROM favorites_table WHERE id = :id")
    suspend fun deleteProductToFavorites(id:String)


    @Query("select * from favorites_table")
    fun getAllProducts(): Flow<List<FavoritesDbModel>>


    @Query("select * from favorites_table where :id")
    suspend fun getFavoriteProductWithId(id:Int):FavoritesDbModel

    @Query("SELECT EXISTS(SELECT 1 FROM favorites_table WHERE id = :id)")
    suspend fun isProductFavorite(id: Int): Boolean
}