package com.tesisforero.motosecure.ui

import com.tesisforero.motosecure.viewmodel.usuario.LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tesisforero.motosecure.R
import com.tesisforero.motosecure.ui.theme.emerald_dark


class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()  // Obtener el ViewModel
    private var dniUser: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                navigateToRegister = { navigateToRegister() },
                navigateToPasswordRecovery = { navigateToPasswordRecovery() },
                navigateToHome = { navigateToHome(dniUser) },
                viewModel = viewModel  // Pasa el ViewModel a la pantalla
            )
        }
    }

    private fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun navigateToPasswordRecovery() {
        startActivity(Intent(this, PasswordRecoveryActivity::class.java))
    }

    private fun navigateToHome(dniUser: String){
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("dniUser", dniUser)
        startActivity(intent)
    }
}

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToPasswordRecovery: () -> Unit,
    navigateToHome: (String) -> Unit,
    viewModel: LoginViewModel
) {
    var dni by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) } // Estado de carga
    var isUserInfoFetched by remember { mutableStateOf(false) } // Indica si los datos de usuario se obtuvieron

    val loginResult by viewModel.loginResult.collectAsState()


    // Navega a la pantalla de inicio
    LaunchedEffect(loginResult, isUserInfoFetched) {
        if (loginResult?.success == true) {
            navigateToHome(dni)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Mitad Superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(emerald_dark),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logot),
                contentDescription = "Logo",
                modifier = Modifier.size(180.dp)
            )
        }

        // Mitad Inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // DNI
                OutlinedTextField(
                    value = dni,
                    onValueChange = { dni = it },
                    label = { Text("DNI") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Olvidé mi contraseña
                TextButton(onClick = navigateToPasswordRecovery) {
                    Text(text = "Olvidé mi contraseña", fontSize = 14.sp, color = emerald_dark)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Iniciar Sesión
                Button(
                    onClick = {
                        isLoading = true
                        viewModel.login(dni, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = emerald_dark)
                ) {
                    if (isLoading) {
                        // Muestra el indicador de carga mientras está en proceso
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 3.dp
                        )
                    } else {
                        // Texto del botón cuando no está cargando
                        Text(text = "Iniciar sesión", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Spacer(modifier = Modifier.height(16.dp))

                // Registrarse
                TextButton(onClick = navigateToRegister) {
                    Text(text = "¿No tienes cuenta? Regístrate", fontSize = 14.sp, color = emerald_dark)
                }

                // Mostrar el resultado del login, si está disponible
                loginResult?.let {
                    if (it.success) {
                        isLoading = false // Detener la animación cuando el login sea exitoso
                        // No es necesario navegar nuevamente aquí porque lo hacemos dentro de la lógica de LaunchedEffect
                    } else {
                        isLoading = false // Detener la animación en caso de error
                        Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navigateToRegister = {}, navigateToPasswordRecovery = {}, navigateToHome = {} ,viewModel = LoginViewModel())
}