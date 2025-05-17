package com.zhichengzhang.artistsearchandroid.ui.screens

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhichengzhang.artistsearchandroid.R
import com.zhichengzhang.artistsearchandroid.ui.components.loading.MiniLoadingCircle
import com.zhichengzhang.artistsearchandroid.viewmodel.screens.LoginViewModel

fun isEmailValid(email: String): Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean{
    return !password.isBlank()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSuccessLogin: () -> Unit,
    onRegisterLogin: () -> Unit,
    navController: NavController,
    loginViewModel: LoginViewModel = LoginViewModel()
){
    val loginError by loginViewModel.loginError.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    val isAuthorized by loginViewModel.isAuthorized.collectAsState()
    val password by loginViewModel.password.collectAsState()
    val email by loginViewModel.email.collectAsState()
    val isNight = isSystemInDarkTheme()

    val topAppBarColor = if (!isNight) colorResource(R.color.top_bar_day) else colorResource(R.color.top_bar_night)
    val backgroundColor = if (!isNight) colorResource(R.color.background_day) else colorResource(R.color.background_night)
    val titleColor = if (!isNight) colorResource(R.color.text_day) else colorResource(R.color.text_night)
    val iconTint = if (!isNight) colorResource(R.color.icon_day) else colorResource(R.color.icon_night)
    val buttonBackgroundColor = if (!isNight) colorResource(R.color.button_background_day) else colorResource(R.color.button_background_night)
    val buttonTextColor = if (!isNight) colorResource(R.color.button_text_day) else colorResource(R.color.button_text_night)
    val errorColor = colorResource(R.color.error_color)
    val broderColor = if(!isNight) colorResource(R.color.button_background_day) else colorResource(R.color.button_background_night)
    val textColor = if(!isNight) colorResource(R.color.text_day) else colorResource(R.color.text_night)
    val hyperlinkColor = broderColor

    val backIcon = painterResource(R.drawable.back_icon)

    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val hasEmailBeenFocused = remember { mutableStateOf(false) }
    val hasPasswordBeenFocused = remember { mutableStateOf(false) }

    LaunchedEffect(isAuthorized) {
        if (isAuthorized) {
            navController.previousBackStackEntry?.savedStateHandle?.set("login_success", true)
            navController.popBackStack()
        }
    }

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Login", color = titleColor, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = backIcon,
                            contentDescription = "Back",
                            tint = iconTint
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topAppBarColor
                )
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        loginViewModel.onEmailChanged(it)
                        if (hasEmailBeenFocused.value) {
                            emailError.value = it.isBlank() || !isEmailValid(it)
                        }
                    },
                    label = { Text("Email address")},
                    isError = hasEmailBeenFocused.value && emailError.value,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = broderColor,
                        errorBorderColor = errorColor,
                        focusedLabelColor = broderColor,
                        errorLabelColor = errorColor,
                        cursorColor = broderColor
                    ),
                    textStyle = TextStyle(color = textColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                hasEmailBeenFocused.value = true
                            } else if (hasEmailBeenFocused.value) {
                                emailError.value = email.isBlank() || !isEmailValid(email)
                            }
                        }
                )

                if (hasEmailBeenFocused.value && emailError.value) {
                    Text(
                        if (email.isBlank()) "Email cannot be empty" else "Invalid email format",
                        color = errorColor,
                        modifier = Modifier.padding(5.dp)
                    )
                }


                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        loginViewModel.onPasswordChanged(it)
                        if (hasPasswordBeenFocused.value) {
                            passwordError.value = it.isBlank()
                        }
                    },
                    label = { Text("Password") },
                    isError = (hasPasswordBeenFocused.value && passwordError.value),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = broderColor,
                        errorBorderColor = errorColor,
                        focusedLabelColor = broderColor,
                        errorLabelColor = errorColor,
                        cursorColor = broderColor
                    ),
                    textStyle = TextStyle(color = textColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            if (it.isFocused) {
                                hasPasswordBeenFocused.value = true
                            } else if (hasPasswordBeenFocused.value) {
                                passwordError.value = password.isBlank()
                            }
                        }
                )

                if (hasPasswordBeenFocused.value && passwordError.value) {
                    Text("Password cannot be empty", color = errorColor, modifier = Modifier.padding(5.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        hasEmailBeenFocused.value = true
                        hasPasswordBeenFocused.value = true

                        emailError.value = email.isBlank() || !isEmailValid(email)
                        passwordError.value = password.isBlank()

                        if (!emailError.value && !passwordError.value) {
                            loginViewModel.login()
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonBackgroundColor,
                        contentColor = buttonTextColor
                    )
                ) {
                    if (isLoading) {
                        MiniLoadingCircle()
                    } else {
                        Text("Login")
                    }
                }


                if (loginError && !emailError.value && !passwordError.value) {
                    Text("Username or password is incorrect", color = errorColor, modifier = Modifier.padding(10.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Don't have an account yet?", color=textColor)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Register",
                        color = hyperlinkColor,
                        modifier = Modifier.clickable { onRegisterLogin() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen(){
}