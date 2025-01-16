package com.skapps.fakestoreapp.domain.usecase.basket.getallproductsflow

import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBasketProductsFlowUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : GetAllBasketProductsFlowUseCase {
    override fun invoke(): Flow<List<BasketProductEntity>> {
        return repository.getAllProductsFlow()
    }
}