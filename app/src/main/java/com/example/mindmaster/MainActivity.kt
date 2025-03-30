package com.example.mindmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mindmaster.ui.theme.MindMasterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMasterTheme {
                val controladorNavegacion = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(controladorNavegacion, startDestination = "inicio") {
                        composable("inicio") { PantallaInicio(controladorNavegacion) }
                        composable("dificultad") { PantallaSeleccionDificultad(controladorNavegacion) }
                        composable("creditos") { PantallaCreditos(controladorNavegacion) }
                    }
                }
            }
        }
    }

    @Composable
    fun PantallaInicio(controladorNavegacion: NavController) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.iconomindmaster),
                contentDescription = "Icono MindMaster",
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { controladorNavegacion.navigate("dificultad") }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "JUGAR", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Button(onClick = { /* Acción para Opciones */ }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "OPCIONES", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Button(onClick = { controladorNavegacion.navigate("creditos") }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "CRÉDITOS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    @Composable
    fun PantallaSeleccionDificultad(controladorNavegacion: NavController) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SELECCIONE LA DIFICULTAD",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            BotonDificultad("FÁCIL") { /* Acción fácil */ }
            Spacer(modifier = Modifier.height(16.dp))
            BotonDificultad("NORMAL") { /* Acción normal */ }
            Spacer(modifier = Modifier.height(16.dp))
            BotonDificultad("DIFÍCIL") { /* Acción difícil */ }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { controladorNavegacion.popBackStack() }) {
                Text(text = "Atrás")
            }
        }
    }

    @Composable
    fun PantallaCreditos(controladorNavegacion: NavController) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MindMaster",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Desarrollado por:\nJohan Felipe Ordoñez \nDiego Gomez",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { controladorNavegacion.popBackStack() }) {
                Text(text = "Volver")
            }
        }
    }

    @Composable
    fun BotonDificultad(texto: String, onClick: () -> Unit) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
        ) {
            Text(
                text = texto,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun VistaPreviaPantallaInicio() {
        MindMasterTheme {
            PantallaInicio(rememberNavController())
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun VistaPreviaPantallaSeleccionDificultad() {
        MindMasterTheme {
            PantallaSeleccionDificultad(rememberNavController())
        }
    }
}
