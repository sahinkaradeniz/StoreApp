package com.skapps.fakestoreapp.data.datasource.local.basket


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.skapps.fakestoreapp.data.models.cart.BasketProductsDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProduct(product: BasketProductsDbModel)

    @Query("DELETE FROM basket_table WHERE id = :id")
    suspend fun deleteProductById(id: Int)

    @Query("DELETE FROM basket_table")
    suspend fun clearBasket()

    @Query("SELECT * FROM basket_table")
    fun getAllProductsFlow(): Flow<List<BasketProductsDbModel>>

    @Query("SELECT * FROM basket_table")
    suspend fun getAllProductsOnce(): List<BasketProductsDbModel>


    @Query("SELECT * FROM basket_table WHERE id = :id LIMIT 1")
    suspend fun getProductById(id: Int): BasketProductsDbModel?


    @Query("UPDATE basket_table SET quantity = quantity + 1 WHERE id = :id")
    suspend fun incrementQuantity(id: Int)


    @Query("""
        UPDATE basket_table 
        SET quantity = CASE WHEN quantity > 0 THEN quantity - 1 ELSE 0 END 
        WHERE id = :id
    """)
    suspend fun decrementQuantityInternal(id: Int)

    @Query("""
        DELETE FROM basket_table 
        WHERE id = :id AND quantity = 0
    """)
    suspend fun removeIfQuantityZero(id: Int)

    @Transaction
    suspend fun decrementQuantityOrRemove(id: Int) {
        decrementQuantityInternal(id)
        removeIfQuantityZero(id)
    }

    @Query("SELECT SUM(quantity) FROM basket_table")
    fun getTotalQuantityFlow(): Flow<Int?>



    @Transaction
    suspend fun addOrIncrementProduct(product: BasketProductsDbModel) {
        val existingProduct = getProductById(product.id)
        if (existingProduct != null) {
            incrementQuantity(product.id)
        } else {
            upsertProduct(product.copy(quantity = 1))
        }
    }
}