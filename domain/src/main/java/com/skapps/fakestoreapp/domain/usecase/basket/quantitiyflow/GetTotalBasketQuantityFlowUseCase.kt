package com.skapps.fakestoreapp.domain.usecase.basket.quantitiyflow

import kotlinx.coroutines.flow.Flow


interface GetTotalBasketQuantityFlowUseCase {
    operator fun invoke(): Flow<Int?>
}
