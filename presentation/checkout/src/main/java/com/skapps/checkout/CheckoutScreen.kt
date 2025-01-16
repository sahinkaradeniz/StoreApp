package com.skapps.checkout

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skapps.fakestoreapp.coreui.components.AppTopBar
import com.skapps.fakestoreapp.coreui.theme.CollectSideEffect
import kotlinx.coroutines.launch

@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel = hiltViewModel(),
    onBack: () -> Unit,
    finish: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    CollectSideEffect(
        sideEffect = viewModel.sideEffect,
    ) { sideEffect ->
        when (sideEffect) {
            CheckoutSideEffect.PaymentSuccess -> {
                finish()
            }

            is CheckoutSideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTopBar("CHECKOUT", onBack, true)
        Spacer(modifier = Modifier.height(20.dp))

        CheckoutTextField(
            value = uiState.name,
            onValueChange = { viewModel.onAction(CheckoutUiAction.NameChanged(it)) },
            label = "NAME",
            isError = uiState.nameError
        )

        CheckoutTextField(
            value = uiState.email,
            onValueChange = { viewModel.onAction(CheckoutUiAction.EmailChanged(it)) },
            label = "EMAIL",
            isError = uiState.emailError,
            keyboardType = KeyboardType.Email
        )

        CheckoutTextField(
            value = uiState.phone,
            onValueChange = { viewModel.onAction(CheckoutUiAction.PhoneChanged(it)) },
            label = "PHONE",
            isError = uiState.phoneError,
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { coroutineScope.launch { viewModel.onAction(CheckoutUiAction.Pay) } },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (uiState.nameError || uiState.emailError || uiState.phoneError) Color.Gray else MaterialTheme.colorScheme.primary
            ),
            enabled = !uiState.nameError && !uiState.emailError && !uiState.phoneError,
        ) {
            Text(text = "PAY", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun CheckoutTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = if (isError) Color.Red else Color.Gray) },
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) Color.Red else MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = if (isError) Color.Red else Color.Gray
        )
    )
    if (isError) {
        Text(text = "$label is required", color = Color.Red, fontSize = 12.sp)
    }
}