package com.example.mindmaster
import android.content.ContentValues
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

import androidx.compose.runtime.mutableStateListOf
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
    onBackClik: () -> Unit
) {
    val context = LocalContext.current

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
            Text(text = "ATRÁS")
        }
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
    val totalTarjetas = tamañoMatriz*tamañoMatriz
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
            delay(750)
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
        var score = 0
        var dificultad = "NO aplica"
        when(tamañoMatriz){
            3-> dificultad = "Facil"
            4-> dificultad = "normal"
            5-> dificultad = "dificil"
        }
        val context = LocalContext.current

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
                                        insertPuntuacion(context, dificultad, score)
                                    } else if (secuenciaUsuario.size == secuencia.size) {
                                        mostrarResultado.value = "¡Correcto!"
                                        when(tamañoMatriz){
                                            3->score+=100
                                            4->score+=200
                                            5->score+=300
                                        }
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