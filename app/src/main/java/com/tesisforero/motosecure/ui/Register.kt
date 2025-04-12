package com.tesisforero.motosecure.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.tesisforero.motosecure.ui.theme.emerald_dark
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tesisforero.motosecure.viewmodel.usuario.RegisterViewModel
import com.tesisforero.motosecure.viewmodel.usuario.ReniecViewModel

class RegisterActivity : ComponentActivity() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen(
                viewModel = viewModel,
                navigateToLogin = {navigateToLogin()}
            )
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

}


@Composable
fun RegisterScreen(
    navigateToLogin: () -> Unit,
    viewModel: RegisterViewModel,
    reniecViewModel: ReniecViewModel = viewModel()
) {
    val context = LocalContext.current
    var dni by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf(false) }


    val registerResult by viewModel.registerResult.collectAsState()

    val reniecResponse = reniecViewModel.userInfo.collectAsState()
    val isLoading = reniecViewModel.isLoading.collectAsState()


    LaunchedEffect(reniecResponse.value) {
        reniecResponse.value?.let {
            Log.d("ReniecResponse", "name: ${it.data.name}, surname: ${it.data.surname}")
            userName = it.data.name
            lastname = it.data.surname
        }
    }

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
                (context as? ComponentActivity)?.finish()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = emerald_dark)
            }
        }

        Text("Registro", color = emerald_dark, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 DNI con botón "Buscar"
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = dni,
                onValueChange = { dni = it },
                label = { Text("DNI", color = emerald_dark) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = emerald_dark,
                    unfocusedBorderColor = emerald_dark,
                    cursorColor = emerald_dark,
                    focusedLabelColor = emerald_dark
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    reniecViewModel.fetchUserInfo(dni)
                },
                colors = ButtonDefaults.buttonColors(containerColor = emerald_dark),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Buscar", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 📌 Animación Circular (cargando) mientras buscamos
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .padding(16.dp),
                color = emerald_dark
            )
        } else {
            // 📌 Campos de texto para nombres, apellidos, etc.
            listOf(
                "Nombres" to userName,
                "Apellidos" to lastname,
                "Número" to phone,
                "Correo" to email
            ).forEach { (label, value) ->
                OutlinedTextField(
                    value = value,
                    onValueChange = { newValue ->
                        when (label) {
                            "Nombres" -> userName = newValue
                            "Apellidos" -> lastname = newValue
                            "Número" -> phone = newValue
                            "Correo" -> email = newValue
                        }
                    },
                    label = { Text(label, color = emerald_dark) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = emerald_dark,
                        unfocusedBorderColor = emerald_dark,
                        cursorColor = emerald_dark,
                        focusedLabelColor = emerald_dark
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // 📌 Campo Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                confirmPasswordError = confirmPassword.isNotEmpty() && confirmPassword != it
            },
            label = { Text("Contraseña", color = emerald_dark) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = emerald_dark,
                unfocusedBorderColor = emerald_dark,
                cursorColor = emerald_dark,
                focusedLabelColor = emerald_dark
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        // 📌 Campo Confirmar Contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = it != password
            },
            label = { Text("Confirmar Contraseña", color = emerald_dark) },
            isError = confirmPasswordError,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (confirmPasswordError) Color.Red else emerald_dark,
                unfocusedBorderColor = if (confirmPasswordError) Color.Red else emerald_dark,
                cursorColor = emerald_dark,
                focusedLabelColor = emerald_dark
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (confirmPasswordError) {
            Text(
                "Las contraseñas deben coincidir",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        // 📌 Botón "Registrarse"
        Button(
            onClick = {
                if (password == confirmPassword) {
                    viewModel.register(dni, password, userName, lastname, phone, email)
                } else {
                    confirmPasswordError = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = emerald_dark),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Registrarse", color = Color.White)
        }


        registerResult?.let {
            if (it.success) {
                navigateToLogin()
            } else {
                Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(navigateToLogin ={},viewModel = RegisterViewModel())
}
