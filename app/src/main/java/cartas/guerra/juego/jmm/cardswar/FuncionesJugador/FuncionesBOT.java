package cartas.guerra.juego.jmm.cardswar.FuncionesJugador;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import cartas.guerra.juego.jmm.cardswar.BaseDeDatos.AccionesBot;
import cartas.guerra.juego.jmm.cardswar.BaseDeDatos.DatabaseAccess;
import cartas.guerra.juego.jmm.cardswar.MainActivity;
import cartas.guerra.juego.jmm.cardswar.R;

/**
 * Clase que recogerá los métodos propios del BOT
 */
public class FuncionesBOT {

    public TextView tvToolbar;
    public ArrayList<String> ordenNombresEjercitos;

    public TextView tvVehiculosJugadores[];
    public ImageView ivVehiculosJugadores[];
    public TextView tvComodinesJugadores[];
    public TextView tvCartasJugadores[];
    public RelativeLayout relativeJugadores[];

    String[] nombreAcciones = {
            "echarorgano", "echarmedicina", "echarvirus", "intercambiarcuerpo", "robarorgano", "intercambiarorgano", "expulsarvirus",
            "echarno"};

    boolean carta1Elegida = false;
    boolean dejarCarta1Elegida = false;
    boolean carta2Elegida = false;
    boolean dejarCarta2Elegida = false;
    boolean carta3Elegida = false;
    boolean dejarCarta3Elegida = false;

    ArrayList<Integer> listLimiteBotPorcentajeAplic = new ArrayList<>();

    FuncionesGenerales funcionesGenerales;

    public int numeroJugadores;

    int valorMayorEcharCartaArma[][][] = new int[3][6][6];
    int valorMayorEcharCartaIntercambioVeh[][][] = new int[3][6][6];
    int valorMayorEcharCartaRoboVeh[][] = new int[6][6];
    int valorMayorEcharCartaIntercambioEq[] = new int[6];

    ArrayList<Boolean> restoVehConseguidos = new ArrayList<>();
    ArrayList<Integer> restoContadorVeh = new ArrayList<>();
    ArrayList<Double> restoPuntosJug = new ArrayList<>();

    int rivalConPuntuacionMasAlta = 0;

    public boolean expulsionArmas = false;

    public int numCasillaVehJugRobar = 0;

    public SoundPool sonidoJuego;

    ArrayList<String> nombresEjercitos;

    int sumaProbabilidades = 0;

    ImageView ivGifVictoria;

    public TableLayout tableCartas;
    public TableRow[] tableRowVehs;
    public LinearLayout linearJuego;

    // Array con la lista de vehículos intercambiables del Bot
    ArrayList<Integer> vehBotIntercambiable = new ArrayList<>();
    // Array con la lista de vehículos intercambiables del restod e jugadores
    ArrayList<Integer> vehRestoIntercambiable = new ArrayList<>();
    // Array con la lista de jugadores que pueden intercambiar
    ArrayList<Integer> nombreJugIntercambiable = new ArrayList<>();
    // Array con la lista de diferencias entre el contador de un vehículo y otro intercambiables
    ArrayList<Integer> diferenciaVehIntercambiable = new ArrayList<>();

    public boolean numRepetido = false;

    public TableLayout tableJugVehiculos;

    public FuncionesBOT(TextView tvJugVehs[], ImageView ivJugVehs[], TextView tvJugComs[], TextView tvCartasJug[], int numJug, TextView tvtoolbar, SoundPool sonidojuego,
                        ArrayList<String> nomEjercitos, ArrayList<String> ordenNomEjercitos, ImageView ivgifvictoria, TableLayout tbCartas, TableRow[] tbRowVehs,
                        LinearLayout lJuego, TableLayout tabJugVeh, RelativeLayout relJugs[])
    {
        this.tvVehiculosJugadores = tvJugVehs;
        this.ivVehiculosJugadores = ivJugVehs;
        this.tvComodinesJugadores = tvJugComs;
        this.tvCartasJugadores = tvCartasJug;
        this.numeroJugadores = numJug;
        this.tvToolbar = tvtoolbar;
        this.sonidoJuego = sonidojuego;
        this.nombresEjercitos = nomEjercitos;
        this.ordenNombresEjercitos = ordenNomEjercitos;
        this.ivGifVictoria = ivgifvictoria;
        this.tableCartas = tbCartas;
        this.tableRowVehs = tbRowVehs;
        this.linearJuego = lJuego;
        this.tableJugVehiculos = tabJugVeh;
        this.relativeJugadores = relJugs;
    }

    public FuncionesBOT() {

    }

    public void accionBot(TextView tvCartasJugadores[], ImageView ivCartasJugadores[], TextView tvVehiculosJugadores[],
                          TextView tvMensajeVictoria, int numTirada, String[] barajaCartas, Context context,
                          RelativeLayout relVehiculosJugadores[], ArrayList ordenTurno, int nombreJug, View view, MediaPlayer musicaFondo)
    {
        AccionesBot accionesBot = AccionesBot.getInstance();

        System.out.println("Solan0 ->");
        listLimiteBotPorcentajeAplic.clear();
        listLimiteBotPorcentajeAplic.add(0, 0);
        listLimiteBotPorcentajeAplic.add(1, 0);
        listLimiteBotPorcentajeAplic.add(2, 0);
        listLimiteBotPorcentajeAplic.add(3, 0);
        listLimiteBotPorcentajeAplic.add(4, 0);
        listLimiteBotPorcentajeAplic.add(5, 0);

        for (int a = 0; a < 3; a++)
        {
            for (int e = 0; e < 6; e++)
            {
                for (int i = 0; i < 6; i++)
                {
                    valorMayorEcharCartaArma[a][e][i] = 0;
                    valorMayorEcharCartaIntercambioVeh[a][e][i] = 0;
                    valorMayorEcharCartaRoboVeh[e][i] = 0;
                    valorMayorEcharCartaIntercambioEq[i] = 0;
                }
            }
        }

        funcionesGenerales = new FuncionesGenerales(tvVehiculosJugadores, ivVehiculosJugadores, tvComodinesJugadores, tvCartasJugadores, ivCartasJugadores,
                relVehiculosJugadores, numeroJugadores, nombresEjercitos, tableCartas, tableRowVehs, linearJuego, context, tableJugVehiculos);

        funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, context, musicaFondo);
        for (int i = 1; i < numeroJugadores; i++)
        {
            funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaFondo);
            System.out.println("Gigante -> ******************************************************" + numeroJugadores);
        }

        restoVehConseguidos.clear();
        restoVehConseguidos.add(accionesBot.getVeh1JugConseguido());
        restoVehConseguidos.add(accionesBot.getVeh2JugConseguido());
        restoVehConseguidos.add(accionesBot.getVeh3JugConseguido());
        restoVehConseguidos.add(accionesBot.getVeh4JugConseguido());
        restoVehConseguidos.add(accionesBot.getVehMulticolorJugConseguido());

        restoContadorVeh.clear();
        restoContadorVeh.add(accionesBot.getContadorJugBatallon());
        restoContadorVeh.add(accionesBot.getContadorJugTanque());
        restoContadorVeh.add(accionesBot.getContadorJugSubmarino());
        restoContadorVeh.add(accionesBot.getContadorJugCaza());
        restoContadorVeh.add(accionesBot.getContadorJugVehMulticolor());

        restoPuntosJug.add(accionesBot.getPuntosJug());

        for (int i = 1; i < numeroJugadores; i++)
        {
            System.out.println("Oakley -> " + i + " = " + nombreJug);
            if (i != nombreJug)
            {
                for (int a = 0; a < 5; a++)
                {
                    restoVehConseguidos.add(accionesBot.getVehBotConseguido((i - 1) * 5 + a));
                }
            }
            else
            {
                restoVehConseguidos.add(false);
                restoVehConseguidos.add(false);
                restoVehConseguidos.add(false);
                restoVehConseguidos.add(false);
                restoVehConseguidos.add(false);
            }
        }

        System.out.println("Quiles -> Jugador----------------------------------------------");

        for (int a = 1; a < numeroJugadores; a++)
        {
            System.out.println("La Verrrugaaa" + nombreJug);
            if (a != nombreJug)
            {
                System.out.println("La Verrrugaaa2");
                int i = a - 1;
                restoContadorVeh.add(accionesBot.getContadorBotBatallon(i));
                restoContadorVeh.add(accionesBot.getContadorBotTanque(i));
                restoContadorVeh.add(accionesBot.getContadorBotSubmarino(i));
                restoContadorVeh.add(accionesBot.getContadorBotCaza(i));
                restoContadorVeh.add(accionesBot.getContadorBotVehMulticolor(i));

                restoPuntosJug.add(accionesBot.getPuntosBot(i));
            }
            else
            {
                restoContadorVeh.add(-5);
                restoContadorVeh.add(-5);
                restoContadorVeh.add(-5);
                restoContadorVeh.add(-5);
                restoContadorVeh.add(-5);

                restoPuntosJug.add(-100.0);
            }
        }

        System.out.println("Fernando ---------------------------------------------------------------");
        for (int i = 0; i < 3; i++)
        {
            System.out.println("Fernando - " + tvCartasJugadores[(nombreJug) * 3 + i].getText());

            System.out.println("Bogut -> " + nombreJug + " - " + (String) tvCartasJugadores[(nombreJug) * 3 + i].getText());
            if (relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 0)
            {
                sePuedeLanzarVehiculo(tvCartasJugadores[(nombreJug) * 3 + i], listLimiteBotPorcentajeAplic, (i * 2), (i * 2 + 1), accionesBot, nombreJug);
                System.out.println("Solan1 ->");
            }
            else if (relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 1)
            {
                sePuedeLanzarMedicina(tvCartasJugadores[(nombreJug) * 3 + i], listLimiteBotPorcentajeAplic, (i * 2), (i * 2 + 1), accionesBot, nombreJug);
                System.out.println("Solan2 ->");
            }
            else if (relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 2)
            {
                for (int a = 0; a < numeroJugadores; a++)
                {
                    if (a != nombreJug)
                    {
                        sePuedeLanzarArma(nombreJug, tvCartasJugadores[(nombreJug) * 3 + i], restoVehConseguidos, restoContadorVeh, a, i);
                    }
                    else
                    {
                        valorMayorEcharCartaArma[i][nombreJug][a] = 0;
                        System.out.println("Quilesss -> Carta" + i + ": Jugador" + nombreJug + " sobre Jugador" + a + " - " + valorMayorEcharCartaArma[i][nombreJug][a]);
                    }
                }

                if (posValorMayorEcharCartaArma2(i, nombreJug) > 0)
                {
                    listLimiteBotPorcentajeAplic.set(i * 2, posValorMayorEcharCartaArma2(i, nombreJug));
                    listLimiteBotPorcentajeAplic.set(i * 2 + 1, 0);
                    System.out.println("Solan3 ->");
                }
                else
                {
                    listLimiteBotPorcentajeAplic.set(i * 2, 0);
                    listLimiteBotPorcentajeAplic.set(i * 2 + 1, 100);
                    System.out.println("Solan4 ->");
                }
            }
            else if (relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 3)
            {
                double puntuacionMasAlta = 0;
                rivalConPuntuacionMasAlta = 0;
                System.out.println("Entrada0 -> ");
                for (int a = 0; a < numeroJugadores; a++)
                {
                    if (a != nombreJug)
                    {
                        if (a == 0)
                        {
                            if (accionesBot.getPuntosJug() > puntuacionMasAlta)
                            {
                                puntuacionMasAlta = accionesBot.getPuntosJug();
                                rivalConPuntuacionMasAlta = a;
                            }
                        }
                        else
                        {
                            if (accionesBot.getPuntosBot(a - 1) > puntuacionMasAlta)
                            {
                                puntuacionMasAlta = accionesBot.getPuntosBot(a - 1);
                                rivalConPuntuacionMasAlta = a;
                            }
                        }
                    }
                }

                sePuedeLanzarIntercambioEquipo(tvCartasJugadores[(nombreJug) * 3 + i], listLimiteBotPorcentajeAplic,
                        (i * 2), (i * 2 + 1), accionesBot, nombreJug, rivalConPuntuacionMasAlta);
                System.out.println("Solan5 ->");
            }
            else if (relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 4)
            {
                for (int a = 0; a < numeroJugadores; a++)
                {
                    if (a != nombreJug)
                    {
                        sePuedeLanzarRoboVehiculo(tvCartasJugadores[(nombreJug) * 3 + i], accionesBot, nombreJug, a);
                    }
                }

                if (posValorMayorEcharCartaRoboVeh2(nombreJug) > 0)
                {
                    listLimiteBotPorcentajeAplic.set(i * 2, posValorMayorEcharCartaRoboVeh2(nombreJug));
                    listLimiteBotPorcentajeAplic.set(i * 2 + 1, 0);
                    System.out.println("Solan6 ->");
                }
                else
                {
                    listLimiteBotPorcentajeAplic.set(i * 2, 0);
                    listLimiteBotPorcentajeAplic.set(i * 2 + 1, 100);
                    System.out.println("Solan7 ->");
                }
            }
            else if (relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 5)
            {
                for (int a = 0; a < numeroJugadores; a++)
                {
                    if (a != nombreJug)
                    {
                        sePuedeLanzarIntercambioVehiculo(nombreJug, tvCartasJugadores[nombreJug * 3 + i], restoVehConseguidos,
                                restoContadorVeh, a, i, accionesBot, restoPuntosJug);
                    }
                    else
                    {
                        valorMayorEcharCartaIntercambioVeh[i][nombreJug][a] = 0;
                    }
                }

                /*for (int e = 0; e < valorMayorEcharCartaIntercambioVeh.length; e++ )
                {
                    for (int f = 0; f < valorMayorEcharCartaIntercambioVeh[e].length; f++)
                    {
                        for (int g = 0; g < valorMayorEcharCartaIntercambioVeh[e][f].length; g++)
                        {
                            System.out.println("Luna -> " + e + "|" + f + "|" + g + ": " + valorMayorEcharCartaIntercambioVeh[e][f][g]);
                        }
                    }
                }*/

                if (posValorMayorEcharCartaIntercambioVeh2(i, nombreJug) > 0)
                {
                    System.out.println("Fermin1");
                    listLimiteBotPorcentajeAplic.set(i * 2, posValorMayorEcharCartaIntercambioVeh2(i, nombreJug));
                    listLimiteBotPorcentajeAplic.set(i * 2 + 1, 0);
                }
                else
                {
                    System.out.println("Fermin2");
                    listLimiteBotPorcentajeAplic.set(i * 2, 0);
                    listLimiteBotPorcentajeAplic.set(i * 2 + 1, 100);
                }
            }
            else if (relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 6)
            {
                sePuedeLanzarExpulsionArmas(tvCartasJugadores[nombreJug * 3 + i], listLimiteBotPorcentajeAplic, i * 2, i * 2 + 1,
                        accionesBot, nombreJug);
                System.out.println("Solan8 ->");
            }
            else if (relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 7)
            {
                int sumaProb = 0;

                // Sumamos las probabilidades de cada carta para posteriormente sacar el número aleatorio.
                System.out.println("Solan9 ->" + listLimiteBotPorcentajeAplic.size());
                for (int a = 0; a < listLimiteBotPorcentajeAplic.size(); a++)
                {
                    sumaProb += listLimiteBotPorcentajeAplic.get(a);
                    System.out.println("Navajero -> Limite" + a + ": " + sumaProb);
                }

                sePuedeLanzarDejarCartas(tvCartasJugadores[nombreJug * 3 + i], listLimiteBotPorcentajeAplic, i * 2, i * 2 + 1,
                        sumaProb, accionesBot);
                System.out.println("Solan8 ->");
            }
            else
            {
                listLimiteBotPorcentajeAplic.set(i * 2, 0);
                listLimiteBotPorcentajeAplic.set(i * 2 + 1, 0);
            }
        }

        for (int i = 0; i < 3; i++)
        {
            if ((relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 0) ||
                    (relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 1) ||
                    (relacionarCartaConProbAccion((String) tvCartasJugadores[(nombreJug) * 3 + i].getText()) == 2))
            {
                if (listLimiteBotPorcentajeAplic.get(i * 2) > 0)
                {
                    for (int a = 0; a < 3; a++)
                    {
                        listLimiteBotPorcentajeAplic.set(a * 2 + 1, 0);
                    }

                    break;
                }
            }
        }

        /*if (relacionarCartaConProbAccion((String) tvCartasJugadores[limMinimoCartas].getText()) == 7)
        {
            sePuedeLanzarDejarCartas(tvCartasJugadores[limMinimoCartas], listLimiteBotPorcentajeAplic, 0, 1, sumaProbabilidades, accionesBot);
        }

        if (relacionarCartaConProbAccion((String) tvCartasJugadores[limMinimoCartas + 1].getText()) == 7)
        {
            sePuedeLanzarDejarCartas(tvCartasJugadores[limMinimoCartas], listLimiteBotPorcentajeAplic, 2, 3, sumaProbabilidades, accionesBot);
        }

        if (relacionarCartaConProbAccion((String) tvCartasJugadores[limMinimoCartas + 2].getText()) == 7)
        {
            sePuedeLanzarDejarCartas(tvCartasJugadores[limMinimoCartas], listLimiteBotPorcentajeAplic, 4, 5, sumaProbabilidades, accionesBot);
        }*/

        for (int i = 0; i < listLimiteBotPorcentajeAplic.size(); i++)
        {
            if (i == 0)
            {
                sumaProbabilidades = 0;
            }

            sumaProbabilidades += listLimiteBotPorcentajeAplic.get(i);
        }

        elegirCartaBot(sumaProbabilidades, listLimiteBotPorcentajeAplic.get(0), listLimiteBotPorcentajeAplic.get(1),
                listLimiteBotPorcentajeAplic.get(2), listLimiteBotPorcentajeAplic.get(3), listLimiteBotPorcentajeAplic.get(4),
                listLimiteBotPorcentajeAplic.get(5),
                tvCartasJugadores[nombreJug * 3 + 0], tvCartasJugadores[nombreJug * 3 + 1], tvCartasJugadores[nombreJug * 3 + 2],
                tvVehiculosJugadores[nombreJug * 4 + 0], tvVehiculosJugadores[nombreJug * 4 + 1],
                tvVehiculosJugadores[nombreJug * 4 + 2], tvVehiculosJugadores[nombreJug * 4 + 3],
                tvMensajeVictoria, numTirada, barajaCartas, accionesBot, context, nombreJug, view);

        funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, context, musicaFondo);
        System.out.println("Gigante -> ---------------------------------");
        for (int i = 1; i < numeroJugadores; i++)
        {
            funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaFondo);
            System.out.println("Gigante -> ******************************************************");
        }
    }

    // Método que determinará que tipo de acción realizaremos en función de la carta sacada.
    public int relacionarCartaConProbAccion(String nombreCarta)
    {
        if ((nombreCarta.equals("Batallon")) || (nombreCarta.equals("Tanque")) || (nombreCarta.equals("Submarino")) || (nombreCarta.equals("Caza")) || (nombreCarta.equals("Vehículo Multicolor")))
        {
            return 0;
        }
        else if ((nombreCarta.equals("Medicina1")) || (nombreCarta.equals("Medicina2")) || (nombreCarta.equals("Medicina3")) || (nombreCarta.equals("Medicina4")) || (nombreCarta.equals("Medicina Multicolor")))
        {
            return 1;
        }
        else if ((nombreCarta.equals("Metralleta")) || (nombreCarta.equals("Lanzacohetes")) ||(nombreCarta.equals("Torpedos")) ||(nombreCarta.equals("Misiles")) ||(nombreCarta.equals("Arma Multicolor")))
        {
            return 2;
        }
        else if (nombreCarta.equals("Intercambiar Brigada"))
        {
            return 3;
        }
        else if (nombreCarta.equals("Robar Vehículo"))
        {
            return 4;
        }
        else if (nombreCarta.equals("Cambiar Vehículo"))
        {
            return 5;
        }
        else if (nombreCarta.equals("Expulsar Armas"))
        {
            return 6;
        }
        else if (nombreCarta.equals("Dejar Cartas"))
        {
            return 7;
        }
        else
        {
            return 8;
        }
    }

    public void sePuedeLanzarDejarCartas(TextView tvCarta, ArrayList probCarta, int posArrayCarta, int posArrayDejarCarta,
                                   int sumaProbabilidades, AccionesBot accionesBot)
    {
        // Recogemos el texto de cada carta del BOT
        String txtBotCarta = (String) tvCarta.getText();

        if (botTieneCartaConcreta(txtBotCarta, "Dejar cartas"))
        {
            if (sumaProbabilidades > 900)
            {
                probCarta.set(posArrayCarta, 0);
                probCarta.set(posArrayDejarCarta, 100);
            }
            else
            {
                if (sumaProbabilidades > 600)
                {
                    probCarta.set(posArrayCarta, 100);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else
                {
                    if (sumaProbabilidades > 0)
                    {
                        probCarta.set(posArrayCarta, 200);
                        probCarta.set(posArrayDejarCarta, 100);
                    }
                    else
                    {
                        probCarta.set(posArrayCarta, 200);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                }
            }
        }
    }

    public void sePuedeLanzarExpulsionArmas(TextView tvCarta, ArrayList probCarta, int posArrayCarta, int posArrayDejarCarta,
                                            AccionesBot accionesBot, int posJug2)
    {
        int posJug = posJug2 - 1;

        String txtBotCarta = (String) tvCarta.getText();

        if ((botTieneCartaConcreta(txtBotCarta, "Expulsar Armas")))
        {
            int arrContadVehBot[] = new int[]{
                    accionesBot.getContadorBotBatallon(posJug), accionesBot.getContadorBotTanque(posJug),
                    accionesBot.getContadorBotSubmarino(posJug), accionesBot.getContadorBotCaza(posJug),
                    accionesBot.getContadorBotVehMulticolor(posJug)};

            int numVirus = 0;

            System.out.println("Inocente ***********************");
            for (int i = 0; i < arrContadVehBot.length; i++)
            {
                System.out.println("Inocente -> Jugador" + posJug + ": " + arrContadVehBot[i]);
                if (arrContadVehBot[i] == -1)
                {
                    numVirus += 1;
                }
            }

            ArrayList<Double> puntosRestoJug = new ArrayList<>();
            puntosRestoJug.add(0, accionesBot.getPuntosJug());
            for (int i = 1; i < numeroJugadores; i++)
            {
                if (i != posJug2)
                {
                    puntosRestoJug.add(i, accionesBot.getPuntosBot(i - 1));
                }
                else
                {
                    puntosRestoJug.add(i, 0.0);
                }
            }

            System.out.println("NumVirus -> " + numVirus + " - " + funcionesGenerales.puntosRivalConMasPuntos(accionesBot, funcionesGenerales.rivalConMasPuntos(puntosRestoJug)));

            if (numVirus > 0)
            {
                if (funcionesGenerales.puntosRivalConMasPuntos(accionesBot, funcionesGenerales.rivalConMasPuntos(puntosRestoJug)) - accionesBot.puntosBot[posJug] >= 3)
                {
                    probCarta.set(posArrayCarta, 900);
                    probCarta.set(posArrayDejarCarta, 0);
                }
                else if (funcionesGenerales.puntosRivalConMasPuntos(accionesBot, funcionesGenerales.rivalConMasPuntos(puntosRestoJug)) - accionesBot.puntosBot[posJug] >= 2)
                {
                    if (numVirus > 2)
                    {
                        probCarta.set(posArrayCarta, 900);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                    else
                    {
                        probCarta.set(posArrayCarta, 600);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                }
                else if (funcionesGenerales.puntosRivalConMasPuntos(accionesBot, funcionesGenerales.rivalConMasPuntos(puntosRestoJug)) - accionesBot.puntosBot[posJug] >= 1)
                {
                    if (numVirus > 2)
                    {
                        probCarta.set(posArrayCarta, 600);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                    else
                    {
                        probCarta.set(posArrayCarta, 300);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                }
                else
                {
                    if (numVirus > 2)
                    {
                        probCarta.set(posArrayCarta, 300);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                    else
                    {
                        probCarta.set(posArrayCarta, 200);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                }
            }
            else
            {
                probCarta.set(posArrayCarta, 0);
                probCarta.set(posArrayDejarCarta, 100);
            }
        }
    }

    public void sePuedeLanzarIntercambioVehiculo(int nombreJug2, TextView tvCarta, ArrayList restoVehConseguidos, ArrayList restoContadorVeh,
                                                 int posRestoVehConseguidos, int posCarta, AccionesBot accionesBot, ArrayList restoPuntosJug)
    {
        int nombreJug = nombreJug2 - 1;
        String txtBotCarta = (String) tvCarta.getText();

        if ((botTieneCartaConcreta(txtBotCarta, "Cambiar Vehículo")))
        {
            ArrayList<Integer> contadorBotConseguidos = new ArrayList<>();
            contadorBotConseguidos.add(accionesBot.getContadorBotBatallon(nombreJug));
            contadorBotConseguidos.add(accionesBot.getContadorBotTanque(nombreJug));
            contadorBotConseguidos.add(accionesBot.getContadorBotSubmarino(nombreJug));
            contadorBotConseguidos.add(accionesBot.getContadorBotCaza(nombreJug));
            contadorBotConseguidos.add(accionesBot.getContadorBotVehMulticolor(nombreJug));

            ArrayList<Boolean> vehBotConseguidos = new ArrayList<>();
            vehBotConseguidos.add(accionesBot.getVehBotConseguido(nombreJug * 5 + 0));
            vehBotConseguidos.add(accionesBot.getVehBotConseguido(nombreJug * 5 + 1));
            vehBotConseguidos.add(accionesBot.getVehBotConseguido(nombreJug * 5 + 2));
            vehBotConseguidos.add(accionesBot.getVehBotConseguido(nombreJug * 5 + 3));
            vehBotConseguidos.add(accionesBot.getVehBotConseguido(nombreJug * 5 + 4));

            ArrayList<Integer> contadorRestoConseguidos = new ArrayList<>();
            contadorRestoConseguidos.add(accionesBot.getContadorJugBatallon());
            contadorRestoConseguidos.add(accionesBot.getContadorJugTanque());
            contadorRestoConseguidos.add(accionesBot.getContadorJugSubmarino());
            contadorRestoConseguidos.add(accionesBot.getContadorJugCaza());
            contadorRestoConseguidos.add(accionesBot.getContadorJugVehMulticolor());
            for (int i = 0; i < numeroJugadores - 1; i++)
            {
                if (i != nombreJug)
                {
                    contadorRestoConseguidos.add(accionesBot.getContadorBotBatallon(i));
                    contadorRestoConseguidos.add(accionesBot.getContadorBotTanque(i));
                    contadorRestoConseguidos.add(accionesBot.getContadorBotSubmarino(i));
                    contadorRestoConseguidos.add(accionesBot.getContadorBotCaza(i));
                    contadorRestoConseguidos.add(accionesBot.getContadorBotVehMulticolor(i));
                }
                else
                {
                    contadorRestoConseguidos.add(-5);
                    contadorRestoConseguidos.add(-5);
                    contadorRestoConseguidos.add(-5);
                    contadorRestoConseguidos.add(-5);
                    contadorRestoConseguidos.add(-5);
                }
            }

            ArrayList<Boolean> vehRestoConseguidos = new ArrayList<>();
            vehRestoConseguidos.add(accionesBot.getVeh1JugConseguido());
            vehRestoConseguidos.add(accionesBot.getVeh2JugConseguido());
            vehRestoConseguidos.add(accionesBot.getVeh3JugConseguido());
            vehRestoConseguidos.add(accionesBot.getVeh4JugConseguido());
            vehRestoConseguidos.add(accionesBot.getVehMulticolorJugConseguido());
            for (int i = 0; i < numeroJugadores - 1; i++)
            {
                if (i != nombreJug)
                {
                    vehRestoConseguidos.add(accionesBot.getVehBotConseguido(i * 5 + 0));
                    vehRestoConseguidos.add(accionesBot.getVehBotConseguido(i * 5 + 1));
                    vehRestoConseguidos.add(accionesBot.getVehBotConseguido(i * 5 + 2));
                    vehRestoConseguidos.add(accionesBot.getVehBotConseguido(i * 5 + 3));
                    vehRestoConseguidos.add(accionesBot.getVehBotConseguido(i * 5 + 4));
                }
                else
                {
                    vehRestoConseguidos.add(false);
                    vehRestoConseguidos.add(false);
                    vehRestoConseguidos.add(false);
                    vehRestoConseguidos.add(false);
                    vehRestoConseguidos.add(false);
                }
            }

            if (vehiculoIntercambiable(contadorBotConseguidos, contadorRestoConseguidos, vehBotConseguidos, vehRestoConseguidos, numeroJugadores, nombreJug2))
            {
                if (funcionesGenerales.jugadorConMasPuntos(accionesBot.getPuntosBot(nombreJug), restoPuntosJug))
                {
                    if (valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] < 300)
                    {
                        valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] = 300;
                    }
                }
                else
                {
                    if ((double) restoPuntosJug.get(funcionesGenerales.rivalConMasPuntos(restoPuntosJug)) - accionesBot.getPuntosBot(nombreJug) >= 3.0)
                    {
                        if (valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] < 900)
                        {
                            valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] = 900;
                        }
                    }
                    else
                    {
                        if ((double) restoPuntosJug.get(funcionesGenerales.rivalConMasPuntos(restoPuntosJug)) - accionesBot.getPuntosBot(nombreJug) >= 2.0)
                        {
                            if (valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] < 600)
                            {
                                valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] = 600;
                            }
                        }
                        else
                        {
                            if ((double) restoPuntosJug.get(funcionesGenerales.rivalConMasPuntos(restoPuntosJug)) - accionesBot.getPuntosBot(nombreJug) >= 1.0)
                            {
                                if (valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] < 300)
                                {
                                    valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] = 300;
                                }
                            }
                            else
                            {
                                if (valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] < 100)
                                {
                                    valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] = 100;
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                if (valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] <= 0)
                {
                    valorMayorEcharCartaIntercambioVeh[posCarta][nombreJug2][posRestoVehConseguidos] = 0;
                }
            }
        }
    }

    public void sePuedeLanzarIntercambioEquipo(TextView tvCarta, ArrayList probCarta, int posArrayCarta, int posArrayDejarCarta,
                                               AccionesBot accionesBot, int jugBot2, int posRival)
    {
        int jugBot = jugBot2 - 1;
        System.out.println("Entrada2 -> ");

        double ptsBot = accionesBot.getPuntosBot(jugBot);
        double ptsRestoJug;

        valorMayorEcharCartaIntercambioEq[jugBot2] = 0;

        if (posRival == 0)
        {
            ptsRestoJug = accionesBot.getPuntosJug();
        }
        else
        {
            ptsRestoJug = accionesBot.getPuntosBot(posRival - 1);
        }

        System.out.println("Brenda " + jugBot2 + "-> " + ptsBot + " - " + ptsRestoJug);

        String txtBotCarta = (String) tvCarta.getText();

        if ((botTieneCartaConcreta(txtBotCarta, "Intercambiar Brigada")))
        {
            System.out.println("Entrada3 " + jugBot);
            if (ptsRestoJug - ptsBot >= 3)
            {
                System.out.println("Entrada3.1 " + jugBot);
                if (valorMayorEcharCartaIntercambioEq[jugBot2] < 900)
                {
                    probCarta.set(posArrayCarta, 900);
                    probCarta.set(posArrayDejarCarta, 0);
                    valorMayorEcharCartaIntercambioEq[jugBot2] = 900;
                }
            }
            else if (ptsRestoJug - ptsBot >= 2)
            {
                System.out.println("Entrada3.2 " + jugBot);
                if (valorMayorEcharCartaIntercambioEq[jugBot2] < 600)
                {
                    probCarta.set(posArrayCarta, 600);
                    probCarta.set(posArrayDejarCarta, 0);
                    valorMayorEcharCartaIntercambioEq[jugBot2] = 600;
                }
            }
            else if (ptsRestoJug - ptsBot >= 1)
            {
                System.out.println("Entrada3.3 " + jugBot);
                if (valorMayorEcharCartaIntercambioEq[jugBot2] < 300)
                {
                    probCarta.set(posArrayCarta, 300);
                    probCarta.set(posArrayDejarCarta, 0);
                    valorMayorEcharCartaIntercambioEq[jugBot2] = 300;
                }
            }
            else if (ptsRestoJug - ptsBot > 0)
            {
                System.out.println("Entrada3.4 " + jugBot);
                if (valorMayorEcharCartaIntercambioEq[jugBot2] < 100)
                {
                    probCarta.set(posArrayCarta, 100);
                    probCarta.set(posArrayDejarCarta, 0);
                    valorMayorEcharCartaIntercambioEq[jugBot2] = 100;
                }
            }
            else
            {
                System.out.println("Entrada3.5 " + jugBot);
                if (ptsBot - ptsRestoJug >= 3)
                {
                    if (valorMayorEcharCartaIntercambioEq[jugBot2] <= 0)
                    {
                        System.out.println("Entrada3.5.1 " + jugBot);
                        probCarta.set(posArrayCarta, 0);
                        probCarta.set(posArrayDejarCarta, 100);
                        valorMayorEcharCartaIntercambioEq[jugBot2] = 0;
                    }
                }
                else if (ptsBot - ptsRestoJug >= 2)
                {
                    if (valorMayorEcharCartaIntercambioEq[jugBot2] <= 0)
                    {
                        System.out.println("Entrada3.5.2 " + jugBot);
                        probCarta.set(posArrayCarta, 0);
                        probCarta.set(posArrayDejarCarta, 100);
                        valorMayorEcharCartaIntercambioEq[jugBot2] = 0;
                    }
                }
                else if (ptsBot - ptsRestoJug >= 1)
                {
                    if (valorMayorEcharCartaIntercambioEq[jugBot2] <= 0)
                    {
                        System.out.println("Entrada3.5.3 " + jugBot);
                        probCarta.set(posArrayCarta, 0);
                        probCarta.set(posArrayDejarCarta, 100);
                        valorMayorEcharCartaIntercambioEq[jugBot2] = 0;
                    }
                }
                else
                {
                    if (valorMayorEcharCartaIntercambioEq[jugBot2] <= 0)
                    {
                        System.out.println("Entrada3.5.4 " + jugBot);
                        probCarta.set(posArrayCarta, 0);
                        probCarta.set(posArrayDejarCarta, 100);
                        valorMayorEcharCartaIntercambioEq[jugBot2] = 0;
                    }
                }
            }
        }
    }

    public void sePuedeLanzarRoboVehiculo(TextView tvCarta, AccionesBot accionesBot, int jugBot2, int posRival2)
    {
        int jugBot = jugBot2 - 1;
        int posRival = posRival2 - 1;

        String txtBotCarta = (String) tvCarta.getText();

        System.out.println("Amor -> " + jugBot + " - " + posRival);

        valorMayorEcharCartaRoboVeh[jugBot2][posRival2] = 0;

        if ((botTieneCartaConcreta(txtBotCarta, "Robar Vehículo")))
        {
            if (accionesBot.getNumBotVehConseguidos(jugBot) < 4)
            {
                boolean arrVehBotConseguidos[] = new boolean[]{
                        accionesBot.getVehBotConseguido(((jugBot) * 5) + 0) , accionesBot.getVehBotConseguido(((jugBot) * 5) + 1),
                        accionesBot.getVehBotConseguido(((jugBot) * 5) + 2), accionesBot.getVehBotConseguido(((jugBot) * 5) + 3),
                        accionesBot.getVehBotConseguido(((jugBot) * 5) + 4)};

                boolean arrVehRestoJugConseguidos[];
                if (posRival2 == 0)
                {
                    arrVehRestoJugConseguidos = new boolean[]{
                        accionesBot.getVeh1JugConseguido(), accionesBot.getVeh2JugConseguido(), accionesBot.getVeh3JugConseguido(),
                        accionesBot.getVeh4JugConseguido(), accionesBot.getVehMulticolorJugConseguido()};
                }
                else
                {
                    arrVehRestoJugConseguidos = new boolean[]{
                            accionesBot.getVehBotConseguido((posRival * 5) + 0), accionesBot.getVehBotConseguido((posRival * 5) + 1),
                            accionesBot.getVehBotConseguido((posRival * 5) + 2), accionesBot.getVehBotConseguido((posRival * 5) + 3),
                            accionesBot.getVehBotConseguido((posRival * 5) + 4)};
                }

                int arrContadVehRestoJug[];
                if (posRival2 == 0)
                {
                    arrContadVehRestoJug = new int[]{
                            accionesBot.getContadorJugBatallon(), accionesBot.getContadorJugTanque(),
                            accionesBot.getContadorJugSubmarino(), accionesBot.getContadorJugCaza(),
                            accionesBot.getContadorJugVehMulticolor()};
                }
                else
                {
                    arrContadVehRestoJug = new int[]{
                            accionesBot.getContadorBotBatallon(posRival), accionesBot.getContadorBotTanque(posRival),
                            accionesBot.getContadorBotSubmarino(posRival), accionesBot.getContadorBotCaza(posRival),
                            accionesBot.getContadorBotVehMulticolor(posRival)};
                }

                double ptsBot = accionesBot.getPuntosBot(jugBot);

                double ptsRestoJug;
                if (posRival2 == 0)
                {
                    ptsRestoJug = accionesBot.getPuntosJug();
                }
                else
                {
                    ptsRestoJug = accionesBot.getPuntosBot(posRival);
                }

                boolean jugTieneVehiculoQueYoNoTengo = false;

                for (int i = 0; i < arrVehBotConseguidos.length; i++)
                {
                    if (!arrVehBotConseguidos[i] && arrVehRestoJugConseguidos[i])
                    {
                        jugTieneVehiculoQueYoNoTengo = true;
                        if (arrContadVehRestoJug[i] == 0)
                        {
                            if (ptsBot >= ptsRestoJug)
                            {
                                if (valorMayorEcharCartaRoboVeh[jugBot2][posRival2] < 600)
                                {
                                    valorMayorEcharCartaRoboVeh[jugBot2][posRival2] = 600;
                                }
                            }
                            else
                            {
                                if (valorMayorEcharCartaRoboVeh[jugBot2][posRival2] < 900)
                                {
                                    valorMayorEcharCartaRoboVeh[jugBot2][posRival2] = 900;
                                }
                            }
                        }
                        else if (arrContadVehRestoJug[i] == 1)
                        {
                            if (valorMayorEcharCartaRoboVeh[jugBot2][posRival2] < 900)
                            {
                                valorMayorEcharCartaRoboVeh[jugBot2][posRival2] = 900;
                            }
                        }
                        else if (arrContadVehRestoJug[i] == -1)
                        {
                            if (ptsBot >= ptsRestoJug)
                            {
                                if (valorMayorEcharCartaRoboVeh[jugBot2][posRival2] < 100)
                                {
                                    valorMayorEcharCartaRoboVeh[jugBot2][posRival2] = 100;
                                }
                            }
                            else
                            {
                                if (ptsRestoJug - ptsBot >= 3)
                                {
                                    if (valorMayorEcharCartaRoboVeh[jugBot2][posRival2] < 900)
                                    {
                                        valorMayorEcharCartaRoboVeh[jugBot2][posRival2] = 900;
                                    }
                                }
                                else if (ptsRestoJug - ptsBot >= 2)
                                {
                                    if (valorMayorEcharCartaRoboVeh[jugBot2][posRival2] < 600)
                                    {
                                        valorMayorEcharCartaRoboVeh[jugBot2][posRival2] = 600;
                                    }
                                }
                                else
                                {
                                    if (valorMayorEcharCartaRoboVeh[jugBot2][posRival2] < 300)
                                    {
                                        valorMayorEcharCartaRoboVeh[jugBot2][posRival2] = 300;
                                    }
                                }
                            }
                        }
                        else if (arrContadVehRestoJug[i] == 2)
                        {
                            if (valorMayorEcharCartaRoboVeh[jugBot2][posRival2] <= 0)
                            {
                                valorMayorEcharCartaRoboVeh[jugBot2][posRival2] = 0;
                            }
                        }
                    }
                }

                if (!jugTieneVehiculoQueYoNoTengo)
                {
                    if (valorMayorEcharCartaRoboVeh[jugBot2][posRival2] <= 0)
                    {
                        valorMayorEcharCartaRoboVeh[jugBot2][posRival2] = 0;
                    }
                }
            }
        }
    }

    // Método que comprobará si el BOT puede dejar un vehículo en algún casillero.
    public void sePuedeLanzarVehiculo(TextView tvCarta, ArrayList probCarta, int posArrayCarta, int posArrayDejarCarta,
                                      AccionesBot accionesBot, int nombreJug2)
    {
        int nombreJug = nombreJug2 - 1;

        // Recogemos el texto de cada carta del BOT
        String txtBotCarta = (String) tvCarta.getText();

        System.out.println("Dorian -> " + (((nombreJug2) * 5) + 0));

        // Comprobamos si entre sus cartas el BOT tiene algún vehículo que no esté ya en el tablero
        // Si podemos lanzar un vehículo al tablero la probabilidad de dejar carta será 0. Si el
        // vehículo ya está en el tablero podremos descartar la carta.
        if (botTieneCartaConcreta(txtBotCarta, "Batallon"))
        {
            if (!accionesBot.getVehBotConseguido(((nombreJug) * 5) + 0))
            {
                probCarta.set(posArrayCarta, 600);
                probCarta.set(posArrayDejarCarta, 0);
            }
            else
            {
                if (accionesBot.getContadorBotBatallon((nombreJug)) == 2)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else if (accionesBot.getContadorBotBatallon((nombreJug)) == 1)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else if (accionesBot.getContadorBotBatallon((nombreJug)) == 0)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 50);
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Tanque"))
        {
            if (!accionesBot.getVehBotConseguido(((nombreJug) * 5) + 1))
            {
                probCarta.set(posArrayCarta, 600);
                probCarta.set(posArrayDejarCarta, 0);
            }
            else
            {
                if (accionesBot.getContadorBotTanque((nombreJug)) == 2)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else if (accionesBot.getContadorBotTanque((nombreJug)) == 1)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else if (accionesBot.getContadorBotTanque((nombreJug)) == 0)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 50);
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Submarino"))
        {
            if (!accionesBot.getVehBotConseguido(((nombreJug) * 5) + 2))
            {
                probCarta.set(posArrayCarta, 600);
                probCarta.set(posArrayDejarCarta, 0);
            }
            else
            {
                if (accionesBot.getContadorBotSubmarino((nombreJug)) == 2)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else if (accionesBot.getContadorBotSubmarino((nombreJug)) == 1)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else if (accionesBot.getContadorBotSubmarino((nombreJug)) == 0)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 50);
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Caza"))
        {
            if (!accionesBot.getVehBotConseguido(((nombreJug) * 5) + 3))
            {
                probCarta.set(posArrayCarta, 600);
                probCarta.set(posArrayDejarCarta, 0);
            }
            else
            {
                if (accionesBot.getContadorBotCaza((nombreJug)) == 2)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else if (accionesBot.getContadorBotCaza((nombreJug)) == 1)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else if (accionesBot.getContadorBotCaza((nombreJug)) == 0)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 50);
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Vehículo Multicolor"))
        {
            if (!accionesBot.getVehBotConseguido(((nombreJug) * 5) + 4))
            {
                probCarta.set(posArrayCarta, 600);
                probCarta.set(posArrayDejarCarta, 0);
            }
            else
            {
                if (accionesBot.getContadorBotVehMulticolor((nombreJug)) == 2)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else if (accionesBot.getContadorBotVehMulticolor((nombreJug)) == 1)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else if (accionesBot.getContadorBotVehMulticolor((nombreJug)) == 0)
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 100);
                }
                else
                {
                    probCarta.set(posArrayCarta, 0);
                    probCarta.set(posArrayDejarCarta, 50);
                }
            }
        }
    }

    public void sePuedeLanzarArma(int nombreJug, TextView tvCarta, ArrayList restoVehConseguidos, ArrayList restoContadorVeh,
                                  int posRestoVehConseguidos, int posCarta)
    {
        String txtBotCarta = (String) tvCarta.getText();

        System.out.println("Quiles -> /*/*/*/*/*/*" + nombreJug + " - " + posRestoVehConseguidos);
        for (int i = 0; i < restoVehConseguidos.size(); i++)
        {
            System.out.println("Quiles -> restoVehConseguidos" + restoVehConseguidos.get(i));
            System.out.println("Quiles -> restoContadorVeh" + restoContadorVeh.get(i));
        }
        System.out.println("Quiles -> ************************************");

        // Primero comprobamos de que tipo de Medicina es la carta.
        if (botTieneCartaConcreta(txtBotCarta, "Metralleta"))
        {
            // Si aún no tenemos en el tablero los vehículos sobre los que poder echar la Medicina la
            // carta no podrá ser echada.
            if (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 0).equals(false)
                    && restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(false))
            {
                if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] <= 0)
                {
                    System.out.println("Tema0 - Batallon" + posRestoVehConseguidos);
                    valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 0;
                }
            }
            else
            {
                // Si la carta se puede echar sobre un vehículo primero comprobamos si hay prioridad muy alta,
                // es decir, si algún vehículo está infectado.
                if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 0).equals(true)
                        && restoContadorVeh.get(posRestoVehConseguidos * 5 + 0).equals(-1))
                        || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                        && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(-1)
                        && (funcionesGenerales.tipoVehiculoMulticolor(
                        tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 0
                        || funcionesGenerales.tipoVehiculoMulticolor(
                        tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 4)))
                {
                    if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 600)
                    {
                        valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 600;
                    }
                }
                else
                {
                    // En segundo lugar, comprobamos si hay algún vehículo medicado susceptible de poder ser
                    // inmunizado. Esta carta tendrá una prioridad alta.
                    if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 0).equals(true)
                            && restoContadorVeh.get(posRestoVehConseguidos * 5 + 0).equals(1))
                            || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                            && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(1)
                            && (funcionesGenerales.tipoVehiculoMulticolor(
                            tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 0
                            || funcionesGenerales.tipoVehiculoMulticolor(
                            tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 4)))
                    {
                        if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 300)
                        {
                            valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 300;
                        }
                    }
                    else
                    {
                        // Si tenemos alguna carta limpia está tendrá una prioridad normal.
                        if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 0).equals(true)
                                && restoContadorVeh.get(posRestoVehConseguidos * 5 + 0).equals(0))
                                || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                                && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(0)))
                        {
                            if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 200)
                            {
                                valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 200;
                            }
                        }
                        else
                        {
                            // Si no tenemos ningún vehículo limpio, medicado o infectado significará que estará inmunizado
                            // por lo que la carta nunca podrá ser echada.

                            if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] <= 0)
                            {
                                System.out.println("Tema1");
                                valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 0;
                            }
                        }
                    }
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Lanzacohetes"))
        {
            // Si aún no tenemos en el tablero los vehículos sobre los que poder echar la Medicina la
            // carta no podrá ser echada.
            if (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 1).equals(false)
                    && restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(false))
            {
                if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] <= 0)
                {
                    valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 0;
                }
            }
            else
            {
                // Si la carta se puede echar sobre un vehículo primero comprobamos si hay prioridad muy alta,
                // es decir, si algún vehículo está infectado.
                if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 1).equals(true)
                        && restoContadorVeh.get(posRestoVehConseguidos * 5 + 1).equals(-1))
                        || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                        && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(-1)
                        && (funcionesGenerales.tipoVehiculoMulticolor(
                        tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 1
                        || funcionesGenerales.tipoVehiculoMulticolor(
                        tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 4)))
                {
                    if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 600)
                    {
                        valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 600;
                    }
                }
                else
                {
                    // En segundo lugar, comprobamos si hay algún vehículo medicado susceptible de poder ser
                    // inmunizado. Esta carta tendrá una prioridad alta.
                    if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 1).equals(true)
                            && restoContadorVeh.get(posRestoVehConseguidos * 5 + 1).equals(1))
                            || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                            && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(1)
                            && (funcionesGenerales.tipoVehiculoMulticolor(
                            tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 1
                            || funcionesGenerales.tipoVehiculoMulticolor(
                            tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 4)))
                    {
                        if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 300)
                        {
                            valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 300;
                        }
                    }
                    else
                    {
                        // Si tenemos alguna carta limpia está tendrá una prioridad normal.
                        if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 1).equals(true)
                                && restoContadorVeh.get(posRestoVehConseguidos * 5 + 1).equals(0))
                                || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                                && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(0)))
                        {
                            if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 200)
                            {
                                valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 200;
                            }
                        }
                        else
                        {
                            // Si no tenemos ningún vehículo limpio, medicado o infectado significará que estará inmunizado
                            // por lo que la carta nunca podrá ser echada.

                            if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] <= 0)
                            {
                                valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 0;
                            }
                        }
                    }
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Torpedos"))
        {
            // Si aún no tenemos en el tablero los vehículos sobre los que poder echar la Medicina la
            // carta no podrá ser echada.
            if (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 2).equals(false)
                    && restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(false))
            {
                if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] <= 0)
                {
                    valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 0;
                }
            }
            else
            {
                // Si la carta se puede echar sobre un vehículo primero comprobamos si hay prioridad muy alta,
                // es decir, si algún vehículo está infectado.
                if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 2).equals(true)
                        && restoContadorVeh.get(posRestoVehConseguidos * 5 + 2).equals(-1))
                        || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                        && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(-1)
                        && (funcionesGenerales.tipoVehiculoMulticolor(
                        tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 2
                        || funcionesGenerales.tipoVehiculoMulticolor(
                        tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 4)))
                {
                    if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 600)
                    {
                        valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 600;
                    }
                }
                else
                {
                    // En segundo lugar, comprobamos si hay algún vehículo medicado susceptible de poder ser
                    // inmunizado. Esta carta tendrá una prioridad alta.
                    if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 2).equals(true)
                            && restoContadorVeh.get(posRestoVehConseguidos * 5 + 2).equals(1))
                            || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                            && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(1)
                            && (funcionesGenerales.tipoVehiculoMulticolor(
                            tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 2
                            || funcionesGenerales.tipoVehiculoMulticolor(
                            tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 4)))
                    {
                        if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 300)
                        {
                            valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 300;
                        }
                    }
                    else
                    {
                        // Si tenemos alguna carta limpia está tendrá una prioridad normal.
                        if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 2).equals(true)
                                && restoContadorVeh.get(posRestoVehConseguidos * 5 + 2).equals(0))
                                || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                                && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(0)))
                        {
                            if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 200)
                            {
                                valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 200;
                            }
                        }
                        else
                        {
                            // Si no tenemos ningún vehículo limpio, medicado o infectado significará que estará inmunizado
                            // por lo que la carta nunca podrá ser echada.

                            if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] <= 0)
                            {
                                valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 0;
                            }
                        }
                    }
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Misiles"))
        {
            // Si aún no tenemos en el tablero los vehículos sobre los que poder echar la Medicina la
            // carta no podrá ser echada.
            if (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 3).equals(false)
                    && restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(false))
            {
                if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] <= 0)
                {
                    valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 0;
                }
            }
            else
            {
                // Si la carta se puede echar sobre un vehículo primero comprobamos si hay prioridad muy alta,
                // es decir, si algún vehículo está infectado.
                if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 3).equals(true)
                        && restoContadorVeh.get(posRestoVehConseguidos * 5 + 3).equals(-1))
                        || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                        && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(-1)
                        && (funcionesGenerales.tipoVehiculoMulticolor(
                        tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 3
                        || funcionesGenerales.tipoVehiculoMulticolor(
                        tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 4)))
                {
                    if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 600)
                    {
                        valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 600;
                    }
                }
                else
                {
                    // En segundo lugar, comprobamos si hay algún vehículo medicado susceptible de poder ser
                    // inmunizado. Esta carta tendrá una prioridad alta.
                    if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 3).equals(true)
                            && restoContadorVeh.get(posRestoVehConseguidos * 5 + 3).equals(1))
                            || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                            && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(1)
                            && (funcionesGenerales.tipoVehiculoMulticolor(
                            tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 3
                            || funcionesGenerales.tipoVehiculoMulticolor(
                            tvVehiculosJugadores, tvComodinesJugadores, posRestoVehConseguidos) == 4)))
                    {
                        if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 300)
                        {
                            valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 300;
                        }
                    }
                    else
                    {
                        // Si tenemos alguna carta limpia está tendrá una prioridad normal.
                        if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 3).equals(true)
                                && restoContadorVeh.get(posRestoVehConseguidos * 5 + 3).equals(0))
                                || (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(true)
                                && restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(0)))
                        {
                            if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 200)
                            {
                                valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 200;
                            }
                        }
                        else
                        {
                            // Si no tenemos ningún vehículo limpio, medicado o infectado significará que estará inmunizado
                            // por lo que la carta nunca podrá ser echada.

                            if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] <= 0)
                            {
                                valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 0;
                            }
                        }
                    }
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Arma Multicolor"))
        {
            if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 0).equals(false))
                    && (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 1).equals(false))
                    && (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 2).equals(false))
                    && (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 3).equals(false))
                    && (restoVehConseguidos.get(posRestoVehConseguidos * 5 + 4).equals(false)))
            {
                if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] <= 0)
                {
                    valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 0;
                }
            }
            else
            {
                if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 0).equals(-1)) ||
                        (restoContadorVeh.get(posRestoVehConseguidos * 5 + 1).equals(-1))  ||
                        (restoContadorVeh.get(posRestoVehConseguidos * 5 + 2).equals(-1))  ||
                        (restoContadorVeh.get(posRestoVehConseguidos * 5 + 3).equals(-1))  ||
                        (restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(-1)))
                {
                    if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 600)
                    {
                        valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 600;
                    }
                }
                else
                {
                    if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 0).equals(1)) ||
                            (restoContadorVeh.get(posRestoVehConseguidos * 5 + 1).equals(1))  ||
                            (restoContadorVeh.get(posRestoVehConseguidos * 5 + 2).equals(1))  ||
                            (restoContadorVeh.get(posRestoVehConseguidos * 5 + 3).equals(1))  ||
                            (restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(1)))
                    {
                        if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 300)
                        {
                            valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 300;
                        }
                    }
                    else
                    {
                        if ((restoVehConseguidos.get(posRestoVehConseguidos * 5 + 0).equals(0)) ||
                                (restoContadorVeh.get(posRestoVehConseguidos * 5 + 1).equals(0))  ||
                                (restoContadorVeh.get(posRestoVehConseguidos * 5 + 2).equals(0))  ||
                                (restoContadorVeh.get(posRestoVehConseguidos * 5 + 3).equals(0))  ||
                                (restoContadorVeh.get(posRestoVehConseguidos * 5 + 4).equals(0)))
                        {
                            if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] < 100)
                            {
                                valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 100;
                            }
                        }
                        else
                        {
                            if (valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] <= 100)
                            {
                                valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos] = 0;
                            }
                        }
                    }
                }
            }
        }



        System.out.println("Quiles -> " + txtBotCarta);
        System.out.println("Quilesss -> Carta" + posCarta + ": Jugador" + nombreJug + " sobre Jugador" + posRestoVehConseguidos + " - " + valorMayorEcharCartaArma[posCarta][nombreJug][posRestoVehConseguidos]);
        //valorMayorEcharCartaArma[posRestoVehConseguidos] = 0;
    }

    // Comprobamos si la carta de Medicina se puede echar o no.
    public void sePuedeLanzarMedicina(TextView tvCarta, ArrayList probCarta, int posArrayCarta, int posArrayDejarCarta,
                                      AccionesBot accionesBot, int nombreJug2)
    {
        int nombreJug = nombreJug2 - 1;
        System.out.println("Supersonic -> " + nombreJug);
        String txtBotCarta = (String) tvCarta.getText();

        // Primero comprobamos de que tipo de Medicina es la carta.
        if (botTieneCartaConcreta(txtBotCarta, "Medicina1"))
        {
            // Si aún no tenemos en el tablero los vehículos sobre los que poder echar la Medicina la
            // carta no podrá ser echada.
            if (!accionesBot.getVehBotConseguido((nombreJug) * 5 + 0) && !accionesBot.getVehBotConseguido((nombreJug) * 5 + 4))
            {
                probCarta.set(posArrayCarta, 0);
                probCarta.set(posArrayDejarCarta, 100);
            }
            else
            {
                // Si la carta se puede echar sobre un vehículo primero comprobamos si hay prioridad muy alta,
                // es decir, si algún vehículo está infectado.
                if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 0) && accionesBot.contadorBotBatallon[(nombreJug)] == -1) ||
                        (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == -1))
                {
                    probCarta.set(posArrayCarta, 600);
                    probCarta.set(posArrayDejarCarta, 0);
                }
                else
                {
                    // En segundo lugar, comprobamos si hay algún vehículo medicado susceptible de poder ser
                    // inmunizado. Esta carta tendrá una prioridad alta.
                    if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 0) && accionesBot.contadorBotBatallon[(nombreJug)] == 1) ||
                            (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == 1))
                    {
                        probCarta.set(posArrayCarta, 300);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                    else
                    {
                        // Si tenemos alguna carta limpia está tendrá una prioridad normal.
                        if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 0) && accionesBot.contadorBotBatallon[(nombreJug)] == 0) ||
                                (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == 0))
                        {
                            probCarta.set(posArrayCarta, 200);
                            probCarta.set(posArrayDejarCarta, 0);
                        }
                        else
                        {
                            // Si no tenemos ningún vehículo limpio, medicado o infectado significará que estará inmunizado
                            // por lo que la carta nunca podrá ser echada.

                            probCarta.set(posArrayCarta, 0);
                            probCarta.set(posArrayDejarCarta, 100);
                        }
                    }
                }
            }

        }
        else if (botTieneCartaConcreta(txtBotCarta, "Medicina2"))
        {
            // Si aún no tenemos en el tablero los vehículos sobre los que poder echar la Medicina la
            // carta no podrá ser echada.
            if (!accionesBot.getVehBotConseguido((nombreJug) * 5 + 1) && !accionesBot.getVehBotConseguido((nombreJug) * 5 + 4))
            {
                probCarta.set(posArrayCarta, 0);
                probCarta.set(posArrayDejarCarta, 100);
            }
            else
            {
                // Si la carta se puede echar sobre un vehículo primero comprobamos si hay prioridad muy alta,
                // es decir, si algún vehículo está infectado.
                if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 1) && accionesBot.contadorBotTanque[(nombreJug)] == -1) ||
                        (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == -1))
                {
                    probCarta.set(posArrayCarta, 600);
                    probCarta.set(posArrayDejarCarta, 0);
                }
                else
                {
                    // En segundo lugar, comprobamos si hay algún vehículo medicado susceptible de poder ser
                    // inmunizado. Esta carta tendrá una prioridad alta.
                    if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 1) && accionesBot.contadorBotTanque[(nombreJug)] == 1) ||
                            (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == 1))
                    {
                        probCarta.set(posArrayCarta, 300);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                    else
                    {
                        // Si tenemos alguna carta limpia está tendrá una prioridad normal.
                        if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 1) && accionesBot.contadorBotTanque[(nombreJug)] == 0) ||
                                (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == 0))
                        {
                            probCarta.set(posArrayCarta, 200);
                            probCarta.set(posArrayDejarCarta, 0);
                        }
                        else
                        {
                            // Si no tenemos ningún vehículo limpio, medicado o infectado significará que estará inmunizado
                            // por lo que la carta nunca podrá ser echada.

                            probCarta.set(posArrayCarta, 0);
                            probCarta.set(posArrayDejarCarta, 100);
                        }
                    }
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Medicina3"))
        {
            // Si aún no tenemos en el tablero los vehículos sobre los que poder echar la Medicina la
            // carta no podrá ser echada.
            if (!accionesBot.getVehBotConseguido((nombreJug) * 5 + 2) && !accionesBot.getVehBotConseguido((nombreJug) * 5 + 4))
            {
                probCarta.set(posArrayCarta, 0);
                probCarta.set(posArrayDejarCarta, 100);
            }
            else
            {
                // Si la carta se puede echar sobre un vehículo primero comprobamos si hay prioridad muy alta,
                // es decir, si algún vehículo está infectado.
                if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 2) && accionesBot.contadorBotSubmarino[(nombreJug)] == -1) ||
                        (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == -1))
                {
                    probCarta.set(posArrayCarta, 600);
                    probCarta.set(posArrayDejarCarta, 0);
                }
                else
                {
                    // En segundo lugar, comprobamos si hay algún vehículo medicado susceptible de poder ser
                    // inmunizado. Esta carta tendrá una prioridad alta.
                    if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 2) && accionesBot.contadorBotSubmarino[(nombreJug)] == 1) ||
                            (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == 1))
                    {
                        probCarta.set(posArrayCarta, 300);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                    else
                    {
                        // Si tenemos alguna carta limpia está tendrá una prioridad normal.
                        if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 2) && accionesBot.contadorBotSubmarino[(nombreJug)] == 0) ||
                                (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == 0))
                        {
                            probCarta.set(posArrayCarta, 200);
                            probCarta.set(posArrayDejarCarta, 0);
                        }
                        else
                        {
                            // Si no tenemos ningún vehículo limpio, medicado o infectado significará que estará inmunizado
                            // por lo que la carta nunca podrá ser echada.

                            probCarta.set(posArrayCarta, 0);
                            probCarta.set(posArrayDejarCarta, 100);
                        }
                    }
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Medicina4"))
        {
            // Si aún no tenemos en el tablero los vehículos sobre los que poder echar la Medicina la
            // carta no podrá ser echada.
            if (!accionesBot.getVehBotConseguido((nombreJug) * 5 + 3) && !accionesBot.getVehBotConseguido((nombreJug) * 5 + 4))
            {
                probCarta.set(posArrayCarta, 0);
                probCarta.set(posArrayDejarCarta, 100);
            }
            else
            {
                // Si la carta se puede echar sobre un vehículo primero comprobamos si hay prioridad muy alta,
                // es decir, si algún vehículo está infectado.
                if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 3) && accionesBot.contadorBotCaza[(nombreJug)] == -1) ||
                        (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == -1))
                {
                    probCarta.set(posArrayCarta, 600);
                    probCarta.set(posArrayDejarCarta, 0);
                }
                else
                {
                    // En segundo lugar, comprobamos si hay algún vehículo medicado susceptible de poder ser
                    // inmunizado. Esta carta tendrá una prioridad alta.
                    if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 3) && accionesBot.contadorBotCaza[(nombreJug)] == 1) ||
                            (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == 1))
                    {
                        probCarta.set(posArrayCarta, 300);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                    else
                    {
                        // Si tenemos alguna carta limpia está tendrá una prioridad normal.
                        if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 3) && accionesBot.contadorBotCaza[(nombreJug)] == 0) ||
                                (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == 0))
                        {
                            probCarta.set(posArrayCarta, 200);
                            probCarta.set(posArrayDejarCarta, 0);
                        }
                        else
                        {
                            // Si no tenemos ningún vehículo limpio, medicado o infectado significará que estará inmunizado
                            // por lo que la carta nunca podrá ser echada.

                            probCarta.set(posArrayCarta, 0);
                            probCarta.set(posArrayDejarCarta, 100);
                        }
                    }
                }
            }
        }
        else if (botTieneCartaConcreta(txtBotCarta, "Medicina Multicolor"))
        {
            if (!accionesBot.getVehBotConseguido((nombreJug) * 5 + 0) && !accionesBot.getVehBotConseguido((nombreJug) * 5 + 1) &&
                    !accionesBot.getVehBotConseguido((nombreJug) * 5 + 2) && !accionesBot.getVehBotConseguido((nombreJug) * 5 + 3) &&
                    !accionesBot.getVehBotConseguido((nombreJug) * 5 + 4))
            {
                probCarta.set(posArrayCarta, 0);
                probCarta.set(posArrayDejarCarta, 100);
            }
            else
            {
                if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 0) && accionesBot.contadorBotBatallon[(nombreJug)] == -1) ||
                        (accionesBot.getVehBotConseguido((nombreJug) * 5 + 1) && accionesBot.contadorBotTanque[(nombreJug)] == -1) ||
                        (accionesBot.getVehBotConseguido((nombreJug) * 5 + 2) && accionesBot.contadorBotSubmarino[(nombreJug)] == -1) ||
                        (accionesBot.getVehBotConseguido((nombreJug) * 5 + 3) && accionesBot.contadorBotCaza[(nombreJug)] == -1) ||
                        (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == -1))
                {
                    probCarta.set(posArrayCarta, 600);
                    probCarta.set(posArrayDejarCarta, 0);
                }
                else
                {
                    if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 0) && accionesBot.contadorBotBatallon[(nombreJug)] == 1) ||
                            (accionesBot.getVehBotConseguido((nombreJug) * 5 + 1) && accionesBot.contadorBotTanque[(nombreJug)] == 1) ||
                            (accionesBot.getVehBotConseguido((nombreJug) * 5 + 2) && accionesBot.contadorBotSubmarino[(nombreJug)] == 1) ||
                            (accionesBot.getVehBotConseguido((nombreJug) * 5 + 3) && accionesBot.contadorBotCaza[(nombreJug)] == 1) ||
                            (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == 1))
                    {
                        probCarta.set(posArrayCarta, 300);
                        probCarta.set(posArrayDejarCarta, 0);
                    }
                    else
                    {
                        if ((accionesBot.getVehBotConseguido((nombreJug) * 5 + 0) && accionesBot.contadorBotBatallon[(nombreJug)] == 0) ||
                                (accionesBot.getVehBotConseguido((nombreJug) * 5 + 1) && accionesBot.contadorBotTanque[(nombreJug)] == 0) ||
                                (accionesBot.getVehBotConseguido((nombreJug) * 5 + 2) && accionesBot.contadorBotSubmarino[(nombreJug)] == 0) ||
                                (accionesBot.getVehBotConseguido((nombreJug) * 5 + 3) && accionesBot.contadorBotCaza[(nombreJug)] == 0) ||
                                (accionesBot.getVehBotConseguido((nombreJug) * 5 + 4) && accionesBot.contadorBotVehMulticolor[(nombreJug)] == 0))
                        {
                            probCarta.set(posArrayCarta, 200);
                            probCarta.set(posArrayDejarCarta, 0);
                        }
                        else
                        {
                            probCarta.set(posArrayCarta, 0);
                            probCarta.set(posArrayDejarCarta, 100);
                        }
                    }
                }
            }
        }
    }

    // Comprobamos si la carta corresponde a algún vehículo.
    private boolean botTieneCartaConcreta(String txtBotCarta, String txtVehiculo)
    {
        if (txtBotCarta.equals(txtVehiculo))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int posValorMayorEcharCartaArma(int numCarta, int nombreJug)
    {
        int numRandom = (int) Math.floor(Math.random() * (0 - 1 + 1) + 1);
        System.out.println("QuilessRandom -> " + numRandom);

        int mayorValorMayorEcharCartaArma = 0;

        if (numRandom == 0)
        {
            for (int j = 0; j < valorMayorEcharCartaArma[numCarta][nombreJug].length; j++)
            {
                if (valorMayorEcharCartaArma[numCarta][nombreJug][j] > mayorValorMayorEcharCartaArma)
                {
                    mayorValorMayorEcharCartaArma = j;
                }
            }
        }
        else
        {
            for (int j = valorMayorEcharCartaArma[numCarta][nombreJug].length - 1; j >= 0; j--)
            {
                if (valorMayorEcharCartaArma[numCarta][nombreJug][j] > mayorValorMayorEcharCartaArma)
                {
                    mayorValorMayorEcharCartaArma = j;
                }
            }
        }

        System.out.println("Quiless -> " + valorMayorEcharCartaArma[numCarta][nombreJug][mayorValorMayorEcharCartaArma] + " - " + mayorValorMayorEcharCartaArma);

        return mayorValorMayorEcharCartaArma;
    }

    public int posValorMayorEcharCartaArma2(int numCarta, int nombreJug)
    {
        int mayorValorMayorEcharCartaArma = 0;
        int jota = 0;

        for (int j = 0; j < valorMayorEcharCartaArma[numCarta][nombreJug].length; j++)
        {
            if (valorMayorEcharCartaArma[numCarta][nombreJug][j] > mayorValorMayorEcharCartaArma)
            {
                mayorValorMayorEcharCartaArma = valorMayorEcharCartaArma[numCarta][nombreJug][j];
                jota = j;
            }
        }

        System.out.println("Rota -> " + valorMayorEcharCartaArma[numCarta][nombreJug][jota] + " - " + mayorValorMayorEcharCartaArma);

        return mayorValorMayorEcharCartaArma;
    }

    /*public int posValorMayorEcharCartaIntercambioVeh(AccionesBot accionesBot, int nombreJug)
    {

        int mayorEcharCartaIntercambioVeh = 0;
        int mayorValorEcharCartaIntercambioVeh = 0;

        for (int i = 0; i < valorMayorEcharCartaIntercambioVeh[numCarta][nombreJug].length; i++)
        {
            System.out.println("QuilessIntercillo1 -> " + numCarta + ":" + nombreJug + ":" + i + ": " + valorMayorEcharCartaIntercambioVeh[numCarta][nombreJug][i]);

            if (valorMayorEcharCartaIntercambioVeh[numCarta][nombreJug][i] > mayorValorEcharCartaIntercambioVeh)
            {
                mayorValorEcharCartaIntercambioVeh = valorMayorEcharCartaIntercambioVeh[numCarta][nombreJug][i];
                mayorEcharCartaIntercambioVeh = i;
            }
        }

        System.out.println("QuilessInter1 -> " + valorMayorEcharCartaIntercambioVeh[numCarta][nombreJug][mayorEcharCartaIntercambioVeh] + " - " + mayorEcharCartaIntercambioVeh);

        return mayorEcharCartaIntercambioVeh;
    }*/

    public int posValorMayorEcharCartaIntercambioVeh2(int numCarta, int nombreJug)
    {
        int mayorEcharCartaIntercambioVeh = 0;
        int jota = 0;

        for (int j = 0; j < valorMayorEcharCartaIntercambioVeh[numCarta][nombreJug].length; j++)
        {
            if (valorMayorEcharCartaIntercambioVeh[numCarta][nombreJug][j] > mayorEcharCartaIntercambioVeh)
            {
                mayorEcharCartaIntercambioVeh = valorMayorEcharCartaIntercambioVeh[numCarta][nombreJug][j];
                jota = j;
            }
        }

        System.out.println("QuilessInter2 -> " + valorMayorEcharCartaIntercambioVeh[numCarta][nombreJug][jota] + " - " + mayorEcharCartaIntercambioVeh);

        return mayorEcharCartaIntercambioVeh;
    }

    public int posValorMayorEcharCartaRoboVeh(int nombreJug)
    {
        int mayorEcharCartaRoboVeh = 0;

        for (int i = 0; i < valorMayorEcharCartaRoboVeh[nombreJug].length; i++)
        {
            if (valorMayorEcharCartaRoboVeh[nombreJug][i] > mayorEcharCartaRoboVeh)
            {
                mayorEcharCartaRoboVeh = i;
                System.out.println("Hache -> " + nombreJug + ": " + valorMayorEcharCartaRoboVeh[nombreJug][mayorEcharCartaRoboVeh] + " - " + mayorEcharCartaRoboVeh);
            }
        }

        System.out.println("HacheTodo -> " + valorMayorEcharCartaRoboVeh[nombreJug][mayorEcharCartaRoboVeh] + " - " + mayorEcharCartaRoboVeh);

        return mayorEcharCartaRoboVeh;
    }

    public int posValorMayorEcharCartaRoboVeh2(int nombreJug)
    {
        int mayorEcharCartaRoboVeh = 0;

        for (int i = 0; i < valorMayorEcharCartaRoboVeh[nombreJug].length; i++)
        {
            if (valorMayorEcharCartaRoboVeh[nombreJug][i] > mayorEcharCartaRoboVeh)
            {
                mayorEcharCartaRoboVeh = i;
                System.out.println("Hache -> " + nombreJug + ": " + valorMayorEcharCartaRoboVeh[nombreJug][mayorEcharCartaRoboVeh] + " - " + mayorEcharCartaRoboVeh);
            }
        }

        System.out.println("HacheTodo -> " + valorMayorEcharCartaRoboVeh[nombreJug][mayorEcharCartaRoboVeh] + " - " + mayorEcharCartaRoboVeh);

        return valorMayorEcharCartaRoboVeh[nombreJug][mayorEcharCartaRoboVeh];
    }

    // Método que marcará que carta será elegida para ser lanzada al tablero.
    public void elegirCartaBot(int sumaProb, double limiteCarta1, double limiteCarta2, double limiteCarta3,
                               double limiteCarta4, double limiteCarta5, double limiteCarta6,
                               TextView tvBotCarta1, TextView tvBotCarta2, TextView tvBotCarta3,
                               TextView tvBotVeh1, TextView tvBotVeh2, TextView tvBotVeh3, TextView tvBotVeh4,
                               TextView tvMensajeVictoria, int numTirada, String[] barajaCartas, AccionesBot accionesBot,
                               Context context, int nombreJug, View view)
    {
        System.out.println("Halley0 -> tvVehiculosJugadores:" + ((nombreJug * 5) + 0));

        tvBotCarta1.setBackgroundColor(Color.TRANSPARENT);
        tvBotCarta2.setBackgroundColor(Color.TRANSPARENT);
        tvBotCarta3.setBackgroundColor(Color.TRANSPARENT);

        System.out.println("Dorian" + nombreJug + " -> Bot -> ProbLanzarCarta1: " + limiteCarta1);
        System.out.println("Dorian" + nombreJug + " -> Bot -> ProbLanzarDejarCarta1: " + limiteCarta2);
        System.out.println("Dorian" + nombreJug + " -> Bot -> ProbLanzarCarta2: " + limiteCarta3);
        System.out.println("Dorian" + nombreJug + " -> Bot -> ProbLanzarDejarCarta2: " + limiteCarta4);
        System.out.println("Dorian" + nombreJug + " -> Bot -> ProbLanzarCarta3: " + limiteCarta5);
        System.out.println("Dorian" + nombreJug + " -> Bot -> ProbLanzarDejarCarta3: " + limiteCarta6);

        int valorProb = (int) Math.floor(Math.random() * (0 - sumaProb + 1) + sumaProb);
        System.out.println("Dorian -> VALOR ESCOGIDO de " + sumaProb + ": " + valorProb);

        System.out.println("Navajero -> Suma: " + sumaProb + " -> "
                + limiteCarta1 + " - " + limiteCarta2 + " - " + limiteCarta3 + " - " + limiteCarta4 + " - "
                + limiteCarta5 + " - " + limiteCarta6);

        if (valorProb < limiteCarta1) // No supera el limite0
        {
            carta1Elegida = true;
            System.out.println("Navajero -> carta1Elegida");
            ejecutarAccionCarta(tvBotCarta1, tvBotCarta2, tvBotCarta3, tvBotVeh1, tvBotVeh2, tvBotVeh3, tvBotVeh4,
                    tvMensajeVictoria, numTirada, barajaCartas, accionesBot, context, nombreJug, 0, view);
            tvBotCarta1.setBackgroundColor(Color.MAGENTA);
        }
        else if (valorProb >= limiteCarta1 && valorProb < (limiteCarta1 + limiteCarta2)) // No supera el limite1
        {
            dejarCarta1Elegida = true;
            System.out.println("Navajero -> dejarCarta1Elegida");
            tvBotCarta1.setText(barajaCartas[funcionesGenerales.sacarCarta2()]);
            tvBotCarta1.setBackgroundColor(Color.MAGENTA);

            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarBotCartaNueva));
            funcionesGenerales.reproducirSonidoJuego(context, R.raw.pasarcarta, sonidoJuego);
        }
        else if (valorProb >= (limiteCarta1 + limiteCarta2) && valorProb < (limiteCarta1 + limiteCarta2 + limiteCarta3)) // No supera el limite2
        {
            carta2Elegida = true;
            System.out.println("Navajero -> carta2Elegida");
            ejecutarAccionCarta(tvBotCarta2, tvBotCarta1, tvBotCarta3, tvBotVeh1, tvBotVeh2, tvBotVeh3, tvBotVeh4,
                    tvMensajeVictoria, numTirada, barajaCartas, accionesBot, context, nombreJug, 1, view);
            tvBotCarta2.setBackgroundColor(Color.MAGENTA);
        }
        else if (valorProb >= (limiteCarta1 + limiteCarta2 + limiteCarta3)
                && valorProb < (limiteCarta1 + limiteCarta2 + limiteCarta3 + limiteCarta4)) // No supera el limite3
        {
            dejarCarta2Elegida = true;
            System.out.println("Navajero -> dejarCarta2Elegida");
            tvBotCarta2.setText(barajaCartas[funcionesGenerales.sacarCarta2()]);
            tvBotCarta2.setBackgroundColor(Color.MAGENTA);

            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarBotCartaNueva));
            funcionesGenerales.reproducirSonidoJuego(context, R.raw.pasarcarta, sonidoJuego);
        }
        else if (valorProb >= (limiteCarta1 + limiteCarta2 + limiteCarta3 + limiteCarta4)
                && valorProb < (limiteCarta1 + limiteCarta2 + limiteCarta3 + limiteCarta4 + limiteCarta5))// No supera el limite4
        {
            carta3Elegida = true;
            System.out.println("Navajero -> carta3Elegida");
            ejecutarAccionCarta(tvBotCarta3, tvBotCarta1, tvBotCarta2, tvBotVeh1, tvBotVeh2, tvBotVeh3, tvBotVeh4,
                    tvMensajeVictoria, numTirada, barajaCartas, accionesBot, context, nombreJug, 2, view);
            tvBotCarta3.setBackgroundColor(Color.MAGENTA);
        }
        else
        {
            dejarCarta3Elegida = true;
            System.out.println("Navajero -> dejarCarta3Elegida");
            tvBotCarta3.setText(barajaCartas[funcionesGenerales.sacarCarta2()]);
            tvBotCarta3.setBackgroundColor(Color.MAGENTA);

            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarBotCartaNueva));
            funcionesGenerales.reproducirSonidoJuego(context, R.raw.pasarcarta, sonidoJuego);
        }

        System.out.println("Dorian -> PUNTOSBOT: " + accionesBot.getPuntosBot(nombreJug - 1));
        System.out.println("Dorian -> PUNTOSJUG: " + accionesBot.getPuntosJug());
        System.out.println("Dorian -> *****************************************");

        System.out.println("Navajero -> **************************************************");
    }

    // Método que ejecutará la acción a realizar por la carta lanzada al tablero.
    public void ejecutarAccionCarta(TextView tvCartaPulsada, TextView tvCartaNoPulsada1, TextView tvCartaNoPulsada2,
                                    TextView tvBotVeh1, TextView tvBotVeh2, TextView tvBotVeh3, TextView tvBotVeh4,
                                    TextView tvMensajeVictoria, int numTirada, String[] barajaCartas,
                                    AccionesBot accionesBot, Context context, int nombreJug, int numCarta, View view)
    {
        System.out.println("Halley1 -> tvVehiculosJugadores:" + ((nombreJug * 4) + 0));
        // Si la carta pertenece a un vehículo
        if (relacionarCartaConProbAccion((String) tvCartaPulsada.getText()) == 0)
        {
            // Ejecutamos la acción de la carta vehículo.
            if (lanzarBotVehiculo(tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2, tvMensajeVictoria, numTirada, barajaCartas,
                    accionesBot, nombreJug, context))
            {
                numTirada += 1;

                // Tras lanzar la carta sacamos una nueva carta de la baraja.
                int valorCarta2 = funcionesGenerales.sacarCarta2();
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);
                // Le aplicamos el color a la carta en función del tipo de carta que sea.
                funcionesGenerales.colorCarta(barajaCartas[valorCarta2], tvCartaPulsada);
                // Comprobamos si la baraja está ya agotada.
                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);
            }
        }
        else if (relacionarCartaConProbAccion((String) tvCartaPulsada.getText()) == 1)
        {
            System.out.println("Popa -> " + nombreJug);

            if (lanzarBotAntiarma(tvCartaPulsada, tvBotVeh1, tvBotVeh2, tvBotVeh3, tvBotVeh4, tvComodinesJugadores[nombreJug * 8 + 0],
                    tvComodinesJugadores[nombreJug * 8 + 2], tvComodinesJugadores[nombreJug * 8 + 4], tvComodinesJugadores[nombreJug * 8 + 6],
                    accionesBot, context, nombreJug))
            {
                int valorCarta2 = funcionesGenerales.sacarCarta2();
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);
                funcionesGenerales.colorCarta(barajaCartas[valorCarta2], tvCartaPulsada);

                numTirada += 1;

                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);
            }
        }
        else if (relacionarCartaConProbAccion((String) tvCartaPulsada.getText()) == 2)
        {
            if (lanzarBotArma(tvCartaPulsada, accionesBot, context, numCarta, nombreJug))
            {
                int valorCarta2 = funcionesGenerales.sacarCarta2();
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);
                funcionesGenerales.colorCarta(barajaCartas[valorCarta2], tvCartaPulsada);

                numTirada += 1;
                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);
            }
        }
        else if (relacionarCartaConProbAccion((String) tvCartaPulsada.getText()) == 3)
        {
            if (lanzarBotIntercambioEquipo(tvCartaPulsada, nombreJug, context))
            {
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.intercambio, sonidoJuego);

                int valorCarta2 = funcionesGenerales.sacarCarta2();
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);
                funcionesGenerales.colorCarta(barajaCartas[valorCarta2], tvCartaPulsada);

                numTirada += 1;
                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);
            }
        }
        else if (relacionarCartaConProbAccion((String) tvCartaPulsada.getText()) == 4)
        {
            if (lanzarBotRoboVehiculo(tvCartaPulsada, tvMensajeVictoria, accionesBot, nombreJug, context))
            {
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.intercambio, sonidoJuego);

                int valorCarta2 = funcionesGenerales.sacarCarta2();
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);
                funcionesGenerales.colorCarta(barajaCartas[valorCarta2], tvCartaPulsada);

                numTirada += 1;
                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);
            }
        }
        else if (relacionarCartaConProbAccion((String) tvCartaPulsada.getText()) == 5)
        {
            if (lanzarBotIntercambioVehiculo(nombreJug, context))
            {
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.intercambio, sonidoJuego);

                int valorCarta2 = funcionesGenerales.sacarCarta2();
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);
                funcionesGenerales.colorCarta(barajaCartas[valorCarta2], tvCartaPulsada);

                numTirada += 1;
                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);
            }
        }
        else if (relacionarCartaConProbAccion((String) tvCartaPulsada.getText()) == 6)
        {
            // Ejecutamos la acción de la carta vehículo.
            if (lanzarBotExpulsionArmas(tvCartaPulsada, accionesBot, nombreJug, context))
            {
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.expulsararmas, sonidoJuego);

                numTirada += 1;

                // Tras lanzar la carta sacamos una nueva carta de la baraja.
                int valorCarta2 = funcionesGenerales.sacarCarta2();
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);
                // Le aplicamos el color a la carta en función del tipo de carta que sea.
                funcionesGenerales.colorCarta(barajaCartas[valorCarta2], tvCartaPulsada);
                // Comprobamos si la baraja está ya agotada.
                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);
            }
        }
        else if (relacionarCartaConProbAccion((String) tvCartaPulsada.getText()) == 7)
        {
            for (int i = 0; i < numeroJugadores; i++)
            {
                if (i == 0)
                {
                    funcionesGenerales.echarTresCartas(barajaCartas, tvCartasJugadores[0], tvCartasJugadores[1],
                            tvCartasJugadores[2]);
                }
                else
                {
                    funcionesGenerales.echarTresCartas(barajaCartas, tvCartasJugadores[numeroJugadores * 3 + 0],
                            tvCartasJugadores[numeroJugadores * 3 + 1], tvCartasJugadores[numeroJugadores * 3 + 2]);
                }
            }
            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarSacaTresCartasNuevas));
            funcionesGenerales.reproducirSonidoJuego(context, R.raw.nuevascartas, sonidoJuego);
        }
    }

    public boolean lanzarBotArma(TextView tvCartaPulsada, AccionesBot accionesBot,
                                 Context context, int numCarta, int nombreJug)
    {
        String txtBotCartaPulsada = (String) tvCartaPulsada.getText();

        int posValorMayor = posValorMayorEcharCartaArma(numCarta, nombreJug);
        System.out.println("Gafapasta -> " + posValorMayor + " - " + nombreJug);

        RelativeLayout relVehJug[] = new RelativeLayout[]{ relativeJugadores[posValorMayor * 4 + 0],
                relativeJugadores[posValorMayor * 4 + 1],
                relativeJugadores[posValorMayor * 4 + 2],
                relativeJugadores[posValorMayor * 4 + 3]};

        ImageView ivVehJug[] = new ImageView[]{ ivVehiculosJugadores[posValorMayor * 4 + 0],
                ivVehiculosJugadores[posValorMayor * 4 + 1],
                ivVehiculosJugadores[posValorMayor * 4 + 2],
                ivVehiculosJugadores[posValorMayor * 4 + 3]};

        TextView vehiculosJug[] = new TextView[]{ tvVehiculosJugadores[posValorMayor * 4 + 0],
                tvVehiculosJugadores[posValorMayor * 4 + 1],
                tvVehiculosJugadores[posValorMayor * 4 + 2],
                tvVehiculosJugadores[posValorMayor * 4 + 3]};

         ArrayList<TextView> posiblesVehJugRecibirArma = new ArrayList<>();

        if (txtBotCartaPulsada.equals("Metralleta"))
        {
            System.out.println("Quilesss -> " + txtBotCartaPulsada);

            for (int i = 0; i < vehiculosJug.length; i++)
            {
                if (vehiculosJug[i].getText().equals("Batallon") && restoContadorVeh.get(posValorMayor * 5 + 0) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
                else if (vehiculosJug[i].getText().equals("Vehículo Multicolor") && restoContadorVeh.get(posValorMayor * 5 + 4) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
            }

            System.out.println("Mateo -> " + txtBotCartaPulsada + " - " + posiblesVehJugRecibirArma.size() + " - " + posValorMayor);

            if (posiblesVehJugRecibirArma.size() == 2)
            {
                if (restoContadorVeh.get(posValorMayor * 5 + 0) > restoContadorVeh.get(posValorMayor * 5 + 4))
                {
                    for (int i = 0; i < posiblesVehJugRecibirArma.size(); i++)
                    {
                        if (posiblesVehJugRecibirArma.get(i).getText().equals("Batallon"))
                        {
                            for (int a = 0; a < vehiculosJug.length; a++)
                            {
                                if (vehiculosJug[a].getText().equals("Batallon"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                                }
                            }

                            if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(0))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaBatallonJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1)
                                            + context.getResources().getString(R.string.tvToolbarAtacaBatallonBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(-1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1)
                                            + context.getResources().getString(R.string.tvToolbarDerribaBatallonJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1)
                                            + context.getResources().getString(R.string.tvToolbarDerribaBatallonBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText("---");

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1)
                                            + context.getResources().getString(R.string.tvToolbarDerribaBatallonJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1)
                                            + context.getResources().getString(R.string.tvToolbarAtacaBatallonBot)
                                            + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                                }
                            }

                            break;
                        }
                    }
                }
                else
                {
                    for (int i = 0; i < posiblesVehJugRecibirArma.size(); i++)
                    {
                        if (posiblesVehJugRecibirArma.get(i).getText().equals("Vehículo Multicolor"))
                        {
                            for (int a = 0; a < vehiculosJug.length; a++)
                            {
                                if (vehiculosJug[a].getText().equals("Vehículo Multicolor"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                                }
                            }

                            if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(0))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(-1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText("---");

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(2))
                            {
                                Toast.makeText(context,
                                        "No puedes intercambiar un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
            }
            else if (posiblesVehJugRecibirArma.size() == 1)
            {
                if (posiblesVehJugRecibirArma.get(0).getText().equals("Batallon"))
                {
                    for (int a = 0; a < vehiculosJug.length; a++)
                    {
                        if (vehiculosJug[a].getText().equals("Batallon"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                        }
                    }

                    if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(0))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaBatallonBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(-1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaBatallonJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaBatallonBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText("---");

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaBatallonBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(2))
                    {
                        Toast.makeText(context,
                                "No puedes intercambiar un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else if (posiblesVehJugRecibirArma.get(0).getText().equals("Vehículo Multicolor"))
                {
                    for (int a = 0; a < vehiculosJug.length; a++)
                    {
                        if (vehiculosJug[a].getText().equals("Vehículo Multicolor"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                        }
                    }

                    if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(0))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(-1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText("---");

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(2))
                    {
                        Toast.makeText(context,
                                "No puedes intercambiar un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            return true;
        }
        else if (txtBotCartaPulsada.equals("Lanzacohetes"))
        {
            funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);

            System.out.println("Quilesss -> " + txtBotCartaPulsada);
            for (int i = 0; i < vehiculosJug.length; i++)
            {
                if (vehiculosJug[i].getText().equals("Tanque") && restoContadorVeh.get(posValorMayor * 5 + 1) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
                else if (vehiculosJug[i].getText().equals("Vehículo Multicolor") && restoContadorVeh.get(posValorMayor * 5 + 4) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
            }

            System.out.println("Mateo -> " + txtBotCartaPulsada + " - " + posiblesVehJugRecibirArma.size() + " - " + posValorMayor);

            if (posiblesVehJugRecibirArma.size() == 2)
            {
                if (restoContadorVeh.get(posValorMayor * 5 + 1) > restoContadorVeh.get(posValorMayor * 5 + 4))
                {
                    for (int i = 0; i < posiblesVehJugRecibirArma.size(); i++)
                    {
                        if (posiblesVehJugRecibirArma.get(i).getText().equals("Tanque"))
                        {
                            for (int a = 0; a < vehiculosJug.length; a++)
                            {
                                if (vehiculosJug[a].getText().equals("Tanque"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                                }
                            }

                            if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(0))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(-1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaTanqueJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaTanqueBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText("---");

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(2))
                            {
                                Toast.makeText(context,
                                        "No puedes intercambiar un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
                else
                {
                    for (int i = 0; i < posiblesVehJugRecibirArma.size(); i++)
                    {
                        if (posiblesVehJugRecibirArma.get(i).getText().equals("Vehículo Multicolor"))
                        {
                            for (int a = 0; a < vehiculosJug.length; a++)
                            {
                                if (vehiculosJug[a].getText().equals("Vehículo Multicolor"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                                }
                            }

                            if (restoContadorVeh.get(posValorMayor * 5 + 4) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText("---");

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4) == 2)
                            {
                                Toast.makeText(context,
                                        "No puedes intercambiar un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
            }
            else if (posiblesVehJugRecibirArma.size() == 1)
            {
                if (posiblesVehJugRecibirArma.get(0).getText().equals("Tanque"))
                {
                    for (int a = 0; a < vehiculosJug.length; a++)
                    {
                        if (vehiculosJug[a].getText().equals("Tanque"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                        }
                    }

                    if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(0))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(-1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaTanqueJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaTanqueBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText("---");

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(2))
                    {
                        Toast.makeText(context,
                                "No puedes intercambiar un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else if (posiblesVehJugRecibirArma.get(0).getText().equals("Vehículo Multicolor"))
                {
                    for (int a = 0; a < vehiculosJug.length; a++)
                    {
                        if (vehiculosJug[a].getText().equals("Vehículo Multicolor"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                        }
                    }

                    if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(0))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(-1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText("---");

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(2))
                    {
                        Toast.makeText(context,
                                "No puedes intercambiar un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            return true;
        }
        else if (txtBotCartaPulsada.equals("Torpedos"))
        {
            funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);

            System.out.println("Quilesss -> " + txtBotCartaPulsada);
            for (int i = 0; i < vehiculosJug.length; i++)
            {
                if (vehiculosJug[i].getText().equals("Submarino") && restoContadorVeh.get(posValorMayor * 5 + 2) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
                else if (vehiculosJug[i].getText().equals("Vehículo Multicolor") && restoContadorVeh.get(posValorMayor * 5 + 4) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
            }

            System.out.println("Mateo -> " + txtBotCartaPulsada + " - " + posiblesVehJugRecibirArma.size() + " - " + posValorMayor);

            if (posiblesVehJugRecibirArma.size() == 2)
            {
                if (restoContadorVeh.get(posValorMayor * 5 + 2) > restoContadorVeh.get(posValorMayor * 5 + 4))
                {
                    for (int i = 0; i < posiblesVehJugRecibirArma.size(); i++)
                    {
                        if (posiblesVehJugRecibirArma.get(i).getText().equals("Submarino"))
                        {
                            for (int a = 0; a < vehiculosJug.length; a++)
                            {
                                if (vehiculosJug[a].getText().equals("Submarino"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                                }
                            }

                            if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(0))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(-1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaSubmarinoJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaSubmarinoBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText("---");

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(2))
                            {
                                Toast.makeText(context,
                                        "No puedes intercambiar un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
                else
                {
                    for (int i = 0; i < posiblesVehJugRecibirArma.size(); i++)
                    {
                        if (posiblesVehJugRecibirArma.get(i).getText().equals("Vehículo Multicolor"))
                        {
                            for (int a = 0; a < vehiculosJug.length; a++)
                            {
                                if (vehiculosJug[a].getText().equals("Vehículo Multicolor"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                                }
                            }

                            if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(0))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(-1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText("---");

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(2))
                            {
                                Toast.makeText(context,
                                        "No puedes intercambiar un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
            }
            else if (posiblesVehJugRecibirArma.size() == 1)
            {
                if (posiblesVehJugRecibirArma.get(0).getText().equals("Submarino"))
                {
                    for (int a = 0; a < vehiculosJug.length; a++)
                    {
                        if (vehiculosJug[a].getText().equals("Submarino"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                        }
                    }

                    if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(0))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(-1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaSubmarinoJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaSubmarinoBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText("---");

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(2))
                    {
                        Toast.makeText(context,
                                "No puedes intercambiar un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else if (posiblesVehJugRecibirArma.get(0).getText().equals("Vehículo Multicolor"))
                {
                    for (int a = 0; a < vehiculosJug.length; a++)
                    {
                        if (vehiculosJug[a].getText().equals("Vehículo Multicolor"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                        }
                    }

                    if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(0))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(-1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText("---");

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(2))
                    {
                        Toast.makeText(context,
                                "No puedes intercambiar un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            return true;
        }
        else if (txtBotCartaPulsada.equals("Misiles"))
        {
            funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);

            System.out.println("Quilesss -> " + txtBotCartaPulsada);
            for (int i = 0; i < vehiculosJug.length; i++)
            {
                if (vehiculosJug[i].getText().equals("Caza") && restoContadorVeh.get(posValorMayor * 5 + 3) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
                else if (vehiculosJug[i].getText().equals("Vehículo Multicolor") && restoContadorVeh.get(posValorMayor * 5 + 4) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
            }

            System.out.println("Mateo -> " + txtBotCartaPulsada + " - " + posiblesVehJugRecibirArma.size() + " - " + posValorMayor);

            if (posiblesVehJugRecibirArma.size() == 2)
            {
                if (restoContadorVeh.get(posValorMayor * 5 + 3) > restoContadorVeh.get(posValorMayor * 5 + 4))
                {
                    for (int i = 0; i < posiblesVehJugRecibirArma.size(); i++)
                    {
                        if (posiblesVehJugRecibirArma.get(i).getText().equals("Caza"))
                        {
                            for (int a = 0; a < vehiculosJug.length; a++)
                            {
                                if (vehiculosJug[a].getText().equals("Caza"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                                }
                            }

                            if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(0))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(-1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaCazaJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaCazaBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText("---");

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(2))
                            {
                                Toast.makeText(context,
                                        "No puedes intercambiar un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 3) <= restoContadorVeh.get(posValorMayor * 5 + 4))
                {
                    for (int i = 0; i < posiblesVehJugRecibirArma.size(); i++)
                    {
                        if (posiblesVehJugRecibirArma.get(i).getText().equals("Vehículo Multicolor"))
                        {
                            for (int a = 0; a < vehiculosJug.length; a++)
                            {
                                if (vehiculosJug[a].getText().equals("Vehículo Multicolor"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                                }
                            }

                            if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(0))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(-1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(1))
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(i).getId(), posiblesVehJugRecibirArma.get(i), "1"), posValorMayor).setText("---");

                                if (posValorMayor == 0)
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                                }
                                else
                                {
                                    tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                                }
                            }
                            else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(2))
                            {
                                Toast.makeText(context,
                                        "No puedes intercambiar un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
            }
            else if (posiblesVehJugRecibirArma.size() == 1)
            {
                if (posiblesVehJugRecibirArma.get(0).getText().equals("Caza"))
                {
                    for (int a = 0; a < vehiculosJug.length; a++)
                    {
                        if (vehiculosJug[a].getText().equals("Caza"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                        }
                    }

                    if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(0))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(-1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaCazaJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaCazaBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText("---");

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(2))
                    {
                        Toast.makeText(context,
                                "No puedes intercambiar un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else if (posiblesVehJugRecibirArma.get(0).getText().equals("Vehículo Multicolor"))
                {
                    for (int a = 0; a < vehiculosJug.length; a++)
                    {
                        if (tvVehiculosJugadores[a].getText().equals("Vehículo Multicolor"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                        }
                    }

                    if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(0))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(-1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(1))
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(0).getId(), posiblesVehJugRecibirArma.get(0), "1"), posValorMayor).setText("---");

                        if (posValorMayor == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                        }
                    }
                    else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(2))
                    {
                        Toast.makeText(context,
                                "No puedes intercambiar un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            return true;
        }
        else if (txtBotCartaPulsada.equals("Arma Multicolor"))
        {
            System.out.println("Quilesss -> " + txtBotCartaPulsada);
            for (int i = 0; i < vehiculosJug.length; i++)
            {
                if (vehiculosJug[i].getText().equals("Batallon") && restoContadorVeh.get(posValorMayor * 5 + 0) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
                else if (vehiculosJug[i].getText().equals("Tanque") && restoContadorVeh.get(posValorMayor * 5 + 1) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
                else if (vehiculosJug[i].getText().equals("Submarino") && restoContadorVeh.get(posValorMayor * 5 + 2) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
                else if (vehiculosJug[i].getText().equals("Caza") && restoContadorVeh.get(posValorMayor * 5 + 3) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
                else if (vehiculosJug[i].getText().equals("Vehículo Multicolor") && restoContadorVeh.get(posValorMayor * 5 + 4) < 2)
                {
                    posiblesVehJugRecibirArma.add(vehiculosJug[i]);
                }
            }

            System.out.println("Mateo -> " + txtBotCartaPulsada + " - " + posiblesVehJugRecibirArma.size() + " - " + posValorMayor);

            int posVehAfectado = generarNumAleatorio(posiblesVehJugRecibirArma.size()-1);

            if (posiblesVehJugRecibirArma.get(posVehAfectado).getText().equals("Batallon"))
            {
                for (int a = 0; a < vehiculosJug.length; a++)
                {
                    if (tvVehiculosJugadores[a].getText().equals("Batallon"))
                    {
                        relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                    }
                }

                if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(0))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaBatallonBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(-1))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaBatallonJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaBatallonBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(1))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "1"), posValorMayor).setText("---");

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaBatallonBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 0).equals(2))
                {
                    Toast.makeText(context,
                            "No puedes intercambiar un vehículo acorazado",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else if (posiblesVehJugRecibirArma.get(posVehAfectado).getText().equals("Tanque"))
            {
                for (int a = 0; a < vehiculosJug.length; a++)
                {
                    if (tvVehiculosJugadores[a].getText().equals("Tanque"))
                    {
                        relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                    }
                }

                if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(0))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(-1))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaTanqueJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaTanqueBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(1))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "1"), posValorMayor).setText("---");

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaTanqueBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 1).equals(2))
                {
                    Toast.makeText(context,
                            "No puedes intercambiar un vehículo acorazado",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else if (posiblesVehJugRecibirArma.get(posVehAfectado).getText().equals("Submarino"))
            {
                for (int a = 0; a < vehiculosJug.length; a++)
                {
                    if (tvVehiculosJugadores[a].getText().equals("Submarino"))
                    {
                        relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                    }
                }

                if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(0))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(-1))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaSubmarinoJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaSubmarinoBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(1))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "1"), posValorMayor).setText("---");

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaSubmarinoBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 2).equals(2))
                {
                    Toast.makeText(context,
                            "No puedes intercambiar un vehículo acorazado",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else if (posiblesVehJugRecibirArma.get(posVehAfectado).getText().equals("Caza"))
            {
                for (int a = 0; a < vehiculosJug.length; a++)
                {
                    if (tvVehiculosJugadores[a].getText().equals("Caza"))
                    {
                        relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                    }
                }

                if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(0))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(-1))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaCazaJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaCazaBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(1))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "1"), posValorMayor).setText("---");

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaCazaBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 3).equals(2))
                {
                    Toast.makeText(context,
                            "No puedes intercambiar un vehículo acorazado",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else if (posiblesVehJugRecibirArma.get(posVehAfectado).getText().equals("Vehículo Multicolor"))
            {
                for (int a = 0; a < vehiculosJug.length; a++)
                {
                    if (tvVehiculosJugadores[a].getText().equals("Vehículo Multicolor"))
                    {
                        relVehJug[a].setBackgroundColor(Color.parseColor("#90a3364a"));
                    }
                }

                if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(0))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "1"), posValorMayor).setText(tvCartaPulsada.getText());

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(-1))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "2"), posValorMayor).setText(tvCartaPulsada.getText());

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarDerribaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(1))
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehJugRecibirArma.get(posVehAfectado).getId(), posiblesVehJugRecibirArma.get(posVehAfectado), "1"), posValorMayor).setText("---");

                    if (posValorMayor == 0)
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                    else
                    {
                        tvToolbar.setText(ordenNombresEjercitos.get(nombreJug - 1) + context.getResources().getString(R.string.tvToolbarAtacaMulticolorBot) + ordenNombresEjercitos.get(posValorMayor - 1));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                    }
                }
                else if (restoContadorVeh.get(posValorMayor * 5 + 4).equals(2))
                {
                    Toast.makeText(context,
                            "No puedes intercambiar un vehículo acorazado",
                            Toast.LENGTH_SHORT).show();
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean lanzarBotAntiarma(TextView tvCartaPulsada, TextView tvBotVeh1, TextView tvBotVeh2,
                                     TextView tvBotVeh3, TextView tvBotVeh4, TextView tvBotComodin11, TextView tvBotComodin21,
                                     TextView tvBotComodin31, TextView tvBotComodin41, AccionesBot accionesBot,
                                     Context context, int posJug2)
    {
        int posJug = posJug2 - 1;
        System.out.println("Mara -> ******************************************");
        System.out.println("Mara -> Jug:" + posJug);
        System.out.println("Mara -> " + accionesBot.getContadorBotBatallon(posJug));
        System.out.println("Mara -> " + accionesBot.getContadorBotTanque(posJug));
        System.out.println("Mara -> " + accionesBot.getContadorBotSubmarino(posJug));
        System.out.println("Mara -> " + accionesBot.getContadorBotCaza(posJug));
        System.out.println("Mara -> " + accionesBot.getContadorBotVehMulticolor(posJug));
        System.out.println("Mara -> ////////////////////////////////////////////");

        String txtBotCartaPulsada = (String) tvCartaPulsada.getText();
        TextView vehiculosBot[] = new TextView[]{tvBotVeh1, tvBotVeh2, tvBotVeh3, tvBotVeh4};
        TextView vehCom1Bot[] = new TextView[]{tvBotComodin11, tvBotComodin21, tvBotComodin31, tvBotComodin41};

        RelativeLayout relVehJug[] = new RelativeLayout[]{ relativeJugadores[posJug2 * 4 + 0],
                relativeJugadores[posJug2 * 4 + 1],
                relativeJugadores[posJug2 * 4 + 2],
                relativeJugadores[posJug2 * 4 + 3]};

        ImageView ivVehJug[] = new ImageView[]{ ivVehiculosJugadores[posJug2 * 4 + 0],
                ivVehiculosJugadores[posJug2 * 4 + 1],
                ivVehiculosJugadores[posJug2 * 4 + 2],
                ivVehiculosJugadores[posJug2 * 4 + 3]};

        ArrayList<TextView> posiblesVehBotRecibirMedicina = new ArrayList<>();

        if (txtBotCartaPulsada.equals("Medicina1"))
        {
            for (int i = 0; i < vehiculosBot.length; i++)
            {
                if (vehiculosBot[i].getText().equals("Batallon") && accionesBot.getContadorBotBatallon(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
                else if (vehiculosBot[i].getText().equals("Vehículo Multicolor") && accionesBot.getContadorBotVehMulticolor(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
            }

            if (posiblesVehBotRecibirMedicina.size() == 2)
            {
                if (accionesBot.getContadorBotBatallon(posJug) > accionesBot.getContadorBotVehMulticolor(posJug))
                {
                    for (int i = 0; i < posiblesVehBotRecibirMedicina.size(); i++)
                    {
                        if (posiblesVehBotRecibirMedicina.get(i).getText().equals("Batallon"))
                        {
                            for (int a = 0; a < vehiculosBot.length; a++)
                            {
                                if (vehiculosBot[a].getText().equals("Batallon"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                                }
                            }

                            if (accionesBot.getContadorBotBatallon(posJug) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaBatallon));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotBatallon(posJug) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText("---");

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaBatallon));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotBatallon(posJug) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "2"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaBatallon));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotBatallon(posJug) == 2)
                            {
                                Toast.makeText(context,
                                        "No puedes echar medicina sobre un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
                else if (accionesBot.getContadorBotBatallon(posJug) <= accionesBot.getContadorBotVehMulticolor(posJug))
                {
                    for (int i = 0; i < posiblesVehBotRecibirMedicina.size(); i++)
                    {
                        if (posiblesVehBotRecibirMedicina.get(i).getText().equals("Vehículo Multicolor"))
                        {
                            for (int a = 0; a < vehiculosBot.length; a++)
                            {
                                if (vehiculosBot[a].getText().equals("Vehículo Multicolor"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                                }
                            }

                            if (accionesBot.getContadorBotVehMulticolor(posJug) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText("---");

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "2"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == 2)
                            {
                                Toast.makeText(context,
                                        "No puedes echar medicina sobre un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
            }
            else if (posiblesVehBotRecibirMedicina.size() == 1)
            {
                if (posiblesVehBotRecibirMedicina.get(0).getText().equals("Batallon"))
                {
                    for (int a = 0; a < vehiculosBot.length; a++)
                    {
                        if (vehiculosBot[a].getText().equals("Batallon"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                        }
                    }

                    if (accionesBot.getContadorBotBatallon(posJug) == 0)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaBatallon));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotBatallon(posJug) == -1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText("---");

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaBatallon));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotBatallon(posJug) == 1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "2"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaBatallon));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotBatallon(posJug) == 2)
                    {
                        Toast.makeText(context,
                                "No puedes echar medicina sobre un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else if (posiblesVehBotRecibirMedicina.get(0).getText().equals("Vehículo Multicolor"))
                {
                    for (int a = 0; a < vehiculosBot.length; a++)
                    {
                        if (vehiculosBot[a].getText().equals("Vehículo Multicolor"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                        }
                    }

                    if (accionesBot.getContadorBotVehMulticolor(posJug) == 0)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == -1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText("---");

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == 1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "2"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == 2)
                    {
                        Toast.makeText(context,
                                "No puedes echar medicina sobre un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            return true;
        }
        else if (txtBotCartaPulsada.equals("Medicina2"))
        {
            for (int i = 0; i < vehiculosBot.length; i++)
            {
                if (vehiculosBot[i].getText().equals("Tanque") && accionesBot.getContadorBotTanque(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
                else if (vehiculosBot[i].getText().equals("Vehículo Multicolor") && accionesBot.getContadorBotVehMulticolor(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
            }

            if (posiblesVehBotRecibirMedicina.size() == 2)
            {
                if (accionesBot.getContadorBotTanque(posJug) > accionesBot.getContadorBotVehMulticolor(posJug))
                {
                    for (int i = 0; i < posiblesVehBotRecibirMedicina.size(); i++)
                    {
                        if (posiblesVehBotRecibirMedicina.get(i).getText().equals("Tanque"))
                        {
                            for (int a = 0; a < vehiculosBot.length; a++)
                            {
                                if (vehiculosBot[a].getText().equals("Tanque"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                                }
                            }

                            if (accionesBot.getContadorBotTanque(posJug) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaTanque));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotTanque(posJug) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText("---");

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaTanque));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotTanque(posJug) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "2"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaTanque));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotTanque(posJug) == 2)
                            {
                                Toast.makeText(context,
                                        "No puedes echar medicina sobre un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
                else if (accionesBot.getContadorBotTanque(posJug) <= accionesBot.getContadorBotVehMulticolor(posJug))
                {
                    for (int i = 0; i < posiblesVehBotRecibirMedicina.size(); i++)
                    {
                        if (posiblesVehBotRecibirMedicina.get(i).getText().equals("Vehículo Multicolor"))
                        {
                            for (int a = 0; a < vehiculosBot.length; a++)
                            {
                                if (vehiculosBot[a].getText().equals("Vehículo Multicolor"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                                }
                            }

                            if (accionesBot.getContadorBotVehMulticolor(posJug) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText("---");

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "2"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == 2)
                            {
                                Toast.makeText(context,
                                        "No puedes echar medicina sobre un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
            }
            else if (posiblesVehBotRecibirMedicina.size() == 1)
            {
                if (posiblesVehBotRecibirMedicina.get(0).getText().equals("Tanque"))
                {
                    for (int a = 0; a < vehiculosBot.length; a++)
                    {
                        if (vehiculosBot[a].getText().equals("Tanque"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                        }
                    }

                    if (accionesBot.getContadorBotTanque(posJug) == 0)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaTanque));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotTanque(posJug) == -1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText("---");

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaTanque));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotTanque(posJug) == 1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "2"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaTanque));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotTanque(posJug) == 2)
                    {
                        Toast.makeText(context,
                                "No puedes echar medicina sobre un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else if (posiblesVehBotRecibirMedicina.get(0).getText().equals("Vehículo Multicolor"))
                {
                    for (int a = 0; a < vehiculosBot.length; a++)
                    {
                        if (vehiculosBot[a].getText().equals("Vehículo Multicolor"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                        }
                    }

                    if (accionesBot.getContadorBotVehMulticolor(posJug) == 0)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == -1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText("---");

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == 1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "2"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == 2)
                    {
                        Toast.makeText(context,
                                "No puedes echar medicina sobre un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            return true;
        }
        else if (txtBotCartaPulsada.equals("Medicina3"))
        {
            for (int i = 0; i < vehiculosBot.length; i++)
            {
                if (vehiculosBot[i].getText().equals("Submarino") && accionesBot.getContadorBotSubmarino(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
                else if (vehiculosBot[i].getText().equals("Vehículo Multicolor") && accionesBot.getContadorBotVehMulticolor(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
            }

            if (posiblesVehBotRecibirMedicina.size() == 2)
            {
                if (accionesBot.getContadorBotSubmarino(posJug) > accionesBot.getContadorBotVehMulticolor(posJug))
                {
                    for (int i = 0; i < posiblesVehBotRecibirMedicina.size(); i++)
                    {
                        if (posiblesVehBotRecibirMedicina.get(i).getText().equals("Submarino"))
                        {
                            for (int a = 0; a < vehiculosBot.length; a++)
                            {
                                if (vehiculosBot[a].getText().equals("Submarino"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                                }
                            }

                            if (accionesBot.getContadorBotSubmarino(posJug) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaSubmarino));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotSubmarino(posJug) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText("---");

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaSubmarino));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotSubmarino(posJug) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "2"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaSubmarino));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotSubmarino(posJug) == 2)
                            {
                                Toast.makeText(context,
                                        "No puedes echar medicina sobre un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
                else if (accionesBot.getContadorBotSubmarino(posJug) <= accionesBot.getContadorBotVehMulticolor(posJug))
                {
                    for (int i = 0; i < posiblesVehBotRecibirMedicina.size(); i++)
                    {
                        if (posiblesVehBotRecibirMedicina.get(i).getText().equals("Vehículo Multicolor"))
                        {
                            for (int a = 0; a < vehiculosBot.length; a++)
                            {
                                if (vehiculosBot[a].getText().equals("Vehículo Multicolor"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                                }
                            }

                            if (accionesBot.getContadorBotVehMulticolor(posJug) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText("---");

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "2"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == 2)
                            {
                                Toast.makeText(context,
                                        "No puedes echar medicina sobre un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
            }
            else if (posiblesVehBotRecibirMedicina.size() == 1)
            {
                if (posiblesVehBotRecibirMedicina.get(0).getText().equals("Submarino"))
                {
                    for (int a = 0; a < vehiculosBot.length; a++)
                    {
                        if (vehiculosBot[a].getText().equals("Submarino"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                        }
                    }

                    if (accionesBot.getContadorBotSubmarino(posJug) == 0)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaSubmarino));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotSubmarino(posJug) == -1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText("---");

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaSubmarino));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotSubmarino(posJug) == 1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "2"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaSubmarino));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotSubmarino(posJug) == 2)
                    {
                        Toast.makeText(context,
                                "No puedes echar medicina sobre un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else if (posiblesVehBotRecibirMedicina.get(0).getText().equals("Vehículo Multicolor"))
                {
                    for (int a = 0; a < vehiculosBot.length; a++)
                    {
                        if (vehiculosBot[a].getText().equals("Vehículo Multicolor"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                        }
                    }

                    if (accionesBot.getContadorBotVehMulticolor(posJug) == 0)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == -1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText("---");

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == 1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "2"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == 2)
                    {
                        Toast.makeText(context,
                                "No puedes echar medicina sobre un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            return true;
        }
        else if (txtBotCartaPulsada.equals("Medicina4"))
        {
            for (int i = 0; i < vehiculosBot.length; i++)
            {
                if (vehiculosBot[i].getText().equals("Caza") && accionesBot.getContadorBotCaza(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
                else if (vehiculosBot[i].getText().equals("Vehículo Multicolor") && accionesBot.getContadorBotVehMulticolor(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
            }

            if (posiblesVehBotRecibirMedicina.size() == 2)
            {
                if (accionesBot.getContadorBotCaza(posJug) > accionesBot.getContadorBotVehMulticolor(posJug))
                {
                    for (int i = 0; i < posiblesVehBotRecibirMedicina.size(); i++)
                    {
                        if (posiblesVehBotRecibirMedicina.get(i).getText().equals("Caza"))
                        {
                            for (int a = 0; a < vehiculosBot.length; a++)
                            {
                                if (vehiculosBot[a].getText().equals("Caza"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                                }
                            }

                            if (accionesBot.getContadorBotCaza(posJug) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaCaza));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotCaza(posJug) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText("---");

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaCaza));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotCaza(posJug) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "2"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaCaza));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotCaza(posJug) == 2)
                            {
                                Toast.makeText(context,
                                        "No puedes echar medicina sobre un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
                else if (accionesBot.getContadorBotCaza(posJug) <= accionesBot.getContadorBotVehMulticolor(posJug))
                {
                    for (int i = 0; i < posiblesVehBotRecibirMedicina.size(); i++)
                    {
                        if (posiblesVehBotRecibirMedicina.get(i).getText().equals("Vehículo Multicolor"))
                        {
                            for (int a = 0; a < vehiculosBot.length; a++)
                            {
                                if (vehiculosBot[a].getText().equals("Vehículo Multicolor"))
                                {
                                    relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                                }
                            }

                            if (accionesBot.getContadorBotVehMulticolor(posJug) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "1"), posJug2).setText("---");

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(i).getId(), posiblesVehBotRecibirMedicina.get(i), "2"), posJug2).setText(tvCartaPulsada.getText());

                                tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaMulticolor));
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(posJug) == 2)
                            {
                                Toast.makeText(context,
                                        "No puedes echar medicina sobre un vehículo acorazado",
                                        Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
            }
            else if (posiblesVehBotRecibirMedicina.size() == 1)
            {
                if (posiblesVehBotRecibirMedicina.get(0).getText().equals("Caza"))
                {
                    for (int a = 0; a < vehiculosBot.length; a++)
                    {
                        if (vehiculosBot[a].getText().equals("Caza"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                        }
                    }

                    if (accionesBot.getContadorBotCaza(posJug) == 0)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaCaza));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotCaza(posJug) == -1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText("---");

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaCaza));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotCaza(posJug) == 1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "2"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaCaza));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotCaza(posJug) == 2)
                    {
                        Toast.makeText(context,
                                "No puedes echar medicina sobre un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else if (posiblesVehBotRecibirMedicina.get(0).getText().equals("Vehículo Multicolor"))
                {
                    for (int a = 0; a < vehiculosBot.length; a++)
                    {
                        if (vehiculosBot[a].getText().equals("Vehículo Multicolor"))
                        {
                            relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                        }
                    }

                    if (accionesBot.getContadorBotVehMulticolor(posJug) == 0)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == -1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "1"), posJug2).setText("---");

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == 1)
                    {
                        funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(0).getId(), posiblesVehBotRecibirMedicina.get(0), "2"), posJug2).setText(tvCartaPulsada.getText());

                        tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaMulticolor));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                    }
                    else if (accionesBot.getContadorBotVehMulticolor(posJug) == 2)
                    {
                        Toast.makeText(context,
                                "No puedes echar medicina sobre un vehículo acorazado",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            return true;
        }
        else if (txtBotCartaPulsada.equals("Medicina Multicolor"))
        {
            for (int i = 0; i < vehiculosBot.length; i++)
            {
                if (vehiculosBot[i].getText().equals("Batallon") && accionesBot.getContadorBotBatallon(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
                else if (vehiculosBot[i].getText().equals("Tanque") && accionesBot.getContadorBotTanque(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
                else if (vehiculosBot[i].getText().equals("Submarino") && accionesBot.getContadorBotSubmarino(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
                else if (vehiculosBot[i].getText().equals("Caza") && accionesBot.getContadorBotCaza(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
                else if (vehiculosBot[i].getText().equals("Vehículo Multicolor") && accionesBot.getContadorBotVehMulticolor(posJug) < 2)
                {
                    posiblesVehBotRecibirMedicina.add(vehiculosBot[i]);
                }
            }

            int posVehAfectado = generarNumAleatorio(posiblesVehBotRecibirMedicina.size()-1);
            System.out.println("Reir -> " + posVehAfectado);

            if (posiblesVehBotRecibirMedicina.get(posVehAfectado).getText().equals("Batallon"))
            {
                for (int a = 0; a < vehiculosBot.length; a++)
                {
                    if (vehiculosBot[a].getText().equals("Batallon"))
                    {
                        relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                    }
                }

                if (accionesBot.getContadorBotBatallon(posJug) == 0)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "1"), posJug2).setText(tvCartaPulsada.getText());

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaBatallon));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotBatallon(posJug) == -1)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "1"), posJug2).setText("---");

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaBatallon));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotBatallon(posJug) == 1)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "2"), posJug2).setText(tvCartaPulsada.getText());

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaBatallon));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotBatallon(posJug) == 2)
                {
                    Toast.makeText(context,
                            "No puedes echar medicina sobre un vehículo acorazado",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else if (posiblesVehBotRecibirMedicina.get(posVehAfectado).getText().equals("Tanque"))
            {
                for (int a = 0; a < vehiculosBot.length; a++)
                {
                    if (vehiculosBot[a].getText().equals("Tanque"))
                    {
                        relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                    }
                }

                if (accionesBot.getContadorBotTanque(posJug) == 0)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "1"), posJug2).setText(tvCartaPulsada.getText());

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaTanque));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotTanque(posJug) == -1)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "1"), posJug2).setText("---");

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaTanque));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotTanque(posJug) == 1)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "2"), posJug2).setText(tvCartaPulsada.getText());

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaTanque));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotTanque(posJug) == 2)
                {
                    Toast.makeText(context,
                            "No puedes echar medicina sobre un vehículo acorazado",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else if (posiblesVehBotRecibirMedicina.get(posVehAfectado).getText().equals("Submarino"))
            {
                for (int a = 0; a < vehiculosBot.length; a++)
                {
                    if (vehiculosBot[a].getText().equals("Submarino"))
                    {
                        relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                    }
                }

                if (accionesBot.getContadorBotSubmarino(posJug) == 0)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "1"), posJug2).setText(tvCartaPulsada.getText());

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaSubmarino));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotSubmarino(posJug) == -1)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "1"), posJug2).setText("---");

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaSubmarino));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotSubmarino(posJug) == 1)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "2"), posJug2).setText(tvCartaPulsada.getText());

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaSubmarino));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotSubmarino(posJug) == 2)
                {
                    Toast.makeText(context,
                            "No puedes echar medicina sobre un vehículo acorazado",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else if (posiblesVehBotRecibirMedicina.get(posVehAfectado).getText().equals("Caza"))
            {
                for (int a = 0; a < vehiculosBot.length; a++)
                {
                    if (vehiculosBot[a].getText().equals("Caza"))
                    {
                        relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                    }
                }

                if (accionesBot.getContadorBotCaza(posJug) == 0)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "1"), posJug2).setText(tvCartaPulsada.getText());

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaCaza));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotCaza(posJug) == -1)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "1"), posJug2).setText("---");

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaCaza));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotCaza(posJug) == 1)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "2"), posJug2).setText(tvCartaPulsada.getText());

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaCaza));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotCaza(posJug) == 2)
                {
                    Toast.makeText(context,
                            "No puedes echar medicina sobre un vehículo acorazado",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else if (posiblesVehBotRecibirMedicina.get(posVehAfectado).getText().equals("Vehículo Multicolor"))
            {
                for (int a = 0; a < vehiculosBot.length; a++)
                {
                    if (vehiculosBot[a].getText().equals("Vehículo Multicolor"))
                    {
                        relVehJug[a].setBackgroundColor(Color.parseColor("#90febb18"));
                    }
                }

                if (accionesBot.getContadorBotVehMulticolor(posJug) == 0)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "1"), posJug2).setText(tvCartaPulsada.getText());

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotVehMulticolor(posJug) == -1)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "1"), posJug2).setText("---");

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotVehMulticolor(posJug) == 1)
                {
                    funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                            funcionesGenerales.sacarIdTvMedicina(posiblesVehBotRecibirMedicina.get(posVehAfectado).getId(), posiblesVehBotRecibirMedicina.get(posVehAfectado), "2"), posJug2).setText(tvCartaPulsada.getText());

                    tvToolbar.setText(ordenNombresEjercitos.get(posJug) + context.getResources().getString(R.string.tvToolbarInmunizaMulticolor));
                    funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                }
                else if (accionesBot.getContadorBotVehMulticolor(posJug) == 2)
                {
                    Toast.makeText(context,
                            "No puedes echar medicina sobre un vehículo acorazado",
                            Toast.LENGTH_SHORT).show();
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public int generarNumAleatorio(int limite)
    {
        return (int) Math.floor(Math.random() * (0 - limite + 1) + limite);
    }

    // Método que comprobará si el BOT puede dejar un vehículo en algún casillero
    public boolean lanzarBotVehiculo(TextView tvCartaPulsada, TextView tvCartaNoPulsada1, TextView tvCartaNoPulsada2,
                                     TextView tvMensajeVictoria, int numTirada, String[] barajaCartas,
                                     AccionesBot accionesBot, int nombreJug2, Context context)
    {
        int nombreJug = nombreJug2 - 1;
        // Recogemos el texto de cada carta del BOT
        String txtBotCartaPulsada = (String) tvCartaPulsada.getText();

        System.out.println("Halley2 -> tvVehiculosJugadores:" + ((nombreJug2 * 4) + 0) + " y " + txtBotCartaPulsada);
        for (int i = 0; i < 5; i++)
        {
            System.out.println("Halley2 -> getVehBotConseguido:" + (((nombreJug2) * 5) + i));
        }

        TextView tvCasillaVacia = textViewCasillaCartaVacia(tvVehiculosJugadores[(nombreJug2 * 4) + 0],
                tvVehiculosJugadores[(nombreJug2 * 4) + 1], tvVehiculosJugadores[(nombreJug2 * 4) + 2],
                tvVehiculosJugadores[(nombreJug2 * 4) + 3]);

        // Comprobamos si entre sus cartas el BOT tiene algún vehículo
        if (botTieneCartaConcreta(txtBotCartaPulsada, "Batallon"))
        {
            // Si tiene un vehículo y no ha salido anteriormente entramos
            if (!accionesBot.getVehBotConseguido(((nombreJug) * 5) + 0))
            {
                // Insertamos la carta del vehículo no repetido en el tablero.
                tvCasillaVacia.setText("Batallon");

                tvToolbar.setText(ordenNombresEjercitos.get(nombreJug) + context.getResources().getString(R.string.tvToolbarReuneBatallon));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.batallon, sonidoJuego);

                funcionesGenerales.colorCarta("Batallon", tvCasillaVacia);
                // Comprobamos si ya el BOT ha reunido todos los vehículos en el tablero
                funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumBotVehConseguidos((nombreJug)), "BOT", tvMensajeVictoria, context);

                numTirada += 1;

                // Sustituimos la carta colocada en el tablero por una nueva.
                int valorCarta2 = funcionesGenerales.sacarCarta2();
                System.out.println("BOT :: Carta" + numTirada + " -> " + barajaCartas[valorCarta2]);
                // Insertamos el nombre de la carta en la casilla correspondiente.
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);

                // Comprobamos si la baraja de cartas restantes está ya agoatada o no
                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);

                return true;
            }
            else
            {
                return false;
            }
        }
        else if (botTieneCartaConcreta(txtBotCartaPulsada, "Tanque"))
        {
            if (!accionesBot.getVehBotConseguido(((nombreJug) * 5) + 1))
            {
                tvCasillaVacia.setText("Tanque");

                tvToolbar.setText(ordenNombresEjercitos.get(nombreJug) + context.getResources().getString(R.string.tvToolbarReuneTanque));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.tanque, sonidoJuego);

                funcionesGenerales.colorCarta("Tanque", tvCasillaVacia);
                funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumBotVehConseguidos((nombreJug)), "BOT", tvMensajeVictoria, context);

                numTirada += 1;
                int valorCarta2 = funcionesGenerales.sacarCarta2();
                System.out.println("BOT :: Carta" + numTirada + " -> " + barajaCartas[valorCarta2]);
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);

                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);

                return true;
            }
            else
            {
                return false;
            }
        }
        else if (botTieneCartaConcreta(txtBotCartaPulsada, "Submarino"))
        {
            if (!accionesBot.getVehBotConseguido(((nombreJug) * 5) + 2))
            {
                tvCasillaVacia.setText("Submarino");

                tvToolbar.setText(ordenNombresEjercitos.get(nombreJug) + context.getResources().getString(R.string.tvToolbarReuneSubmarino));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.submarino, sonidoJuego);

                funcionesGenerales.colorCarta("Submarino", tvCasillaVacia);
                funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumBotVehConseguidos((nombreJug)), "BOT", tvMensajeVictoria, context);

                numTirada += 1;
                int valorCarta2 = funcionesGenerales.sacarCarta2();
                System.out.println("BOT :: Carta" + numTirada + " -> " + barajaCartas[valorCarta2]);
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);

                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);

                return true;
            }
            else
            {
                return false;
            }
        }
        else if (botTieneCartaConcreta(txtBotCartaPulsada, "Caza"))
        {
            if (!accionesBot.getVehBotConseguido(((nombreJug) * 5) + 3))
            {
                tvCasillaVacia.setText("Caza");

                tvToolbar.setText(ordenNombresEjercitos.get(nombreJug) + context.getResources().getString(R.string.tvToolbarReuneCaza));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.caza, sonidoJuego);

                funcionesGenerales.colorCarta("Caza", tvCasillaVacia);
                funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumBotVehConseguidos((nombreJug)), "BOT", tvMensajeVictoria, context);

                numTirada += 1;
                int valorCarta2 = funcionesGenerales.sacarCarta2();
                System.out.println("BOT :: Carta" + numTirada + " -> " + barajaCartas[valorCarta2]);
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);

                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);

                return true;
            }
            else
            {
                return false;
            }
        }
        else if (botTieneCartaConcreta(txtBotCartaPulsada, "Vehículo Multicolor"))
        {
            if (!accionesBot.getVehBotConseguido(((nombreJug) * 5) + 4))
            {
                tvCasillaVacia.setText("Vehículo Multicolor");

                tvToolbar.setText(ordenNombresEjercitos.get(nombreJug) + context.getResources().getString(R.string.tvToolbarReuneMulticolor));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.vehmulticolor, sonidoJuego);

                funcionesGenerales.colorCarta("Vehículo Multicolor", tvCasillaVacia);
                funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumBotVehConseguidos((nombreJug)), "BOT", tvMensajeVictoria, context);

                numTirada += 1;
                int valorCarta2 = funcionesGenerales.sacarCarta2();
                System.out.println("BOT :: Carta" + numTirada + " -> " + barajaCartas[valorCarta2]);
                tvCartaPulsada.setText(barajaCartas[valorCarta2]);

                funcionesGenerales.barajaAgotada(numTirada, tvCartaPulsada, tvCartaNoPulsada1, tvCartaNoPulsada2);

                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    // Método que comprobará cuantas casillas del tablero aún están vacías para saber sobre que casillas podemos
    // lanzar un vehículo nuevo.
    private TextView textViewCasillaCartaVacia(TextView tvBotCasilla1, TextView tvBotCasilla2, TextView tvBotCasilla3, TextView tvBotCasilla4)
    {
        ArrayList<TextView> tvBotCasillasVacias = new ArrayList();

        // Si la casilla está vacia la añadimos a un array.
        if (tvBotCasilla1.getText().equals("---"))
        {
            tvBotCasillasVacias.add(tvBotCasilla1);
        }

        if (tvBotCasilla2.getText().equals("---"))
        {
            tvBotCasillasVacias.add(tvBotCasilla2);
        }

        if (tvBotCasilla3.getText().equals("---"))
        {
            tvBotCasillasVacias.add(tvBotCasilla3);
        }

        if (tvBotCasilla4.getText().equals("---"))
        {
            tvBotCasillasVacias.add(tvBotCasilla4);
        }

        // Aplicaremos un random para determinar sobre cual de las casillas vacías el BOT lanzará su vehículo nuevo.
        int posArrayTvBotCasillasVacias = (int)(Math.random()*tvBotCasillasVacias.size());

        if (tvBotCasillasVacias.size() > 1)
        {
            return tvBotCasillasVacias.get(posArrayTvBotCasillasVacias);
        }
        else
        {
            return tvBotCasillasVacias.get(0);
        }
    }

    public boolean lanzarBotIntercambioEquipo(TextView tvCartaPulsada, int posJug, Context context)
    {
        String txtBotCartaPulsada = (String) tvCartaPulsada.getText();

        System.out.println("Kiss -> " + posJug + " - " + rivalConPuntuacionMasAlta);

        if (botTieneCartaConcreta(txtBotCartaPulsada, "Intercambiar Brigada"))
        {
            String strTvBotVeh1 = (String) tvVehiculosJugadores[posJug * 4 + 0].getText();
            String strTvBotVeh2 = (String) tvVehiculosJugadores[posJug * 4 + 1].getText();
            String strTvBotVeh3 = (String) tvVehiculosJugadores[posJug * 4 + 2].getText();
            String strTvBotVeh4 = (String) tvVehiculosJugadores[posJug * 4 + 3].getText();
            String strTvJugVeh1 = (String) tvVehiculosJugadores[rivalConPuntuacionMasAlta * 4 + 0].getText();
            String strTvJugVeh2 = (String) tvVehiculosJugadores[rivalConPuntuacionMasAlta * 4 + 1].getText();
            String strTvJugVeh3 = (String) tvVehiculosJugadores[rivalConPuntuacionMasAlta * 4 + 2].getText();
            String strTvJugVeh4 = (String) tvVehiculosJugadores[rivalConPuntuacionMasAlta * 4 + 3].getText();

            tvVehiculosJugadores[posJug * 4 + 0].setText(strTvJugVeh1);
            tvVehiculosJugadores[posJug * 4 + 1].setText(strTvJugVeh2);
            tvVehiculosJugadores[posJug * 4 + 2].setText(strTvJugVeh3);
            tvVehiculosJugadores[posJug * 4 + 3].setText(strTvJugVeh4);
            relativeJugadores[posJug * 4 + 0].setBackgroundColor(Color.parseColor("#908a8a8a"));
            relativeJugadores[posJug * 4 + 1].setBackgroundColor(Color.parseColor("#908a8a8a"));
            relativeJugadores[posJug * 4 + 2].setBackgroundColor(Color.parseColor("#908a8a8a"));
            relativeJugadores[posJug * 4 + 3].setBackgroundColor(Color.parseColor("#908a8a8a"));

            tvVehiculosJugadores[rivalConPuntuacionMasAlta * 4 + 0].setText(strTvBotVeh1);
            tvVehiculosJugadores[rivalConPuntuacionMasAlta * 4 + 1].setText(strTvBotVeh2);
            tvVehiculosJugadores[rivalConPuntuacionMasAlta * 4 + 2].setText(strTvBotVeh3);
            tvVehiculosJugadores[rivalConPuntuacionMasAlta * 4 + 3].setText(strTvBotVeh4);
            relativeJugadores[rivalConPuntuacionMasAlta * 4 + 0].setBackgroundColor(Color.parseColor("#908a8a8a"));
            relativeJugadores[rivalConPuntuacionMasAlta * 4 + 1].setBackgroundColor(Color.parseColor("#908a8a8a"));
            relativeJugadores[rivalConPuntuacionMasAlta * 4 + 2].setBackgroundColor(Color.parseColor("#908a8a8a"));
            relativeJugadores[rivalConPuntuacionMasAlta * 4 + 3].setBackgroundColor(Color.parseColor("#908a8a8a"));

            String strTvBotComodin11 = (String) tvComodinesJugadores[posJug * 8 + 0].getText();
            String strTvBotComodin12 = (String) tvComodinesJugadores[posJug * 8 + 1].getText();
            String strTvBotComodin21 = (String) tvComodinesJugadores[posJug * 8 + 2].getText();
            String strTvBotComodin22 = (String) tvComodinesJugadores[posJug * 8 + 3].getText();
            String strTvBotComodin31 = (String) tvComodinesJugadores[posJug * 8 + 4].getText();
            String strTvBotComodin32 = (String) tvComodinesJugadores[posJug * 8 + 5].getText();
            String strTvBotComodin41 = (String) tvComodinesJugadores[posJug * 8 + 6].getText();
            String strTvBotComodin42 = (String) tvComodinesJugadores[posJug * 8 + 7].getText();
            String strTvJugComodin11 = (String) tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 0].getText();
            String strTvJugComodin12 = (String) tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 1].getText();
            String strTvJugComodin21 = (String) tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 2].getText();
            String strTvJugComodin22 = (String) tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 3].getText();
            String strTvJugComodin31 = (String) tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 4].getText();
            String strTvJugComodin32 = (String) tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 5].getText();
            String strTvJugComodin41 = (String) tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 6].getText();
            String strTvJugComodin42 = (String) tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 7].getText();

            tvComodinesJugadores[posJug * 8 + 0].setText(strTvJugComodin11);
            tvComodinesJugadores[posJug * 8 + 1].setText(strTvJugComodin12);
            tvComodinesJugadores[posJug * 8 + 2].setText(strTvJugComodin21);
            tvComodinesJugadores[posJug * 8 + 3].setText(strTvJugComodin22);
            tvComodinesJugadores[posJug * 8 + 4].setText(strTvJugComodin31);
            tvComodinesJugadores[posJug * 8 + 5].setText(strTvJugComodin32);
            tvComodinesJugadores[posJug * 8 + 6].setText(strTvJugComodin41);
            tvComodinesJugadores[posJug * 8 + 7].setText(strTvJugComodin42);
            tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 0].setText(strTvBotComodin11);
            tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 1].setText(strTvBotComodin12);
            tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 2].setText(strTvBotComodin21);
            tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 3].setText(strTvBotComodin22);
            tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 4].setText(strTvBotComodin31);
            tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 5].setText(strTvBotComodin32);
            tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 6].setText(strTvBotComodin41);
            tvComodinesJugadores[rivalConPuntuacionMasAlta * 8 + 7].setText(strTvBotComodin42);

            if (rivalConPuntuacionMasAlta == 0)
            {
                tvToolbar.setText(ordenNombresEjercitos.get(posJug - 1) + context.getResources().getString(R.string.tvToolbarIntercambioEjercitoJugador));
            }
            else
            {
                tvToolbar.setText(ordenNombresEjercitos.get(posJug - 1) + context.getResources().getString(R.string.tvToolbarIntercambioEjercitoBot)
                        + ordenNombresEjercitos.get(rivalConPuntuacionMasAlta - 1));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean lanzarBotIntercambioVehiculo(int jugBot2, Context context)
    {
        // Variable que tomará un número random de los vehículos a intercambiar
        int valorRandomIntercambio;
        // Lista que almacena los valoresRandom que van saliendo representados en valorRandomIntercambio
        ArrayList<Integer> valoresRandomIntercambiables = new ArrayList<>();
        // Contador que contabilizará el número de valores que van saliendo
        int contadorIntercambiable = 0;
        // Variable que recoge la máxima diferencia sacada entre el vehículo del BOT y el resto de vehículos intercambiables
        int posDiferenciaMayorVehIntercambiable = 0;
        // Posicion en el array de la mayor diferencia entre contadores.
        int mayorValorRandomIntercambio = -1;

        // Se sacarán valores random hasta que llegue al tamaño de la lista de diferencia de contadores
        for (int i = 0; i < diferenciaVehIntercambiable.size(); i++)
        {
            System.out.println("Pedro - " + diferenciaVehIntercambiable.get(i));

            if (diferenciaVehIntercambiable.size() > 1)
            {
                // Si el valor Random sacado ya esta repetido se vuelve a sacar otro.
                do {
                    valorRandomIntercambio = (int) Math.floor(Math.random() * (0 - diferenciaVehIntercambiable.size() + 1) + diferenciaVehIntercambiable.size());
                    funcionesGenerales.numEsRepetido(valorRandomIntercambio, valoresRandomIntercambiables);

                    System.out.println("Alfredo - " + valorRandomIntercambio);
                }
                while (numRepetido == true);

                // Si el valor Random salido no está repetido se almacena en un array donde se almacenan los valores ya salidos previamente.
                valoresRandomIntercambiables.add(valorRandomIntercambio);

                // Si la diferencia del valor que acaba de salir es mayor que la variable que los recoge entoces esa variable tendrá un nuevo valor.
                if (diferenciaVehIntercambiable.get(valorRandomIntercambio) > posDiferenciaMayorVehIntercambiable)
                {
                    posDiferenciaMayorVehIntercambiable = diferenciaVehIntercambiable.get(valorRandomIntercambio);
                    mayorValorRandomIntercambio = valorRandomIntercambio;
                }
            }
            else
            {
                mayorValorRandomIntercambio = 0;
            }
        }

        System.out.println("Pedro2 - " + mayorValorRandomIntercambio + " - " + diferenciaVehIntercambiable.get(mayorValorRandomIntercambio) + " - "
        + vehBotIntercambiable.get(mayorValorRandomIntercambio) + " - " + vehRestoIntercambiable.get(mayorValorRandomIntercambio) + " - " +
        nombreJugIntercambiable.get(mayorValorRandomIntercambio) + " - " + jugBot2);

        for (int i = jugBot2 * 4; i < jugBot2 * 4 + 4; i++)
        {
            if (tvVehiculosJugadores[i].getText().equals(tipoVehiculoSegunContador(vehBotIntercambiable.get(mayorValorRandomIntercambio))))
            {
                int numJugIntercambiable = nombreJugIntercambiable.get(mayorValorRandomIntercambio);

                System.out.println("Pedro3 - " + numJugIntercambiable);

                for (int a = numJugIntercambiable * 4; a < numJugIntercambiable * 4 + 4; a++)
                {
                    if (tvVehiculosJugadores[a].getText().equals(tipoVehiculoSegunContador(vehRestoIntercambiable.get(mayorValorRandomIntercambio))))
                    {
                        String auxBotIntercambiable = (String) tvVehiculosJugadores[i].getText();
                        String auxRestoIntercambiable = (String) tvVehiculosJugadores[a].getText();
                        String auxComBotIntercambiable = (String) tvComodinesJugadores[i * 2].getText();
                        String auxComRestoIntercambiable = (String) tvComodinesJugadores[a * 2].getText();

                        System.out.println("Pedro3 - " + auxBotIntercambiable + " - " + auxRestoIntercambiable);

                        tvVehiculosJugadores[i].setText(auxRestoIntercambiable);
                        tvVehiculosJugadores[a].setText(auxBotIntercambiable);
                        tvComodinesJugadores[i * 2].setText(auxComRestoIntercambiable);
                        tvComodinesJugadores[a * 2].setText(auxComBotIntercambiable);

                        relativeJugadores[i].setBackgroundColor(Color.parseColor("#908a8a8a"));
                        relativeJugadores[a].setBackgroundColor(Color.parseColor("#908a8a8a"));

                        if (numJugIntercambiable == 0)
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(jugBot2 - 1)
                                    + context.getResources().getString(R.string.tvToolbarIntercambioUnidadBot1) + auxBotIntercambiable
                                    + context.getResources().getString(R.string.tvToolbarIntercambioUnidadBot2) + auxRestoIntercambiable
                                    + context.getResources().getString(R.string.tvToolbarIntercambioUnidad3Jugador));
                        }
                        else
                        {
                            tvToolbar.setText(ordenNombresEjercitos.get(jugBot2 - 1)
                                    + context.getResources().getString(R.string.tvToolbarIntercambioUnidadBot1) + auxBotIntercambiable
                                    + context.getResources().getString(R.string.tvToolbarIntercambioUnidadBot2) + auxRestoIntercambiable
                                    + context.getResources().getString(R.string.tvToolbarIntercambioUnidad3Bot)
                                    + ordenNombresEjercitos.get(numJugIntercambiable - 1));
                        }

                        System.out.println("Draft - " + tvToolbar.getText());

                        break;
                    }
                }
            }
        }

        return true;
    }

    public String tipoVehiculoSegunContador(int contador)
    {
        if (contador % 5 == 0)
        {
            return "Batallon";
        }
        else if (contador % 5 == 1)
        {
            return "Tanque";
        }
        else if (contador % 5 == 2)
        {
            return "Submarino";
        }
        else if (contador % 5 == 3)
        {
            return "Caza";
        }
        else
        {
            return "Vehículo Multicolor";
        }
    }

    // Comprobamos si la carta sacada de la baraja no se ha sacado anteriormente.
    public void numEsRepetido(int numSacado, ArrayList<Integer> baraja)
    {
        numRepetido = false;

        for (int i = 0; i < baraja.size(); i++)
        {
            if (numSacado == baraja.get(i))
            {
                System.out.println("Turno: listNumJug -> " + numSacado + " = " + baraja.get(i));
                numRepetido = true;
                break;
            }
            else
            {
                numRepetido = false;
            }
        }
    }

    public void ejecutarBotIntercambio2(String strVehBot, String strVehJug, TextView vehiculosBot[], TextView vehiculosJug[],
                                        TextView vehCom1Bot[], TextView vehCom2Bot[], TextView vehCom1Jug[], TextView vehCom2Jug[],
                                        int jugBot, int jugRival, Context context)
    {
        System.out.println("Navajero -> Oden: " + strVehBot + " - " + strVehJug);

        for (int i = 0; i < vehiculosBot.length; i++)
        {
            for (int j = 0; j < vehiculosJug.length; j++)
            {
                if (tvConCartaConcreta(vehiculosBot[i], strVehBot) && tvConCartaConcreta(vehiculosJug[j], strVehJug))
                {
                    ejecutarBotIntercambio(vehiculosBot[i], vehiculosJug[j],
                            vehCom1Bot[i], vehCom2Bot[i], vehCom1Jug[j], vehCom2Jug[j], jugBot, jugRival, context);
                }
            }
        }
    }

    public void ejecutarBotIntercambio(TextView tvBotVeh, TextView tvJugVeh, TextView tvBotCom1, TextView tvBotCom2,
                                       TextView tvJugCom1, TextView tvJugCom2, int jugBot, int jugRival, Context context)
    {
        tvToolbar.setText(ordenNombresEjercitos.get(jugBot)
                + context.getResources().getString(R.string.tvToolbarIntercambioUnidadBot1) + tvBotVeh.getText()
                + context.getResources().getString(R.string.tvToolbarIntercambioUnidadBot2) + tvJugVeh.getText()
                + context.getResources().getString(R.string.tvToolbarIntercambioUnidad3Bot) + ordenNombresEjercitos.get(jugRival));

        System.out.println("Mensajes - "+  tvToolbar.getText());

        String auxTvTxtVehiculo = (String) tvBotVeh.getText();
        String auxTvTxtVehiculo2 = (String) tvJugVeh.getText();
        tvBotVeh.setText(auxTvTxtVehiculo2);
        tvJugVeh.setText(auxTvTxtVehiculo);

        String auxTvTxtCom1 = (String) tvBotCom1.getText();
        String aux2TvTxtCom1 = (String) tvJugCom1.getText();
        tvBotCom1.setText(aux2TvTxtCom1);
        tvJugCom1.setText(auxTvTxtCom1);

        String auxTvTxtCom2 = (String) tvBotCom2.getText();
        String aux2TvTxtCom2 = (String) tvJugCom2.getText();
        tvBotCom2.setText(aux2TvTxtCom2);
        tvJugCom2.setText(auxTvTxtCom2);

        System.out.println("Navajero -> NuevoValorBot: " + tvJugVeh.getText() + " - " + "NuevoValorJug: " + auxTvTxtVehiculo);
    }

    public boolean tvConCartaConcreta(TextView tvVeh, String txtBotCarta)
    {
        if (tvVeh.getText().equals(txtBotCarta))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int relacionarCasillaConArrayVehConseguidos(TextView tvCasilla)
    {
        if (tvCasilla.getText().equals("Batallon"))
        {
            return 0;
        }
        else if (tvCasilla.getText().equals("Tanque"))
        {
            return 1;
        }
        else if (tvCasilla.getText().equals("Submarino"))
        {
            return 2;
        }
        else if (tvCasilla.getText().equals("Caza"))
        {
            return 3;
        }
        else if (tvCasilla.getText().equals("Vehículo Multicolor"))
        {
            return 4;
        }
        else
        {
            return 5;
        }
    }

    public boolean lanzarBotRoboVehiculo(TextView tvCartaPulsada, TextView tvMensajeVictoria, AccionesBot accionesBot,
                                         int posJug2, Context context)
    {
        int posJug = posJug2 - 1;

        String txtBotCartaPulsada = (String) tvCartaPulsada.getText();

        int posRestoJug2 = posValorMayorEcharCartaRoboVeh(posJug2);
        int posRestoJug = posRestoJug2 - 1;

        System.out.println("Rubia -> " + posJug + " - " + posRestoJug);

        if (botTieneCartaConcreta(txtBotCartaPulsada, "Robar Vehículo"))
        {
            boolean arrVehBotConseguidos[] = new boolean[]{
                    accionesBot.getVehBotConseguido(posJug * 5 + 0), accionesBot.getVehBotConseguido(posJug * 5 + 1), accionesBot.getVehBotConseguido(posJug * 5 + 2),
                    accionesBot.getVehBotConseguido(posJug * 5 + 3), accionesBot.getVehBotConseguido(posJug * 5 + 4), false};

            boolean arrVehJugConseguidos[];
            int arrContadVehRestoJug[];
            if (posRestoJug2 == 0)
            {
                arrVehJugConseguidos = new boolean[] {
                        accionesBot.getVeh1JugConseguido(), accionesBot.getVeh2JugConseguido(), accionesBot.getVeh3JugConseguido(),
                        accionesBot.getVeh4JugConseguido(), accionesBot.getVehMulticolorJugConseguido(), false};

                arrContadVehRestoJug = new int[] {
                        accionesBot.getContadorJugBatallon(), accionesBot.getContadorJugTanque(), accionesBot.getContadorJugSubmarino(),
                        accionesBot.getContadorJugCaza(), accionesBot.getContadorJugVehMulticolor(), -5};
            }
            else
            {
                arrVehJugConseguidos = new boolean[]{
                        accionesBot.getVehBotConseguido(posRestoJug * 5 + 0), accionesBot.getVehBotConseguido(posRestoJug * 5 + 1),
                        accionesBot.getVehBotConseguido(posRestoJug * 5 + 2), accionesBot.getVehBotConseguido(posRestoJug * 5 + 3),
                        accionesBot.getVehBotConseguido(posRestoJug * 5 + 4), false};

                arrContadVehRestoJug = new int[] {
                        accionesBot.getContadorBotBatallon(posRestoJug), accionesBot.getContadorBotTanque(posRestoJug), accionesBot.getContadorBotSubmarino(posRestoJug),
                        accionesBot.getContadorBotCaza(posRestoJug), accionesBot.getContadorBotVehMulticolor(posRestoJug), -5};
            }

            TextView tvCasillasBot[] = new TextView[]{ tvVehiculosJugadores[posJug2 * 4 + 0], tvVehiculosJugadores[posJug2 * 4 + 1],
                    tvVehiculosJugadores[posJug2 * 4 + 2], tvVehiculosJugadores[posJug2 * 4 + 3]};
            TextView tvCasillasBotCom1[] = new TextView[]{ tvComodinesJugadores[posJug2 * 8 + 0], tvComodinesJugadores[posJug2 * 8 + 2],
                    tvComodinesJugadores[posJug2 * 8 + 4], tvComodinesJugadores[posJug2 * 8 + 6]};
            TextView tvCasillasBotCom2[] = new TextView[]{ tvComodinesJugadores[posJug2 * 8 + 1], tvComodinesJugadores[posJug2 * 8 + 3],
                    tvComodinesJugadores[posJug2 * 8 + 5], tvComodinesJugadores[posJug2 * 8 + 7]};
            RelativeLayout relVehBot[] = new RelativeLayout[]{ relativeJugadores[posJug2 * 4 + 0], relativeJugadores[posJug2 * 4 + 1],
                    relativeJugadores[posJug2 * 4 + 2], relativeJugadores[posJug2 * 4 + 3]};
            ImageView ivVehBot[] = new ImageView[]{ ivVehiculosJugadores[posJug2 * 4 + 0], ivVehiculosJugadores[posJug2 * 4 + 1],
                    ivVehiculosJugadores[posJug2 * 4 + 2], ivVehiculosJugadores[posJug2 * 4 + 3]};

            TextView tvCasillasJug[] = new TextView[]{ tvVehiculosJugadores[posRestoJug2 * 4 + 0], tvVehiculosJugadores[posRestoJug2 * 4 + 1],
                    tvVehiculosJugadores[posRestoJug2 * 4 + 2], tvVehiculosJugadores[posRestoJug2 * 4 + 3]};
            TextView tvCasillasJugCom1[] = new TextView[]{ tvComodinesJugadores[posRestoJug2 * 8 + 0], tvComodinesJugadores[posRestoJug2 * 8 + 2],
                    tvComodinesJugadores[posRestoJug2 * 8 + 4], tvComodinesJugadores[posRestoJug2 * 8 + 6]};
            TextView tvCasillasJugCom2[] = new TextView[]{ tvComodinesJugadores[posRestoJug2 * 8 + 1], tvComodinesJugadores[posRestoJug2 * 8 + 3],
                    tvComodinesJugadores[posRestoJug2 * 8 + 5], tvComodinesJugadores[posRestoJug2 * 8 + 7]};
            RelativeLayout relVehJug[] = new RelativeLayout[]{ relativeJugadores[posRestoJug2 * 4 + 0], relativeJugadores[posRestoJug2 * 4 + 1],
                    relativeJugadores[posRestoJug2 * 4 + 2], relativeJugadores[posRestoJug2 * 4 + 3]};
            ImageView ivVehJug[] = new ImageView[]{ ivVehiculosJugadores[posRestoJug2 * 4 + 0], ivVehiculosJugadores[posRestoJug2 * 4 + 1],
                    ivVehiculosJugadores[posRestoJug2 * 4 + 2], ivVehiculosJugadores[posRestoJug2 * 4 + 3]};

            int mejorVeh = -2;

            int vehBotRobar = -1;

            for (int i = 0; i < arrVehBotConseguidos.length; i++)
            {
                System.out.println("Rubia Entraa0 -> " + i + ": " + arrVehBotConseguidos[i]
                        + " = " + arrVehJugConseguidos[i] + " y contador es "
                        + arrContadVehRestoJug[i]);

                if (!arrVehBotConseguidos[i])
                {
                    if (arrVehJugConseguidos[i])
                    {
                        if (arrContadVehRestoJug[i] < 2)
                        {
                            int nuevoVeh = arrContadVehRestoJug[i];
                            System.out.println("Rubia Entraa1" + nuevoVeh);
                            System.out.println("Rubia Entraa1" + nuevoVeh);

                            if (nuevoVeh > mejorVeh)
                            {
                                System.out.println("Rubia Entraa2" + nuevoVeh);
                                mejorVeh = nuevoVeh;

                                vehBotRobar = i;
                            }
                        }
                    }
                }
            }

            System.out.println("Rubia Entraa0 -> " + mejorVeh + " - " + vehBotRobar);

            int j = 0;

            for (int i = 0; i < 4; i++)
            {
                if (tvCasillasBot[i].getText().equals("---"))
                {
                    j = i;
                    break;
                }
            }

            switch (vehBotRobar)
            {
                case 0:
                    ejecutarRoboBot(tvCasillasJug, "Batallon");
                    break;

                case 1:
                    ejecutarRoboBot(tvCasillasJug, "Tanque");
                    break;

                case 2:
                    ejecutarRoboBot(tvCasillasJug, "Submarino");
                    break;

                case 3:
                    ejecutarRoboBot(tvCasillasJug, "Caza");
                    break;

                case 4:
                    ejecutarRoboBot(tvCasillasJug, "Vehículo Multicolor");
                    break;
            }

            if (posRestoJug2 == 0)
            {
                tvToolbar.setText(ordenNombresEjercitos.get(posJug)
                        + context.getResources().getString(R.string.tvToolbarRobaUnidad)
                        + tvCasillasJug[numCasillaVehJugRobar].getText() + " al JUGADOR");
            }
            else
            {
                tvToolbar.setText(ordenNombresEjercitos.get(posJug)
                        + context.getResources().getString(R.string.tvToolbarRobaUnidad)
                        + tvCasillasJug[numCasillaVehJugRobar].getText() + " al " + ordenNombresEjercitos.get(posRestoJug2 - 1));
            }

            tvCasillasBot[j].setText(tvCasillasJug[numCasillaVehJugRobar].getText());
            tvCasillasBotCom1[j].setText(tvCasillasJugCom1[numCasillaVehJugRobar].getText());
            tvCasillasBotCom2[j].setText(tvCasillasJugCom2[numCasillaVehJugRobar].getText());
            relVehBot[j].setBackgroundColor(Color.parseColor("#908a8a8a"));

            tvCasillasJug[numCasillaVehJugRobar].setText("---");
            tvCasillasJugCom1[numCasillaVehJugRobar].setText("---");
            tvCasillasJugCom2[numCasillaVehJugRobar].setText("---");
            relVehJug[numCasillaVehJugRobar].setBackgroundColor(Color.parseColor("#908a8a8a"));

            funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumBotVehConseguidos(posJug), "BOT", tvMensajeVictoria, context);

            return true;
        }
        else
        {
            return false;
        }
    }

    public void ejecutarRoboBot(TextView tvCasillasJug[], String tipoVeh)
    {
        for (int i = 0; i < tvCasillasJug.length; i++)
        {
            if (tvCasillasJug[i].getText().equals(tipoVeh))
            {
                numCasillaVehJugRobar = i;
            }
        }
    }

    public void limpiarVirus(TextView tvBotCom1, ArrayList arrComVehBot)
    {
        if (tvBotCom1.getText().equals("Metralleta") || tvBotCom1.getText().equals("Lanzacohetes") ||
                tvBotCom1.getText().equals("Torpedos") || tvBotCom1.getText().equals("Misiles") ||
                tvBotCom1.getText().equals("Arma Multicolor"))
        {
            arrComVehBot.add(tvBotCom1.getText());
            tvBotCom1.setText("---");
        }
    }

    public boolean lanzarBotExpulsionArmas(TextView tvCartaPulsada, AccionesBot accionesBot, int posJug2, Context context)
    {
        RelativeLayout relVehBot[] = new RelativeLayout[]{ relativeJugadores[posJug2 * 4 + 0],
                relativeJugadores[posJug2 * 4 + 1],
                relativeJugadores[posJug2 * 4 + 2],
                relativeJugadores[posJug2 * 4 + 3]};

        ImageView ivVehBot[] = new ImageView[]{ ivVehiculosJugadores[posJug2 * 4 + 0],
                ivVehiculosJugadores[posJug2 * 4 + 1],
                ivVehiculosJugadores[posJug2 * 4 + 2],
                ivVehiculosJugadores[posJug2 * 4 + 3]};

        String txtBotCartaPulsada = (String) tvCartaPulsada.getText();

        tvToolbar.setText(ordenNombresEjercitos.get(posJug2 - 1)
                + context.getResources().getString(R.string.tvToolbarBotiquinDorado));

        relVehBot[posJug2].setBackgroundColor(Color.parseColor("#90dccb09"));

        if (botTieneCartaConcreta(txtBotCartaPulsada, "Expulsar Armas"))
        {
            ArrayList<String> arrVehConVirus = new ArrayList<>();

            for (int i = posJug2 * 8; i < posJug2 * 8 + 8; i++)
            {
                if (i == posJug2 * 8 + 0 || i == posJug2 * 8 + 2 || i == posJug2 * 8 + 4 || i == posJug2 * 8 + 6)
                {
                    if (tvComodinesJugadores[i].getText().equals("Metralleta") || tvComodinesJugadores[i].getText().equals("Lanzacohetes") ||
                    tvComodinesJugadores[i].getText().equals("Torpedos") || tvComodinesJugadores[i].getText().equals("Misiles") ||
                            tvComodinesJugadores[i].getText().equals("Arma Multicolor"))
                    {
                        ejecutarExpulsionArmas((String) tvComodinesJugadores[i].getText(), accionesBot, context, posJug2);
                        limpiarVirus(tvComodinesJugadores[i], arrVehConVirus);
                    }
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean vehiculoIntercambiable(ArrayList<Integer> contadorBotConseguidos, ArrayList<Integer> contadorJugConseguidos,
                                          ArrayList<Boolean> vehBotConseguidos, ArrayList<Boolean> vehRestoConseguidos, int numJugadores, int nombreJug2)
    {
        boolean hayVehiculosIntercambiables = false;

        vehBotIntercambiable.clear();
        vehRestoIntercambiable.clear();
        nombreJugIntercambiable.clear();
        diferenciaVehIntercambiable.clear();

        // Recorremos todos los vehículos conseguidos o no por el BOT
        for (int i = 0; i < vehBotConseguidos.size(); i++)
        {
            System.out.println("JuanBOT - " + vehBotConseguidos.get(i) + " - " + contadorBotConseguidos.get(i));
            // Entrará a comprobar sólo aquellos vehículos conseguidos y que no estén acorazados
            if (contadorBotConseguidos.get(i) > -5 && contadorBotConseguidos.get(i) < 2)
            {
                // Recorro al resto de jugadores de la partida
                for (int j = 0; j < numJugadores; j++)
                {
                    // Si el BOT tiene un vehículo y alguno de los otros jugadores tiene el mismo vehículo pero con mayor contador será intercambiable
                    if (vehBotConseguidos.get(i) && vehRestoConseguidos.get(j * 5 + i) && contadorBotConseguidos.get(i) < contadorJugConseguidos.get(j * 5 + i)
                            && contadorJugConseguidos.get(j * 5 + i) < 2)
                    {
                        int diferenciaCont = contadorJugConseguidos.get(j * 5 + i) - contadorBotConseguidos.get(i);

                        vehBotIntercambiable.add(i);
                        vehRestoIntercambiable.add(i);
                        nombreJugIntercambiable.add(j);
                        diferenciaVehIntercambiable.add(diferenciaCont);

                        hayVehiculosIntercambiables = true;
                    }

                    // Recorremos todos los vehículos conseguidos o no por el resto de jugadores
                    for (int k = 0; k < vehBotConseguidos.size(); k++)
                    {
                        System.out.println("JuanResto - " + vehRestoConseguidos.get(j * 5 + k) + " - " + contadorJugConseguidos.get(j * 5 + k));
                        // Si el BOT tiene un vehículo que el contrario no tiene y viceversa con un contador menor el vehículo es intercambiable.
                        if (vehBotConseguidos.get(i) && !vehRestoConseguidos.get(j * 5 + i)  && !vehBotConseguidos.get(k) && vehRestoConseguidos.get(j * 5 + k)
                                && contadorBotConseguidos.get(i) < contadorJugConseguidos.get(j * 5 + k) && contadorJugConseguidos.get(j * 5 + k) < 2)
                        {
                            int diferenciaCont = contadorJugConseguidos.get(j * 5 + k) - contadorBotConseguidos.get(i);

                            vehBotIntercambiable.add(i);
                            vehRestoIntercambiable.add(j * 5 + k);
                            nombreJugIntercambiable.add(j);
                            diferenciaVehIntercambiable.add(diferenciaCont);

                            hayVehiculosIntercambiables = true;
                        }
                    }

                    System.out.println("JuanResto ------------------------------------------------------- " );
                }
            }
        }

        System.out.println("Antonio " + hayVehiculosIntercambiables);

        return  hayVehiculosIntercambiables;
    }

    public void ejecutarExpulsionArmas(String armaBot, AccionesBot accionesBot, Context context, int numeroJug)
    {
        TextView tvVehiculosJug[] = new TextView[25];
        int posTvVehiculosJug = 0;

        System.out.println("Elvis: " + numeroJug);

        for (int i = 0; i < tvVehiculosJugadores.length; i++)
        {
            if (i != 4 * numeroJug + 0 && i != 4 * numeroJug + 1 && i != 4 * numeroJug + 2 && i != 4 * numeroJug + 3)
            {
                tvVehiculosJug[posTvVehiculosJug] = tvVehiculosJugadores[i];
                posTvVehiculosJug += 1;
            }
        }

        // Hacemos lo mismo con los comodines de las casillas de las cartas de los jugadores rivales.
        TextView tvComodines1Jug[] = new TextView[25];
        int posTvComodinesJug = 0;

        for (int i = 0; i < tvComodinesJugadores.length; i++)
        {
            if (i != 8 * numeroJug + 0 && i != 8 * numeroJug + 1 && i != 8 * numeroJug + 2 && i != 8 * numeroJug + 3 &&
                    i != 8 * numeroJug + 4 && i != 8 * numeroJug + 5 && i != 8 * numeroJug + 6 && i != 8 * numeroJug + 7)
            {
                long z = i % 2;

                if (z == 0)
                {
                    tvComodines1Jug[posTvComodinesJug] = tvComodinesJugadores[i];
                    posTvComodinesJug += 1;
                }
            }
        }

        // Recogemos en un nuevo array el orden de preferencia que tendrán los textview que recogerán las armas
        // expulsadas por el jugador.
        TextView nuevoTvVehiculosJug[] = funcionesGenerales.reordenarTvVehiculosRivales(tvVehiculosJug);
        TextView nuevoTvComodinesJug[] = funcionesGenerales.reordenarTvComodinesRivales(tvComodines1Jug);

        for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
        {
            System.out.println("Hinds -> " + i + ": " + nuevoTvVehiculosJug[i].getText() + " - " + nuevoTvComodinesJug[i].getText() + " - " + numeroJug);
        }

        ArrayList<TextView> tvVehJugPosibles = new ArrayList<>();

        // Comprobamos primero que tipo de arma ha expulsado el jugador
        if (armaBot.equals("Metralleta"))
        {
            // Recorremos el tablero del Bot en busca de vehículos que pueda recoger esa arma
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                if (i != 4 * numeroJug + 0 || i != 4 * numeroJug + 1 || i != 4 * numeroJug + 2 || i != 4 * numeroJug + 3)
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosJug[i], context, 7, 8);

                    if (jugBot < 0)
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Batallon") && (accionesBot.getContadorJugBatallon() < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorJugVehMulticolor() < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                    }
                    else
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Batallon") && (accionesBot.getContadorBotBatallon(jugBot) < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                    }
                }
            }

            // A continuación, seleccionamos aleatoriamente el vehículo que recogerá esa arma
            TextView tvJugElegido = new TextView(context);
            if (tvVehJugPosibles.size() > 0)
            {
                tvJugElegido = tvVehJugPosibles.get(generarNumAleatorio(tvVehJugPosibles.size() - 1));
            }

            // Recorremos de nuevo el tablero del BOT en busca del vehículo seleccionado
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                System.out.println("Eyenga -> " + tvJugElegido.getText() + " - " + nuevoTvVehiculosJug[i].getText());

                if (tvJugElegido.getText().equals(nuevoTvVehiculosJug[i].getText()))
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosJug[i], context, 7, 8);

                    if (jugBot < 0)
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Batallon"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugBatallon() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                                expulsionArmas = true;
                            }
                            else if (accionesBot.getContadorJugBatallon() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Metralleta");
                            }
                            else if (accionesBot.getContadorJugBatallon() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugVehMulticolor() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorJugVehMulticolor() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Metralleta");
                            }
                            else if (accionesBot.getContadorJugVehMulticolor() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }

                        break;
                    }
                    else
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Batallon"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotBatallon(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                                expulsionArmas = true;
                            }
                            else if (accionesBot.getContadorBotBatallon(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Metralleta");
                            }
                            else if (accionesBot.getContadorBotBatallon(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Metralleta");
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }

                        break;
                    }
                }
            }
        }
        else if (armaBot.equals("Lanzacohetes"))
        {
            // Recorremos el tablero del Bot en busca de vehículos que pueda recoger esa arma
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                if (i != 4 * numeroJug + 0 || i != 4 * numeroJug + 1 || i != 4 * numeroJug + 2 || i != 4 * numeroJug + 3)
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosJug[i], context, 7, 8);

                    if (jugBot < 0)
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Tanque") && (accionesBot.getContadorJugTanque() < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorJugVehMulticolor() < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                    }
                    else
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Tanque") && (accionesBot.getContadorBotTanque(jugBot) < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                    }
                }
            }

            // A continuación, seleccionamos aleatoriamente el vehículo que recogerá esa arma
            TextView tvJugElegido = new TextView(context);
            if (tvVehJugPosibles.size() > 0)
            {
                tvJugElegido = tvVehJugPosibles.get(generarNumAleatorio(tvVehJugPosibles.size() - 1));
            }

            // Recorremos de nuevo el tablero del BOT en busca del vehículo seleccionado
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                System.out.println("Eyenga -> " + tvJugElegido.getText() + " - " + nuevoTvVehiculosJug[i].getText());

                if (tvJugElegido.getText().equals(nuevoTvVehiculosJug[i].getText()))
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosJug[i], context, 7, 8);

                    if (jugBot < 0)
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Tanque"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugTanque() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                                expulsionArmas = true;
                            }
                            else if (accionesBot.getContadorJugTanque() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Lanzacohetes");
                            }
                            else if (accionesBot.getContadorJugTanque() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugVehMulticolor() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorJugVehMulticolor() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Lanzacohetes");
                            }
                            else if (accionesBot.getContadorJugVehMulticolor() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }

                        break;
                    }
                    else
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Tanque"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotTanque(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                                expulsionArmas = true;
                            }
                            else if (accionesBot.getContadorBotTanque(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Lanzacohetes");
                            }
                            else if (accionesBot.getContadorBotTanque(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Lanzacohetes");
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }

                        break;
                    }
                }
            }
        }
        else if (armaBot.equals("Torpedos"))
        {
            // Recorremos el tablero del Bot en busca de vehículos que pueda recoger esa arma
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                if (i != 4 * numeroJug + 0 || i != 4 * numeroJug + 1 || i != 4 * numeroJug + 2 || i != 4 * numeroJug + 3)
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosJug[i], context, 7, 8);

                    if (jugBot < 0)
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Submarino") && (accionesBot.getContadorJugSubmarino() < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorJugVehMulticolor() < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                    }
                    else
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Tanque") && (accionesBot.getContadorBotSubmarino(jugBot) < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                    }
                }
            }

            // A continuación, seleccionamos aleatoriamente el vehículo que recogerá esa arma
            TextView tvJugElegido = new TextView(context);
            if (tvVehJugPosibles.size() > 0)
            {
                tvJugElegido = tvVehJugPosibles.get(generarNumAleatorio(tvVehJugPosibles.size() - 1));
            }

            // Recorremos de nuevo el tablero del BOT en busca del vehículo seleccionado
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                System.out.println("Eyenga -> " + tvJugElegido.getText() + " - " + nuevoTvVehiculosJug[i].getText());

                if (tvJugElegido.getText().equals(nuevoTvVehiculosJug[i].getText()))
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosJug[i], context, 7, 8);

                    if (jugBot < 0)
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Submarino"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugSubmarino() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                                expulsionArmas = true;
                            }
                            else if (accionesBot.getContadorJugSubmarino() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Torpedos");
                            }
                            else if (accionesBot.getContadorJugSubmarino() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugVehMulticolor() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorJugVehMulticolor() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Torpedos");
                            }
                            else if (accionesBot.getContadorJugVehMulticolor() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }

                        break;
                    }
                    else
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Submarino"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotSubmarino(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                                expulsionArmas = true;
                            }
                            else if (accionesBot.getContadorBotSubmarino(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Torpedos");
                            }
                            else if (accionesBot.getContadorBotSubmarino(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Torpedos");
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }

                        break;
                    }
                }
            }
        }
        else if (armaBot.equals("Misiles"))
        {
            // Recorremos el tablero del Bot en busca de vehículos que pueda recoger esa arma
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                if (i != 4 * numeroJug + 0 || i != 4 * numeroJug + 1 || i != 4 * numeroJug + 2 || i != 4 * numeroJug + 3)
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosJug[i], context, 7, 8);

                    if (jugBot < 0)
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Caza") && (accionesBot.getContadorJugCaza() < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorJugVehMulticolor() < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                    }
                    else
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Caza") && (accionesBot.getContadorBotCaza(jugBot) < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                    }
                }
            }

            // A continuación, seleccionamos aleatoriamente el vehículo que recogerá esa arma
            TextView tvJugElegido = new TextView(context);
            if (tvVehJugPosibles.size() > 0)
            {
                tvJugElegido = tvVehJugPosibles.get(generarNumAleatorio(tvVehJugPosibles.size() - 1));
            }

            // Recorremos de nuevo el tablero del BOT en busca del vehículo seleccionado
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                System.out.println("Eyenga -> " + tvJugElegido.getText() + " - " + nuevoTvVehiculosJug[i].getText());

                if (tvJugElegido.getText().equals(nuevoTvVehiculosJug[i].getText()))
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosJug[i], context, 7, 8);

                    if (jugBot < 0)
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Caza"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugCaza() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                                expulsionArmas = true;
                            }
                            else if (accionesBot.getContadorJugCaza() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Misiles");
                            }
                            else if (accionesBot.getContadorJugCaza() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugVehMulticolor() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorJugVehMulticolor() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Misiles");
                            }
                            else if (accionesBot.getContadorJugVehMulticolor() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }

                        break;
                    }
                    else
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Caza"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotCaza(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                                expulsionArmas = true;
                            }
                            else if (accionesBot.getContadorBotCaza(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Misiles");
                            }
                            else if (accionesBot.getContadorBotCaza(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Misiles");
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }

                        break;
                    }
                }
            }
        }
        else if (armaBot.equals("Arma Multicolor"))
        {
            // Recorremos el tablero del Bot en busca de vehículos que pueda recoger esa arma
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                if (i != 4 * numeroJug + 0 || i != 4 * numeroJug + 1 || i != 4 * numeroJug + 2 || i != 4 * numeroJug + 3)
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosJug[i], context, 7, 8);

                    if (jugBot < 0)
                    {
                        // Si hay un vehículo que puede recoger ese arma le añadimos a la lista de vehículos posibles
                        if ((nuevoTvVehiculosJug[i].getText().equals("Batallon") && (accionesBot.getContadorJugBatallon() < 2)) ||
                                (nuevoTvVehiculosJug[i].getText().equals("Tanque") && (accionesBot.getContadorJugTanque() < 2)) ||
                                (nuevoTvVehiculosJug[i].getText().equals("Submarino") && (accionesBot.getContadorJugSubmarino() < 2)) ||
                                (nuevoTvVehiculosJug[i].getText().equals("Caza") && (accionesBot.getContadorJugCaza() < 2)) ||
                                (nuevoTvVehiculosJug[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorJugVehMulticolor() < 2)))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                    }
                    else
                    {
                        // Si hay un vehículo que puede recoger ese arma le añadimos a la lista de vehículos posibles
                        if ((nuevoTvVehiculosJug[i].getText().equals("Batallon") && (accionesBot.getContadorBotBatallon(jugBot) < 2)) ||
                                (nuevoTvVehiculosJug[i].getText().equals("Tanque") && (accionesBot.getContadorBotTanque(jugBot) < 2)) ||
                                (nuevoTvVehiculosJug[i].getText().equals("Submarino") && (accionesBot.getContadorBotSubmarino(jugBot) < 2)) ||
                                (nuevoTvVehiculosJug[i].getText().equals("Caza") && (accionesBot.getContadorBotCaza(jugBot) < 2)) ||
                                (nuevoTvVehiculosJug[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2)))
                        {
                            tvVehJugPosibles.add(nuevoTvVehiculosJug[i]);
                        }
                    }
                }
            }

            // A continuación, seleccionamos aleatoriamente el vehículo que recogerá esa arma

            TextView tvBotElegido = null;
            if (tvVehJugPosibles.size() > 0)
            {
                tvBotElegido = tvVehJugPosibles.get(generarNumAleatorio(tvVehJugPosibles.size()-1));
            }

            // Recorremos de nuevo el tablero del BOT en busca del vehículo seleccionado
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                System.out.println("Eyenga -> " + tvBotElegido.getText() + " - " + nuevoTvVehiculosJug[i].getText());
                if (tvBotElegido.getText().equals(nuevoTvVehiculosJug[i].getText()))
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosJug[i], context, 7, 8);

                    if (jugBot < 0)
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Batallon"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugBatallon() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorJugBatallon() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Arma Multicolor");
                            }
                            else if (accionesBot.getContadorJugBatallon() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Tanque"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugTanque() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorJugTanque() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Arma Multicolor");
                            }
                            else if (accionesBot.getContadorJugTanque() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Submarino"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugSubmarino() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorJugSubmarino() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Arma Multicolor");
                            }
                            else if (accionesBot.getContadorJugSubmarino() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Caza"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugCaza() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorJugCaza() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Arma Multicolor");
                            }
                            else if (accionesBot.getContadorJugCaza() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorJugVehMulticolor() == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorJugVehMulticolor() == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Arma Multicolor");
                            }
                            else if (accionesBot.getContadorJugVehMulticolor() == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                    }
                    else
                    {
                        if (nuevoTvVehiculosJug[i].getText().equals("Batallon"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotBatallon(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorBotBatallon(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Arma Multicolor");
                            }
                            else if (accionesBot.getContadorBotBatallon(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Tanque"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotTanque(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorBotTanque(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Arma Multicolor");
                            }
                            else if (accionesBot.getContadorBotTanque(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Submarino"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotSubmarino(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorBotSubmarino(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Arma Multicolor");
                            }
                            else if (accionesBot.getContadorBotSubmarino(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else if (nuevoTvVehiculosJug[i].getText().equals("Caza"))
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotCaza(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorBotCaza(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Arma Multicolor");
                            }
                            else if (accionesBot.getContadorBotCaza(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                        else
                        {
                            // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                            if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                            {
                                nuevoTvComodinesJug[i].setText("---");
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                            {
                                nuevoTvComodinesJug[i].setText("Arma Multicolor");
                            }
                            else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                            {
                                nuevoTvVehiculosJug[i].setText("---");
                                nuevoTvComodinesJug[i].setText("---");
                            }
                        }
                    }

                    break;
                }
            }
        }
    }

    public int saberContadorPorTvVehiculo(TextView tvVehiculo, View view, AccionesBot accionesBot)
    {
        String idTvVehiculo = tvVehiculo.getResources().getResourceEntryName(view.getId());

        if (idTvVehiculo.substring(4, 7).equals("Jug"))
        {
            if (tvVehiculo.getText().equals("Batallon"))
            {
                return accionesBot.getContadorJugBatallon();
            }
            else if (tvVehiculo.getText().equals("Tanque"))
            {
                return accionesBot.getContadorJugTanque();
            }
            else if (tvVehiculo.getText().equals("Submarino"))
            {
                return accionesBot.getContadorJugSubmarino();
            }
            else if (tvVehiculo.getText().equals("Caza"))
            {
                return accionesBot.getContadorJugCaza();
            }
            else
            {
                return accionesBot.getContadorJugVehMulticolor();
            }
        }
        else
        {
            int numJugBot = Integer.parseInt(idTvVehiculo.substring(7, 8)) - 1;

            if (tvVehiculo.getText().equals("Batallon"))
            {
                return accionesBot.getContadorBotBatallon(numJugBot);
            }
            else if (tvVehiculo.getText().equals("Tanque"))
            {
                return accionesBot.getContadorBotTanque(numJugBot);
            }
            else if (tvVehiculo.getText().equals("Submarino"))
            {
                return accionesBot.getContadorBotSubmarino(numJugBot);
            }
            else if (tvVehiculo.getText().equals("Caza"))
            {
                return accionesBot.getContadorBotCaza(numJugBot);
            }
            else
            {
                return accionesBot.getContadorBotVehMulticolor(numJugBot);
            }
        }
    }
}
