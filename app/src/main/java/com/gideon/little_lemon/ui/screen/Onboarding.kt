package com.gideon.little_lemon.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gideon.little_lemon.Home
import com.gideon.little_lemon.R
import com.gideon.little_lemon.UserViewModel
import com.gideon.little_lemon.ui.karlaFamily
import com.gideon.little_lemon.ui.theme.brandGreen
import com.gideon.little_lemon.ui.theme.brandYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(
    navController: NavController,
    userViewModel: UserViewModel
) {
    // State (hoisted-friendly: you can pass these down if you prefer)
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }


    val surfacePadding = 16.dp

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_logo),
                    contentDescription = "Little Lemon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 24.dp, start = 64.dp, end = 64.dp)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(brandGreen)
                ) {


                    Text(
                        "Let's get to know you",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = Color.White,
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        },
        bottomBar = {
            OnboardingBottomBar(userViewModel, firstName, lastName, email, navController)
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = surfacePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))

            // Section title
            Text(
                "Personal information",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontFamily = karlaFamily
            )

            Spacer(Modifier.height(16.dp))

            LabeledTextField(
                label = "First name",
                value = firstName,
                onValueChange = { firstName = it },
                imeAction = ImeAction.Next
            )

            Spacer(Modifier.height(12.dp))

            LabeledTextField(
                label = "Last name",
                value = lastName,
                onValueChange = { lastName = it },
                imeAction = ImeAction.Next
            )

            Spacer(Modifier.height(12.dp))

            LabeledTextField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            )
        }
    }
}

@Composable
private fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = karlaFamily
        )
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(onDone = { /* hide keyboard if needed */ }),
            colors = OutlinedTextFieldDefaults.colors(
                // keep it looking enabled; optional tuning:
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}


@Composable
private fun OnboardingBottomBar(
    userViewModel: UserViewModel,
    firstName: String,
    lastName: String,
    email: String,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                userViewModel.registerUser(firstName, lastName, email)
                navController.navigate(Home.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = brandYellow,
                contentColor = Color.Black
            ),
            enabled = firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()
        ) {
            Text("Register", fontWeight = FontWeight.Bold, fontFamily = karlaFamily)
        }
    }
}