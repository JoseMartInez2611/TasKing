package com.example.tasking.models.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.tasking.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasking.models.components.CustomButton
import com.example.tasking.models.components.Input
import com.example.tasking.models.components.showToast
import com.example.tasking.utils.SessionManager


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()

    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passwordFocusRequester = remember { FocusRequester() }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.tasking_logo),
            contentDescription = null,
            modifier = modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )

        Input(
            label = stringResource(R.string.LabelUserName),
            value = userName,
            onValueChange = { userName = it },
            imeAction = ImeAction.Next,
            onNext = { passwordFocusRequester.requestFocus() }
        )

        Input(
            label = stringResource(R.string.LabelPassword),
            value = password,
            onValueChange = { password = it },
            focusRequester = passwordFocusRequester
        )

        CustomButton(
            text = stringResource(R.string.LoginButton),
            onClick = {
                if (userName.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.login(userName, password)
                } else {
                    showToast(context, context.getString(R.string.Error_1))
                }
            }
        )

        when (val state=loginState) {
            is LoginState.Success -> {
                showToast(context, "Login exitoso")
                SessionManager.saveToken(context, state.token)
            }
            is LoginState.Error -> {
                showToast(context, (loginState as LoginState.Error).message)
            }
            LoginState.Loading -> {
                Text("Iniciando sesión...")
            }
            LoginState.Idle -> { /* No hacer nada */ }
        }
    }
}
