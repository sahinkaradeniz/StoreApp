package com.skapps.fakestoreapp.domain.usecase.basket.quantitiyflow

import com.skapps.fakestoreapp.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalBasketBasketQuantityFlowUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : GetTotalBasketQuantityFlowUseCase {
    override fun invoke(): Flow<Int?> {
        return repository.getTotalQuantityFlow()
    }
}