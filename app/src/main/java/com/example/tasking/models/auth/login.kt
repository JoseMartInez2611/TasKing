package com.example.tasking.models.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.example.tasking.models.components.CustomButton
import com.example.tasking.models.components.Input
import com.example.tasking.models.components.showToast
import com.example.tasking.utils.SessionManager


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()

    var loading by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passwordFocusRequester = remember { FocusRequester() }


    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            val token = (loginState as LoginState.Success).token
            SessionManager.saveToken(context, token)
            loading = false
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        } else if (loginState is LoginState.Error) {
            loading = false
            Toast.makeText(context, "Error de login", Toast.LENGTH_SHORT).show()
        }
    }
    if (loading) {
        LoadingScreen()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.tasking_logo),
                contentDescription = null,
                modifier = modifier
                    .size(400.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                focusRequester = passwordFocusRequester,
                isPassword = true
            )

            CustomButton(
                text = stringResource(R.string.LoginButton),
                onClick = {
                    if (userName.isNotEmpty() && password.isNotEmpty()) {
                        loading=true
                        viewModel.login(userName, password)
                    } else {
                        showToast(context, context.getString(R.string.Error_1))
                    }
                }
            )

        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}