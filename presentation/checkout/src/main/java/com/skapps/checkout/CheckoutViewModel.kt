package com.skapps.checkout

import androidx.lifecycle.viewModelScope
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.domain.onErrorWithMessage
import com.skapps.fakestoreapp.domain.onSuccess
import com.skapps.fakestoreapp.domain.usecase.basket.clear.ClearBasketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckoutViewModel @Inject constructor(
    loadingManager: GlobalLoadingManager,
    private val clearBasketUseCase: ClearBasketUseCase
) : BaseViewModel<CheckoutUiState, CheckoutUiAction, CheckoutSideEffect>(
    initialState = CheckoutUiState.EMPTY,
    globalLoadingManager = loadingManager
) {
    override fun onAction(uiAction: CheckoutUiAction) {
        when (uiAction) {
            is CheckoutUiAction.NameChanged -> {
                updateUiState { copy(name = uiAction.name, nameError = false) }
            }
            is CheckoutUiAction.EmailChanged -> {
                updateUiState { copy(email = uiAction.email, emailError = false) }
            }
            is CheckoutUiAction.PhoneChanged -> {
                updateUiState { copy(phone = uiAction.phone, phoneError = false) }
            }
            is CheckoutUiAction.Pay -> {
                validateAndPay()
            }
        }
    }

    private fun validateAndPay() {
        val currentState = uiState.value
        val isNameValid = currentState.name.isNotBlank()
        val isEmailValid = currentState.email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"))
        val isPhoneNumberValid = currentState.phone.matches(Regex("^\\d{10}$"))
        if (!isNameValid || !isEmailValid || !isPhoneNumberValid) {
            updateUiState {
                copy(
                    nameError = !isNameValid,
                    emailError = !isEmailValid,
                    phoneError = !isPhoneNumberValid
                )
            }
            return
        }

        viewModelScope.launch {
            doWithGlobalLoading (
                block = {
                    delay(1000)
                    clearBasket()
                }
            )
        }
    }

    private fun clearBasket() {
        viewModelScope.launch {
            clearBasketUseCase().onErrorWithMessage {
                emitSideEffect(CheckoutSideEffect.ShowError(it))
            }.onSuccess {
                emitSideEffect(CheckoutSideEffect.PaymentSuccess)
            }
        }
    }
}