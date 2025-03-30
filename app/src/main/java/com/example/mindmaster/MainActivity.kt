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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navegacion()
                    }
                }
            }
        }
    }

@Composable
fun PantallaOpciones(
    onBackClick:()-> Unit
) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Título
            Text(
                text = "Opciones",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Opciones de Música y Sonido
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Música", fontSize = 18.sp)
                    var musicaActivada = true
                    Switch(
                        checked = musicaActivada,
                        onCheckedChange = { musicaActivada = it }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Sonido", fontSize = 18.sp)
                    var sonidoActivado = true
                    Switch(
                        checked = sonidoActivado,
                        onCheckedChange = { sonidoActivado = it }
                    )
                }
            }

            // Botón de Atrás
            Button(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Atrás")
            }
        }
    }

@Composable
fun PantallaInicio(
    onInicioClick: ()-> Unit,
    onOptionsClick: ()-> Unit
) {
    // Pantalla principal: Icono en el centro y 3 botones en la parte inferior
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Espacio superior
        Spacer(modifier = Modifier.height(16.dp))
        // Icono (recuerda tener "IconoMindMaster" en res/drawable)
        Image(
            painter = painterResource(id = R.drawable.iconomindmaster),
            contentDescription = "Icono MindMaster",
            modifier = Modifier.size(150.dp)
        )
        // Botones inferiores
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón "JUGAR": al hacer click, navega a la pantalla de selección de dificultad
            Button(
                onClick = onInicioClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "JUGAR", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            // Botón "OPCIONES" (acción pendiente)
            Button(
                onClick = onOptionsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "OPCIONES", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            // Botón "CRÉDITOS" (acción pendiente)
            Button(
                onClick = { /* Agrega la acción para Créditos */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "CRÉDITOS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun PantallaDificultad(
    onClickVolver: () -> Unit
) {
    // Pantalla de selección de dificultad: título y botones para cada nivel
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "SELECCIONE LA DIFICULTAD",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para "FÁCIL"
        DifficultyButton(
            text = "FÁCIL",
            onClick = { /* Acciones para la dificultad fácil */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para "NORMAL"
        DifficultyButton(
            text = "NORMAL",
            onClick = { /* Acciones para la dificultad normal */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para "DIFÍCIL"
        DifficultyButton(
            text = "DIFÍCIL",
            onClick = { /* Acciones para la dificultad difícil */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClickVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DifficultyButton(text: String, onClick: () -> Unit) {
    // Botón estilizado para seleccionar la dificultad
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(50.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun Navegacion(){
    val navHostController = rememberNavController()
    NavHost(navController = navHostController,
        startDestination = Pantallas.Inicio.name) {
        composable(route = Pantallas.Inicio.name){
            PantallaInicio (
                onInicioClick = {navHostController.navigate(Pantallas.dificultad.name)},
                onOptionsClick = {navHostController.navigate(Pantallas.Opciones.name)}
            )
        }
        composable(route = Pantallas.dificultad.name){
            PantallaDificultad(
                onClickVolver = {navHostController.navigate(Pantallas.Inicio.name)}
            )
        }
        composable(route = Pantallas.Inicio.name){
            PantallaOpciones (
                onBackClick = {navHostController.navigate(Pantallas.Inicio.name)}
            )
        }

    }
}

enum class Pantallas(){
    Inicio,
    Opciones,
    dificultad
}