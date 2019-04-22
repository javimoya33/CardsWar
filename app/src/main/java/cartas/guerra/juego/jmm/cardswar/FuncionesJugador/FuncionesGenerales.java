package cartas.guerra.juego.jmm.cardswar.FuncionesJugador;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

import cartas.guerra.juego.jmm.cardswar.BaseDeDatos.AccionesBot;
import cartas.guerra.juego.jmm.cardswar.MainActivity;
import cartas.guerra.juego.jmm.cardswar.MenuMain;
import cartas.guerra.juego.jmm.cardswar.PopupWindow;
import cartas.guerra.juego.jmm.cardswar.R;

/**
 * Created by Acer on 04/09/2018.
 */
public class FuncionesGenerales {

    public TextView tvCasillasJugadores[];
    public ImageView ivCasillasJugadores[];
    public TextView tvJugadoresComodin[];
    public TextView tvCartasJugadores[];
    public ImageView ivCartasJugadores[];
    public RelativeLayout relJugadoresVehiculos[];
    public int numeroJugadores;
    int ordenPosTvVehiculos[];
    ArrayList<String> nombresEjercitos;
    public TableLayout tableCartas;
    public TableRow[] tableRowVehs;
    public LinearLayout linearJuego;
    public ColorMatrixColorFilter filter;
    public Context context;
    public TableLayout tableJugVehiculos;

    public FuncionesGenerales(TextView tvCasJugs[], ImageView ivCasJugs[], TextView tvJugsCom[],
                              TextView tvCartasJugs[], ImageView ivCartasJugs[], RelativeLayout relJugsVeh[], int numJug,
                              ArrayList<String> nomEjercitos, TableLayout tbCartas, TableRow[] tbRowVehs, LinearLayout lJuego,
                              Context context, TableLayout tabJugVeh)
    {
        super();

        this.tvCasillasJugadores = tvCasJugs;
        this.ivCasillasJugadores = ivCasJugs;
        this.tvJugadoresComodin = tvJugsCom;
        this.tvCartasJugadores = tvCartasJugs;
        this.ivCartasJugadores = ivCartasJugs;
        this.relJugadoresVehiculos = relJugsVeh;
        this.numeroJugadores = numJug;
        this.nombresEjercitos = nomEjercitos;
        this.tableCartas = tbCartas;
        this.tableRowVehs = tbRowVehs;
        this.linearJuego = lJuego;
        this.context = context;
        this.tableJugVehiculos = tabJugVeh;
    }

    // Boolean que determina si el número de la barja ya ha salido o no.
    private boolean numRepetido;

    // Array con los números de la baraja.
    private ArrayList<Integer> valorTurnoJug = new ArrayList<>();
    private ArrayList<Integer> valorBarajaCartas = new ArrayList<>();

    // Método para sacar una carta de la baraja de cartas restantes.
    public int sacarCarta2()
    {
        int valorCarta;

        do {
            valorCarta = (int) Math.floor(Math.random() * (0 - 68 + 1) + 68);
            numEsRepetido(valorCarta, valorBarajaCartas);
        }
        while (numRepetido == true);

        valorBarajaCartas.add(valorCarta);

        for (int i = 0; i < numeroJugadores; i++)
        {
            relJugadoresVehiculos[i * 4].setClickable(false);
            relJugadoresVehiculos[i * 4 + 1].setClickable(false);
            relJugadoresVehiculos[i * 4 + 2].setClickable(false);
            relJugadoresVehiculos[i * 4 + 3].setClickable(false);
        }

        return valorCarta;
    }

    // Método que recoge las acciones que realiza el juego cuando todos los vehículos han sido colocados en el tablero.
    public boolean todosVehiculosConseguidos(int numVehConseguidos, String jugador, TextView tvMensajeVictoria, Context context)
    {
        // Si los cuatro vehículos están en el tablero se mostrará un mensaje de victoria.
        System.out.println(jugador + " :: CARTALazacano -> " + numVehConseguidos);
        if (numVehConseguidos >= 4)
        {
            System.out.println(jugador + " :: Carta -> ¡¡¡HAS GANADO!!!");
            tvMensajeVictoria.setText(jugador + " :: " + context.getResources().getString(R.string.tvMensajeVictoria));
            return true;
        }
        else
        {
            return false;
        }
    }

    // Método que comprueba si la baraja de cartas restante ha sido agotada o no.
    public void barajaAgotada(int numTirada, TextView tvJugCarta1, TextView tvJugCarta2, TextView tvJugCarta3)
    {
        // Si se agota la baraja se saca una nueva baraja.
        if (numTirada >= 70) {

            valorBarajaCartas.clear();
        }
    }

    public void colorCarta(String nombreCarta, TextView tvCarta)
    {
        if ((nombreCarta.equals("Batallon")) || (nombreCarta.equals("Metralleta")) || (nombreCarta.equals("Medicina1")))
        {
            tvCarta.setTextColor(Color.parseColor("#7B241C"));
        }
        else if ((nombreCarta.equals("Tanque")) || (nombreCarta.equals("Lanzacohetes")) || (nombreCarta.equals("Medicina2")))
        {
            tvCarta.setTextColor(Color.parseColor("#117A65"));
        }
        else if ((nombreCarta.equals("Submarino")) || (nombreCarta.equals("Torpedos")) || (nombreCarta.equals("Medicina3")))
        {
            tvCarta.setTextColor(Color.parseColor("#1A5276"));
        }
        else if ((nombreCarta.equals("Caza")) || (nombreCarta.equals("Misiles")) || (nombreCarta.equals("Medicina4")))
        {
            tvCarta.setTextColor(Color.parseColor("#9C640C"));
        }
        else if ((nombreCarta.equals("Vehículo Multicolor")) || (nombreCarta.equals("Arma Multicolor")) || (nombreCarta.equals("Medicina Multicolor")))
        {
            tvCarta.setTextColor(Color.parseColor("#5B2C6F"));
        }
    }

    public TextView asociarIdComodinConTvComodin(String idTvComodin)
    {
        if (idTvComodin.equals("textJugComodin11"))
        {
            return tvJugadoresComodin[0];
        }
        else if (idTvComodin.equals("textJugComodin12"))
        {
            return tvJugadoresComodin[1];
        }
        else if (idTvComodin.equals("textJugComodin21"))
        {
            return tvJugadoresComodin[2];
        }
        else if (idTvComodin.equals("textJugComodin22"))
        {
            return tvJugadoresComodin[3];
        }
        else if (idTvComodin.equals("textJugComodin31"))
        {
            return tvJugadoresComodin[4];
        }
        else if (idTvComodin.equals("textJugComodin32"))
        {
            return tvJugadoresComodin[5];
        }
        else if (idTvComodin.equals("textJugComodin41"))
        {
            return tvJugadoresComodin[6];
        }
        else if (idTvComodin.equals("textJugComodin42"))
        {
            return tvJugadoresComodin[7];
        }
        else
        {
            return null;
        }
    }

    public String sacarIdTvMedicina(int idCasillaVehiculo, TextView tvVehiculo, String numMedicina)
    {
        String idTvVehiculo = tvVehiculo.getResources().getResourceEntryName(idCasillaVehiculo);

        String idTvMedicina1 = idTvVehiculo.replaceAll("Vehiculo", "Comodin") + numMedicina;

        String idTvMedicina2 = "";

        if (idTvMedicina1.substring(7, 8).equals("0"))
        {
            System.out.println("Universo -> Entraa" + idTvMedicina2);
            idTvMedicina2 = idTvMedicina1.replace("0", "");
        }
        else
        {
            System.out.println("Universo -> Entraa" + idTvMedicina2);
            idTvMedicina2 = idTvMedicina1;
        }

        System.out.println("Universo -> " + idTvMedicina2);

        return idTvMedicina2;
    }

    public TextView asociarIdMedicinaConTvMedicinaBot(String idTvMedicina, int multiplicador)
    {
        System.out.println("Vives -> " + idTvMedicina + multiplicador);
        if (idTvMedicina.equals("textBot1Comodin11") || idTvMedicina.equals("textBot2Comodin11") || idTvMedicina.equals("textBot3Comodin11") || idTvMedicina.equals("textBot4Comodin11") || idTvMedicina.equals("textBot5Comodin11"))
        {
            return tvJugadoresComodin[multiplicador * 8 + 0];
        }
        else if (idTvMedicina.equals("textBot1Comodin12") || idTvMedicina.equals("textBot2Comodin12") || idTvMedicina.equals("textBot3Comodin12") || idTvMedicina.equals("textBot4Comodin12") || idTvMedicina.equals("textBot5Comodin12"))
        {
            return tvJugadoresComodin[multiplicador * 8 + 1];
        }
        else if (idTvMedicina.equals("textBot1Comodin21") || idTvMedicina.equals("textBot2Comodin21") || idTvMedicina.equals("textBot3Comodin21") || idTvMedicina.equals("textBot4Comodin21") || idTvMedicina.equals("textBot5Comodin21"))
        {
            return tvJugadoresComodin[multiplicador * 8 + 2];
        }
        else if (idTvMedicina.equals("textBot1Comodin22") || idTvMedicina.equals("textBot2Comodin22") || idTvMedicina.equals("textBot3Comodin22") || idTvMedicina.equals("textBot4Comodin22") || idTvMedicina.equals("textBot5Comodin22"))
        {
            return tvJugadoresComodin[multiplicador * 8 + 3];
        }
        else if (idTvMedicina.equals("textBot1Comodin31") || idTvMedicina.equals("textBot2Comodin31") || idTvMedicina.equals("textBot3Comodin31") || idTvMedicina.equals("textBot4Comodin31") || idTvMedicina.equals("textBot5Comodin31"))
        {
            return tvJugadoresComodin[multiplicador * 8 + 4];
        }
        else if (idTvMedicina.equals("textBot1Comodin32") || idTvMedicina.equals("textBot2Comodin32") || idTvMedicina.equals("textBot3Comodin32") || idTvMedicina.equals("textBot4Comodin32") || idTvMedicina.equals("textBot5Comodin32"))
        {
            return tvJugadoresComodin[multiplicador * 8 + 5];
        }
        else if (idTvMedicina.equals("textBot1Comodin41") || idTvMedicina.equals("textBot2Comodin41") || idTvMedicina.equals("textBot3Comodin41") || idTvMedicina.equals("textBot4Comodin41") || idTvMedicina.equals("textBot5Comodin41"))
        {
            return tvJugadoresComodin[multiplicador * 8 + 6];
        }
        else if (idTvMedicina.equals("textBot1Comodin42") || idTvMedicina.equals("textBot2Comodin42") || idTvMedicina.equals("textBot3Comodin42") || idTvMedicina.equals("textBot4Comodin42") || idTvMedicina.equals("textBot5Comodin42"))
        {
            return tvJugadoresComodin[multiplicador * 8 + 7];
        }
        else if (idTvMedicina.equals("textJugComodin11"))
        {
            return tvJugadoresComodin[0];
        }
        else if (idTvMedicina.equals("textJugComodin12"))
        {
            return tvJugadoresComodin[1];
        }
        else if (idTvMedicina.equals("textJugComodin21"))
        {
            return tvJugadoresComodin[2];
        }
        else if (idTvMedicina.equals("textJugComodin22"))
        {
            return tvJugadoresComodin[3];
        }
        else if (idTvMedicina.equals("textJugComodin31"))
        {
            return tvJugadoresComodin[4];
        }
        else if (idTvMedicina.equals("textJugComodin32"))
        {
            return tvJugadoresComodin[5];
        }
        else if (idTvMedicina.equals("textJugComodin41"))
        {
            return tvJugadoresComodin[6];
        }
        else if (idTvMedicina.equals("textJugComodin42"))
        {
            return tvJugadoresComodin[7];
        }
        else
        {
            return null;
        }
    }

    public void controlVehiculosComodinesPuntosBot(AccionesBot accionesBot, int jugador2, TextView tvMensajeVictoria, MediaPlayer musicafondo)
    {
        int jugador = jugador2 - 1;

        accionesBot.setNumBotVehConseguidos(0, jugador);

        accionesBot.setNumBotNo(4, jugador);
        accionesBot.setNumBotLimpios(0, jugador);
        accionesBot.setNumBotMedicados(0, jugador);
        accionesBot.setNumBotInmunizados(0, jugador);
        accionesBot.setNumBotVirus(0, jugador);

        for (int i = 0; i < 5; i++)
        {
            accionesBot.setVehBotConseguido(false, ((jugador) * 5) + i);
        }

        accionesBot.setContadorBotBatallon(-5, jugador);
        accionesBot.setContadorBotTanque(-5, jugador);
        accionesBot.setContadorBotSubmarino(-5, jugador);
        accionesBot.setContadorBotCaza(-5, jugador);
        accionesBot.setContadorBotVehMulticolor(-5, jugador);

        accionesBot.setPuntosBot(0, jugador);

        controlVehComPtsBot(tvCasillasJugadores[(jugador2) * 4 + 0], tvJugadoresComodin[(jugador2) * 8 + 0],
                tvJugadoresComodin[(jugador2) * 8 + 1], tvMensajeVictoria, accionesBot, jugador, filter, context, musicafondo);
        controlVehComPtsBot(tvCasillasJugadores[(jugador2) * 4 + 1], tvJugadoresComodin[(jugador2) * 8 + 2],
                tvJugadoresComodin[(jugador2) * 8 + 3], tvMensajeVictoria, accionesBot, jugador, filter, context, musicafondo);
        controlVehComPtsBot(tvCasillasJugadores[(jugador2) * 4 + 2], tvJugadoresComodin[(jugador2) * 8 + 4],
                tvJugadoresComodin[(jugador2) * 8 + 5], tvMensajeVictoria, accionesBot, jugador, filter, context, musicafondo);
        controlVehComPtsBot(tvCasillasJugadores[(jugador2) * 4 + 3], tvJugadoresComodin[(jugador2) * 8 + 6],
                tvJugadoresComodin[(jugador2) * 8 + 7], tvMensajeVictoria, accionesBot, jugador, filter, context, musicafondo);

        System.out.println("Yung ******************************");
        System.out.println("Yung:" + (jugador + 1) + " getNumBotNo -> " + accionesBot.getNumBotNo(jugador));
        System.out.println("Yung:" + (jugador + 1) + " getNumBotLimpios -> " + accionesBot.getNumBotLimpios(jugador));
        System.out.println("Yung:" + (jugador + 1) + " getNumBotMedicados -> " + accionesBot.getNumBotMedicados(jugador));
        System.out.println("Yung:" + (jugador + 1) + " getNumBotInmunizados -> " + accionesBot.getNumBotInmunizados((jugador)));
        System.out.println("Yung:" + (jugador + 1) + " getNumBotVirus -> " + accionesBot.getNumBotVirus(jugador));
        System.out.println("Yung --------------------------------");
        System.out.println("Yung:" + (jugador + 1) + " getVehBot1Conseguido -> " + accionesBot.getVehBotConseguido(((jugador) * 5) + 0));
        System.out.println("Yung:" + (jugador + 1) + " getVehBot2Conseguido -> " + accionesBot.getVehBotConseguido(((jugador) * 5) + 1));
        System.out.println("Yung:" + (jugador + 1) + " getVehBot3Conseguido -> " + accionesBot.getVehBotConseguido(((jugador) * 5) + 2));
        System.out.println("Yung:" + (jugador + 1) + " getVehBot4Conseguido -> " + accionesBot.getVehBotConseguido(((jugador) * 5) + 3));
        System.out.println("Yung:" + (jugador + 1) + " getVehBot5Conseguido -> " + accionesBot.getVehBotConseguido(((jugador) * 5) + 4));
        System.out.println("Yung --------------------------------");

    }

    public void controlVehComPtsBot(TextView tvCasilla, TextView tvCom1, TextView tvCom2, TextView tvMensajeVictoria, AccionesBot accionesBot, int jugBot,
                                    ColorMatrixColorFilter filter, Context context, MediaPlayer musicaFondo)
    {
        System.out.println("Flaca1 -> " + tvCasilla.getText());
        if (!tvCasilla.getText().equals("---"))
        {
            System.out.println("Flaca2");
            accionesBot.setNumBotVehConseguidos(accionesBot.getNumBotVehConseguidos(jugBot) + 1, jugBot);
            accionesBot.setNumBotLimpios(accionesBot.getNumBotLimpios(jugBot) + 1, jugBot);
            accionesBot.setNumBotNo(accionesBot.getNumBotNo(jugBot) - 1, jugBot);
            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 1, jugBot);

            if (tvCasilla.getText().equals("Batallon"))
            {
                accionesBot.setVehBotConseguido(true, ((jugBot) * 5) + 0);
                accionesBot.setContadorBotBatallon(0, jugBot);
            }
            else if (tvCasilla.getText().equals("Tanque"))
            {
                accionesBot.setVehBotConseguido(true, ((jugBot) * 5) + 1);
                accionesBot.setContadorBotTanque(0, jugBot);
            }
            else if (tvCasilla.getText().equals("Submarino"))
            {
                accionesBot.setVehBotConseguido(true, ((jugBot) * 5) + 2);
                accionesBot.setContadorBotSubmarino(0, jugBot);
            }
            else if (tvCasilla.getText().equals("Caza"))
            {
                accionesBot.setVehBotConseguido(true, ((jugBot) * 5) + 3);
                accionesBot.setContadorBotCaza(0, jugBot);
            }
            else if (tvCasilla.getText().equals("Vehículo Multicolor"))
            {
                accionesBot.setVehBotConseguido(true, ((jugBot) * 5) + 4);
                accionesBot.setContadorBotVehMulticolor(0, jugBot);
            }

            if (!tvCasilla.getText().equals("Vehículo Multicolor"))
            {
                if (!tvCom1.getText().equals("---"))
                {
                    accionesBot.setNumBotLimpios(accionesBot.getNumBotLimpios(jugBot) - 1, jugBot);

                    if (tvCom1.getText().equals("Medicina1"))
                    {
                        if (tvCom2.getText().equals("Medicina1") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumBotInmunizados(accionesBot.getNumBotInmunizados(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.2, jugBot);
                            accionesBot.setContadorBotBatallon(accionesBot.getContadorBotBatallon(jugBot) + 2, jugBot);
                        }
                        else
                        {
                            accionesBot.setNumBotMedicados(accionesBot.getNumBotMedicados(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.1, jugBot);
                            accionesBot.setContadorBotBatallon(accionesBot.getContadorBotBatallon(jugBot) + 1, jugBot);
                        }
                    }
                    else if (tvCom1.getText().equals("Medicina2"))
                    {
                        if (tvCom2.getText().equals("Medicina2") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumBotInmunizados(accionesBot.getNumBotInmunizados(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.2, jugBot);
                            accionesBot.setContadorBotTanque(accionesBot.getContadorBotTanque(jugBot) + 2, jugBot);
                        }
                        else
                        {
                            accionesBot.setNumBotMedicados(accionesBot.getNumBotMedicados(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.1, jugBot);
                            accionesBot.setContadorBotTanque(accionesBot.getContadorBotTanque(jugBot) + 1, jugBot);
                        }
                    }
                    else if (tvCom1.getText().equals("Medicina3"))
                    {
                        if (tvCom2.getText().equals("Medicina3") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumBotInmunizados(accionesBot.getNumBotInmunizados(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.2, jugBot);
                            accionesBot.setContadorBotSubmarino(accionesBot.getContadorBotSubmarino(jugBot) + 2, jugBot);
                        }
                        else
                        {
                            accionesBot.setNumBotMedicados(accionesBot.getNumBotMedicados(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.1, jugBot);
                            accionesBot.setContadorBotSubmarino(accionesBot.getContadorBotSubmarino(jugBot) + 1, jugBot);
                        }
                    }
                    else if (tvCom1.getText().equals("Medicina4"))
                    {
                        if (tvCom2.getText().equals("Medicina4") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumBotInmunizados(accionesBot.getNumBotInmunizados(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.2, jugBot);
                            accionesBot.setContadorBotCaza(accionesBot.getContadorBotCaza(jugBot) + 2, jugBot);
                        }
                        else
                        {
                            accionesBot.setNumBotMedicados(accionesBot.getNumBotMedicados(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.1, jugBot);
                            accionesBot.setContadorBotCaza(accionesBot.getContadorBotCaza(jugBot) + 1, jugBot);
                        }
                    }
                    else if (tvCom1.getText().equals("Medicina Multicolor"))
                    {
                        if (tvCasilla.getText().equals("Batallon"))
                        {
                            if (!tvCom2.getText().equals("---"))
                            {
                                accionesBot.setNumBotInmunizados(accionesBot.getNumBotInmunizados(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotBatallon(accionesBot.getContadorBotBatallon(jugBot) + 2, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.2, jugBot);
                            }
                            else
                            {
                                accionesBot.setNumBotMedicados(accionesBot.getNumBotMedicados(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotBatallon(accionesBot.getContadorBotBatallon(jugBot) + 1, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.1, jugBot);
                            }
                        }
                        else if (tvCasilla.getText().equals("Tanque"))
                        {
                            if (!tvCom2.getText().equals("---"))
                            {
                                accionesBot.setNumBotInmunizados(accionesBot.getNumBotInmunizados(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotTanque(accionesBot.getContadorBotTanque(jugBot) + 2, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.2, jugBot);
                            }
                            else
                            {
                                accionesBot.setNumBotMedicados(accionesBot.getNumBotMedicados(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotTanque(accionesBot.getContadorBotTanque(jugBot) + 1, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.1, jugBot);
                            }
                        }
                        else if (tvCasilla.getText().equals("Submarino"))
                        {
                            if (!tvCom2.getText().equals("---"))
                            {
                                accionesBot.setNumBotInmunizados(accionesBot.getNumBotInmunizados(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotSubmarino(accionesBot.getContadorBotSubmarino(jugBot) + 2, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.2, jugBot);
                            }
                            else
                            {
                                accionesBot.setNumBotMedicados(accionesBot.getNumBotMedicados(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotSubmarino(accionesBot.getContadorBotSubmarino(jugBot) + 1, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.1, jugBot);
                            }
                        }
                        else if (tvCasilla.getText().equals("Caza"))
                        {
                            if (!tvCom2.getText().equals("---"))
                            {
                                accionesBot.setNumBotInmunizados(accionesBot.getNumBotInmunizados(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotCaza(accionesBot.getContadorBotCaza(jugBot) + 2, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.2, jugBot);
                            }
                            else
                            {
                                accionesBot.setNumBotMedicados(accionesBot.getNumBotMedicados(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotCaza(accionesBot.getContadorBotCaza(jugBot) + 1, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.1, jugBot);
                            }
                        }
                    }
                    else if (tvCom1.getText().equals("Metralleta"))
                    {
                        if (tvCom2.getText().equals("Metralleta") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumBotVehConseguidos(accionesBot.getNumBotVehConseguidos(jugBot) - 1, jugBot);
                            accionesBot.setVehBotConseguido(false, ((jugBot) * 5) + 0);
                            accionesBot.setNumBotNo(accionesBot.getNumBotNo(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.9, jugBot);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                            accionesBot.setContadorBotBatallon(0, jugBot);
                        }
                        else
                        {
                            accionesBot.setNumBotVirus(accionesBot.getNumBotVirus(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.1, jugBot);
                            accionesBot.setContadorBotBatallon(accionesBot.getContadorBotBatallon(jugBot) - 1, jugBot);
                        }
                    }
                    else if (tvCom1.getText().equals("Lanzacohetes"))
                    {
                        if (tvCom2.getText().equals("Lanzacohetes") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumBotVehConseguidos(accionesBot.getNumBotVehConseguidos(jugBot) - 1, jugBot);
                            accionesBot.setVehBotConseguido(false, ((jugBot) * 5) + 1);
                            accionesBot.setNumBotNo(accionesBot.getNumBotNo(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.9, jugBot);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                            accionesBot.setContadorBotTanque(0, jugBot);
                        }
                        else
                        {
                            accionesBot.setNumBotVirus(accionesBot.getNumBotVirus(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.1, jugBot);
                            accionesBot.setContadorBotTanque(accionesBot.getContadorBotTanque(jugBot) - 1, jugBot);
                        }
                    }
                    else if (tvCom1.getText().equals("Torpedos"))
                    {
                        if (tvCom2.getText().equals("Torpedos") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumBotVehConseguidos(accionesBot.getNumBotVehConseguidos(jugBot) - 1, jugBot);
                            accionesBot.setVehBotConseguido(false, ((jugBot) * 5) + 2);
                            accionesBot.setNumBotNo(accionesBot.getNumBotNo(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.9, jugBot);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                            accionesBot.setContadorBotSubmarino(0, jugBot);
                        }
                        else
                        {
                            accionesBot.setNumBotVirus(accionesBot.getNumBotVirus(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.1, jugBot);
                            accionesBot.setContadorBotSubmarino(accionesBot.getContadorBotSubmarino(jugBot) - 1, jugBot);
                        }
                    }
                    else if (tvCom1.getText().equals("Misiles"))
                    {
                        if (tvCom2.getText().equals("Misiles") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumBotVehConseguidos(accionesBot.getNumBotVehConseguidos(jugBot) - 1, jugBot);
                            accionesBot.setVehBotConseguido(false, ((jugBot) * 5) + 3);
                            accionesBot.setNumBotNo(accionesBot.getNumBotNo(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.9, jugBot);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                            accionesBot.setContadorBotCaza(0, jugBot);
                        }
                        else
                        {
                            accionesBot.setNumBotVirus(accionesBot.getNumBotVirus(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.1, jugBot);
                            accionesBot.setContadorBotCaza(accionesBot.getContadorBotCaza(jugBot) - 1, jugBot);
                        }
                    }
                    else if (tvCom1.getText().equals("Arma Multicolor"))
                    {
                        if (tvCasilla.getText().equals("Batallon"))
                        {
                            if (!tvCom2.getText().equals("---"))
                            {
                                accionesBot.setNumBotVehConseguidos(accionesBot.getNumBotVehConseguidos(jugBot) - 1, jugBot);
                                accionesBot.setVehBotConseguido(false, ((jugBot) * 5) + 0);
                                accionesBot.setNumBotNo(accionesBot.getNumBotNo(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotBatallon(0, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.9, jugBot);

                                tvCasilla.setText("---");
                                tvCom1.setText("---");
                                tvCom2.setText("---");
                            }
                            else
                            {
                                accionesBot.setNumBotVirus(accionesBot.getNumBotVirus(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotBatallon(accionesBot.getContadorBotBatallon(jugBot) - 1, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.1, jugBot);
                            }
                        }
                        else if (tvCasilla.getText().equals("Tanque"))
                        {
                            if (!tvCom2.getText().equals("---"))
                            {
                                accionesBot.setNumBotVehConseguidos(accionesBot.getNumBotVehConseguidos(jugBot) - 1, jugBot);
                                accionesBot.setVehBotConseguido(false, ((jugBot) * 5) + 1);
                                accionesBot.setNumBotNo(accionesBot.getNumBotNo(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotTanque(0, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.9, jugBot);

                                tvCasilla.setText("---");
                                tvCom1.setText("---");
                                tvCom2.setText("---");
                            }
                            else
                            {
                                accionesBot.setNumBotVirus(accionesBot.getNumBotVirus(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotTanque(accionesBot.getContadorBotTanque(jugBot) - 1, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.1, jugBot);
                            }
                        }
                        else if (tvCasilla.getText().equals("Submarino"))
                        {
                            if (!tvCom2.getText().equals("---"))
                            {
                                accionesBot.setNumBotVehConseguidos(accionesBot.getNumBotVehConseguidos(jugBot) - 1, jugBot);
                                accionesBot.setVehBotConseguido(false, ((jugBot) * 5) + 2);
                                accionesBot.setNumBotNo(accionesBot.getNumBotNo(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotSubmarino(0, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.9, jugBot);

                                tvCasilla.setText("---");
                                tvCom1.setText("---");
                                tvCom2.setText("---");
                            }
                            else
                            {
                                accionesBot.setNumBotVirus(accionesBot.getNumBotVirus(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotSubmarino(accionesBot.getContadorBotSubmarino(jugBot) - 1, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.1, jugBot);
                            }
                        }
                        else if (tvCasilla.getText().equals("Caza"))
                        {
                            if (!tvCom2.getText().equals("---"))
                            {
                                accionesBot.setNumBotVehConseguidos(accionesBot.getNumBotVehConseguidos(jugBot) - 1, jugBot);
                                accionesBot.setVehBotConseguido(false, ((jugBot) * 5) + 3);
                                accionesBot.setNumBotNo(accionesBot.getNumBotNo(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotCaza(0, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.9, jugBot);

                                tvCasilla.setText("---");
                                tvCom1.setText("---");
                                tvCom2.setText("---");
                            }
                            else
                            {
                                accionesBot.setNumBotVirus(accionesBot.getNumBotVirus(jugBot) + 1, jugBot);
                                accionesBot.setContadorBotCaza(accionesBot.getContadorBotCaza(jugBot) - 1, jugBot);
                                accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.1, jugBot);
                            }
                        }
                    }
                }
            }
            else
            {
                if (!tvCom1.getText().equals("---"))
                {
                    accionesBot.setNumBotLimpios(accionesBot.getNumBotLimpios(jugBot) - 1, jugBot);

                    if (tvCom1.getText().equals("Medicina1") || tvCom1.getText().equals("Medicina2") || tvCom1.getText().equals("Medicina3") ||
                            tvCom1.getText().equals("Medicina4") || tvCom1.getText().equals("Medicina Multicolor"))
                    {
                        if (tvCom2.getText().equals("Medicina1") || tvCom2.getText().equals("Medicina2") || tvCom2.getText().equals("Medicina3") ||
                                tvCom2.getText().equals("Medicina4") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumBotInmunizados(accionesBot.getNumBotInmunizados(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.2, jugBot);
                            accionesBot.setContadorBotVehMulticolor(accionesBot.getContadorBotVehMulticolor(jugBot) + 2, jugBot);
                        }
                        else
                        {
                            accionesBot.setNumBotMedicados(accionesBot.getNumBotMedicados(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) + 0.1, jugBot);
                            accionesBot.setContadorBotVehMulticolor(accionesBot.getContadorBotVehMulticolor(jugBot) + 1, jugBot);
                        }
                    }
                    else if (tvCom1.getText().equals("Metralleta") || tvCom1.getText().equals("Lanzacohetes") || tvCom1.getText().equals("Torpedos") ||
                            tvCom1.getText().equals("Misiles") || tvCom1.getText().equals("Arma Multicolor"))
                    {
                        if (tvCom2.getText().equals("Metralleta") || tvCom2.getText().equals("Lanzacohetes") || tvCom2.getText().equals("Torpedos") ||
                                tvCom2.getText().equals("Misiles") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumBotVehConseguidos(accionesBot.getNumBotVehConseguidos(jugBot) - 1, jugBot);
                            accionesBot.setVehBotConseguido(false, ((jugBot) * 5) + 0);
                            accionesBot.setNumBotNo(accionesBot.getNumBotNo(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.9, jugBot);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                            accionesBot.setContadorBotVehMulticolor(0, jugBot);
                        }
                        else
                        {
                            accionesBot.setNumBotVirus(accionesBot.getNumBotVirus(jugBot) + 1, jugBot);
                            accionesBot.setPuntosBot(accionesBot.getPuntosBot(jugBot) - 0.1, jugBot);
                            accionesBot.setContadorBotVehMulticolor(accionesBot.getContadorBotVehMulticolor(jugBot) - 1, jugBot);
                        }
                    }
                }
            }
        }

        determinarImagenCasillaVehiculosBot(tvCasillasJugadores, ivCasillasJugadores, accionesBot);

        System.out.println("Yung: ----------------------------------------------------------------------");
        System.out.println("Yung:" + (jugBot + 1) + " contadorBatallon -> " + accionesBot.getContadorBotBatallon(jugBot));
        System.out.println("Yung:" + (jugBot + 1) + " contadorTanque -> " + accionesBot.getContadorBotTanque(jugBot));
        System.out.println("Yung:" + (jugBot + 1) + " contadorSubmarino -> " + accionesBot.getContadorBotSubmarino(jugBot));
        System.out.println("Yung:" + (jugBot + 1) + " contadorCaza -> " + accionesBot.getContadorBotCaza(jugBot));
        System.out.println("Yung:" + (jugBot + 1) + " contadorVehMulticolor -> " + accionesBot.getContadorBotVehMulticolor(jugBot));
        System.out.println("Yung: ----------------------------------------------------------------------");

        if (botEsCampeon(accionesBot, jugBot, filter, context, musicaFondo))
        {
            tvMensajeVictoria.setText(context.getResources().getString(R.string.tvMensajeVictoria2) + (jugBot + 1) + "!!!");
        }
    }

    public void determinarImagenCasillaCartas(TextView tvCartasJugadores[], ImageView ivCartasJugadores[])
    {
        for (int i = 0; i < 3; i++)
        {
            System.out.println("Noches -> " + tvCartasJugadores[i].getText());

            if (tvCartasJugadores[i].getText().equals("Batallon"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.batallon);
            }
            else if (tvCartasJugadores[i].getText().equals("Tanque"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.tanque);
            }
            else if (tvCartasJugadores[i].getText().equals("Submarino"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.submarino);
            }
            else if (tvCartasJugadores[i].getText().equals("Caza"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.caza);
            }
            else if (tvCartasJugadores[i].getText().equals("Vehículo Multicolor"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.vehiculo_multicolor);
            }
            else if (tvCartasJugadores[i].getText().equals("Medicina1"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.medicina1);
            }
            else if (tvCartasJugadores[i].getText().equals("Medicina2"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.medicina2);
            }
            else if (tvCartasJugadores[i].getText().equals("Medicina3"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.medicina3);
            }
            else if (tvCartasJugadores[i].getText().equals("Medicina4"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.medicina4);
            }
            else if (tvCartasJugadores[i].getText().equals("Medicina Multicolor"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.medicina_multicolor);
            }
            else if (tvCartasJugadores[i].getText().equals("Metralleta"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.metralleta);
            }
            else if (tvCartasJugadores[i].getText().equals("Lanzacohetes"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.lanzacohetes);
            }
            else if (tvCartasJugadores[i].getText().equals("Torpedos"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.torpedos);
            }
            else if (tvCartasJugadores[i].getText().equals("Misiles"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.misiles);
            }
            else if (tvCartasJugadores[i].getText().equals("Arma Multicolor"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.arma_multicolor);
            }
            else if (tvCartasJugadores[i].getText().equals("Robar Vehículo"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.robar_vehiculo);
            }
            else if (tvCartasJugadores[i].getText().equals("Cambiar Vehículo"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.intercambio_vehiculo);
            }
            else if (tvCartasJugadores[i].getText().equals("Expulsar Armas"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.expulsar_armas);
            }
            else if (tvCartasJugadores[i].getText().equals("Intercambiar Brigada"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.intercambio_brigada);
            }
            else if (tvCartasJugadores[i].getText().equals("Dejar Cartas"))
            {
                ivCartasJugadores[i].setImageResource(R.drawable.dejar_cartas);
            }
            else
            {
                ivCartasJugadores[i].setImageResource(R.drawable.ningun_vehiculo);
            }
        }
    }

    public void determinarImagenCasillaVehiculos(TextView tvVehiculosJugadores[], ImageView ivVehiculosJugadores[], AccionesBot accionesBot)
    {
        for (int i = 0; i < 4; i++)
        {
            if (tvVehiculosJugadores[i].getText().equals("Batallon"))
            {
                if (accionesBot.getContadorJugBatallon() == 2)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.batallon_inmunizado);
                }
                else if (accionesBot.getContadorJugBatallon() == 1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.batallon_medicado);
                }
                else if (accionesBot.getContadorJugBatallon() == -1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.batallon_herido);
                }
                else
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.batallon);
                }
            }
            else if (tvVehiculosJugadores[i].getText().equals("Tanque"))
            {
                if (accionesBot.getContadorJugTanque() == 2)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.tanque_inmunizado);
                }
                else if (accionesBot.getContadorJugTanque() == 1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.tanque_medicado);
                }
                else if (accionesBot.getContadorJugTanque() == -1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.tanque_herido);
                }
                else
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.tanque);
                }
            }
            else if (tvVehiculosJugadores[i].getText().equals("Submarino"))
            {
                if (accionesBot.getContadorJugSubmarino() == 2)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.submarino_inmunizado);
                }
                else if (accionesBot.getContadorJugSubmarino() == 1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.submarino_medicado);
                }
                else if (accionesBot.getContadorJugSubmarino() == -1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.submarino_herido);
                }
                else
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.submarino);
                }
            }
            else if (tvVehiculosJugadores[i].getText().equals("Caza"))
            {
                if (accionesBot.getContadorJugCaza() == 2)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.caza_inmunizado);
                }
                else if (accionesBot.getContadorJugCaza() == 1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.caza_medicado);
                }
                else if (accionesBot.getContadorJugCaza() == -1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.caza_herido);
                }
                else
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.caza);
                }
            }
            else if (tvVehiculosJugadores[i].getText().equals("Vehículo Multicolor"))
            {
                if (accionesBot.getContadorJugVehMulticolor() == 2)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.vehiculo_multicolor_inmunizado);
                }
                else if (accionesBot.getContadorJugVehMulticolor() == 1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.vehiculo_multicolor_medicado);
                }
                else if (accionesBot.getContadorJugVehMulticolor() == -1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.vehiculo_multicolor_herido);
                }
                else
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.vehiculo_multicolor);
                }
            }
            else
            {
                ivVehiculosJugadores[i].setImageResource(R.drawable.ningun_vehiculo);
            }
        }
    }

    public void reproducirSonidoJuego(Context context, int idSonido, final SoundPool sonidoJuego)
    {
        final int soundID = sonidoJuego.load(context, idSonido, 1);

        sonidoJuego.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {

                sonidoJuego.play(soundID, 100, 100, 1, 0, 1f);
            }
        });
    }

    public void determinarImagenCasillaVehiculosBot(TextView tvVehiculosJugadores[], ImageView ivVehiculosJugadores[], AccionesBot accionesBot)
    {
        int contador = 0;
        int numJug = 0;

        for (int i = 4; i < tvVehiculosJugadores.length; i++)
        {
            if (contador > 3)
            {
                contador = 0;
                numJug += 1;
            }

            if (tvVehiculosJugadores[i].getText().equals("Batallon"))
            {
                if (accionesBot.getContadorBotBatallon(numJug) == 2)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.batallon_inmunizado);
                }
                else if (accionesBot.getContadorBotBatallon(numJug) == 1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.batallon_medicado);
                }
                else if (accionesBot.getContadorBotBatallon(numJug) == -1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.batallon_herido);
                }
                else
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.batallon);
                }
            }
            else if (tvVehiculosJugadores[i].getText().equals("Tanque"))
            {
                if (accionesBot.getContadorBotTanque(numJug) == 2)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.tanque_inmunizado);
                }
                else if (accionesBot.getContadorBotTanque(numJug) == 1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.tanque_medicado);
                }
                else if (accionesBot.getContadorBotTanque(numJug) == -1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.tanque_herido);
                }
                else
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.tanque);
                }
            }
            else if (tvVehiculosJugadores[i].getText().equals("Submarino"))
            {
                if (accionesBot.getContadorBotSubmarino(numJug) == 2)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.submarino_inmunizado);
                }
                else if (accionesBot.getContadorBotSubmarino(numJug) == 1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.submarino_medicado);
                }
                else if (accionesBot.getContadorBotSubmarino(numJug) == -1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.submarino_herido);
                }
                else
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.submarino);
                }
            }
            else if (tvVehiculosJugadores[i].getText().equals("Caza"))
            {
                if (accionesBot.getContadorBotCaza(numJug) == 2)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.caza_inmunizado);
                }
                else if (accionesBot.getContadorBotCaza(numJug) == 1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.caza_medicado);
                }
                else if (accionesBot.getContadorBotCaza(numJug) == -1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.caza_herido);
                }
                else
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.caza);
                }
            }
            else if (tvVehiculosJugadores[i].getText().equals("Vehículo Multicolor"))
            {
                if (accionesBot.getContadorBotVehMulticolor(numJug) == 2)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.vehiculo_multicolor_inmunizado);
                }
                else if (accionesBot.getContadorBotVehMulticolor(numJug) == 1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.vehiculo_multicolor_medicado);
                }
                else if (accionesBot.getContadorBotVehMulticolor(numJug) == -1)
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.vehiculo_multicolor_herido);
                }
                else
                {
                    ivVehiculosJugadores[i].setImageResource(R.drawable.vehiculo_multicolor);
                }
            }
            else
            {
                ivVehiculosJugadores[i].setImageResource(R.drawable.ningun_vehiculo);
            }

            contador += 1;
        }
    }

    public boolean botEsCampeon(AccionesBot accionesBot, int jugBot, ColorMatrixColorFilter filter, Context context, MediaPlayer musicaFondo)
    {
        if (accionesBot.getNumBotVehConseguidos(jugBot) >= 4)
        {
            if (accionesBot.getContadorBotBatallon(jugBot) != -1 && accionesBot.getContadorBotTanque(jugBot) != -1 && accionesBot.getContadorBotSubmarino(jugBot) != -1
                    && accionesBot.getContadorBotCaza(jugBot) != -1 && accionesBot.getContadorBotVehMulticolor(jugBot) != -1)
            {
                for (int i = 0; i < ivCartasJugadores.length; i++)
                {
                    ivCartasJugadores[i].setColorFilter(filter);
                }

                for (int i = 0; i < ivCasillasJugadores.length; i++)
                {
                    ivCasillasJugadores[i].setColorFilter(filter);
                }

                for (int i = 0; i < tableRowVehs.length; i++)
                {
                    tableRowVehs[i].setBackground(ContextCompat.getDrawable(context, R.drawable.fondo_juego_derrota));
                }

                tableCartas.setBackground(ContextCompat.getDrawable(context, R.drawable.fondo_juego_derrota));
                linearJuego.setBackground(ContextCompat.getDrawable(context, R.drawable.fondo_pantalla_derrota));
                tableJugVehiculos.setBackground(ContextCompat.getDrawable(context, R.drawable.fondo_juego_derrota));

                musicaFondo.stop();

                String mensajeCabecera = context.getResources().getString(R.string.tvMensajeDerrota);

                Intent intent_Popup = new Intent(context, PopupWindow.class);
                intent_Popup.putExtra("mensajeCabecera", mensajeCabecera);
                intent_Popup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent_Popup);

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

    public int tipoVehiculoMulticolor(TextView[] tvCasillasVeh, TextView[] tvCasillasCom, int numJug)
    {
        int tipoVehMulti = 0;

        for (int i = numJug * 4; i < numJug * 4 + 4; i++)
        {
            if (tvCasillasVeh[i].getText().equals("Vehículo Multicolor"))
            {
                switch ((String) tvCasillasCom[i].getText())
                {
                    case "Metralleta":
                        tipoVehMulti = 0;
                        break;

                    case "Lanzacohetes":
                        tipoVehMulti = 1;
                        break;

                    case "Torpedos":
                        tipoVehMulti = 2;
                        break;

                    case "Misiles":
                        tipoVehMulti = 3;
                        break;

                    case "Arma Multicolor":
                        tipoVehMulti = 4;
                        break;

                    case "Medicina1":
                        tipoVehMulti = 0;
                        break;

                    case "Medicina2":
                        tipoVehMulti = 1;
                        break;

                    case "Medicina3":
                        tipoVehMulti = 2;
                        break;

                    case "Medicina4":
                        tipoVehMulti = 3;
                        break;

                    case "Medicina Multicolor":
                        tipoVehMulti = 4;
                        break;
                }

                System.out.println("Rodri Aragon -> Sobre Jugador" + numJug + ": " + tipoVehMulti);
            }
        }

        return tipoVehMulti;
    }

    public void controlVehiculosComodinesPuntos(AccionesBot accionesBot, TextView tvMensajeVictoria, ImageView ivGifVictoria, Context context, MediaPlayer musicaFondo)
    {
        accionesBot.setNumJugVehConseguidos(0);

        accionesBot.setNumJugNo(4);
        accionesBot.setNumJugLimpios(0);
        accionesBot.setNumJugMedicados(0);
        accionesBot.setNumJugInmunizados(0);
        accionesBot.setNumJugVirus(0);

        accionesBot.setVeh1JugConseguido(false);
        accionesBot.setVeh2JugConseguido(false);
        accionesBot.setVeh3JugConseguido(false);
        accionesBot.setVeh4JugConseguido(false);
        accionesBot.setVehMulticolorJugConseguido(false);

        accionesBot.setContadorJugBatallon(-5);
        accionesBot.setContadorJugTanque(-5);
        accionesBot.setContadorJugSubmarino(-5);
        accionesBot.setContadorJugCaza(-5);
        accionesBot.setContadorJugVehMulticolor(-5);

        accionesBot.setPuntosJug(0);

        controlVehComPts(tvCasillasJugadores[0], tvJugadoresComodin[0], tvJugadoresComodin[1], accionesBot, tvMensajeVictoria, ivGifVictoria, context, musicaFondo);
        controlVehComPts(tvCasillasJugadores[1], tvJugadoresComodin[2], tvJugadoresComodin[3], accionesBot, tvMensajeVictoria, ivGifVictoria, context, musicaFondo);
        controlVehComPts(tvCasillasJugadores[2], tvJugadoresComodin[4], tvJugadoresComodin[5], accionesBot, tvMensajeVictoria, ivGifVictoria, context, musicaFondo);
        controlVehComPts(tvCasillasJugadores[3], tvJugadoresComodin[6], tvJugadoresComodin[7], accionesBot, tvMensajeVictoria, ivGifVictoria, context, musicaFondo);

        System.out.println("GiganteJugBatallon -> " + accionesBot.getVeh1JugConseguido());
        System.out.println("GiganteJugTanque -> " + accionesBot.getVeh2JugConseguido());
        System.out.println("GiganteJugSubmarino -> " + accionesBot.getVeh3JugConseguido());
        System.out.println("GiganteJugCaza -> " + accionesBot.getVeh4JugConseguido());
        System.out.println("GiganteJugVehMulti -> " + accionesBot.getVehMulticolorJugConseguido());
        System.out.println("GiganteBotBatallon -> ****************************************");
        System.out.println("GiganteJugBatallon -> " + accionesBot.getContadorJugBatallon());
        System.out.println("GiganteJugTanque -> " + accionesBot.getContadorJugTanque());
        System.out.println("GiganteJugSubmarino -> " + accionesBot.getContadorJugSubmarino());
        System.out.println("GiganteJugCaza -> " + accionesBot.getContadorJugCaza());
        System.out.println("GiganteJugVehMulti -> " + accionesBot.getContadorJugVehMulticolor());
    }

    public void controlVehComPts(TextView tvCasilla, TextView tvCom1, TextView tvCom2, AccionesBot accionesBot,
                                 TextView tvMensajeVictoria, ImageView ivGifVictoria, Context context, MediaPlayer musicaFondo)
    {
        System.out.println("Flaco1 -> " + tvCasilla.getText());
        if (!tvCasilla.getText().equals("---"))
        {
            System.out.println("Flaco2");
            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() + 1);
            accionesBot.setNumJugLimpios(accionesBot.getNumJugLimpios() + 1);
            accionesBot.setNumJugNo(accionesBot.getNumJugNo() - 1);
            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 1);

            if (tvCasilla.getText().equals("Batallon"))
            {
                System.out.println("Navajero -> Durillas1 -> Batallon");
                accionesBot.setVeh1JugConseguido(true);
                accionesBot.setContadorJugBatallon(0);
            }
            else if (tvCasilla.getText().equals("Tanque"))
            {
                accionesBot.setVeh2JugConseguido(true);
                accionesBot.setContadorJugTanque(0);
            }
            else if (tvCasilla.getText().equals("Submarino"))
            {
                accionesBot.setVeh3JugConseguido(true);
                accionesBot.setContadorJugSubmarino(0);
            }
            else if (tvCasilla.getText().equals("Caza"))
            {
                accionesBot.setVeh4JugConseguido(true);
                accionesBot.setContadorJugCaza(0);
            }
            else if (tvCasilla.getText().equals("Vehículo Multicolor"))
            {
                accionesBot.setVehMulticolorJugConseguido(true);
                accionesBot.setContadorJugVehMulticolor(0);
            }

            if (!tvCom1.getText().equals("---"))
            {
                accionesBot.setNumJugLimpios(accionesBot.getNumJugLimpios() - 1);

                if (tvCom1.getText().equals("Medicina1"))
                {
                    if (tvCasilla.getText().equals("Batallon"))
                    {
                        if (tvCom2.getText().equals("Medicina1") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugBatallon(accionesBot.getContadorJugBatallon() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugBatallon(accionesBot.getContadorJugBatallon() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                    else
                    {
                        System.out.println("Upamecano1");

                        if (!tvCom2.getText().equals("---"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                }
                else if (tvCom1.getText().equals("Medicina2"))
                {
                    if (tvCasilla.getText().equals("Tanque"))
                    {
                        if (tvCom2.getText().equals("Medicina2") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugTanque(accionesBot.getContadorJugTanque() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugTanque(accionesBot.getContadorJugTanque() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                    else
                    {
                        System.out.println("Upamecano2");

                        if (!tvCom2.getText().equals("---"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                }
                else if (tvCom1.getText().equals("Medicina3"))
                {
                    if (tvCasilla.getText().equals("Submarino"))
                    {
                        if (tvCom2.getText().equals("Medicina3") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugSubmarino(accionesBot.getContadorJugSubmarino() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugSubmarino(accionesBot.getContadorJugSubmarino() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                    else
                    {
                        System.out.println("Upamecano3");

                        if (!tvCom2.getText().equals("---"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                }
                else if (tvCom1.getText().equals("Medicina4"))
                {
                    if (tvCasilla.getText().equals("Caza"))
                    {
                        if (tvCom2.getText().equals("Medicina4") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugCaza(accionesBot.getContadorJugCaza() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugCaza(accionesBot.getContadorJugCaza() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                    else
                    {
                        System.out.println("Upamecano4");

                        if (!tvCom2.getText().equals("---"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                }
                else if (tvCom1.getText().equals("Medicina Multicolor"))
                {
                    if (tvCasilla.getText().equals("Batallon"))
                    {
                        if (tvCom2.getText().equals("Medicina1") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugBatallon(accionesBot.getContadorJugBatallon() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugBatallon(accionesBot.getContadorJugBatallon() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                    else if (tvCasilla.getText().equals("Tanque"))
                    {
                        if (tvCom2.getText().equals("Medicina2") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugTanque(accionesBot.getContadorJugTanque() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugTanque(accionesBot.getContadorJugTanque() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                    else if (tvCasilla.getText().equals("Submarino"))
                    {
                        if (tvCom2.getText().equals("Medicina3") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugSubmarino(accionesBot.getContadorJugSubmarino() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugSubmarino(accionesBot.getContadorJugSubmarino() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                    else if (tvCasilla.getText().equals("Caza"))
                    {
                        if (tvCom2.getText().equals("Medicina4") || tvCom2.getText().equals("Medicina Multicolor"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugCaza(accionesBot.getContadorJugCaza() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugCaza(accionesBot.getContadorJugCaza() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                    else if (tvCasilla.getText().equals("Vehículo Multicolor"))
                    {
                        System.out.println("Upamecano5");

                        if (!tvCom2.getText().equals("---"))
                        {
                            accionesBot.setNumJugInmunizados(accionesBot.getNumJugInmunizados() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() + 2);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.2);
                        }
                        else
                        {
                            accionesBot.setNumJugMedicados(accionesBot.getNumJugMedicados() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() + 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() + 0.1);
                        }
                    }
                }
                else if (tvCom1.getText().equals("Metralleta"))
                {
                    if (tvCasilla.getText().equals("Batallon"))
                    {
                        if (tvCom2.getText().equals("Metralleta") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVeh1JugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugBatallon(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugBatallon(accionesBot.getContadorJugBatallon() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                    else
                    {
                        System.out.println("Konate1");

                        if (!tvCom2.getText().equals("---"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVehMulticolorJugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugVehMulticolor(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                }
                else if (tvCom1.getText().equals("Lanzacohetes"))
                {
                    if (tvCasilla.getText().equals("Tanque"))
                    {
                        if (tvCom2.getText().equals("Lanzacohetes") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVeh2JugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugTanque(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugTanque(accionesBot.getContadorJugTanque() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                    else
                    {
                        System.out.println("Konate2");

                        if (!tvCom2.getText().equals("---"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVehMulticolorJugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugVehMulticolor(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                }
                else if (tvCom1.getText().equals("Torpedos"))
                {
                    if (tvCasilla.getText().equals("Submarino"))
                    {
                        if (tvCom2.getText().equals("Torpedos") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVeh3JugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugSubmarino(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugSubmarino(accionesBot.getContadorJugSubmarino() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                    else
                    {
                        System.out.println("Konate3");

                        if (!tvCom2.getText().equals("---"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVehMulticolorJugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugVehMulticolor(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                }
                else if (tvCom1.getText().equals("Misiles"))
                {
                    if (tvCasilla.getText().equals("Caza"))
                    {
                        if (tvCom2.getText().equals("Misiles") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVeh4JugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugCaza(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugCaza(accionesBot.getContadorJugCaza() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                    else
                    {
                        System.out.println("Konate4");

                        if (!tvCom2.getText().equals("---"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVehMulticolorJugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugVehMulticolor(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                }
                else if (tvCom1.getText().equals("Arma Multicolor"))
                {
                    if (tvCasilla.getText().equals("Batallon"))
                    {
                        if (tvCom2.getText().equals("Metralleta") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVeh1JugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugBatallon(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugBatallon(accionesBot.getContadorJugBatallon() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                    else if (tvCasilla.getText().equals("Tanque"))
                    {
                        if (tvCom2.getText().equals("Lanzacohetes") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVeh2JugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugTanque(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugTanque(accionesBot.getContadorJugTanque() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                    else if (tvCasilla.getText().equals("Submarino"))
                    {
                        if (tvCom2.getText().equals("Torpedos") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVeh3JugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugSubmarino(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugSubmarino(accionesBot.getContadorJugSubmarino() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                    else if (tvCasilla.getText().equals("Caza"))
                    {
                        if (tvCom2.getText().equals("Misiles") || tvCom2.getText().equals("Arma Multicolor"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVeh4JugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugCaza(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugCaza(accionesBot.getContadorJugCaza() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                    else if (tvCasilla.getText().equals("Vehículo Multicolor"))
                    {
                        System.out.println("Konate5");

                        if (!tvCom2.getText().equals("---"))
                        {
                            accionesBot.setNumJugVehConseguidos(accionesBot.getNumJugVehConseguidos() - 1);
                            accionesBot.setVehMulticolorJugConseguido(false);
                            accionesBot.setNumJugNo(accionesBot.getNumJugNo() + 1);
                            accionesBot.setContadorJugVehMulticolor(-5);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 1);

                            tvCasilla.setText("---");
                            tvCom1.setText("---");
                            tvCom2.setText("---");
                        }
                        else
                        {
                            accionesBot.setNumJugVirus(accionesBot.getNumJugVirus() + 1);
                            accionesBot.setContadorJugVehMulticolor(accionesBot.getContadorJugVehMulticolor() - 1);
                            accionesBot.setPuntosJug(accionesBot.getPuntosJug() - 0.1);
                        }
                    }
                }
            }
        }

        determinarImagenCasillaCartas(tvCartasJugadores, ivCartasJugadores);
        determinarImagenCasillaVehiculos(tvCasillasJugadores, ivCasillasJugadores, accionesBot);

        if (jugEsCampeon(accionesBot, ivGifVictoria, context, musicaFondo))
        {
            tvMensajeVictoria.setText(context.getResources().getString(R.string.tvMensajeVictoria3));
        }
    }

    public boolean jugEsCampeon(AccionesBot accionesBot, ImageView ivGifVictoria, Context context, MediaPlayer musicaFondo)
    {
        if (accionesBot.getNumJugVehConseguidos() >= 4)
        {
            if (accionesBot.getContadorJugBatallon() != -1 && accionesBot.getContadorJugTanque() != -1 && accionesBot.getContadorJugSubmarino() != -1
                    && accionesBot.getContadorJugCaza() != -1 && accionesBot.getContadorJugVehMulticolor() != -1)
            {
                ivGifVictoria.setVisibility(View.VISIBLE);

                musicaFondo.stop();

                String mensajeCabecera = context.getResources().getString(R.string.tvMensajeVictoria3);

                Intent intent_Popup = new Intent(context, PopupWindow.class);
                intent_Popup.putExtra("mensajeCabecera", mensajeCabecera);
                intent_Popup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent_Popup);

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

    public void echarTresCartas(String barajaCartas[], TextView tvCarta1, TextView tvCarta2, TextView tvCarta3)
    {
        // Repartimos tres cartas a cada jugador.
        for (int i = 1; i <= 3; i++)
        {
            String cartaSacada = barajaCartas[sacarCarta2()];

            if (i == 1)
            {
                tvCarta1.setText(cartaSacada);
                colorCarta(cartaSacada, tvCarta1);
            }
            else if (i == 2)
            {
                tvCarta2.setText(cartaSacada);
                colorCarta(cartaSacada, tvCarta2);
            }
            else if (i == 3)
            {
                tvCarta3.setText(cartaSacada);
                colorCarta(cartaSacada, tvCarta3);
            }
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

    public ArrayList<Integer> decidirTurnos(int numJugadores)
    {
        for (int i = 0; i < numJugadores; i++)
        {
            valorTurnoJug.add(i, i);
        }

        Collections.shuffle(valorTurnoJug);

        /*int turnoJug;

        for (int i = 0; i < numJugadores; i++)
        {
            do {
                turnoJug = (int) Math.floor(Math.random() * (numJugadores) + 0);
                numEsRepetido(turnoJug, valorTurnoJug);
            }
            while (numRepetido == true);

            System.out.println("Turno: numJugadores -> " + turnoJug);
            valorTurnoJug.add(turnoJug);
        }*/

        return valorTurnoJug;
    }

    public ArrayList<String> decidirNombresEjercitos(int numJugadores)
    {
        ArrayList <String> ordenNombresEjercitos = new ArrayList<>();

        ArrayList<Integer> ordenNumEjercito = new ArrayList<>();
        boolean numRepetido;
        int numEjercito;

        for (int i = 0; i < numJugadores; i++)
        {
            do {
                numRepetido = false;
                numEjercito = (int) Math.floor(Math.random() * (nombresEjercitos.size()) + 0);

                System.out.println("Aladin1 -> " + numEjercito + " - " + ordenNumEjercito.size());

                for (int j = 0; j < ordenNumEjercito.size(); j++)
                {
                    System.out.println("Aladin2 -> " + numEjercito + " - " + ordenNumEjercito.size());

                    if (numEjercito == ordenNumEjercito.get(j))
                    {
                        numRepetido = true;
                    }
                }
            }
            while (numRepetido == true);

            ordenNumEjercito.add(i, numEjercito);
        }

        for (int a = 0; a < ordenNumEjercito.size(); a++)
        {
            ordenNombresEjercitos.add(a, nombresEjercitos.get(ordenNumEjercito.get(a)));

            System.out.println("Aladin3 -> " + ordenNumEjercito.get(a));
        }

        return ordenNombresEjercitos;
    }

    public void nombrarEjercitos(TextView[] tvNombresEjercitos, ArrayList<String> strNombresEjercitos)
    {
        for (int i = 0; i < strNombresEjercitos.size() - 1; i++)
        {
            System.out.println("Dembele -> " + strNombresEjercitos.get(i));
            tvNombresEjercitos[i].setText(strNombresEjercitos.get(i));
        }
    }

    public int saberPosicionMiTurno()
    {
        System.out.println("Turnoooooooooooooooooooooo: listNumJug -> ");
        ArrayList<Integer> ordenTurno = valorTurnoJug;

        int posMiTurno = 0;

        while (!ordenTurno.get(posMiTurno).equals(0))
        {
            posMiTurno += 1;
        }

        return posMiTurno;
    }

    public boolean jugadorConMasPuntos(double puntosBot, ArrayList<Double> puntosRestoJug)
    {
        boolean tengoMasPuntosQueNadie = true;

        for (int i = 0; i < puntosRestoJug.size(); i++)
        {
            if (puntosRestoJug.get(i) > puntosBot)
            {
                tengoMasPuntosQueNadie = false;
            }
        }

        return tengoMasPuntosQueNadie;
    }

    public int rivalConMasPuntos(ArrayList<Double> puntosRestoJug)
    {
        int puntuacionMasAlta = 0;
        int jugadorConMasPuntos = 0;

        for (int i = 0; i < puntosRestoJug.size(); i++)
        {
            if (puntosRestoJug.get(i) > puntuacionMasAlta)
            {
                jugadorConMasPuntos = i;
            }
        }

        return jugadorConMasPuntos;
    }

    public double puntosRivalConMasPuntos(AccionesBot accionesBot, int jugadorConMasPuntos)
    {
        if (jugadorConMasPuntos == 0)
        {
            return accionesBot.getPuntosJug();
        }
        else
        {
            return accionesBot.getPuntosBot(jugadorConMasPuntos - 1);
        }
    }

    public int saberNombreJugPorSuId(TextView tvJugBotVeh, View view, int limiteMinimo, int limiteMaximo)
    {
        String idTvVehiculo = tvJugBotVeh.getResources().getResourceEntryName(view.getId());

        String strNombreJug = idTvVehiculo.substring(limiteMinimo, limiteMaximo);
        System.out.println("Aelita1 -> " + idTvVehiculo + " - " + strNombreJug + " - " + tvJugBotVeh.getText());

        try
        {
            int strNombreJug2 = Integer.parseInt(strNombreJug) - 1;

            if (strNombreJug2 < 0)
            {
                return 0;
            }
            else
            {
                return strNombreJug2;
            }
        }
        catch (NumberFormatException excepcion) {
            return -1;
        }
    }

    public int saberNombreJugPorSuId2(TextView tvJugBotVeh, Context context, int limiteMinimo, int limiteMaximo)
    {
        int idtvJugBotVeh = tvJugBotVeh.getId();
        String stringname = context.getResources().getResourceEntryName(idtvJugBotVeh);

        String strNombreJug = stringname.substring(limiteMinimo, limiteMaximo);
        System.out.println("Aelita2 -> " + stringname + " - "  + strNombreJug + " - " + tvJugBotVeh.getText());

        int strNombreJug2 = Integer.parseInt(strNombreJug) - 1;

        return strNombreJug2;
    }

    public int saberNombreJugPorSuId3(TextView tvJugBotVeh, View view, int limiteMinimo, int limiteMaximo)
    {
        String idTvVehiculo = tvJugBotVeh.getResources().getResourceEntryName(view.getId());

        String strNombreJug = idTvVehiculo.substring(limiteMinimo, limiteMaximo);
        System.out.println("Aelita1 -> " + idTvVehiculo + " - " + strNombreJug + " - " + tvJugBotVeh.getText());

        try
        {
            int strNombreJug2 = Integer.parseInt(strNombreJug) - 1;

            if (strNombreJug2 < 1)
            {
                return 1;
            }
            else
            {
                return strNombreJug2;
            }
        }
        catch (NumberFormatException excepcion)
        {
            return -1;
        }
    }

    public int saberNombreJugPorSuRow(TableRow tbJugBotRow, View view, int limiteMinimo, int limiteMaximo)
    {
        String idTvVehiculo = tbJugBotRow.getResources().getResourceEntryName(view.getId());

        String strNombreJug = idTvVehiculo.substring(limiteMinimo, limiteMaximo);
        System.out.println("Aelita8 -> " + idTvVehiculo);

        try
        {
            Integer.parseInt(strNombreJug);
            return Integer.parseInt(strNombreJug);
        }
        catch (NumberFormatException excepcion) {
            return 0;
        }
    }

    public TextView[] reordenarTvVehiculosRivales(TextView tvVehiculos[])
    {
        ordenPosTvVehiculos = new int[25];
        ArrayList<TextView> listVehiculos = new ArrayList<>();

        TextView[] nuevoOrdenTvVehiculos = new TextView[25];

        for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
        {
            listVehiculos.add(tvVehiculos[i]);
        }

        int posNuevoOrdenTvVehiculos = 0;
        while (listVehiculos.size() > 0)
        {
            int valorCarta = (int) Math.floor(Math.random() * (0 - listVehiculos.size() + 1) + listVehiculos.size());
            ordenPosTvVehiculos[posNuevoOrdenTvVehiculos] = valorCarta - 1;

            nuevoOrdenTvVehiculos[posNuevoOrdenTvVehiculos] = listVehiculos.get(valorCarta - 1);
            System.out.println("Espeso -> " + nuevoOrdenTvVehiculos[posNuevoOrdenTvVehiculos].getText());

            listVehiculos.remove(valorCarta - 1);
            posNuevoOrdenTvVehiculos += 1;
        }

        return nuevoOrdenTvVehiculos;
    }

    public TextView[] reordenarTvComodinesRivales(TextView tvComodines[])
    {
        ArrayList<TextView> listComodines = new ArrayList<>();

        TextView[] nuevoOrdenTvComodines = new TextView[25];

        for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
        {
            listComodines.add(tvComodines[i]);
        }

        int posNuevoOrdenTvComodines = 0;
        while (listComodines.size() > 0)
        {
            nuevoOrdenTvComodines[posNuevoOrdenTvComodines] = listComodines.get(ordenPosTvVehiculos[posNuevoOrdenTvComodines]);

            System.out.println("Green -> ****************************************");
            for (int i = 0; i < listComodines.size(); i++)
            {
                System.out.println("Green -> " + listComodines.get(i).getText());
            }

            listComodines.remove(ordenPosTvVehiculos[posNuevoOrdenTvComodines]);

            posNuevoOrdenTvComodines += 1;
        }

        return nuevoOrdenTvComodines;
    }
}
