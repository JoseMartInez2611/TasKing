package com.example.tasking.models.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasking.R
import androidx.lifecycle.viewmodel.compose.viewModel
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
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
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


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun CustomButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .width(220.dp)
            .height(80.dp)
            .padding(10.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Normal,
            fontSize = (20.sp),
        )
    }
}

@Composable
fun Input(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Done,
    onNext: (() -> Unit)? = null,
    focusRequester: FocusRequester? = null
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = label,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            modifier = modifier
                .align(Alignment.Start)
                .padding(start = 42.dp, bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onNext?.invoke()
                }
            ),
            placeholder = {
                Text(
                    text = label,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 25.dp)
                .padding(bottom = 25.dp)
                .then(
                    if (focusRequester != null) {
                        modifier.focusRequester(focusRequester)
                    } else {
                        modifier
                    }
                )
        )
    }
}
