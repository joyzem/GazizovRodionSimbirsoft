package com.example.androidpractice.feature.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidpractice.core.designsystem.component.CenteredTopAppBar
import com.example.androidpractice.core.designsystem.theme.AppTheme
import com.example.androidpractice.core.designsystem.R as designR

@Composable
fun AuthScreen(
    email: String,
    onEmailInput: (String) -> Unit,
    password: String,
    onPasswordInput: (String) -> Unit,
    isPasswordHidden: Boolean,
    onChangePasswordVisibility: () -> Unit,
    isLoginButtonActive: Boolean,
    loading: Boolean,
    onLoginButtonClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = {
                    Text(stringResource(id = R.string.authorization))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(painterResource(id = designR.drawable.ic_arrow_back), null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = dimensionResource(id = designR.dimen.spacing_l))
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                stringResource(id = R.string.auth_with_social_networks_to_participate_in_events),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = designR.color.black_70),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewAuthScreen() {
    AppTheme {
        AuthScreen(
            email = "example@mail.ru",
            onEmailInput = {},
            password = "asdifj",
            onPasswordInput = {},
            isPasswordHidden = false,
            onChangePasswordVisibility = { /*TODO*/ },
            isLoginButtonActive = true,
            loading = false,
            onForgotPasswordClick = { /*TODO*/ },
            onLoginButtonClick = {},
            onSignUpClick = { /*TODO*/ }) {

        }
    }
}