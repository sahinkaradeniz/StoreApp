package com.skapps.fakestoreapp.domain

data class ApiErrorModel(
    override val message: String,
    override val code: Int
): IApiErrorModel


interface IApiErrorModel {
    val message: String
    val code: Int
}