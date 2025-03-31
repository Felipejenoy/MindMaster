package com.example.mindmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mindmaster.ui.theme.MindMasterTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
fun PantallaInicio(
    onJugarClick: () -> Unit,
    onPuntuacionesClick: () -> Unit,
    onOpcionesClick: () -> Unit,
    onCreditosClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.iconomindmaster),
            contentDescription = "Icono MindMaster",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onJugarClick) {
            Text(text = "JUGAR", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onPuntuacionesClick) {
            Text(text = "PUNTUACIONES", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onOpcionesClick) {
            Text(text = "OPCIONES", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onCreditosClick) {
            Text(text = "CRÉDITOS", fontSize = 18.sp)
        }
    }
}

@Composable
fun PantallaDificultad(onAtrasClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "SELECCIONE LA DIFICULTAD", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Acción fácil */ }) {
            Text(text = "FÁCIL")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Acción normal */ }) {
            Text(text = "NORMAL")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Acción difícil */ }) {
            Text(text = "DIFÍCIL")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAtrasClick) {
            Text(text = "ATRÁS")
        }
    }
}

@Composable
fun PantallaCreditos(onVolverClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "MindMaster", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Desarrollado por:\nJohan Felipe Ordoñez \nDiego Gomez", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onVolverClick) {
            Text(text = "VOLVER")
        }
    }
}

@Composable
fun PantallaOpciones (onInicioClick: ()-> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Opciones",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        var musicaActivada by remember { mutableStateOf(false) }
        var sonidoActivado by remember { mutableStateOf(false) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = "Música")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = musicaActivada,
                onCheckedChange = { musicaActivada = it }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Sonido")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = sonidoActivado,
                onCheckedChange = { sonidoActivado = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onInicioClick) {
            Text(text = "ATRÁS")
        }
    }
}

@Composable
fun PantallaMejoresPuntuaciones(
    onBackClik: ()-> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Mejores puntuaciones",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Tabla de puntuaciones
        val puntuaciones = listOf(
            Triple(1, 2500, "02:35"),
            Triple(2, 2400, "03:10"),
            Triple(3, 2300, "01:45"),
            Triple(4, 2200, "04:20"),
            Triple(5, 2100, "02:50"),
            Triple(6, 2000, "01:30"),
            Triple(7, 1900, "03:40"),
            Triple(8, 1800, "02:10"),
            Triple(9, 1700, "01:50"),
            Triple(10, 1600, "04:00")
        )

        TableHeader()

        puntuaciones.forEach { (posicion, puntuacion, tiempo) ->
            TableRow(posicion, puntuacion, tiempo)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClik) {
            Text(text = "ATRÁS")
        }
    }
}

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Posición", style = MaterialTheme.typography.titleSmall)
        Text(text = "Puntuación", style = MaterialTheme.typography.titleSmall)
        Text(text = "Tiempo", style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
fun TableRow(posicion: Int, puntuacion: Int, tiempo: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = posicion.toString())
        Text(text = puntuacion.toString())
        Text(text = tiempo)
    }
}

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Pantallas.Inicio.name) {
        composable(Pantallas.Inicio.name) {
            PantallaInicio(
                onJugarClick = { navController.navigate(Pantallas.Dificultad.name) },
                onPuntuacionesClick = {navController.navigate(Pantallas.Puntuaciones.name)},
                onOpcionesClick = {navController.navigate(Pantallas.Opciones.name)},
                onCreditosClick = { navController.navigate(Pantallas.Creditos.name) }
            )
        }
        composable(Pantallas.Dificultad.name) {
            PantallaDificultad(
                onAtrasClick = { navController.popBackStack() }
            )
        }
        composable(Pantallas.Creditos.name) {
            PantallaCreditos(
                onVolverClick = { navController.popBackStack() }
            )
        }
        composable(Pantallas.Opciones.name) {
            PantallaOpciones (
                onInicioClick = { navController.popBackStack() }
            )
        }
        composable(Pantallas.Puntuaciones.name) {
            PantallaMejoresPuntuaciones(
                onBackClik = { navController.popBackStack() }
            )
        }
    }
}

enum class Pantallas {
    Inicio,
    Dificultad,
    Creditos,
    Opciones,
    Puntuaciones
}