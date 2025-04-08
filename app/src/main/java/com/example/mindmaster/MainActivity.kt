package com.example.mindmaster

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mindmaster.ui.theme.MindMasterTheme


import androidx.compose.runtime.toMutableStateList
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

fun reproducirSonido(context: Context) {
    val mediaPlayer = MediaPlayer.create(context, R.raw.pup)
    mediaPlayer.start()
    mediaPlayer.setOnCompletionListener { it.release() }
}
@Composable
fun Musicamastermind(context: Context) {
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    // Solo reproducir si la música está activada
    LaunchedEffect(OpcionesUsuario.musicaActivada) {
        mediaPlayer?.release()
        mediaPlayer = null

        if (OpcionesUsuario.musicaActivada) {
            mediaPlayer = MediaPlayer.create(context, R.raw.musicmastermind)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}
@Composable
fun SonidoDeFondo(context: Context) {
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    // Solo reproducir si el sonido está activado
    LaunchedEffect(OpcionesUsuario.sonidoActivado) {
        mediaPlayer?.release()
        mediaPlayer = null

        if (OpcionesUsuario.sonidoActivado) {
            mediaPlayer = MediaPlayer.create(context, R.raw.sonidodefondo)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}
@Composable
fun PantallaInicio(
    context: Context, // Se necesita para acceder a los recursos
    onJugarClick: () -> Unit,
    onPuntuacionesClick: () -> Unit,
    onOpcionesClick: () -> Unit,
    onCreditosClick: () -> Unit,
) {
    // Iniciar música instrumental
    if (OpcionesUsuario.musicaActivada) {
        Musicamastermind(context )
    }
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
        Button(onClick = {
            reproducirSonido(context)
            onJugarClick()
        }) {
            Text(text = "JUGAR", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            reproducirSonido(context)
            onPuntuacionesClick()
        }) {
            Text(text = "PUNTUACIONES", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            reproducirSonido(context)
            onOpcionesClick()
        }) {
            Text(text = "OPCIONES", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            reproducirSonido(context)
            onCreditosClick()
        }) {
            Text(text = "CRÉDITOS", fontSize = 18.sp)
        }
    }
}

@Composable
fun PantallaDificultad (navController: NavController, context: Context, onAtrasClick: () -> Unit,
                        onFacilClick: () -> Unit,
                        onNormalClick: () -> Unit,
                        onDificilClick: ()->Unit) {
    if (OpcionesUsuario.musicaActivada) {
        Musicamastermind(context )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "SELECCIONE LA DIFICULTAD", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate(Pantallas.Facil.name) }) {
            reproducirSonido(context)
            Text(text = "FÁCIL 3*3")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate(Pantallas.Normal.name) }) {
            reproducirSonido(context)
            Text(text = "NORMAL 4*4")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate(Pantallas.Dificil.name) }) {
            reproducirSonido(context)
            Text(text = "DIFÍCIL 5*5")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onAtrasClick) {
            reproducirSonido(context)
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
fun PantallaFacil(context: Context, onVolverClick: () -> Unit) {
    SonidoDeFondo(context) // Iniciar música de fondo
    JuegoMemoria(
        context = context,
        tamañoMatriz = 3,
        onVolverClick = onVolverClick,
        titulo = "Modo Fácil (3x3)"
    )
}
@Composable
fun PantallaNormal(context: Context, onVolverClick: () -> Unit) {
    SonidoDeFondo(context)
    JuegoMemoria(
        context = context,
        tamañoMatriz = 4,
        onVolverClick = onVolverClick,
        titulo = "Modo Normal (4x4)"
    )
}
@Composable
fun PantallaDificil(context: Context, onVolverClick: () -> Unit) {
    SonidoDeFondo(context)
    JuegoMemoria(
        context = context,
        tamañoMatriz = 5,
        onVolverClick = onVolverClick,
        titulo = "Modo Difícil (5x5)"
        )
}

@Composable
fun JuegoMemoria(
    context: Context,
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
fun PantallaCreditos(context: Context,onVolverClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "MindMaster", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Desarrollado por:\nJohan Felipe Ordoñez \nDiego Gomez\nValentina Sanchez", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onVolverClick) {
            reproducirSonido(context)
            Text(text = "VOLVER")
        }
    }
}
object OpcionesUsuario {
    var musicaActivada by mutableStateOf(true)
    var sonidoActivado by mutableStateOf(true)
}
@Composable
fun PantallaOpciones(context: Context, onInicioClick: () -> Unit) {
    if (OpcionesUsuario.musicaActivada) {
        Musicamastermind(context )
    }
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
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Switch de música
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = "Música")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = OpcionesUsuario.musicaActivada,
                onCheckedChange = { OpcionesUsuario.musicaActivada = it }
            )
        }

        // Switch de sonido
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Sonido")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = OpcionesUsuario.sonidoActivado,
                onCheckedChange = { OpcionesUsuario.sonidoActivado = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onInicioClick) {
            reproducirSonido(context)

            Text(text = "ATRÁS")
        }
    }
}


@Composable
fun PantallaMejoresPuntuaciones(context: Context,
    onBackClik: ()-> Unit
) {
    // Iniciar música instrumental
    if (OpcionesUsuario.musicaActivada) {
        Musicamastermind(context )
    }
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
            reproducirSonido(context)
            Text(text = "ATRÁS")
        }
    }
}

@Composable
fun Navegacion() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Pantallas.Inicio.name) {
        composable(Pantallas.Inicio.name) {
            PantallaInicio(
                context = context,
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
                onDificilClick = { navController.navigate(Pantallas.Dificil.name) },
                navController = navController,
                context = context // Pasar el contexto aquí
            )
        }
        composable(Pantallas.Creditos.name) {
            PantallaCreditos(
                context = context,
                onVolverClick = { navController.popBackStack() }
            )
        }
        composable(Pantallas.Opciones.name) {
            PantallaOpciones(
                context = context,
                onInicioClick = { navController.popBackStack() }
            )
        }
        composable(Pantallas.Puntuaciones.name) {
            PantallaMejoresPuntuaciones(
                context = context,
                onBackClik = { navController.popBackStack() }
            )
        }
        composable(Pantallas.Puntuaciones.name) {
            PantallaMejoresPuntuaciones(
                context = context,
                onBackClik = { navController.popBackStack() }
            )
        }
        composable(Pantallas.Facil.name) {
            PantallaFacil(context = context, onVolverClick = { navController.popBackStack() })
        }
        composable(Pantallas.Normal.name) {
            PantallaNormal(context = context, onVolverClick = { navController.popBackStack() })
        }
        composable(Pantallas.Dificil.name) {
            PantallaDificil(context = context, onVolverClick = { navController.popBackStack() })
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