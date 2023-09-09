package com.example.androidpractice.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidpractice.core.designsystem.component.AppButton
import com.example.androidpractice.core.designsystem.component.AppTextField
import com.example.androidpractice.core.designsystem.component.CenteredTopAppBar
import com.example.androidpractice.core.designsystem.theme.AppTheme
import com.example.androidpractice.core.designsystem.R as designR

@Composable
internal fun AuthScreen(
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
                    IconButton(onClick = onNavigateUp, modifier = Modifier.size(24.dp)) {
                        Icon(painterResource(id = designR.drawable.ic_arrow_back), null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(horizontal = dimensionResource(id = designR.dimen.spacing_l)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                stringResource(id = R.string.auth_with_social_networks_to_participate_in_events),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = designR.color.black_70)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = designR.dimen.spacing_l)))
            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                SocialNetworkButton(
                    socialNetworkImageId = designR.drawable.ic_vk,
                    contentDescriptionId = null,
                    onClick = {}
                )
                SocialNetworkButton(
                    socialNetworkImageId = designR.drawable.ic_facebook,
                    contentDescriptionId = null,
                    onClick = {}
                )
                SocialNetworkButton(
                    socialNetworkImageId = designR.drawable.ic_ok,
                    contentDescriptionId = null,
                    onClick = {}
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                stringResource(id = R.string.or_auth_within_app),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = designR.color.black_70)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = designR.dimen.spacing_l)))
            AppTextField(
                value = email,
                onValueChange = onEmailInput,
                label = stringResource(id = R.string.e_mail),
                hint = stringResource(id = R.string.input_e_mail)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = designR.dimen.spacing_l)))
            AppTextField(
                value = password,
                onValueChange = onPasswordInput,
                label = stringResource(id = R.string.password),
                hint = stringResource(id = R.string.input_password),
                visualTransformation = if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    if (isPasswordHidden) {
                        IconButton(onClick = onChangePasswordVisibility) {
                            Icon(painterResource(id = designR.drawable.ic_eye_open), null)
                        }
                    } else {
                        IconButton(onClick = onChangePasswordVisibility) {
                            Icon(painterResource(id = designR.drawable.ic_eye_close), null)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = designR.dimen.spacing_l)))
            AppButton(
                onClick = onLoginButtonClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = isLoginButtonActive && !loading
            ) {
                Text(stringResource(id = R.string.login).uppercase())
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = designR.dimen.spacing_l)))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    color = MaterialTheme.colors.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        onForgotPasswordClick()
                    }
                )
                Text(
                    text = stringResource(id = R.string.signing_up),
                    color = MaterialTheme.colors.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        onSignUpClick()
                    }
                )
            }
        }
    }
}

@Composable
private fun SocialNetworkButton(
    socialNetworkImageId: Int,
    contentDescriptionId: Int?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(socialNetworkImageId),
        contentDescription = contentDescriptionId?.let { stringResource(id = it) },
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() }
    )
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
            isPasswordHidden = true,
            onChangePasswordVisibility = { },
            isLoginButtonActive = false,
            loading = false,
            onForgotPasswordClick = { },
            onLoginButtonClick = { },
            onSignUpClick = { }
        ) {
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSocialNetworkIcon() {
    AppTheme {
        SocialNetworkButton(
            socialNetworkImageId = designR.drawable.ic_vk,
            contentDescriptionId = null,
            modifier = Modifier.padding(16.dp)
        ) {
        }
    }
}
