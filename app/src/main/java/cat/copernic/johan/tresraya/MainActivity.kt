package cat.copernic.johan.tresraya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import java.util.*
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {

    lateinit var textoGame : TextView
    lateinit var botones : Array<Int>
    // -1 maquina ---- 1 jugador
    var tablero = arrayOf(
        0, 0, 0,
        0, 0, 0,
        0, 0, 0
    )

    // 1 finalizado // 0 en curso
    var estadoPartida = 0

    var totalFichas = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ********inicializar variables
        // pasar a no visible el campo de texto
        textoGame =  findViewById(R.id.gameText)
        textoGame.visibility = View.INVISIBLE
        // iiciar el array de los botones
        botones = arrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2,
            R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8
        )

    }

    fun ponerFicha(boton : View){
        if (estadoPartida == 0){
            val posBoton : Int = botones.asList().indexOf(boton.id)

            if (tablero[posBoton] == 0){
                tablero[posBoton] = 1
                totalFichas ++
                boton.setBackgroundResource((R.drawable.cross))
            }
            comprobarGanador(posBoton)
            if (estadoPartida != 1){
                //Thread.sleep(1000)
                ponerFichaMaquina(boton)
            }

        }
    }

    fun ponerFichaMaquina(botonPulsado : View){
        if (estadoPartida == 0 && totalFichas < 9){
            // -------------- jugada logica
//            val actuJugada : Int = botones.asList().indexOf(botonPulsado.id)
////            val proxJugada : Int = comprobarJugadaMaquina(botonPulsado)
//            var proxBoton : View = findViewById<Button>(comprobarJugadaMaquina(botonPulsado))
//            val posProxBoton : Int = botones.asList().indexOf(proxBoton.id)
//
//            tablero[posProxBoton] = -1
//            totalFichas ++
//            if (totalFichas == 9){
//                estadoPartida = 1
//            }
//            proxBoton.setBackgroundResource((R.drawable.cercle))
//            Thread.sleep(2L)
//            comprobarGanador(posProxBoton)

            // ----------------jugada random
            var actuJugada : Int = (0..8).random()
            var salida : Boolean = false

            while (!salida){
                if (tablero[actuJugada] == 0){
                    var proxBoton : View = findViewById<Button>( botones[actuJugada] )
                    proxBoton.setBackgroundResource(R.drawable.cercle)
                    tablero[actuJugada] = -1
                    totalFichas ++
                    salida = true
                } else {
                    actuJugada = (0..8).random()
                }
            }
        }
    }

    fun comprobarJugadaMaquina(botonPulsado: View) : Int {
        // comprobar la siguiente jugada de la maquina
        var proxJugada : Int = 3


        return proxJugada
    }

    fun comprobarGanador(posJugada : Int){
        // comprobar si hay ganador
        if (totalFichas == 9){
            estadoPartida = 1
            textoGame.setText(R.string.tie)
            textoGame.visibility = View.VISIBLE
        }
    }


}