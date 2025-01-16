package com.skapps.fakestoreapp.domain.entitiy

data class GetPagedProductsParams (
    val sortType: SortType = SortType.NONE,
    val pageSize: Int = 20
)