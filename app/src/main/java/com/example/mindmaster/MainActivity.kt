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
                // Creamos el NavController para gestionar la navegación
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomeScreen(navController) }
                        composable("difficulty") { DifficultySelectionScreen(navController) }
                        composable("credits") { CreditsScreen(navController) }

                    }
                }
            }
        }
    }

    @Composable
    fun HomeScreen(navController: NavController) {
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
                    onClick = { navController.navigate("difficulty") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "JUGAR", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                // Botón "OPCIONES" (acción pendiente)
                Button(
                    onClick = { /* Agrega la acción para Opciones */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "OPCIONES", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                // Botón "CRÉDITOS" (acción pendiente)
                Button(
                    onClick = { navController.navigate("credits") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "CRÉDITOS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

            }
        }
    }

    @Composable
    fun DifficultySelectionScreen(navController: NavController) {
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

            Spacer(modifier = Modifier.height(32.dp))

            // Botón "Atrás"
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Atrás")
            }
        }
    }


    @Composable
    fun CreditsScreen(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MindMaster",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Desarrollado por:\nJohan Felipe Ordoñez \nDiego Gomez",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Volver")
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

    @Preview(showBackground = true)
    @Composable
    fun HomeScreenPreview() {
        MindMasterTheme {
            // Para el preview, se puede crear un NavController de ejemplo o bien omitir la navegación
            HomeScreen(navController = rememberNavController())
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DifficultySelectionScreenPreview() {
        MindMasterTheme {
            DifficultySelectionScreen(navController = rememberNavController())
        }
    }
}
