package com.skapps.fakestoreapp.domain.entitiy

data class SearchPagedProductParams(
    val query: String,
    val pageSize: Int = 20
)