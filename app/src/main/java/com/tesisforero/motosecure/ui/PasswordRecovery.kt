package com.tesisforero.motosecure.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.tesisforero.motosecure.ui.theme.emerald_dark
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

class PasswordRecoveryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordRecoveryScreen()
        }
    }
}

@Composable
fun PasswordRecoveryScreen() {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Botón de regreso
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish() // Cierra PasswordRecoveryActivity
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = emerald_dark)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text("Recuperar contraseña", color = emerald_dark, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 Campo de correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico", color = emerald_dark) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = emerald_dark,
                unfocusedBorderColor = emerald_dark,
                cursorColor = emerald_dark,
                focusedLabelColor = emerald_dark
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 📌 Botón "Enviar"
        Button(
            onClick = { showConfirmation = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = emerald_dark),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Enviar", color = Color.White)
        }

        if (showConfirmation) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Correo enviado. Revisa tu bandeja.", color = emerald_dark)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPasswordRecoveryScreen() {
    PasswordRecoveryScreen()
}
