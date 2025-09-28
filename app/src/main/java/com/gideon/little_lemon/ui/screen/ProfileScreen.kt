package com.gideon.little_lemon.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gideon.little_lemon.R
import com.gideon.little_lemon.UserViewModel
import com.gideon.little_lemon.ui.karlaFamily
import com.gideon.little_lemon.ui.theme.brandYellow

@Composable
fun ProfileScreen(
    userViewModel: UserViewModel
) {
    val surfacePadding = 16.dp

    Scaffold(
        topBar = {
            ProfileTopBar()
        },
        bottomBar = {
            ProfileBottomBar(userViewModel)
        },
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
                value = userViewModel.user.value.firstName,
            )

            Spacer(Modifier.height(12.dp))

            LabeledTextField(
                label = "Last name",
                value = userViewModel.user.value.lastName,
            )

            Spacer(Modifier.height(12.dp))

            LabeledTextField(
                label = "Email",
                value = userViewModel.user.value.email,
            )

            Spacer(Modifier.height(28.dp))
        }
    }
}

@Composable
private fun LabeledTextField(
    label: String,
    value: String,
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
            onValueChange = {},          // no-op
            readOnly = true,             // <-- key part
            enabled = true,              // keep normal look
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                // keep it looking enabled; optional tuning:
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}

@Composable
private fun ProfileTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = "Little Lemon",
            contentScale = ContentScale.Inside,
            modifier = Modifier.fillMaxWidth()
        )

        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Little Lemon",
            contentScale = ContentScale.Inside,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun ProfileBottomBar(userViewModel: UserViewModel) {
    // Stays fixed at the bottom
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = { userViewModel.logout() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = brandYellow,
                contentColor = Color.Black
            )
        ) {
            Text("Log out", fontWeight = FontWeight.Bold, fontFamily = karlaFamily)
        }
    }
}

@Preview
@Composable
private fun ProfileTopBarPreview() {
    ProfileTopBar()
}