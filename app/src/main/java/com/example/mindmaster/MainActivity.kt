package com.example.mindmaster
import androidx.compose.runtime.toMutableStateList
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
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
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.runtime.mutableStateListOf

import androidx.compose.runtime.mutableStateListOf


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
fun PantallaDificultad(
    onAtrasClick: () -> Unit,
    onFacilClick: () -> Unit,
    onNormalClick: () -> Unit,
    onDificilClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "SELECCIONE LA DIFICULTAD", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onFacilClick) {
            Text(text = "FÁCIL (3x3)")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNormalClick) {
            Text(text = "NORMAL (4x4)")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onDificilClick) {
            Text(text = "DIFÍCIL (5x5)")
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
fun PantallaFacil(onVolverClick: () -> Unit) {
    JuegoMemoria(
        tamañoMatriz = 3,
        onVolverClick = onVolverClick,
        titulo = "Modo Fácil (3x3)"
    )
}

@Composable
fun PantallaNormal(onVolverClick: () -> Unit) {
    JuegoMemoria(
        tamañoMatriz = 4,
        onVolverClick = onVolverClick,
        titulo = "Modo Normal (4x4)"
    )
}

@Composable
fun PantallaDificil(onVolverClick: () -> Unit) {
    JuegoMemoria(
        tamañoMatriz = 5,
        onVolverClick = onVolverClick,
        titulo = "Modo Difícil (5x5)"
    )
}

@Composable
fun JuegoMemoria(
    tamañoMatriz: Int,
    onVolverClick: () -> Unit,
    titulo: String
) {
    val totalTarjetas = tamañoMatriz * tamañoMatriz
    val secuencia = remember { mutableStateListOf<Int>() }
    val secuenciaUsuario = remember { mutableStateListOf<Int>() }
    val mostrarResultado = remember { mutableStateOf<String?>(null) }
    val iluminando = remember { mutableStateOf(true) }
    val tarjetasIluminadas = remember { mutableStateListOf(*Array(totalTarjetas) { false }) }
    val tarjetasSeleccionadas = remember { mutableStateListOf(*Array(totalTarjetas) { false }) }
    val reiniciarJuego = remember { mutableStateOf(false) }

    LaunchedEffect(Unit, reiniciarJuego.value) {
        secuencia.clear()
        secuencia.addAll((0 until totalTarjetas).shuffled())
        iluminando.value = true
        tarjetasSeleccionadas.replaceAll { false }
        tarjetasIluminadas.replaceAll { false }

        for (i in secuencia.indices) {
            val index = secuencia[i]
            tarjetasIluminadas[index] = true
            delay(500)
            tarjetasIluminadas[index] = false
            delay(250)
        }

        iluminando.value = false
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(titulo, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // MATRIZ DINÁMICA
        Column {
            for (i in 0 until tamañoMatriz) {
                Row {
                    for (j in 0 until tamañoMatriz) {
                        val index = i * tamañoMatriz + j
                        val iluminado = tarjetasIluminadas[index]
                        val seleccionado = tarjetasSeleccionadas[index]

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (tamañoMatriz > 4) 60.dp else 80.dp) // Tamaño adaptable
                                .background(
                                    when {
                                        iluminado -> Color.Yellow
                                        seleccionado -> Color.DarkGray
                                        else -> Color.Gray
                                    }
                                )
                                .clickable(enabled = !iluminando.value && mostrarResultado.value == null) {
                                    tarjetasSeleccionadas[index] = true
                                    secuenciaUsuario.add(index)
                                    val indexActual = secuenciaUsuario.size - 1
                                    if (secuenciaUsuario[indexActual] != secuencia[indexActual]) {
                                        mostrarResultado.value = "Fallaste, intenta de nuevo."
                                    } else if (secuenciaUsuario.size == secuencia.size) {
                                        mostrarResultado.value = "¡Correcto!"
                                    }
                                }
                        )
                    }
                }
            }
        }

        mostrarResultado.value?.let { resultado ->
            Spacer(modifier = Modifier.height(24.dp))
            Text(resultado, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                mostrarResultado.value = null
                secuenciaUsuario.clear()
                reiniciarJuego.value = !reiniciarJuego.value
            }) {
                Text("Intentar de nuevo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onVolverClick) {
            Text("VOLVER")
        }
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
                onPuntuacionesClick = { navController.navigate(Pantallas.Puntuaciones.name) },
                onOpcionesClick = { navController.navigate(Pantallas.Opciones.name) },
                onCreditosClick = { navController.navigate(Pantallas.Creditos.name) }
            )
        }
        composable(Pantallas.Dificultad.name) {
            PantallaDificultad(
                onAtrasClick = { navController.popBackStack() },
                onFacilClick = { navController.navigate(Pantallas.Facil.name) },
                onNormalClick = { navController.navigate(Pantallas.Normal.name) },
                onDificilClick = { navController.navigate(Pantallas.Dificil.name) }
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
        composable(Pantallas.Facil.name) {
            PantallaFacil { navController.popBackStack() }
        }
        composable(Pantallas.Normal.name) {
            PantallaNormal { navController.popBackStack() }
        }
        composable(Pantallas.Dificil.name) {
            PantallaDificil { navController.popBackStack() }
        }
    }
}

enum class Pantallas {
    Inicio,
    Dificultad,
    Creditos,
    Opciones,
    Puntuaciones,
    Facil,
    Normal,
    Dificil
}
