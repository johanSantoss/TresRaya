package cat.copernic.johan.tresraya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var textoGame: TextView
    lateinit var botones: Array<Int>

    // -1 maquina ---- 1 jugador
    var tablero = arrayOf(
            0, 0, 0,
            0, 0, 0,
            0, 0, 0
    )

    // 1 finalizado // 0 en curso
    var estadoPartida = 0

    var totalFichas = 0

    lateinit var restart: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // ********inicializar variables
        // pasar a no visible el campo de texto
        textoGame = findViewById(R.id.gameText)
        //textoGame.visibility = View.INVISIBLE
        // iiciar el array de los botones
        botones = arrayOf(
                R.id.btn0, R.id.btn1, R.id.btn2,
                R.id.btn3, R.id.btn4, R.id.btn5,
                R.id.btn6, R.id.btn7, R.id.btn8
        )
        restart = findViewById(R.id.btnRestart)
        restart.setOnClickListener {
            restartApp()
        }

    }

    // se coge el button pulsado y si no se había usado y se setea este, además, se llama a usar jugada máquina.
    fun ponerFicha(boton: View) {
        if (estadoPartida == 0) {
            val posBoton: Int = botones.asList().indexOf(boton.id)
            if (tablero[posBoton] == 0) {
                tablero[posBoton] = 1
                totalFichas++
//                boton.isEnabled = false
                boton.setBackgroundResource((R.drawable.cross))
                comprobarGanador(posBoton)
                if (estadoPartida != 1) {
                    ponerFichaMaquina()
                }
                // sirve para hacer el refresh de screen antes de terminar la funtion
//                textoGame.postDelayed(
//                        Runnable {
//                            if (estadoPartida != 1) {
//                                ponerFichaMaquina()
//                            }
//                        }, 1000 )
            }
        }
    }

    // se llama al método que proporciona la posición de la siguiente jugada de la máquina, con está se setea al buton.
    fun ponerFichaMaquina() {
        if (totalFichas == 0) {
            var botonIa: View = findViewById<Button>(botones.get(4))
            val posBotonIa: Int = botones.asList().indexOf(botonIa.id)
            tablero[posBotonIa] = -1
            totalFichas++
            botonIa.setBackgroundResource((R.drawable.cercle))
        } else {
            if (estadoPartida == 0 && totalFichas < 9) {
                // -------------- jugada logica
                val posBotonIa: Int = comprobarJugadaMaquina2()
                val botonIa: View = findViewById<Button>(botones.get(posBotonIa))
                tablero[posBotonIa] = -1
                //botonIa.isEnabled = false
                totalFichas++
                botonIa.setBackgroundResource((R.drawable.cercle))
                comprobarGanador(posBotonIa)
            }
        }
    }

    // Se comprueba la jugada lógica hasta que haya 5 fichas, desde la 6ta ficha se juega de manera aleatoria.
    fun comprobarJugadaMaquina2(): Int {
        // comprobar la siguiente jugada de la maquina
        var proxJugada: Int = 0

        if (totalFichas < 2 && tablero[4] == 0) {
            proxJugada = 4
        } else if (totalFichas < 2 && tablero[4] == 1) {
            proxJugada = listOf(0, 2, 6, 8).random()
        } else if (totalFichas == 3) {
            // ---------------------------------------------------------------------exterior-esquina
            // esquina sup izq--done
            if (tablero[4] == -1 && tablero[0] == 1) {
                if (tablero[1] == 1) {
                    proxJugada = 2
                } else if (tablero[2] == 1) {
                    proxJugada = 1
                } else if (tablero[3] == 1) {
                    proxJugada = 6
                } else if (tablero[5] == 1) {
                    proxJugada = 2
                } else if (tablero[6] == 1) {
                    proxJugada = 3
                } else if (tablero[7] == 1) {
                    proxJugada = 3
                } else if (tablero[8] == 1) {
                    proxJugada = 3
                }

                // esquina sup der -------Done
            } else if (tablero[4] == -1 && tablero[2] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 1
                } else if (tablero[1] == 1) {
                    proxJugada = 0
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 1, 7).random()
                } else if (tablero[5] == 1) {
                    proxJugada = 8
                } else if (tablero[6] == 1) {
                    proxJugada = listOf(1, 3, 5, 7).random()
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(0, 3, 5).random()
                } else if (tablero[8] == 1) {
                    proxJugada = 5
                }
                // esquina inf der
            } else if (tablero[4] == -1 && tablero[8] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = listOf(1, 3, 5, 7).random()
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(2, 3, 5).random()
                } else if (tablero[2] == 1) {
                    proxJugada = 5
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(1, 2, 7).random()
                } else if (tablero[5] == 1) {
                    proxJugada = 2
                } else if (tablero[6] == 1) {
                    proxJugada = 7
                } else if (tablero[7] == 1) {
                    proxJugada = 6
                }
                // esquina inf izq
            } else if (tablero[4] == -1 && tablero[6] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 3
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(3, 5, 8).random()
                } else if (tablero[2] == 1) {
                    proxJugada = listOf(1, 3, 5, 7).random()
                } else if (tablero[3] == 1) {
                    proxJugada = 0
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(1, 7, 8).random()
                } else if (tablero[7] == 1) {
                    proxJugada = 8
                } else if (tablero[8] == 1) {
                    proxJugada = 7
                }
                // -------------------------------------------------------------------exterior-medio
                // medio--top
            } else if (tablero[4] == -1 && tablero[1] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 2
                } else if (tablero[2] == 1) {
                    proxJugada = 0
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 6).random()
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(2, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = listOf(0, 3, 5).random()
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[8] == 1) {
                    proxJugada = listOf(2, 3, 5).random()
                }
                // medio--left
            } else if (tablero[4] == -1 && tablero[3] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 6
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(0, 2).random()
                } else if (tablero[2] == 1) {
                    proxJugada = listOf(0, 1, 7).random()
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = 0
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(6, 8).random()
                } else if (tablero[8] == 1) {
                    proxJugada = listOf(1, 6, 7).random()
                }
                // medio--right
            } else if (tablero[4] == -1 && tablero[5] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = listOf(1, 2, 7).random()
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(0, 2).random()
                } else if (tablero[2] == 1) {
                    proxJugada = 8
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = listOf(1, 7, 8).random()
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(6, 8).random()
                } else if (tablero[8] == 1) {
                    proxJugada = 2
                }
                // medio--but
            } else if (tablero[4] == -1 && tablero[7] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = listOf(3, 5, 6).random()
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[2] == 1) {
                    proxJugada = listOf(3, 5, 8).random()
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 6).random()
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(2, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = 8
                } else if (tablero[8] == 1) {
                    proxJugada = 6
                }
                // ----------------------------------------------------------------------------medio
                // left - top
            } else if (tablero[4] == 1 && tablero[0] == -1) {
                if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[2] == 1) {
                    proxJugada = 6
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[6] == 1) {
                    proxJugada = 2
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                } else if (tablero[8] == 1) {
                    proxJugada = 2
                }
                // right - top
            } else if (tablero[4] == 1 && tablero[2] == -1) {
                if (tablero[0] == 1) {
                    proxJugada = 8
                } else if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[6] == 1) {
                    proxJugada = 8
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                } else if (tablero[8] == 1) {
                    proxJugada = 0
                }
                // left - but
            } else if (tablero[4] == 1 && tablero[6] == -1) {
                if (tablero[0] == 1) {
                    proxJugada = 8
                } else if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[2] == 1) {
                    proxJugada = 0
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                } else if (tablero[8] == 1) {
                    proxJugada = 0
                }
                //right - but
            } else if (tablero[4] == 1 && tablero[8] == -1) {
                if (tablero[0] == 1) {
                    proxJugada = 6
                } else if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[2] == 1) {
                    proxJugada = 6
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[6] == 1) {
                    proxJugada = 2
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                }
            }
        } else if (totalFichas >= 5) {
//             ----------------jugada random
            val jugadasDisponibles: MutableList<Int> = ArrayList()

            for (i in 0..8) {
                if (tablero[i] == 0) {
                    jugadasDisponibles.add(i)
                }
            }
            proxJugada = jugadasDisponibles.random()
        }
        return proxJugada
    }

    //--------------------------- se deja para el final, jugada perfecta!
    fun comprobarJugadaMaquina(): Int {
        // comprobar la siguiente jugada de la maquina
        var proxJugada: Int = 0

        if (totalFichas < 2 && tablero[4] == 0) {
            proxJugada = 4
        } else if (totalFichas < 2 && tablero[4] == 1) {
            proxJugada = listOf(0, 2, 6, 8).random()
        } else if (totalFichas == 3) {
            // ---------------------------------------------------------------------exterior-esquina
            // esquina sup izq--done
            if (tablero[4] == -1 && tablero[0] == 1) {
                if (tablero[1] == 1) {
                    proxJugada = 2
                } else if (tablero[2] == 1) {
                    proxJugada = 1
                } else if (tablero[3] == 1) {
                    proxJugada = 6
                } else if (tablero[5] == 1) {
                    proxJugada = 2
                } else if (tablero[6] == 1) {
                    proxJugada = 3
                } else if (tablero[7] == 1) {
                    proxJugada = 3
                } else if (tablero[8] == 1) {
                    proxJugada = 3
                }

                // esquina sup der -------Done
            } else if (tablero[4] == -1 && tablero[2] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 1
                } else if (tablero[1] == 1) {
                    proxJugada = 0
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 1, 7).random()
                } else if (tablero[5] == 1) {
                    proxJugada = 8
                } else if (tablero[6] == 1) {
                    proxJugada = listOf(1, 3, 5, 7).random()
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(0, 3, 5).random()
                } else if (tablero[8] == 1) {
                    proxJugada = 5
                }
                // esquina inf der
            } else if (tablero[4] == -1 && tablero[8] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = listOf(1, 3, 5, 7).random()
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(2, 3, 5).random()
                } else if (tablero[2] == 1) {
                    proxJugada = 5
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(1, 2, 7).random()
                } else if (tablero[5] == 1) {
                    proxJugada = 2
                } else if (tablero[6] == 1) {
                    proxJugada = 7
                } else if (tablero[7] == 1) {
                    proxJugada = 6
                }
                // esquina inf izq
            } else if (tablero[4] == -1 && tablero[6] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 3
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(3, 5, 8).random()
                } else if (tablero[2] == 1) {
                    proxJugada = listOf(1, 3, 5, 7).random()
                } else if (tablero[3] == 1) {
                    proxJugada = 0
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(1, 7, 8).random()
                } else if (tablero[7] == 1) {
                    proxJugada = 8
                } else if (tablero[8] == 1) {
                    proxJugada = 7
                }
                // -------------------------------------------------------------------exterior-medio
                // medio--top
            } else if (tablero[4] == -1 && tablero[1] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 2
                } else if (tablero[2] == 1) {
                    proxJugada = 0
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 6).random()
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(2, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = listOf(0, 3, 5).random()
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[8] == 1) {
                    proxJugada = listOf(2, 3, 5).random()
                }
                // medio--left
            } else if (tablero[4] == -1 && tablero[3] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 6
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(0, 2).random()
                } else if (tablero[2] == 1) {
                    proxJugada = listOf(0, 1, 7).random()
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = 0
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(6, 8).random()
                } else if (tablero[8] == 1) {
                    proxJugada = listOf(1, 6, 7).random()
                }
                // medio--right
            } else if (tablero[4] == -1 && tablero[5] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = listOf(1, 2, 7).random()
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(0, 2).random()
                } else if (tablero[2] == 1) {
                    proxJugada = 8
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = listOf(1, 7, 8).random()
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(6, 8).random()
                } else if (tablero[8] == 1) {
                    proxJugada = 2
                }
                // medio--but
            } else if (tablero[4] == -1 && tablero[7] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = listOf(3, 5, 6).random()
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[2] == 1) {
                    proxJugada = listOf(3, 5, 8).random()
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 6).random()
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(2, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = 8
                } else if (tablero[8] == 1) {
                    proxJugada = 6
                }
                // ----------------------------------------------------------------------------medio
                // left - top
            } else if (tablero[4] == 1 && tablero[0] == -1) {
                if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[2] == 1) {
                    proxJugada = 6
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[6] == 1) {
                    proxJugada = 2
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                } else if (tablero[8] == 1) {
                    proxJugada = 2
                }
                // right - top
            } else if (tablero[4] == 1 && tablero[2] == -1) {
                if (tablero[0] == 1) {
                    proxJugada = 8
                } else if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[6] == 1) {
                    proxJugada = 8
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                } else if (tablero[8] == 1) {
                    proxJugada = 0
                }
                // left - but
            } else if (tablero[4] == 1 && tablero[6] == -1) {
                if (tablero[0] == 1) {
                    proxJugada = 8
                } else if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[2] == 1) {
                    proxJugada = 0
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                } else if (tablero[8] == 1) {
                    proxJugada = 0
                }
                //right - but
            } else if (tablero[4] == 1 && tablero[8] == -1) {
                if (tablero[0] == 1) {
                    proxJugada = 6
                } else if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[2] == 1) {
                    proxJugada = 6
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[6] == 1) {
                    proxJugada = 2
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                }
            }
/////////////////////////////////////////////////////////////
        } else if (totalFichas >= 5) {
            // ---------------------------------------------------------------------exterior-esquina
            //--------------------------------------------------------------------------------------------------------------------------------DONE-----------------------------
            // esquina sup izq--done
            if (tablero[0] == 1 && tablero[4] == -1) {
                if (tablero[1] == 1 && tablero[2] == -1) {
                    if (totalFichas == 5) {
                        if (tablero[6] == 0) {
                            proxJugada = 6
                        } else {
                            proxJugada = 3
                        }
                    } else {
                        if (totalFichas == 7) {
                            if (tablero[5] == 1) {
                                proxJugada = listOf(7, 8).random()
                            } else {
                                proxJugada = 5
                            }
                        }
                    }
                } else if (tablero[2] == 1 && tablero[1] == -1) {
                    if (totalFichas == 5) {
                        if (tablero[7] == 0) {
                            proxJugada = 7
                        } else {
                            proxJugada = 3
                        }
                    } else if (totalFichas == 7) {
                        if (tablero[3] == -1 && tablero[5] == 0) {
                            proxJugada = 5
                        } else {
                            proxJugada = listOf(6, 8).random()
                        }
                    }
                } else if (tablero[3] == 1 && tablero[6] == -1) {
                    if (totalFichas == 5) {
                        if (tablero[2] == 0) {
                            proxJugada = 2
                        } else {
                            proxJugada = 1
                        }
                    } else if (totalFichas == 7) {
                        if (tablero[7] == 0) {
                            proxJugada = 7
                        } else {
                            proxJugada = listOf(5, 8).random()
                        }
                    }

                } else if (tablero[5] == 1) {
                    if (totalFichas == 5) {
                        if (tablero[6] == 0) {
                            proxJugada = 6
                        } else {
                            proxJugada = 3
                        }
                    } else if (totalFichas == 7) {
                        if (tablero[7] == 1 || tablero[8] == 1) {
                            if (tablero[7] == 1) {
                                proxJugada = 8
                            } else {
                                proxJugada = 7
                            }
                        } else {
                            proxJugada = listOf(7, 8).random()
                        }
                    }
                } else if (tablero[6] == 1 && tablero[3] == -1) {
                    if (totalFichas == 5) {
                        if (tablero[5] == 1) {
                            proxJugada = listOf(1, 7).random()
                        } else {
                            proxJugada = 5
                        }
                    } else if (totalFichas == 7) {
                        if (tablero[1] == -1) {
                            if (tablero[7] == 0) {
                                proxJugada = 7
                            } else {
                                proxJugada = listOf(2, 8).random()
                            }
                        } else {
                            if (tablero[1] == 0) {
                                proxJugada = 1
                            } else {
                                proxJugada = listOf(2, 8).random()
                            }
                        }
                    }

                } else if (tablero[7] == 1) {
                    if (tablero[3] == -1 && tablero[5] == 1) {
                        if (totalFichas == 5) {
                            proxJugada = listOf(2, 8).random()
                        } else if (totalFichas == 7) {
                            if (tablero[8] == -1) {
                                if (tablero[2] == 1) {
                                    proxJugada = 1
                                } else {
                                    proxJugada = 2
                                }
                            } else if (tablero[2] == -1) {
                                if (tablero[6] == 1) {
                                    proxJugada = 8
                                } else if (tablero[8] == 1) {
                                    proxJugada = 6
                                }
                            }
                        }
                    } else {
                        proxJugada = 5
                    }
                    if (tablero[5] == -1 && tablero[3] == 1) {
                        if (totalFichas == 5) {
                            proxJugada = 6
                        } else if (totalFichas == 7) {
                            if (tablero[2] == 1) {
                                proxJugada = 1
                            } else if (tablero[1] == 1) {
                                proxJugada = 2
                            }
                        }
                        proxJugada = 6
                    } else {
                        proxJugada = 3
                    }
                    if (tablero[6] == -1 && tablero[2] == 1) {
                        if (totalFichas == 5) {
                            proxJugada = 1
                        } else if (totalFichas == 7) {
                            if (tablero[6] == 1) {
                                proxJugada = 8
                            } else if (tablero[8] == 1) {
                                proxJugada = 6
                            }
                        }
                    } else {
                        proxJugada = 2
                    }
                    //----------------------------------------------------------------------------------------------hay que continua por aquí
                } else if (tablero[8] == 1) {
                    if (tablero[1] == -1 && tablero[7] == 1) {
                        proxJugada = 6
                    } else {
                        proxJugada = 7
                    }
                    if (tablero[3] == -1 && tablero[5] == 1) {
                        proxJugada = 2
                    } else {
                        proxJugada = 5
                    }
                    if (tablero[5] == -1 && tablero[3] == 1) {
                        proxJugada = 6
                    } else {
                        proxJugada = 3
                    }
                    if (tablero[7] == -1 && tablero[1] == 1) {
                        proxJugada = 2
                    } else {
                        proxJugada = 1
                    }
                }

                // esquina sup der -------Done
            } else if (tablero[4] == -1 && tablero[2] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 1
                } else if (tablero[1] == 1) {
                    proxJugada = 0
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 1, 7).random()
                } else if (tablero[5] == 1) {
                    proxJugada = 8
                } else if (tablero[6] == 1) {
                    proxJugada = listOf(1, 3, 5, 7).random()
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(0, 3, 5).random()
                } else if (tablero[8] == 1) {
                    proxJugada = 5
                }
                // esquina inf der
            } else if (tablero[4] == -1 && tablero[8] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = listOf(1, 3, 5, 7).random()
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(2, 3, 5).random()
                } else if (tablero[2] == 1) {
                    proxJugada = 5
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(1, 2, 7).random()
                } else if (tablero[5] == 1) {
                    proxJugada = 2
                } else if (tablero[6] == 1) {
                    proxJugada = 7
                } else if (tablero[7] == 1) {
                    proxJugada = 6
                }
                // esquina inf izq
            } else if (tablero[4] == -1 && tablero[6] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 3
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(3, 5, 8).random()
                } else if (tablero[2] == 1) {
                    proxJugada = listOf(1, 3, 5, 7).random()
                } else if (tablero[3] == 1) {
                    proxJugada = 0
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(1, 7, 8).random()
                } else if (tablero[7] == 1) {
                    proxJugada = 8
                } else if (tablero[8] == 1) {
                    proxJugada = 7
                }
                // -------------------------------------------------------------------exterior-medio
                // medio--top
            } else if (tablero[4] == -1 && tablero[1] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 2
                } else if (tablero[2] == 1) {
                    proxJugada = 0
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 6).random()
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(2, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = listOf(0, 3, 5).random()
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[8] == 1) {
                    proxJugada = listOf(2, 3, 5).random()
                }
                // medio--left
            } else if (tablero[4] == -1 && tablero[3] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = 6
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(0, 2).random()
                } else if (tablero[2] == 1) {
                    proxJugada = listOf(0, 1, 7).random()
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = 0
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(6, 8).random()
                } else if (tablero[8] == 1) {
                    proxJugada = listOf(1, 6, 7).random()
                }
                // medio--right
            } else if (tablero[4] == -1 && tablero[5] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = listOf(1, 2, 7).random()
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(0, 2).random()
                } else if (tablero[2] == 1) {
                    proxJugada = 8
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = listOf(1, 7, 8).random()
                } else if (tablero[7] == 1) {
                    proxJugada = listOf(6, 8).random()
                } else if (tablero[8] == 1) {
                    proxJugada = 2
                }
                // medio--but
            } else if (tablero[4] == -1 && tablero[7] == 1) {
                if (tablero[0] == 1) {
                    proxJugada = listOf(3, 5, 6).random()
                } else if (tablero[1] == 1) {
                    proxJugada = listOf(0, 2, 6, 8).random()
                } else if (tablero[2] == 1) {
                    proxJugada = listOf(3, 5, 8).random()
                } else if (tablero[3] == 1) {
                    proxJugada = listOf(0, 6).random()
                } else if (tablero[5] == 1) {
                    proxJugada = listOf(2, 8).random()
                } else if (tablero[6] == 1) {
                    proxJugada = 8
                } else if (tablero[8] == 1) {
                    proxJugada = 6
                }
                // ----------------------------------------------------------------------------medio
                // left - top
            } else if (tablero[4] == 1 && tablero[0] == -1) {
                if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[2] == 1) {
                    proxJugada = 6
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[6] == 1) {
                    proxJugada = 2
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                } else if (tablero[8] == 1) {
                    proxJugada = 2
                }
                // right - top
            } else if (tablero[4] == 1 && tablero[2] == -1) {
                if (tablero[0] == 1) {
                    proxJugada = 8
                } else if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[6] == 1) {
                    proxJugada = 8
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                } else if (tablero[8] == 1) {
                    proxJugada = 0
                }
                // left - but
            } else if (tablero[4] == 1 && tablero[6] == -1) {
                if (tablero[0] == 1) {
                    proxJugada = 8
                } else if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[2] == 1) {
                    proxJugada = 0
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                } else if (tablero[8] == 1) {
                    proxJugada = 0
                }
                //right - but
            } else if (tablero[4] == 1 && tablero[8] == -1) {
                if (tablero[0] == 1) {
                    proxJugada = 6
                } else if (tablero[1] == 1) {
                    proxJugada = 7
                } else if (tablero[2] == 1) {
                    proxJugada = 6
                } else if (tablero[3] == 1) {
                    proxJugada = 5
                } else if (tablero[5] == 1) {
                    proxJugada = 3
                } else if (tablero[6] == 1) {
                    proxJugada = 2
                } else if (tablero[7] == 1) {
                    proxJugada = 1
                }
            }

        }



        return proxJugada
    }

    // se comprueba que haya una jugada ganadora.
    fun comprobarGanador(posJugada: Int) {
        /*
        -  Horizontal
        H-1
        H-2
        H-3
        - Vertical
        V-4 -- V-5 -- V-6
        - Diagonal izq-der / Diagonal der-izq
        D-7        ---         D-8

        '0 -- H1 / V4 / D7
        '1 -- H1 / V5
        '2 -- H1 / V6 / D8
        '3 -- H2 / V4
        '5 -- H2 / V6
        '6 -- H3 / V4 / D8
        '7 -- H3 / V5
        '8 -- H3 / V6 / D7

        */


        // comprobar si hay ganador
        if (totalFichas >= 5) {
            var final = false
            if (posJugada == 0) {
                if (tablero[0] == tablero[1] && tablero[0] == tablero[2]) {
                    //H1
                    ponerFichaGanadora(0, 1, 2)
                    estadoPartida = 1
                    final = true
                } else if (tablero[0] == tablero[3] && tablero[0] == tablero[6]) {
                    //V4
                    ponerFichaGanadora(0, 3, 6)
                    estadoPartida = 1
                    final = true
                } else if (tablero[0] == tablero[4] && tablero[0] == tablero[8]) {
                    //D7
                    ponerFichaGanadora(0, 4, 8)
                    estadoPartida = 1
                    final = true
                }
            } else if (posJugada == 1) {
                if (tablero[0] == tablero[1] && tablero[1] == tablero[2]) {
                    //H1
                    ponerFichaGanadora(0, 1, 2)
                    estadoPartida = 1
                    final = true
                } else if (tablero[4] == tablero[1] && tablero[1] == tablero[7]) {
                    //V5
                    ponerFichaGanadora(1, 4, 7)
                    estadoPartida = 1
                    final = true
                }
            } else if (posJugada == 2) {
                if (tablero[0] == tablero[2] && tablero[1] == tablero[2]) {
                    //H1
                    ponerFichaGanadora(2, 1, 0)
                    estadoPartida = 1
                    final = true
                } else if (tablero[2] == tablero[5] && tablero[2] == tablero[8]) {
                    //V6
                    ponerFichaGanadora(2, 5, 8)
                    estadoPartida = 1
                    final = true
                } else if (tablero[2] == tablero[4] && tablero[2] == tablero[6]) {
                    //D8
                    ponerFichaGanadora(2, 4, 6)
                    estadoPartida = 1
                    final = true
                }
            } else if (posJugada == 3) {
                if (tablero[3] == tablero[4] && tablero[3] == tablero[5]) {
                    //H2
                    ponerFichaGanadora(3, 4, 5)
                    estadoPartida = 1
                    final = true
                } else if (tablero[3] == tablero[0] && tablero[3] == tablero[6]) {
                    //V4
                    ponerFichaGanadora(3, 0, 6)
                    estadoPartida = 1
                    final = true
                }
            } else if (posJugada == 5) {
                if (tablero[5] == tablero[4] && tablero[5] == tablero[3]) {
                    //H2
                    ponerFichaGanadora(5, 4, 3)
                    estadoPartida = 1
                    final = true
                } else if (tablero[5] == tablero[2] && tablero[5] == tablero[8]) {
                    //V6
                    ponerFichaGanadora(5, 2, 8)
                    estadoPartida = 1
                    final = true
                }
            } else if (posJugada == 6) {
                if (tablero[6] == tablero[7] && tablero[6] == tablero[8]) {
                    //H3
                    ponerFichaGanadora(6, 7, 8)
                    estadoPartida = 1
                    final = true
                } else if (tablero[6] == tablero[3] && tablero[6] == tablero[0]) {
                    //V4
                    ponerFichaGanadora(6, 3, 0)
                    estadoPartida = 1
                    final = true
                } else if (tablero[6] == tablero[4] && tablero[6] == tablero[2]) {
                    //D8
                    ponerFichaGanadora(6, 4, 2)
                    estadoPartida = 1
                    final = true
                }
            } else if (posJugada == 7) {
                if (tablero[7] == tablero[6] && tablero[7] == tablero[8]) {
                    //H3
                    ponerFichaGanadora(7, 6, 8)
                    estadoPartida = 1
                    final = true
                } else if (tablero[7] == tablero[4] && tablero[7] == tablero[1]) {
                    //V5
                    ponerFichaGanadora(7, 4, 1)
                    estadoPartida = 1
                    final = true
                }
            } else if (posJugada == 8) {
                if (tablero[8] == tablero[7] && tablero[8] == tablero[6]) {
                    //H3
                    ponerFichaGanadora(8, 7, 6)
                    estadoPartida = 1
                    final = true
                } else if (tablero[8] == tablero[5] && tablero[8] == tablero[2]) {
                    //V6
                    ponerFichaGanadora(8, 5, 2)
                    estadoPartida = 1
                    final = true
                } else if (tablero[8] == tablero[4] && tablero[8] == tablero[0]) {
                    //D7
                    ponerFichaGanadora(8, 4, 0)
                    estadoPartida = 1
                    final = true
                }
            }

            if (final == false && totalFichas == 9) {
                estadoPartida = 1
                textoGame.setText(R.string.tie)
            }
        }


    }

    // se le pasan las 3 posiciones ganadoras y se realiza el cambio con la figuras ganadoras.
    fun ponerFichaGanadora(pos1: Int, pos2: Int, pos3: Int) {
        val btn1: Button = findViewById(botones.get(pos1))
        val btn2: Button = findViewById(botones.get(pos2))
        val btn3: Button = findViewById(botones.get(pos3))
        for (i in 0..8){
            if (i != pos1 && i != pos2 && i != pos3){
                val inactivo : Button = findViewById(botones.get(i))
                inactivo.isEnabled = false
            }
        }
        if (tablero[pos1] == 1) {
            btn1.setBackgroundResource((R.drawable.cross_winner))
            btn2.setBackgroundResource((R.drawable.cross_winner))
            btn3.setBackgroundResource((R.drawable.cross_winner))
            textoGame.setText(R.string.win)
        } else {
            btn1.setBackgroundResource((R.drawable.cercle_winner))
            btn2.setBackgroundResource((R.drawable.cercle_winner))
            btn3.setBackgroundResource((R.drawable.cercle_winner))
            textoGame.setText(R.string.lost)
        }


    }

    // acceso rápido a una nueva partida.
    fun restartApp() {
        if (totalFichas > 0) {
            var screen: Intent = Intent(this, MainActivity::class.java)
//        intent.setClass(actividad, actividad.getClass());
            //llamamos a la actividad
//        actividad.startActivity(intent);
            startActivity(screen)
            //finalizamos la actividad actual
//        actividad.finish();
            this.finish()
        }
    }

}