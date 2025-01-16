package com.skapps.checkout

data class CheckoutUiState(
    val name: String = "",
    val nameError: Boolean = false,
    val email: String = "",
    val emailError: Boolean = false,
    val phone: String = "",
    val phoneError: Boolean = false,
) {
    companion object {
        val EMPTY = CheckoutUiState()
    }
}

sealed class CheckoutUiAction {
    data class NameChanged(val name: String) : CheckoutUiAction()
    data class EmailChanged(val email: String) : CheckoutUiAction()
    data class PhoneChanged(val phone: String) : CheckoutUiAction()
    data object Pay : CheckoutUiAction()
}

sealed class CheckoutSideEffect {
    data object PaymentSuccess : CheckoutSideEffect()
    data class ShowError(val message:String): CheckoutSideEffect()
}