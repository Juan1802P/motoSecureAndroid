package com.tesisforero.motosecure.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tesisforero.motosecure.R
import com.tesisforero.motosecure.ui.theme.emerald_dark

class WelcomeActivity: ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Welcome()
        }
    }
}

@Composable
fun Welcome() {
    
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(emerald_dark),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logot),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 50.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "¡Bienvenido!",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(90.dp))
            Button(
                onClick = {
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = emerald_dark
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(250.dp)
                    .height(50.dp)
            ) {
               Text(
                   text = "Empecemos",
                   fontSize = 30.sp,
                   fontWeight = FontWeight.Bold
               )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    Welcome()
}

