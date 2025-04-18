package com.example.mindmaster
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.MediaPlayer
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.ui.platform.LocalContext



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
            mediaPlayer = MediaPlayer.create(context, R.raw.musicamastermind)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }
    }
    DisposableEffect(Unit){
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

class PuntuacionesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "puntuaciones.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        // Crea la tabla "puntuaciones"
        db.execSQL(
            """
            CREATE TABLE puntuaciones (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                dificultad TEXT NOT NULL,
                puntaje INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Si se actualiza la versión de la base de datos, puedes manejar cambios aquí
        db.execSQL("DROP TABLE IF EXISTS puntuaciones")
        onCreate(db)
    }
}

fun insertPuntuacion(context: Context, dificultad: String, puntaje: Int) {
    val dbHelper = PuntuacionesDatabaseHelper(context)
    val db = dbHelper.writableDatabase

    val values = ContentValues().apply {
        put("dificultad", dificultad)
        put("puntaje", puntaje)
    }

    db.insert("puntuaciones", null, values)
    db.close()
}

fun getPuntuaciones(context: Context): List<Pair<String, Int>> {
    val dbHelper = PuntuacionesDatabaseHelper(context)
    val db = dbHelper.readableDatabase

    val cursor = db.rawQuery("SELECT dificultad, puntaje FROM puntuaciones", null)
    val puntuaciones = mutableListOf<Pair<String, Int>>()

    while (cursor.moveToNext()) {
        val dificultad = cursor.getString(cursor.getColumnIndexOrThrow("dificultad"))
        val puntaje = cursor.getInt(cursor.getColumnIndexOrThrow("puntaje"))
        puntuaciones.add(dificultad to puntaje)
    }

    cursor.close()
    db.close()
    return puntuaciones
}
@Composable
fun PantallaMejoresPuntuaciones(
    onBackClik: () -> Unit
) {
    val context = LocalContext.current

    // Iniciar música instrumental
    if (OpcionesUsuario.musicaActivada) {
        Musicamastermind(context )
    }

    // Lista reactiva para las puntuaciones
    val puntuaciones = remember { mutableStateListOf<Pair<String, Int>>() }

    // Inserta las puntuaciones iniciales y recupera datos
    LaunchedEffect(Unit) {
        // Recupera las puntuaciones
        val nuevaLista = getPuntuaciones(context)
        puntuaciones.clear() // Limpia y actualiza la lista reactiva
        puntuaciones.addAll(nuevaLista)
    }

    // Construimos la UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Historial de partidas",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Muestra solo las últimas 10 puntuaciones en orden inverso (más recientes primero)
        puntuaciones.takeLast(10).reversed().forEachIndexed { index, (dificultad, puntaje) ->
            Text(text = "N°:${index + 1}    Dificultad: $dificultad    Puntaje: $puntaje")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackClik) {
            reproducirSonido(context)
            Text(text = "ATRÁS")
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
fun PantallaDificultad(
    context: Context,
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
            reproducirSonido(context)
            Text(text = "ATRÁS")
        }
    }
}

object OpcionesUsuario {
    var musicaActivada by mutableStateOf(true)
    var sonidoActivado by mutableStateOf(true)
}

@Composable
fun PantallaCreditos(
    context: Context,
    onVolverClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "MindMaster",
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Desarrollado por:",
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Johan Felipe Ordoñez\nDiego Gomez\nValentina Sanchez",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nueva sección de descripción del juego
        Text(
            text = "Descripción del Juego:",
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "MindMaster es un desafiante juego de memoria donde debes recordar y repetir secuencias " +
                    "de patrones. El juego muestra una secuencia de colores que debes memorizar y luego " +
                    "repetir correctamente. A medida que avanzas, la dificultad aumenta con secuencias " +
                    "más largas y patrones más complejos.\n\n" +
                    "Características:\n" +
                    "• Tres niveles de dificultad\n" +
                    "• Sistema de puntuación progresivo\n" +
                    "• Registro de tus mejores puntajes\n" +
                    "• Diseño intuitivo y atractivo",
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onVolverClick,
            modifier = Modifier.size(width = 150.dp, height = 50.dp)
        ) {
            Text(text = "VOLVER", fontSize = 16.sp)
        }
    }
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
    val score = remember { mutableStateOf(0) }
    val juegoGanado = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val guardarPuntuacion = remember { mutableStateOf(false) }
    val dificultad = remember {
        mutableStateOf(
            when(tamañoMatriz) {
                3 -> "Fácil"
                4 -> "Normal"
                5 -> "Difícil"
                else -> "NO aplica"
            }
        )
    }

    // Efecto para guardar puntuación cuando cambia el estado
    LaunchedEffect(guardarPuntuacion.value) {
        if (guardarPuntuacion.value) {
            insertPuntuacion(context, dificultad.value, score.value)
            guardarPuntuacion.value = false
        }
    }

    // Efecto para inicializar el juego
    LaunchedEffect(Unit, reiniciarJuego.value) {
        secuencia.clear()
        secuencia.addAll((0 until totalTarjetas).shuffled())
        iluminando.value = true
        tarjetasSeleccionadas.replaceAll { false }
        tarjetasIluminadas.replaceAll { false }
        juegoGanado.value = false

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
        Text("Puntaje: ${score.value}", fontSize = 16.sp)
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
                                .size(if (tamañoMatriz > 4) 60.dp else 80.dp)
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
                                        mostrarResultado.value = "¡Fallaste! Puntaje final: ${score.value}"
                                        juegoGanado.value = false
                                        guardarPuntuacion.value = true
                                    } else if (secuenciaUsuario.size == secuencia.size) {
                                        when(tamañoMatriz) {
                                            3 -> score.value += 100
                                            4 -> score.value += 200
                                            5 -> score.value += 300
                                        }
                                        mostrarResultado.value = "¡Correcto! Puntaje: ${score.value}"
                                        juegoGanado.value = true
                                        guardarPuntuacion.value = true
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

            if (juegoGanado.value) {
                Button(onClick = {
                    mostrarResultado.value = null
                    secuenciaUsuario.clear()
                    reiniciarJuego.value = !reiniciarJuego.value
                }) {
                    Text("Siguiente")
                }
            } else {
                Button(onClick = {
                    mostrarResultado.value = null
                    secuenciaUsuario.clear()
                    reiniciarJuego.value = !reiniciarJuego.value
                    score.value = 0
                }) {
                    Text("Intentar de nuevo")
                }
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
                context = context,
                onAtrasClick = { navController.popBackStack() },
                onFacilClick = { navController.navigate(Pantallas.Facil.name) },
                onNormalClick = { navController.navigate(Pantallas.Normal.name) },
                onDificilClick = { navController.navigate(Pantallas.Dificil.name) }
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
                onBackClik = { navController.popBackStack() }
            )
        }

        composable(Pantallas.Facil.name) {
            PantallaFacil(
                context = context,
                onVolverClick = { navController.popBackStack() }
            )
        }

        composable(Pantallas.Normal.name) {
            PantallaNormal(
                context = context,
                onVolverClick = { navController.popBackStack() }
            )
        }

        composable(Pantallas.Dificil.name) {
            PantallaDificil(
                context = context,
                onVolverClick = { navController.popBackStack() }
            )
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
