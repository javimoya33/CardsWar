package cartas.guerra.juego.jmm.cardswar.FuncionesJugador;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.media.SoundPool;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cartas.guerra.juego.jmm.cardswar.BaseDeDatos.AccionesBot;
import cartas.guerra.juego.jmm.cardswar.R;

/**
 * Clase que recogerá los métodos propios del jugador
 */
public class FuncionesJugador extends FuncionesBOT {

    public TextView tvToolbar;

    public TextView tvVehiculosJugadores[];
    public ImageView ivVehiculosJugadores[];
    public TextView tvComodinesJugadores[];
    public RelativeLayout relVehiculosJugadores[];
    public TableRow tbRowVehiculos;

    public TextView tvCartasJugadores[];
    public ImageView ivCartasJugadores[];

    public static String tipoMedicinaVehiculoMulticolor = "---";

    // Boolean que determina si el número de la barja ya ha salido o no.
    private boolean numRepetido;

    // Array con los números de la baraja.
    private ArrayList<Integer> valorBarajaCartas = new ArrayList<>();

    public static boolean tvVehJugsPulsableIntercambio[];
    public static String tvVehJugAIntercambiar = "";
    public static String tvCom1JugAIntercambiar = "";
    public static String tvCom2JugAIntercambiar = "";

    public static boolean tvEqJugAIntercambiar = false;

    public static boolean tvVehJugsPulsableExpulsion[];
    public static String tvVehComAExpulsar = "";

    FuncionesGenerales funcionesGenerales;

    Context context;

    public int numeroJugadores;

    public boolean expulsionArmas = false;

    public SoundPool sonidoJuego;

    ArrayList<String> nombresEjercitos;

    public TableLayout tableJugadorVehiculos;

    public FuncionesJugador(TextView tvJugVehs[], ImageView ivJugVehs[], TextView tvJugComs[], TextView tvJugCartas[],
                            ImageView ivJugCartas[], String tipoMedVehMulticolor, String vehiculoPulsado,
                            RelativeLayout relJugVehs[], TableRow tbRowJugVehs,
                            boolean[] tvVehJugsPI, boolean[] tvVehJugsPE, String tvVehComAE, Context context,
                            int numJug, TextView tvtoolbar, SoundPool sonidojuego, ArrayList<String> nomEjercitos,
                            ArrayList<String> ordenNomEjercitos, TableLayout tabJugVeh)
    {
        super();
        this.tvVehiculosJugadores = tvJugVehs;
        this.ivVehiculosJugadores = ivJugVehs;
        this.tvComodinesJugadores = tvJugComs;
        this.tvCartasJugadores = tvJugCartas;
        this.ivCartasJugadores = ivJugCartas;
        tipoMedicinaVehiculoMulticolor = tipoMedVehMulticolor;
        tvVehJugAIntercambiar = vehiculoPulsado;
        this.relVehiculosJugadores = relJugVehs;
        this.tbRowVehiculos = tbRowJugVehs;
        tvVehJugsPulsableIntercambio = tvVehJugsPI;
        tvVehJugsPulsableExpulsion= tvVehJugsPE;
        tvVehComAExpulsar = tvVehComAE;
        this.context = context;
        this.numeroJugadores = numJug;
        this.tvToolbar = tvtoolbar;
        this.sonidoJuego = sonidojuego;
        this.nombresEjercitos = nomEjercitos;
        this.ordenNombresEjercitos = ordenNomEjercitos;
        this.tableJugadorVehiculos = tabJugVeh;
    }

    public FuncionesJugador(){}

    // Método que recoge las acciones que realiza el juego al pulsar una carta.
    public boolean dejarCarta(TextView tvJugCartaPulsada, TextView tvJugCarta2, TextView tvJugCarta3,
                              int numTirada, String[] barajaCartas, RelativeLayout relJugVeh, TextView tvJugVeh, TextView tvBotVeh,
                              TextView tvJugCom1, TextView tvJugCom2, TextView tvBotCom1, TextView tvBotCom2,
                              RelativeLayout relBotVeh, TableRow tbRowJugVeh, TableRow tbRowBotVeh, TextView tvMsjVictoria,
                              AccionesBot accionesBot, View view, ImageView ivDejarCartas1, ImageView ivDejarCartas2, ImageView ivDejarCartas3,
                              TableLayout tbCartas, TableRow[] tbRowVehs, LinearLayout linearJuego, ColorMatrixColorFilter filter)
    {
        funcionesGenerales = new FuncionesGenerales(tvVehiculosJugadores, ivVehiculosJugadores, tvComodinesJugadores,
                tvCartasJugadores, ivCartasJugadores, relVehiculosJugadores, numeroJugadores, nombresEjercitos, tbCartas,
                tbRowVehs, linearJuego, context, tableJugadorVehiculos);

        // Recogemos el nombre de la carta pulsada y determinamos que tipo de carta es
        String nombreCarta = (String) tvJugCartaPulsada.getText();
        System.out.println("Imantada1 -> " + nombreCarta);

        if (reunirVehiculo(nombreCarta, tvJugVeh, tvMsjVictoria, accionesBot))
        {
            System.out.println("Cayos Malayos1");

            int valorCarta = sacarCarta();
            tvJugCartaPulsada.setText(barajaCartas[valorCarta]);
            funcionesGenerales.colorCarta(barajaCartas[valorCarta], tvJugCartaPulsada);
            // Sumamos una nueva tirada.
            numTirada += 1;
            // Determinamos si la baraja de cartas restantes se ha agotado o no.
            funcionesGenerales.barajaAgotada(numTirada, tvJugCartaPulsada, tvJugCarta2, tvJugCarta3);

            return true;
        }
        else if (lanzarJugAntiarma(nombreCarta, (String) tvJugVeh.getText(), tvJugVeh, tvJugCom1, accionesBot))
        {
            System.out.println("Cayos Malayos2");

            int valorCarta = sacarCarta();
            tvJugCartaPulsada.setText(barajaCartas[valorCarta]);
            funcionesGenerales.colorCarta(barajaCartas[valorCarta], tvJugCartaPulsada);
            // Sumamos una nueva tirada.
            numTirada += 1;
            // Determinamos si la baraja de cartas restantes se ha agotado o no.
            funcionesGenerales.barajaAgotada(numTirada, tvJugCartaPulsada, tvJugCarta2, tvJugCarta3);

            return true;
        }
        else if (lanzarJugArma(nombreCarta, (String) tvBotVeh.getText(), tvBotVeh, tvBotCom1, accionesBot,
                funcionesGenerales.saberNombreJugPorSuId(tvBotVeh, view, 5, 6)))
        {
            System.out.println("Cayos Malayos3");

            funcionesGenerales = new FuncionesGenerales(tvVehiculosJugadores, ivVehiculosJugadores, tvComodinesJugadores,
                    tvCartasJugadores, ivCartasJugadores, relVehiculosJugadores, numeroJugadores, nombresEjercitos, tbCartas,
                    tbRowVehs, linearJuego, context, tableJugadorVehiculos);

            int valorCarta = sacarCarta();
            tvJugCartaPulsada.setText(barajaCartas[valorCarta]);
            funcionesGenerales.colorCarta(barajaCartas[valorCarta], tvJugCartaPulsada);

            numTirada += 1;

            funcionesGenerales.barajaAgotada(numTirada, tvJugCartaPulsada, tvJugCarta2, tvJugCarta3);

            return true;
        }
        else if (lanzarRoboVehiculo(nombreCarta, (String) tvBotVeh.getText(), tvBotVeh, tvBotCom1, tvBotCom2, relBotVeh,
                accionesBot, funcionesGenerales.saberNombreJugPorSuId3(tvBotVeh, view, 5, 6), view))
        {
            System.out.println("Cayos Malayos4");

            int valorCarta = sacarCarta();
            tvJugCartaPulsada.setText(barajaCartas[valorCarta]);
            funcionesGenerales.colorCarta(barajaCartas[valorCarta], tvJugCartaPulsada);

            numTirada += 1;

            funcionesGenerales.barajaAgotada(numTirada, tvJugCartaPulsada, tvJugCarta2, tvJugCarta3);

            return true;
        }
        else if (lanzarIntercambioVehiculo(nombreCarta, (String) tvJugVeh.getText(),
                relJugVeh, tvJugCom1, tvJugCom2, barajaCartas, tvJugCartaPulsada, accionesBot,
                tvVehiculosJugadores, relVehiculosJugadores,
                ivDejarCartas1, ivDejarCartas2, ivDejarCartas3))
        {
            System.out.println("Cayos Malayos5");

            int valorCarta = sacarCarta();
            tvJugCartaPulsada.setText(barajaCartas[valorCarta]);
            funcionesGenerales.colorCarta(barajaCartas[valorCarta], tvJugCartaPulsada);

            numTirada += 1;

            funcionesGenerales.barajaAgotada(numTirada, tvJugCartaPulsada, tvJugCarta2, tvJugCarta3);

            return true;
        }
        else if (lanzarIntercambioEquipo(nombreCarta, tbRowBotVeh, tbRowJugVeh, tvVehiculosJugadores, tvComodinesJugadores, view))
        {
            System.out.println("Cayos Malayos6");

            int valorCarta = sacarCarta();
            tvJugCartaPulsada.setText(barajaCartas[valorCarta]);
            funcionesGenerales.colorCarta(barajaCartas[valorCarta], tvJugCartaPulsada);

            numTirada += 1;

            funcionesGenerales.barajaAgotada(numTirada, tvJugCartaPulsada, tvJugCarta2, tvJugCarta3);

            return true;
        }
        else if (lanzarDejarCartas(nombreCarta, tbRowBotVeh, tbRowJugVeh, barajaCartas, tvCartasJugadores, numeroJugadores))
        {
            return true;
        }
        else if (lanzarExpulsionArmas(nombreCarta, accionesBot, view, ivDejarCartas1, ivDejarCartas2, ivDejarCartas3))
        {
            System.out.println("Cayos Malayos7");

            int valorCarta = sacarCarta();
            tvJugCartaPulsada.setText(barajaCartas[valorCarta]);
            funcionesGenerales.colorCarta(barajaCartas[valorCarta], tvJugCartaPulsada);

            numTirada += 1;

            funcionesGenerales.barajaAgotada(numTirada, tvJugCartaPulsada, tvJugCarta2, tvJugCarta3);

            for (int i = 0; i < 4; i++)
            {
                ivVehiculosJugadores[i].setAlpha(1f);
                ivVehiculosJugadores[i].setBackgroundColor(Color.TRANSPARENT);
            }

            return true;
        }
        else
        {
            System.out.println("Cayos Malayos8");

            return false;
        }
    }

    public boolean pulseExpulsarArmas(String nombreCarta, ImageView ivDejarCartas1, ImageView ivDejarCartas2, ImageView ivDejarCartas3)
    {
        if (nombreCarta.equals("Expulsar Armas"))
        {
            for (int i = 4; i < relVehiculosJugadores.length; i++)
            {
                relVehiculosJugadores[i].setClickable(true);
                ivDejarCartas1.setClickable(true);
                ivDejarCartas2.setClickable(true);
                ivDejarCartas3.setClickable(true);
            }

            for (int i = 0; i < 4; i++)
            {
                ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
            }

            System.out.println("John Boy");
            if (tvComodinesJugadores[0].getText().equals("Metralleta") || tvComodinesJugadores[0].getText().equals("Lanzacohetes")
                    || tvComodinesJugadores[0].getText().equals("Torpedos") || tvComodinesJugadores[0].getText().equals("Misiles")
                    || tvComodinesJugadores[0].getText().equals("Arma Multicolor"))
            {
                tvComodinesJugadores[0].setBackgroundColor((Color.parseColor("#ffA040")));
            }
            if (tvComodinesJugadores[1].getText().equals("Metralleta") || tvComodinesJugadores[1].getText().equals("Lanzacohetes")
                    || tvComodinesJugadores[1].getText().equals("Torpedos") || tvComodinesJugadores[1].getText().equals("Misiles")
                    || tvComodinesJugadores[1].getText().equals("Arma Multicolor"))
            {
                tvComodinesJugadores[1].setBackgroundColor((Color.parseColor("#ffA040")));
            }
            if (tvComodinesJugadores[2].getText().equals("Metralleta") || tvComodinesJugadores[2].getText().equals("Lanzacohetes")
                    || tvComodinesJugadores[2].getText().equals("Torpedos") || tvComodinesJugadores[2].getText().equals("Misiles")
                    || tvComodinesJugadores[2].getText().equals("Arma Multicolor"))
            {
                tvComodinesJugadores[2].setBackgroundColor((Color.parseColor("#ffA040")));
            }
            if (tvComodinesJugadores[3].getText().equals("Metralleta") || tvComodinesJugadores[3].getText().equals("Lanzacohetes")
                    || tvComodinesJugadores[3].getText().equals("Torpedos") || tvComodinesJugadores[3].getText().equals("Misiles")
                    || tvComodinesJugadores[3].getText().equals("Arma Multicolor"))
            {
                tvComodinesJugadores[3].setBackgroundColor((Color.parseColor("#ffA040")));
            }
            if (tvComodinesJugadores[4].getText().equals("Metralleta") || tvComodinesJugadores[4].getText().equals("Lanzacohetes")
                    || tvComodinesJugadores[4].getText().equals("Torpedos") || tvComodinesJugadores[4].getText().equals("Misiles")
                    || tvComodinesJugadores[4].getText().equals("Arma Multicolor"))
            {
                tvComodinesJugadores[4].setBackgroundColor((Color.parseColor("#ffA040")));
            }
            if (tvComodinesJugadores[5].getText().equals("Metralleta") || tvComodinesJugadores[5].getText().equals("Lanzacohetes")
                    || tvComodinesJugadores[5].getText().equals("Torpedos") || tvComodinesJugadores[5].getText().equals("Misiles")
                    || tvComodinesJugadores[5].getText().equals("Arma Multicolor"))
            {
                tvComodinesJugadores[5].setBackgroundColor((Color.parseColor("#ffA040")));
            }
            if (tvComodinesJugadores[6].getText().equals("Metralleta") || tvComodinesJugadores[6].getText().equals("Lanzacohetes")
                    || tvComodinesJugadores[6].getText().equals("Torpedos") || tvComodinesJugadores[6].getText().equals("Misiles")
                    || tvComodinesJugadores[6].getText().equals("Arma Multicolor"))
            {
                tvComodinesJugadores[6].setBackgroundColor((Color.parseColor("#ffA040")));
            }
            if (tvComodinesJugadores[7].getText().equals("Metralleta") || tvComodinesJugadores[7].getText().equals("Lanzacohetes")
                    || tvComodinesJugadores[7].getText().equals("Torpedos") || tvComodinesJugadores[7].getText().equals("Misiles")
                    || tvComodinesJugadores[7].getText().equals("Arma Multicolor"))
            {
                tvComodinesJugadores[7].setBackgroundColor((Color.parseColor("#ffA040")));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean pulseDejarCartas(String nombreCarta)
    {
        if (nombreCarta.equals("Dejar Cartas"))
        {
            for (int i = 0; i < 4; i++)
            {
                ivVehiculosJugadores[i].setAlpha(0.5f);
                ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean pulseIntercambiarEquipo(String nombreCarta, TableRow tbRowBotVeh, RelativeLayout[] relsBotVeh, View view)
    {
        if (nombreCarta.equals("Intercambiar Brigada"))
        {
            String idTbRowBotVeh = tbRowBotVeh.getResources().getResourceEntryName(view.getId());

            System.out.println("Furioso -> " + idTbRowBotVeh.substring(2, 5));

            for (int i = 4; i < relsBotVeh.length; i++)
            {
                relsBotVeh[i].setAlpha(0.5f);
                relsBotVeh[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean pulseIntercambiarVehiculo(String nombreCarta, String nombreCasillaBotVehiculo, String nombreCasillaJugVehiculo,
                                             RelativeLayout relJugVeh, RelativeLayout relBotVeh, Context context,
                                             AccionesBot accionesBot, int jugBot2, int pos, ImageView ivDejarCartas1, ImageView ivDejarCartas2, ImageView ivDejarCartas3,
                                             boolean lanzarIntercambioVeh)
    {
        int jugBot = jugBot2 - 1;
        int posVehConseguido = (pos - 1) * 5;
        System.out.println("Cipres -> " + nombreCasillaJugVehiculo + nombreCasillaBotVehiculo + " - " + posVehConseguido + " - " + jugBot + " - " + lanzarIntercambioVeh);

        for (int i = 0; i < accionesBot.vehBotConseguido.length; i++)
        {
            System.out.println("Retales -> " + accionesBot.vehBotConseguido[i]);
        }
        System.out.println("Retales -> ------------------------------------------------");

        if (nombreCarta.equals("Cambiar Vehículo"))
        {
            for (int i = 4; i < relVehiculosJugadores.length; i++)
            {
                relVehiculosJugadores[i].setClickable(true);
                ivDejarCartas1.setClickable(true);
                ivDejarCartas2.setClickable(true);
                ivDejarCartas3.setClickable(true);
            }


            if (((nombreCasillaBotVehiculo.equals("Batallon"))) && (nombreCasillaJugVehiculo.equals("Batallon")))
            {
                if (accionesBot.getContadorJugBatallon() < 2 && accionesBot.getContadorBotBatallon(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Batallon")) && ((!accionesBot.getVeh1JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Tanque")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 1)))))

            {
                if (accionesBot.getContadorJugTanque() < 2 && accionesBot.getContadorBotBatallon(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Batallon")) && ((!accionesBot.getVeh1JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Submarino")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 2)))))

            {
                if (accionesBot.getContadorJugSubmarino() < 2 && accionesBot.getContadorBotBatallon(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Batallon")) && ((!accionesBot.getVeh1JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Caza")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 3)))))

            {
                if (accionesBot.getContadorJugCaza() < 2 && accionesBot.getContadorBotBatallon(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Tanque")) && ((!accionesBot.getVeh2JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Batallon")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 0)))))

            {
                if (accionesBot.getContadorJugBatallon() < 2 && accionesBot.getContadorBotTanque(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Tanque"))) && (nombreCasillaJugVehiculo.equals("Tanque")))

            {
                if (accionesBot.getContadorJugTanque() < 2 && accionesBot.getContadorBotTanque(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Tanque")) && ((!accionesBot.getVeh2JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Submarino")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 2)))))

            {
                if (accionesBot.getContadorJugSubmarino() < 2 && accionesBot.getContadorBotTanque(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Tanque")) && ((!accionesBot.getVeh2JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Caza")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 3)))))

            {
                if (accionesBot.getContadorJugCaza() < 2 && accionesBot.getContadorBotTanque(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Submarino")) && ((!accionesBot.getVeh3JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Batallon")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 0)))))

            {
                if (accionesBot.getContadorJugBatallon() < 2 && accionesBot.getContadorBotSubmarino(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Submarino")) && ((!accionesBot.getVeh3JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Tanque")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 1)))))

            {
                if (accionesBot.getContadorJugTanque() < 2 && accionesBot.getContadorBotSubmarino(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Submarino"))) && (nombreCasillaJugVehiculo.equals("Submarino")))

            {
                if (accionesBot.getContadorJugSubmarino() < 2 && accionesBot.getContadorBotSubmarino(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Submarino")) && ((!accionesBot.getVeh3JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Caza")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 3)))))

            {
                if (accionesBot.getContadorJugCaza() < 2 && accionesBot.getContadorBotSubmarino(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Caza")) && ((!accionesBot.getVeh4JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Batallon")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 0)))))

            {
                if (accionesBot.getContadorJugBatallon() < 2 && accionesBot.getContadorBotCaza(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Caza")) && ((!accionesBot.getVeh4JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Tanque")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 1)))))

            {
                if (accionesBot.getContadorJugTanque() < 2 && accionesBot.getContadorBotCaza(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Caza")) && ((!accionesBot.getVeh4JugConseguido())))
                    && ((nombreCasillaJugVehiculo.equals("Submarino")) && ((!accionesBot.getVehBotConseguido(posVehConseguido + 2)))))

            {
                if (accionesBot.getContadorJugSubmarino() < 2 && accionesBot.getContadorBotCaza(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Caza"))) && (nombreCasillaJugVehiculo.equals("Caza")))

            {
                if (accionesBot.getContadorJugCaza() < 2 && accionesBot.getContadorBotCaza(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (((nombreCasillaBotVehiculo.equals("Vehículo Multicolor"))) && (nombreCasillaJugVehiculo.equals("Vehículo Multicolor")))
            {
                if (accionesBot.getContadorJugVehMulticolor() < 2 && accionesBot.getContadorBotVehMulticolor(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (nombreCasillaBotVehiculo.equals("Vehículo Multicolor") && (!accionesBot.getVehBotConseguido(posVehConseguido + 0))
                    && (nombreCasillaJugVehiculo.equals("Batallon")) && (!accionesBot.getVehMulticolorJugConseguido()))
            {
                if (accionesBot.getContadorJugBatallon() < 2 && accionesBot.getContadorBotVehMulticolor(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (nombreCasillaBotVehiculo.equals("Vehículo Multicolor") && (!accionesBot.getVehBotConseguido(posVehConseguido + 1))
                    && (nombreCasillaJugVehiculo.equals("Tanque")) && (!accionesBot.getVehMulticolorJugConseguido()))
            {
                if (accionesBot.getContadorJugTanque() < 2 && accionesBot.getContadorBotVehMulticolor(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (nombreCasillaBotVehiculo.equals("Vehículo Multicolor") && (!accionesBot.getVehBotConseguido(posVehConseguido + 2))
                    && (nombreCasillaJugVehiculo.equals("Submarino")) && (!accionesBot.getVehMulticolorJugConseguido()))
            {
                if (accionesBot.getContadorJugSubmarino() < 2 && accionesBot.getContadorBotVehMulticolor(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (nombreCasillaBotVehiculo.equals("Vehículo Multicolor") && (!accionesBot.getVehBotConseguido(posVehConseguido + 3))
                    && (nombreCasillaJugVehiculo.equals("Caza")) && (!accionesBot.getVehMulticolorJugConseguido()))
            {
                if (accionesBot.getContadorJugCaza() < 2 && accionesBot.getContadorBotVehMulticolor(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (nombreCasillaBotVehiculo.equals("Batallon") && (!accionesBot.getVehBotConseguido(posVehConseguido + 4))
                    && (nombreCasillaJugVehiculo.equals("Vehículo Multicolor")) && (!accionesBot.getVeh1JugConseguido()))
            {
                if (accionesBot.getContadorJugVehMulticolor() < 2 && accionesBot.getContadorBotBatallon(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (nombreCasillaBotVehiculo.equals("Tanque") && (!accionesBot.getVehBotConseguido(posVehConseguido + 4))
                    && (nombreCasillaJugVehiculo.equals("Vehículo Multicolor")) && (!accionesBot.getVeh2JugConseguido()))
            {
                if (accionesBot.getContadorJugVehMulticolor() < 2 && accionesBot.getContadorBotTanque(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (nombreCasillaBotVehiculo.equals("Submarino") && (!accionesBot.getVehBotConseguido(posVehConseguido + 4))
                    && (nombreCasillaJugVehiculo.equals("Vehículo Multicolor")) && (!accionesBot.getVeh3JugConseguido()))
            {
                if (accionesBot.getContadorJugVehMulticolor() < 2 && accionesBot.getContadorBotSubmarino(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else if (nombreCasillaBotVehiculo.equals("Caza") && (!accionesBot.getVehBotConseguido(posVehConseguido + 4))
                    && (nombreCasillaJugVehiculo.equals("Vehículo Multicolor")) && (!accionesBot.getVeh4JugConseguido()))
            {
                if (accionesBot.getContadorJugVehMulticolor() < 2 && accionesBot.getContadorBotCaza(jugBot) < 2)
                {
                    if (lanzarIntercambioVeh)
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                    }
                    else
                    {
                        relJugVeh.setAlpha(0.5f);
                        relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
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
        else
        {
            return false;
        }
    }

    public boolean pulseLanzarArma(String nombreCarta)
    {
        System.out.println("Alex -> " + nombreCarta);

        if (nombreCarta.equals("Metralleta"))
        {
            for (int i = 4; i < tvVehiculosJugadores.length; i++)
            {
                if (tvVehiculosJugadores[i].getText().equals("Batallon") || tvVehiculosJugadores[i].getText().equals("Vehículo Multicolor"))
                {
                    if (tvComodinesJugadores[i * 2 + 1].getText().equals("---"))
                    {
                        ivVehiculosJugadores[i].setAlpha(0.5f);
                        ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                }
            }

            return true;
        }
        else if (nombreCarta.equals("Lanzacohetes"))
        {
            for (int i = 4; i < tvVehiculosJugadores.length; i++)
            {
                if (tvVehiculosJugadores[i].getText().equals("Tanque") || tvVehiculosJugadores[i].getText().equals("Vehículo Multicolor"))
                {
                    if (tvComodinesJugadores[i * 2 + 1].getText().equals("---"))
                    {
                        System.out.println("Calling2");
                        ivVehiculosJugadores[i].setAlpha(0.5f);
                        ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                }
            }

            return true;
        }
        else if (nombreCarta.equals("Torpedos"))
        {
            for (int i = 4; i < tvVehiculosJugadores.length; i++)
            {
                if (tvVehiculosJugadores[i].getText().equals("Submarino") || tvVehiculosJugadores[i].getText().equals("Vehículo Multicolor"))
                {
                    if (tvComodinesJugadores[i * 2 + 1].getText().equals("---"))
                    {
                        System.out.println("Calling3");
                        ivVehiculosJugadores[i].setAlpha(0.5f);
                        ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                }
            }

            return true;
        }
        else if (nombreCarta.equals("Misiles"))
        {
            for (int i = 4; i < tvVehiculosJugadores.length; i++)
            {
                if (tvVehiculosJugadores[i].getText().equals("Caza") || tvVehiculosJugadores[i].getText().equals("Vehículo Multicolor"))
                {
                    if (tvComodinesJugadores[i * 2 + 1].getText().equals("---"))
                    {
                        System.out.println("Calling4");
                        ivVehiculosJugadores[i].setAlpha(0.5f);
                        ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                }
            }

            return true;
        }
        else if (nombreCarta.equals("Arma Multicolor"))
        {
            for (int i = 4; i < tvVehiculosJugadores.length; i++)
            {
                if (!tvVehiculosJugadores[i].getText().equals("---"))
                {
                    if (tvComodinesJugadores[i * 2 + 1].getText().equals("---"))
                    {
                        System.out.println("Calling5");
                        ivVehiculosJugadores[i].setAlpha(0.5f);
                        ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
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

    public boolean pulseLanzarMedicina(String nombreCarta)
    {
        System.out.println("Alex -> " + nombreCarta);

        if (nombreCarta.equals("Medicina1"))
        {
            for (int i = 0; i < 4; i++)
            {
                if (tvVehiculosJugadores[i].getText().equals("Batallon") || tvVehiculosJugadores[i].getText().equals("Vehículo Multicolor"))
                {
                    if (tvComodinesJugadores[i * 2 + 1].getText().equals("---"))
                    {
                        ivVehiculosJugadores[i].setAlpha(0.5f);
                        ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                }
            }

            return true;
        }
        else if (nombreCarta.equals("Medicina2"))
        {
            for (int i = 0; i < 4; i++)
            {
                if (tvVehiculosJugadores[i].getText().equals("Tanque") || tvVehiculosJugadores[i].getText().equals("Vehículo Multicolor"))
                {
                    if (tvComodinesJugadores[i * 2 + 1].getText().equals("---"))
                    {
                        ivVehiculosJugadores[i].setAlpha(0.5f);
                        ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                }
            }

            return true;
        }
        else if (nombreCarta.equals("Medicina3"))
        {
            for (int i = 0; i < 4; i++)
            {
                if (tvVehiculosJugadores[i].getText().equals("Submarino") || tvVehiculosJugadores[i].getText().equals("Vehículo Multicolor"))
                {
                    if (tvComodinesJugadores[i * 2 + 1].getText().equals("---"))
                    {
                        ivVehiculosJugadores[i].setAlpha(0.5f);
                        ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                }
            }

            return true;
        }
        else if (nombreCarta.equals("Medicina4"))
        {
            for (int i = 0; i < 4; i++)
            {
                if (tvVehiculosJugadores[i].getText().equals("Caza") || tvVehiculosJugadores[i].getText().equals("Vehículo Multicolor"))
                {
                    if (tvComodinesJugadores[i * 2 + 1].getText().equals("---"))
                    {
                        ivVehiculosJugadores[i].setAlpha(0.5f);
                        ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                    }
                }
            }

            return true;
        }
        else if (nombreCarta.equals("Medicina Multicolor"))
        {
            for (int i = 0; i < 4; i++)
            {
                if (!tvVehiculosJugadores[i].getText().equals("---"))
                {
                    if (tvComodinesJugadores[i * 2 + 1].getText().equals("---"))
                    {
                        ivVehiculosJugadores[i].setAlpha(0.5f);
                        ivVehiculosJugadores[i].setBackgroundColor(Color.parseColor("#70FFFFFF"));
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

    public boolean pulseRoboVehiculo(String nombreCarta, String nombreCasillaVehiculo, RelativeLayout relBotVeh,
                                     AccionesBot accionesBot, int jugBot2)
    {
        int jugBot = jugBot2 - 1;

        if (nombreCarta.equals("Robar Vehículo"))
        {
            System.out.println("Estrella -> " + jugBot);

            if (jugBot > -1)
            {
                if (accionesBot.getNumJugVehConseguidos() < 4)
                {
                    if ((nombreCasillaVehiculo.equals("Batallon")) && (!accionesBot.getVeh1JugConseguido()) && (accionesBot.getContadorBotBatallon(jugBot) < 2))
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        return true;
                    }
                    else if ((nombreCasillaVehiculo.equals("Tanque")) && (!accionesBot.getVeh2JugConseguido()) && (accionesBot.getContadorBotTanque(jugBot) < 2))
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        return true;
                    }
                    else if ((nombreCasillaVehiculo.equals("Submarino")) && (!accionesBot.getVeh3JugConseguido()) && (accionesBot.getContadorBotSubmarino(jugBot) < 2))
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        return true;
                    }
                    else if ((nombreCasillaVehiculo.equals("Caza")) && (!accionesBot.getVeh4JugConseguido()) && (accionesBot.getContadorBotCaza(jugBot) < 2))
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
                        return true;
                    }
                    else if ((nombreCasillaVehiculo.equals("Vehículo Multicolor")) && (!accionesBot.getVehMulticolorJugConseguido()) && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2))
                    {
                        relBotVeh.setAlpha(0.5f);
                        relBotVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
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

    public boolean pulseReunirVehiculo(String nombreCarta, String nombreCasillaVehiculoBot, RelativeLayout relJugVeh,
                                       AccionesBot accionesBot)
    {
        System.out.println("NombreCarta -> " + nombreCarta);

        if (nombreCarta.equals("Batallon"))
        {
            if (!accionesBot.getVeh1JugConseguido())
            {
                if (nombreCasillaVehiculoBot.equals("---"))
                {
                    relJugVeh.setAlpha(0.5f);
                    relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
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
        else if (nombreCarta.equals("Tanque"))
        {
            if (!accionesBot.getVeh2JugConseguido())
            {
                if (nombreCasillaVehiculoBot.equals("---"))
                {
                    relJugVeh.setAlpha(0.5f);
                    relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
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
        else if (nombreCarta.equals("Submarino"))
        {
            if (!accionesBot.getVeh3JugConseguido())
            {
                if (nombreCasillaVehiculoBot.equals("---"))
                {
                    relJugVeh.setAlpha(0.5f);
                    relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
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
        else if (nombreCarta.equals("Caza"))
        {
            if (!accionesBot.getVeh4JugConseguido())
            {
                if (nombreCasillaVehiculoBot.equals("---"))
                {
                    relJugVeh.setAlpha(0.5f);
                    relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
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
        else if (nombreCarta.equals("Vehículo Multicolor"))
        {
            if (!accionesBot.getVehMulticolorJugConseguido())
            {
                if (nombreCasillaVehiculoBot.equals("---"))
                {
                    relJugVeh.setAlpha(0.5f);
                    relJugVeh.setBackgroundColor(Color.parseColor("#70FFFFFF"));
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
        else
        {
            return false;
        }
    }

    // Método que recoge las acciones que realiza el juego cuando la carta pulsada es un vehículo.
    public boolean reunirVehiculo(String nombreCarta, TextView tvJugVeh, TextView tvMensajeVictoria,
                                  AccionesBot accionesBot)
    {

        // En caso de que la carta pulsada sea un vehículo y esa carta no ha salido antes se suma uno
        // al valor que cuenta cuantas cartas de vehículo hay en el tablero.
        if ((nombreCarta.equals("Batallon")) && (tvJugVeh.getText().equals("---")))
        {
            if (!accionesBot.getVeh1JugConseguido())
            {
                System.out.println("CARTAOASIS -> " + accionesBot.getNumJugVehConseguidos());
                // Comprobamos si todos los vehículos han sido mostrados en el tablero.
                funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumJugVehConseguidos(), "JUGADOR", tvMensajeVictoria, context);

                tvJugVeh.setText("Batallon");
                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarReuneBatallon2));

                funcionesGenerales.colorCarta("Batallon", tvJugVeh);
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.batallon, sonidoJuego);

                return true;
            }
            else
            {
                Toast.makeText(context,
                        "Este vehículo ya lo tienes en el tablero",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        }
        else if ((nombreCarta.equals("Tanque")) && (tvJugVeh.getText().equals("---")))
        {
            if (!accionesBot.getVeh2JugConseguido())
            {
                // Comprobamos si todos los vehículos han sido mostrados en el tablero.
                funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumJugVehConseguidos(), "JUGADOR", tvMensajeVictoria, context);

                tvJugVeh.setText("Tanque");
                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarReuneTanque2));

                funcionesGenerales.colorCarta("Tanque", tvJugVeh);
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.tanque, sonidoJuego);

                return true;
            }
            else
            {
                Toast.makeText(context,
                        "Este vehículo ya lo tienes en el tablero",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        }
        else if ((nombreCarta.equals("Submarino")) && (tvJugVeh.getText().equals("---")))
        {
            if (!accionesBot.getVeh3JugConseguido())
            {
                // Comprobamos si todos los vehículos han sido mostrados en el tablero.
                funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumJugVehConseguidos(), "JUGADOR", tvMensajeVictoria, context);

                tvJugVeh.setText("Submarino");
                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarReuneSubmarino2));

                funcionesGenerales.colorCarta("Submarino", tvJugVeh);
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.submarino, sonidoJuego);

                return true;
            }
            else
            {
                Toast.makeText(context,
                        "Este vehículo ya lo tienes en el tablero",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        }
        else if ((nombreCarta.equals("Caza")) && (tvJugVeh.getText().equals("---")))
        {
            if (!accionesBot.getVeh4JugConseguido())
            {
                // Comprobamos si todos los vehículos han sido mostrados en el tablero.
                funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumJugVehConseguidos(), "JUGADOR", tvMensajeVictoria, context);

                tvJugVeh.setText("Caza");
                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarReuneCaza2));

                funcionesGenerales.colorCarta("Caza", tvJugVeh);
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.caza, sonidoJuego);

                return true;
            }
            else
            {
                Toast.makeText(context,
                        "Este vehículo ya lo tienes en el tablero",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        }
        else if ((nombreCarta.equals("Vehículo Multicolor")) && (tvJugVeh.getText().equals("---")))
        {
            if (!accionesBot.getVehMulticolorJugConseguido())
            {
                // Comprobamos si todos los vehículos han sido mostrados en el tablero.
                funcionesGenerales.todosVehiculosConseguidos(accionesBot.getNumJugVehConseguidos(), "JUGADOR", tvMensajeVictoria, context);

                tvJugVeh.setText("Vehículo Multicolor");
                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarReuneMulticolor2));

                funcionesGenerales.colorCarta("Vehículo Multicolor", tvJugVeh);
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.vehmulticolor, sonidoJuego);

                return true;
            }
            else
            {
                Toast.makeText(context,
                        "Este vehículo ya lo tienes en el tablero",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean lanzarExpulsionArmas(String nombreCarta, AccionesBot accionesBot, View view, ImageView ivDejarCartas1, ImageView ivDejarCartas2, ImageView ivDejarCartas3)
    {
        if (pulseExpulsarArmas(nombreCarta, ivDejarCartas1, ivDejarCartas2, ivDejarCartas3))
        {
            ArrayList<String> arrVehConVirus = new ArrayList<>();

            for (int i = 0; i < 4; i++)
            {
                if (!tvComodinesJugadores[i * 2].getText().equals("---"))
                {
                    ejecutarExpulsionArmas((String) tvComodinesJugadores[i * 2].getText(), accionesBot, view);
                    limpiarVirus(tvComodinesJugadores[i * 2], arrVehConVirus);
                }
            }

            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarBotiquinDorado2));
            funcionesGenerales.reproducirSonidoJuego(context, R.raw.expulsararmas, sonidoJuego);

            return true;
        }
        else
        {
            return false;
        }
    }

    // Método encargado de la acción de expulsar Arma de las cartas del Jugador.
    public void ejecutarExpulsionArmas(String armaJug, AccionesBot accionesBot, View view)
    {

        // En primer lugar recogemos en un array sólo las posiciones correspondientes a las casillas de las carta de los jugadores rivales.
        TextView tvVehiculosBot[] = new TextView[25];
        int posTvVehiculosBot = 0;

        System.out.println("Acquiensce -> *************************************************");
        for (int i = 4; i < tvVehiculosJugadores.length; i++)
        {
            tvVehiculosBot[posTvVehiculosBot] = tvVehiculosJugadores[i];
            System.out.println("Acquiensce -> " + tvVehiculosJugadores[i].getText());
            posTvVehiculosBot += 1;
        }

        // Hacemos lo mismo con los comodines de las casillas de las cartas de los jugadores rivales.
        TextView tvComodines1Bot[] = new TextView[25];
        int posTvComodinesBot = 0;

        for (int i = 8; i < tvComodinesJugadores.length; i++)
        {
            long z = i % 2;

            if (z == 0)
            {
                tvComodines1Bot[posTvComodinesBot] = tvComodinesJugadores[i];
                posTvComodinesBot += 1;
            }
        }

        // Recogemos en un nuevo array el orden de preferencia que tendrán los textview que recogerán las armas
        // expulsadas por el jugador.
        TextView nuevoTvVehiculosBot[] = funcionesGenerales.reordenarTvVehiculosRivales(tvVehiculosBot);

        TextView nuevoTvComodinesBot[] = funcionesGenerales.reordenarTvComodinesRivales(tvComodines1Bot);

        for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
        {
            System.out.println("Juguetes -> " + i + ": " + nuevoTvVehiculosBot[i].getText() + " - " + nuevoTvComodinesBot[i].getText());
        }

        ArrayList<TextView> tvVehBotPosibles = new ArrayList<>();

        // Comprobamos primero que tipo de arma ha expulsado el jugador
        if (armaJug.equals("Metralleta"))
        {
            // Recorremos el tablero del Bot en busca de vehículos que pueda recoger esa arma
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosBot[i], context, 7, 8);

                // Si hay un vehículo que puede recoger ese arma le añadimos a la lista de vehículos posibles
                if (nuevoTvVehiculosBot[i].getText().equals("Batallon") && (accionesBot.getContadorBotBatallon(jugBot) < 2))
                {
                    tvVehBotPosibles.add(nuevoTvVehiculosBot[i]);
                }
                else if (nuevoTvVehiculosBot[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2))
                {
                    tvVehBotPosibles.add(nuevoTvVehiculosBot[i]);
                }
            }

            // A continuación, seleccionamos aleatoriamente el vehículo que recogerá esa arma
            TextView tvBotElegido = new TextView(context);
            if (tvVehBotPosibles.size() > 0)
            {
                tvBotElegido = tvVehBotPosibles.get(generarNumAleatorio(tvVehBotPosibles.size()-1));
            }

            // Recorremos de nuevo el tablero del BOT en busca del vehículo seleccionado
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                System.out.println("Imbroda -> " + tvBotElegido.getText() + " - " + nuevoTvVehiculosBot[i].getText());
                if (tvBotElegido.getText().equals(nuevoTvVehiculosBot[i].getText()))
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosBot[i], context, 7, 8);

                    if (nuevoTvVehiculosBot[i].getText().equals("Batallon"))
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotBatallon(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                            expulsionArmas = true;
                        }
                        else if (accionesBot.getContadorBotBatallon(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Metralleta");
                        }
                        else if (accionesBot.getContadorBotBatallon(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }
                    else
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Metralleta");
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }

                    break;
                }
            }
        }
        else if (armaJug.equals("Lanzacohetes"))
        {
            // Recorremos el tablero del Bot en busca de vehículos que pueda recoger esa arma
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosBot[i], context, 7, 8);

                // Si hay un vehículo que puede recoger ese arma le añadimos a la lista de vehículos posibles
                if (nuevoTvVehiculosBot[i].getText().equals("Tanque") && (accionesBot.getContadorBotTanque(jugBot) < 2))
                {
                    tvVehBotPosibles.add(nuevoTvVehiculosBot[i]);
                }
                else if (nuevoTvVehiculosBot[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2))
                {
                    tvVehBotPosibles.add(nuevoTvVehiculosBot[i]);
                }
            }

            // A continuación, seleccionamos aleatoriamente el vehículo que recogerá esa arma
            TextView tvBotElegido = new TextView(context);
            if (tvVehBotPosibles.size() > 0)
            {
                tvBotElegido = tvVehBotPosibles.get(generarNumAleatorio(tvVehBotPosibles.size()-1));
            }

            // Recorremos de nuevo el tablero del BOT en busca del vehículo seleccionado
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                System.out.println("Imbroda -> " + tvBotElegido.getText() + " - " + nuevoTvVehiculosBot[i].getText());
                if (tvBotElegido.getText().equals(nuevoTvVehiculosBot[i].getText()))
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosBot[i], context, 7, 8);

                    if (nuevoTvVehiculosBot[i].getText().equals("Tanque"))
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotTanque(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotTanque(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Lanzacohetes");
                        }
                        else if (accionesBot.getContadorBotTanque(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }
                    else
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Lanzacohetes");
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }

                    break;
                }
            }
        }
        else if (armaJug.equals("Torpedos"))
        {
            // Recorremos el tablero del Bot en busca de vehículos que pueda recoger esa arma
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosBot[i], context, 7, 8);

                // Si hay un vehículo que puede recoger ese arma le añadimos a la lista de vehículos posibles
                if (nuevoTvVehiculosBot[i].getText().equals("Submarino") && (accionesBot.getContadorBotSubmarino(jugBot) < 2))
                {
                    tvVehBotPosibles.add(nuevoTvVehiculosBot[i]);
                }
                else if (nuevoTvVehiculosBot[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2))
                {
                    tvVehBotPosibles.add(nuevoTvVehiculosBot[i]);
                }
            }

            // A continuación, seleccionamos aleatoriamente el vehículo que recogerá esa arma
            TextView tvBotElegido = new TextView(context);
            if (tvVehBotPosibles.size() > 0)
            {
                tvBotElegido = tvVehBotPosibles.get(generarNumAleatorio(tvVehBotPosibles.size()-1));
            }

            // Recorremos de nuevo el tablero del BOT en busca del vehículo seleccionado
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                System.out.println("Imbroda -> " + tvBotElegido.getText() + " - " + nuevoTvVehiculosBot[i].getText());
                if (tvBotElegido.getText().equals(nuevoTvVehiculosBot[i].getText()))
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosBot[i], context, 7, 8);

                    if (nuevoTvVehiculosBot[i].getText().equals("Submarino"))
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotSubmarino(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotSubmarino(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Torpedos");
                        }
                        else if (accionesBot.getContadorBotSubmarino(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }
                    else
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Torpedos");
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }

                    break;
                }
            }
        }
        else if (armaJug.equals("Misiles"))
        {
            // Recorremos el tablero del Bot en busca de vehículos que pueda recoger esa arma
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosBot[i], context, 7, 8);

                // Si hay un vehículo que puede recoger ese arma le añadimos a la lista de vehículos posibles
                if (nuevoTvVehiculosBot[i].getText().equals("Caza") && (accionesBot.getContadorBotCaza(jugBot) < 2))
                {
                    tvVehBotPosibles.add(nuevoTvVehiculosBot[i]);
                }
                else if (nuevoTvVehiculosBot[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2))
                {
                    tvVehBotPosibles.add(nuevoTvVehiculosBot[i]);
                }
            }

            // A continuación, seleccionamos aleatoriamente el vehículo que recogerá esa arma
            TextView tvBotElegido = new TextView(context);
            if (tvVehBotPosibles.size() > 0)
            {
                tvBotElegido = tvVehBotPosibles.get(generarNumAleatorio(tvVehBotPosibles.size()-1));
            }

            // Recorremos de nuevo el tablero del BOT en busca del vehículo seleccionado
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                System.out.println("Imbroda -> " + tvBotElegido.getText() + " - " + nuevoTvVehiculosBot[i].getText());
                if (tvBotElegido.getText().equals(nuevoTvVehiculosBot[i].getText()))
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosBot[i], context, 7, 8);

                    if (nuevoTvVehiculosBot[i].getText().equals("Caza"))
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotCaza(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotCaza(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Misiles");
                        }
                        else if (accionesBot.getContadorBotCaza(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }
                    else
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Misiles");
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }

                    break;
                }
            }
        }
        else if (armaJug.equals("Arma Multicolor"))
        {
            // Recorremos el tablero del Bot en busca de vehículos que pueda recoger esa arma
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosBot[i], context, 7, 8);

                // Si hay un vehículo que puede recoger ese arma le añadimos a la lista de vehículos posibles
                if ((nuevoTvVehiculosBot[i].getText().equals("Batallon") && (accionesBot.getContadorBotBatallon(jugBot) < 2)) ||
                        (nuevoTvVehiculosBot[i].getText().equals("Tanque") && (accionesBot.getContadorBotTanque(jugBot) < 2)) ||
                        (nuevoTvVehiculosBot[i].getText().equals("Submarino") && (accionesBot.getContadorBotSubmarino(jugBot) < 2)) ||
                        (nuevoTvVehiculosBot[i].getText().equals("Caza") && (accionesBot.getContadorBotCaza(jugBot) < 2)) ||
                        (nuevoTvVehiculosBot[i].getText().equals("Vehículo Multicolor") && (accionesBot.getContadorBotVehMulticolor(jugBot) < 2)))
                {
                    tvVehBotPosibles.add(nuevoTvVehiculosBot[i]);
                }
            }

            // A continuación, seleccionamos aleatoriamente el vehículo que recogerá esa arma

            TextView tvBotElegido = null;
            if (tvVehBotPosibles.size() > 0)
            {
                tvBotElegido = tvVehBotPosibles.get(generarNumAleatorio(tvVehBotPosibles.size()-1));
            }

            // Recorremos de nuevo el tablero del BOT en busca del vehículo seleccionado
            for (int i = 0; i < (numeroJugadores - 1) * 4; i++)
            {
                System.out.println("Imbroda -> " + tvBotElegido.getText() + " - " + nuevoTvVehiculosBot[i].getText());
                if (tvBotElegido.getText().equals(nuevoTvVehiculosBot[i].getText()))
                {
                    int jugBot = funcionesGenerales.saberNombreJugPorSuId2(nuevoTvVehiculosBot[i], context, 7, 8);

                    if (nuevoTvVehiculosBot[i].getText().equals("Batallon"))
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotBatallon(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotBatallon(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Arma Multicolor");
                        }
                        else if (accionesBot.getContadorBotBatallon(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }
                    else if (nuevoTvVehiculosBot[i].getText().equals("Tanque"))
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotTanque(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotTanque(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Arma Multicolor");
                        }
                        else if (accionesBot.getContadorBotTanque(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }
                    else if (nuevoTvVehiculosBot[i].getText().equals("Submarino"))
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotSubmarino(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotSubmarino(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Arma Multicolor");
                        }
                        else if (accionesBot.getContadorBotSubmarino(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }
                    else if (nuevoTvVehiculosBot[i].getText().equals("Caza"))
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotCaza(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotCaza(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Arma Multicolor");
                        }
                        else if (accionesBot.getContadorBotCaza(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }
                    else
                    {
                        // Aplicamos la acción del arma en función de la situación en la que se encuentre el vehículo del BOT
                        if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                        {
                            nuevoTvComodinesBot[i].setText("---");
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                        {
                            nuevoTvComodinesBot[i].setText("Arma Multicolor");
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                        {
                            nuevoTvVehiculosBot[i].setText("---");
                            nuevoTvComodinesBot[i].setText("---");
                        }
                    }

                    break;
                }
            }
        }
    }

    public int generarNumAleatorio(int limite)
    {
        return (int) Math.floor(Math.random() * (0 - limite + 1) + limite);
    }

    public void limpiarVirus(TextView tvJugCom1, ArrayList arrComVehJug)
    {
        if (tvJugCom1.getText().equals("Metralleta") || tvJugCom1.getText().equals("Lanzacohetes") ||
                tvJugCom1.getText().equals("Torpedos") || tvJugCom1.getText().equals("Misiles") ||
                tvJugCom1.getText().equals("Arma Multicolor"))
        {
            arrComVehJug.add(tvJugCom1.getText());
            tvJugCom1.setText("---");
        }
    }

    public boolean lanzarDejarCartas(String nombreCarta, TableRow tvRowBotVeh, TableRow tvRowJugVeh, String[] barajaCartas,
                                     TextView[] tvCartasJugadores, int numJugadores)
    {
        if (pulseDejarCartas(nombreCarta))
        {
            for (int i = 0; i < numJugadores; i++)
            {
                if (i == 0)
                {
                    funcionesGenerales.echarTresCartas(barajaCartas, tvCartasJugadores[0], tvCartasJugadores[1],
                            tvCartasJugadores[2]);
                }
                else
                {
                    funcionesGenerales.echarTresCartas(barajaCartas, tvCartasJugadores[i * 3 + 0],
                            tvCartasJugadores[i * 3 + 1], tvCartasJugadores[i * 3 + 2]);
                }
            }
            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarSacaTresCartasNuevas));
            funcionesGenerales.reproducirSonidoJuego(context, R.raw.nuevascartas, sonidoJuego);

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean lanzarIntercambioEquipo(String nombreCarta, TableRow tbRowBotVeh, TableRow tbRowJugVeh,
                                           TextView tvVehiculosJugadores[], TextView tvVehiculosComodines[],
                                           View view)
    {
        String strTvVehiculosJug[] = new String[4];
        String strTvComodinesJug[] = new String[8];
        String strTvVehiculosBot[] = new String[4];
        String strTvComodinesBot[] = new String[8];

        if (pulseIntercambiarEquipo(nombreCarta, tbRowBotVeh, relVehiculosJugadores, view))
        {
            tvEqJugAIntercambiar = true;
            int numJugPorRow = funcionesGenerales.saberNombreJugPorSuRow(tbRowBotVeh, view, 5, 6);

            for (int i = 0; i < strTvVehiculosJug.length; i++)
            {
                strTvVehiculosJug[i] = (String) tvVehiculosJugadores[i].getText();
                strTvVehiculosBot[i] = (String) tvVehiculosJugadores[numJugPorRow * 4 + i].getText();
            }

            for (int i = 0; i < strTvVehiculosJug.length; i++)
            {
                tvVehiculosJugadores[i].setText(strTvVehiculosBot[i]);
                tvVehiculosJugadores[numJugPorRow * 4 + i].setText(strTvVehiculosJug[i]);
            }

            for (int i = 0; i < strTvComodinesJug.length; i++)
            {
                strTvComodinesJug[i] = (String) tvVehiculosComodines[i].getText();
                strTvComodinesBot[i] = (String) tvVehiculosComodines[numJugPorRow * 8 + i].getText();
            }

            for (int i = 0; i < strTvComodinesJug.length; i++)
            {
                tvVehiculosComodines[i].setText(strTvComodinesBot[i]);
                tvVehiculosComodines[numJugPorRow * 8 + i].setText(strTvComodinesJug[i]);
            }

            if (numJugPorRow - 1 < 0)
            {
                Toast.makeText(context,
                        "Lanza la carta sobre el ejercito enemigo que quieres intercambiar",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarIntercambioEjercitoJugador2)
                        + ordenNombresEjercitos.get(numJugPorRow - 1));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.intercambio, sonidoJuego);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean lanzarIntercambioVehiculo(String nombreCarta, String nombreCasillaJugVehiculo, RelativeLayout relJugVeh,
                                             TextView tvJugComodin1, TextView tvJugComodin2, String[] barajaCartas,
                                             TextView tvJugCartaPulsada, AccionesBot accionesBot, TextView tvVehsJug[],
                                             RelativeLayout relVehsJug[], ImageView ivDejarCartas1, ImageView ivDejarCartas2, ImageView ivDejarCartas3)
    {
        tvVehJugAIntercambiar = nombreCasillaJugVehiculo;
        tvCom1JugAIntercambiar = (String) tvJugComodin1.getText();
        tvCom2JugAIntercambiar = (String) tvJugComodin2.getText();

        int contPosiblesIntercambios = 0;

        for (int i = 1; i < numeroJugadores; i++)
        {
            if(pulseIntercambiarVehiculo(nombreCarta, (String) tvVehsJug[i * 4 + 0].getText(),
                    tvVehJugAIntercambiar, relJugVeh, relVehsJug[i * 4 + 0], context, accionesBot, i, i,
                    ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, true))
            {
                tvVehJugsPulsableIntercambio[i * 4 + 0] = true;

                //ivVehsJug[i * 4 + 0].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                contPosiblesIntercambios += 1;
            }

            if(pulseIntercambiarVehiculo(nombreCarta, (String) tvVehsJug[i * 4 + 1].getText(),
                    tvVehJugAIntercambiar, relJugVeh, relVehsJug[i * 4 + 1], context, accionesBot, i, i,
                    ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, true))
            {
                tvVehJugsPulsableIntercambio[i * 4 + 1] = true;
                //ivVehsJug[i * 4 + 1].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                contPosiblesIntercambios += 1;
            }

            if(pulseIntercambiarVehiculo(nombreCarta, (String) tvVehsJug[i * 4 + 2].getText(),
                    tvVehJugAIntercambiar, relJugVeh, relVehsJug[i * 4 + 2], context, accionesBot, i, i,
                    ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, true))
            {
                tvVehJugsPulsableIntercambio[i * 4 + 2] = true;
                //ivVehsJug[i * 4 + 2].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                contPosiblesIntercambios += 1;
            }

            if(pulseIntercambiarVehiculo(nombreCarta, (String) tvVehsJug[i * 4 + 3].getText(),
                    tvVehJugAIntercambiar, relJugVeh, relVehsJug[i * 4 + 3], context, accionesBot, i, i,
                    ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, true))
            {
                tvVehJugsPulsableIntercambio[i * 4 + 3] = true;
                //ivVehsJug[i * 4 + 3].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.pulsado, sonidoJuego);
                contPosiblesIntercambios += 1;
            }
        }

        if (contPosiblesIntercambios > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean lanzarRoboVehiculo(String nombreCarta, String nombreCasillaVehiculoBot, TextView tvBotVeh,
                                      TextView tvBotComodin1, TextView tvBotComodin2, RelativeLayout relBotVeh,
                                      AccionesBot accionesBot, int jugBot, View view)
    {
        if (pulseRoboVehiculo(nombreCarta, nombreCasillaVehiculoBot, relBotVeh, accionesBot, jugBot))
        {
            if (tvVehiculosJugadores[0].getText().equals("---"))
            {
                tvVehiculosJugadores[0].setText(nombreCasillaVehiculoBot);
                tvComodinesJugadores[0].setText(tvBotComodin1.getText());
                tvComodinesJugadores[1].setText(tvBotComodin2.getText());
            }
            else if (tvVehiculosJugadores[1].getText().equals("---"))
            {
                tvVehiculosJugadores[1].setText(nombreCasillaVehiculoBot);
                tvComodinesJugadores[2].setText(tvBotComodin1.getText());
                tvComodinesJugadores[3].setText(tvBotComodin2.getText());
            }
            else if (tvVehiculosJugadores[2].getText().equals("---"))
            {
                tvVehiculosJugadores[2].setText(nombreCasillaVehiculoBot);
                tvComodinesJugadores[4].setText(tvBotComodin1.getText());
                tvComodinesJugadores[5].setText(tvBotComodin2.getText());
            }
            else if (tvVehiculosJugadores[3].getText().equals("---"))
            {
                tvVehiculosJugadores[3].setText(nombreCasillaVehiculoBot);
                tvComodinesJugadores[6].setText(tvBotComodin1.getText());
                tvComodinesJugadores[7].setText(tvBotComodin2.getText());
            }

            tvBotVeh.setText("---");
            tvBotComodin1.setText("---");
            tvBotComodin2.setText("---");

            String idTbRowBotVeh = relBotVeh.getResources().getResourceEntryName(view.getId());
            int numBot = Integer.parseInt(idTbRowBotVeh.substring(5, 6));
            System.out.println("Pila -> " + numBot);

            if (numBot < 1)
            {
                Toast.makeText(context,
                        "No puedes robarte a ti mismo",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarRobaUnidad2)
                        + nombreCasillaVehiculoBot + context.getResources().getString(R.string.tvToolbarRobaUnidad3)
                        + ordenNombresEjercitos.get(numBot - 1));
                funcionesGenerales.reproducirSonidoJuego(context, R.raw.intercambio, sonidoJuego);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean lanzarJugAntiarma(String nombreCartaMed, String nombreCartaVeh, TextView tvJugVeh, TextView tvCom1Jug,
                                     AccionesBot accionesBot)
    {
        if (pulseLanzarMedicina(nombreCartaMed))
        {
            System.out.println("Pulso1 -> " + nombreCartaVeh + nombreCartaMed + " - " + accionesBot.getContadorJugBatallon() + " - " + accionesBot.getContadorJugTanque() + " - " + accionesBot.getContadorJugSubmarino() + " - " + accionesBot.getContadorJugCaza() + " - " + accionesBot.getContadorJugVehMulticolor());
            // Comprobamos si la carta sobre la que vamos a echar la medicina son del mismo tipo (Medicina1 con Vehiculo1)
            if (nombreCartaVeh.equals("Batallon")
                    && (tvCom1Jug.getText().equals("---") || tvCom1Jug.getText().equals("Metralleta")
                    || tvCom1Jug.getText().equals("Arma Multicolor") || tvCom1Jug.getText().equals("Medicina1")
                    || tvCom1Jug.getText().equals("Medicina Multicolor")))
            {
                // Comprobamos que la carta lanzada sea de tipo Medicina.
                if ((nombreCartaMed.equals("Medicina1"))||(nombreCartaMed.equals("Medicina Multicolor")))
                {
                    if (accionesBot.getContadorJugBatallon() < 2)
                    {
                        if (accionesBot.getContadorJugBatallon() == 0)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "1")).setText(nombreCartaMed);

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarRefuerzaBatallon2));
                        }
                        else if (accionesBot.getContadorJugBatallon() == 1)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "2")).setText(nombreCartaMed);

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarInmunizaBatallon2));
                        }
                        else if (accionesBot.getContadorJugBatallon() == -1)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "1")).setText("---");

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarReparaBatallon));
                        }

                        return true;
                    }
                    else
                    {
                        Toast.makeText(context,
                                "Este vehículo ya está acorazado",
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                else
                {
                    Toast.makeText(context,
                            "No puedes lanzar este botiquín sobre ese vehículo",
                            Toast.LENGTH_SHORT).show();

                    return false;
                }
            }
            else if (nombreCartaVeh.equals("Tanque")
                    && (tvCom1Jug.getText().equals("---") || tvCom1Jug.getText().equals("Lanzacohetes")
                    || tvCom1Jug.getText().equals("Arma Multicolor") || tvCom1Jug.getText().equals("Medicina2")
                    || tvCom1Jug.getText().equals("Medicina Multicolor")))
            {
                if ((nombreCartaMed.equals("Medicina2"))||(nombreCartaMed.equals("Medicina Multicolor")))
                {
                    if (accionesBot.getContadorJugTanque() < 2)
                    {
                        if (accionesBot.getContadorJugTanque() == 0)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "1")).setText(nombreCartaMed);

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarRefuerzaTanque2));
                        }
                        else if (accionesBot.getContadorJugTanque() == 1)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "2")).setText(nombreCartaMed);

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarInmunizaTanque2));
                        }
                        else if (accionesBot.getContadorJugTanque() == -1)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "1")).setText("---");

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarReparaTanque));
                        }

                        return true;
                    }
                    else
                    {
                        Toast.makeText(context,
                                "Este vehículo ya está acorazado",
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                else
                {
                    Toast.makeText(context,
                            "No puedes lanzar este botiquín sobre ese vehículo",
                            Toast.LENGTH_SHORT).show();

                    return false;
                }
            }
            else if (nombreCartaVeh.equals("Submarino")
                    && (tvCom1Jug.getText().equals("---") || tvCom1Jug.getText().equals("Torpedos")
                    || tvCom1Jug.getText().equals("Arma Multicolor") || tvCom1Jug.getText().equals("Medicina3")
                    || tvCom1Jug.getText().equals("Medicina Multicolor")))
            {
                if ((nombreCartaMed.equals("Medicina3"))||(nombreCartaMed.equals("Medicina Multicolor")))
                {
                    if (accionesBot.getContadorJugSubmarino() < 2)
                    {
                        if (accionesBot.getContadorJugSubmarino() == 0)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "1")).setText(nombreCartaMed);

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarRefuerzaSubmarino2));
                        }
                        else if (accionesBot.getContadorJugSubmarino() == 1)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "2")).setText(nombreCartaMed);

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarInmunizaSubmarino2));
                        }
                        else if (accionesBot.getContadorJugSubmarino() == -1)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "1")).setText("---");

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarReparaSubmarino));
                        }

                        return true;
                    }
                    else
                    {
                        Toast.makeText(context,
                                "Este vehículo ya está acorazado",
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
            else if (nombreCartaVeh.equals("Caza")
                    && (tvCom1Jug.getText().equals("---") || tvCom1Jug.getText().equals("Misiles")
                    || tvCom1Jug.getText().equals("Arma Multicolor") || tvCom1Jug.getText().equals("Medicina4")
                    || tvCom1Jug.getText().equals("Medicina Multicolor")))
            {
                if ((nombreCartaMed.equals("Medicina4"))||(nombreCartaMed.equals("Medicina Multicolor")))
                {
                    if (accionesBot.getContadorJugCaza() < 2)
                    {
                        if (accionesBot.getContadorJugCaza() == 0)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "1")).setText(nombreCartaMed);

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarRefuerzaCaza2));
                        }
                        else if (accionesBot.getContadorJugCaza() == 1)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "2")).setText(nombreCartaMed);

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarInmunizaCaza2));
                        }
                        else if (accionesBot.getContadorJugCaza() == -1)
                        {
                            funcionesGenerales.asociarIdComodinConTvComodin(
                                    funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "1")).setText("---");

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarReparaCaza));
                        }

                        return true;
                    }
                    else
                    {
                        Toast.makeText(context,
                                "Este vehículo ya está acorazado",
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                else
                {
                    Toast.makeText(context,
                            "No puedes lanzar este botiquín sobre ese vehículo",
                            Toast.LENGTH_SHORT).show();

                    return false;
                }
            }
            else if (nombreCartaVeh.equals("Vehículo Multicolor"))
            {
                if (accionesBot.getContadorJugVehMulticolor() < 2)
                {
                    if (accionesBot.getContadorJugVehMulticolor() == 0)
                    {
                        funcionesGenerales.asociarIdComodinConTvComodin(
                                funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "1")).setText(nombreCartaMed);

                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                        tvToolbar.setText(context.getResources().getString(R.string.tvToolbarRefuerzaMulticolor2));
                    }
                    else if (accionesBot.getContadorJugVehMulticolor() == 1)
                    {
                        funcionesGenerales.asociarIdComodinConTvComodin(
                                funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "2")).setText(nombreCartaMed);

                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.inmunizado, sonidoJuego);
                        tvToolbar.setText(context.getResources().getString(R.string.tvToolbarInmunizaMulticolor2));
                    }
                    else if (accionesBot.getContadorJugVehMulticolor() == -1)
                    {
                        funcionesGenerales.asociarIdComodinConTvComodin(
                                funcionesGenerales.sacarIdTvMedicina(tvJugVeh.getId(), tvJugVeh, "1")).setText("---");

                        funcionesGenerales.reproducirSonidoJuego(context, R.raw.medicado, sonidoJuego);
                        tvToolbar.setText(context.getResources().getString(R.string.tvToolbarReparaMulticolor));
                    }

                    return true;
                }
                else
                {
                    Toast.makeText(context,
                            "Este vehículo ya está acorazado",
                            Toast.LENGTH_SHORT).show();

                    return false;
                }
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

    public boolean lanzarJugArma(String nombreCartaVir, String nombreCartaVeh, TextView tvBotVeh, TextView tvBotCom1,
                                 AccionesBot accionesBot, int jugBot)
    {
        if (pulseLanzarArma(nombreCartaVir))
        {
            int jugBot2 = jugBot + 1;

            System.out.println("Kylian -> " + jugBot + " - " + nombreCartaVeh);

            if (jugBot > -1)
            {
                // Comprobamos si la carta sobre la que vamos a echar el virus son del mismo tipo (Virus1 con Vehiculo1)
                if (nombreCartaVeh.equals("Batallon"))
                {
                    // Comprobamos que la carta lanzada sea de tipo Medicina.
                    if ((nombreCartaVir.equals("Metralleta") || nombreCartaVir.equals("Arma Multicolor"))
                            && (tvBotCom1.getText().equals("---") || tvBotCom1.getText().equals("Metralleta")
                            || tvBotCom1.getText().equals("Arma Multicolor") || tvBotCom1.getText().equals("Medicina1")
                            || tvBotCom1.getText().equals("Medicina Multicolor")))
                    {
                        if (accionesBot.getContadorBotBatallon(jugBot) > -2)
                        {
                            if (accionesBot.getContadorBotBatallon(jugBot) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "1"), jugBot2).setText(nombreCartaVir);

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarAtacaBatallonJugador2) + ordenNombresEjercitos.get(jugBot));
                            }
                            else if (accionesBot.getContadorBotBatallon(jugBot) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "2"), jugBot2).setText(nombreCartaVir);

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarDerribaBatallonJugador2) + ordenNombresEjercitos.get(jugBot));
                            }
                            else if (accionesBot.getContadorBotBatallon(jugBot) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "1"), jugBot2).setText("---");

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarAtacaBatallonJugador2) + ordenNombresEjercitos.get(jugBot));
                            }
                            else if (accionesBot.getContadorBotBatallon(jugBot) == 2)
                            {
                                Toast.makeText(context, "Este vehículo está acorazado",
                                        Toast.LENGTH_LONG).show();

                                return false;
                            }

                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else
                    {
                        Toast.makeText(context,
                                "No puedes lanzar este arma sobre ese vehículo",
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                else if (nombreCartaVeh.equals("Tanque"))
                {
                    if ((nombreCartaVir.equals("Lanzacohetes") || nombreCartaVir.equals("Arma Multicolor"))
                            && (tvBotCom1.getText().equals("---") || tvBotCom1.getText().equals("Lanzacohetes")
                            || tvBotCom1.getText().equals("Arma Multicolor") || tvBotCom1.getText().equals("Medicina2")
                            || tvBotCom1.getText().equals("Medicina Multicolor")))
                    {
                        if (accionesBot.getContadorBotTanque(jugBot) > -2)
                        {
                            System.out.println("Planeador -> " + tvBotVeh.getId() + " " + nombreCartaVir);

                            if (accionesBot.getContadorBotTanque(jugBot) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "1"), jugBot2).setText(nombreCartaVir);

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarAtacaTanqueJugador2) + ordenNombresEjercitos.get(jugBot));
                            }
                            else if (accionesBot.getContadorBotTanque(jugBot) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "2"), jugBot2).setText(nombreCartaVir);

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarDerribaTanqueJugador2) + ordenNombresEjercitos.get(jugBot));
                            }
                            else if (accionesBot.getContadorBotTanque(jugBot) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "1"), jugBot2).setText("---");

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarAtacaTanqueJugador2) + ordenNombresEjercitos.get(jugBot));
                            }
                            else if (accionesBot.getContadorBotTanque(jugBot) == 2)
                            {
                                Toast.makeText(context, "Este vehículo está acorazado",
                                        Toast.LENGTH_LONG).show();

                                return false;
                            }

                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else
                    {
                        Toast.makeText(context,
                                "No puedes lanzar este arma sobre ese vehículo",
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                else if (nombreCartaVeh.equals("Submarino"))
                {
                    if ((nombreCartaVir.equals("Torpedos") || nombreCartaVir.equals("Arma Multicolor"))
                            && (tvBotCom1.getText().equals("---") || tvBotCom1.getText().equals("Torpedos")
                            || tvBotCom1.getText().equals("Arma Multicolor") || tvBotCom1.getText().equals("Medicina3")
                            || tvBotCom1.getText().equals("Medicina Multicolor")))
                    {
                        if (accionesBot.getContadorBotSubmarino(jugBot) > -2)
                        {
                            if (accionesBot.getContadorBotSubmarino(jugBot) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "1"), jugBot2).setText(nombreCartaVir);

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarAtacaSubmarinoJugador2) + ordenNombresEjercitos.get(jugBot));
                            }
                            else if (accionesBot.getContadorBotSubmarino(jugBot) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "2"), jugBot2).setText(nombreCartaVir);

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarDerribaSubmarinoJugador2) + ordenNombresEjercitos.get(jugBot));
                            }
                            else if (accionesBot.getContadorBotSubmarino(jugBot) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "1"), jugBot2).setText("---");

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarAtacaSubmarinoJugador2) + ordenNombresEjercitos.get(jugBot));
                            }
                            else if (accionesBot.getContadorBotSubmarino(jugBot) == 2)
                            {
                                Toast.makeText(context, "Este vehículo está acorazado",
                                        Toast.LENGTH_LONG).show();

                                return false;
                            }

                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else
                    {
                        Toast.makeText(context,
                                "No puedes lanzar este arma sobre ese vehículo",
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                else if (nombreCartaVeh.equals("Caza"))
                {
                    if ((nombreCartaVir.equals("Misiles") || nombreCartaVir.equals("Arma Multicolor"))
                            && (tvBotCom1.getText().equals("---") || tvBotCom1.getText().equals("Misiles")
                            || tvBotCom1.getText().equals("Arma Multicolor") || tvBotCom1.getText().equals("Medicina4")
                            || tvBotCom1.getText().equals("Medicina Multicolor")))
                    {
                        if (accionesBot.getContadorBotCaza(jugBot) > -2)
                        {
                            if (accionesBot.getContadorBotCaza(jugBot) == 0)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "1"), jugBot2).setText(nombreCartaVir);

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarAtacaCazaJugador2));
                            }
                            else if (accionesBot.getContadorBotCaza(jugBot) == -1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "2"), jugBot2).setText(nombreCartaVir);

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarDerribaCazaJugador2) + ordenNombresEjercitos.get(jugBot));
                            }
                            else if (accionesBot.getContadorBotCaza(jugBot) == 1)
                            {
                                funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                        funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "1"), jugBot2).setText("---");

                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                                tvToolbar.setText(context.getResources().getString(R.string.tvToolbarAtacaCazaJugador2));
                            }
                            else if (accionesBot.getContadorBotCaza(jugBot) == 2)
                            {
                                Toast.makeText(context, "Este vehículo está acorazado",
                                        Toast.LENGTH_LONG).show();

                                return false;
                            }

                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else
                    {
                        Toast.makeText(context,
                                "No puedes lanzar este arma sobre ese vehículo",
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                else if (nombreCartaVeh.equals("Vehículo Multicolor"))
                {
                    if (accionesBot.getContadorBotVehMulticolor(jugBot) > -2)
                    {
                        if (accionesBot.getContadorBotVehMulticolor(jugBot) == 0)
                        {
                            funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                    funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "1"), jugBot2).setText(nombreCartaVir);

                            if (nombreCartaVir.equals("Metralleta"))
                            {
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                            }
                            else if (nombreCartaVir.equals("Lanzacohetes"))
                            {
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                            }
                            else if (nombreCartaVir.equals("Torpedos"))
                            {
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                            }
                            else if (nombreCartaVir.equals("Misiles"))
                            {
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                            }
                            else
                            {
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                            }
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador2));
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == -1)
                        {
                            funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                    funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "2"), jugBot2).setText(nombreCartaVir);

                            funcionesGenerales.reproducirSonidoJuego(context, R.raw.derribado, sonidoJuego);
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarDerribaMulticolorJugador2) + ordenNombresEjercitos.get(jugBot));
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 1)
                        {
                            funcionesGenerales.asociarIdMedicinaConTvMedicinaBot(
                                    funcionesGenerales.sacarIdTvMedicina(tvBotVeh.getId(), tvBotVeh, "1"), jugBot2).setText("---");

                            if (nombreCartaVir.equals("Metralleta"))
                            {
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.metralleta, sonidoJuego);
                            }
                            else if (nombreCartaVir.equals("Lanzacohetes"))
                            {
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.lanzacohetes, sonidoJuego);
                            }
                            else if (nombreCartaVir.equals("Torpedos"))
                            {
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.torpedos, sonidoJuego);
                            }
                            else if (nombreCartaVir.equals("Misiles"))
                            {
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.misiles, sonidoJuego);
                            }
                            else
                            {
                                funcionesGenerales.reproducirSonidoJuego(context, R.raw.armamulticolor, sonidoJuego);
                            }
                            tvToolbar.setText(context.getResources().getString(R.string.tvToolbarAtacaMulticolorJugador2) + ordenNombresEjercitos.get(jugBot));
                        }
                        else if (accionesBot.getContadorBotVehMulticolor(jugBot) == 2)
                        {
                            Toast.makeText(context, "Este vehículo está acorazado",
                                    Toast.LENGTH_LONG).show();

                            return false;
                        }

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
            else
            {
                Toast.makeText(context,
                        "No puedes lanzarte una arma a ti mismo",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean comprobarTipoMedicinaVehMulticolor(String tipoMedVehMulticolor, String medicina)
    {
        System.out.println("Dorian -> tipoMedVehMulticolor: " + tipoMedVehMulticolor);

        if (tipoMedVehMulticolor.equals(medicina))
        {
            return true;
        }
        else if (tipoMedVehMulticolor.equals("---"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // Método para sacar una carta de la baraja de cartas restantes.
    public int sacarCarta()
    {
        int valorCarta;

        do {
            valorCarta = (int) Math.floor(Math.random() * (0 - 68 + 1) + 68);
            numEsRepetido(valorCarta, valorBarajaCartas);
        }
        while (numRepetido == true);

        valorBarajaCartas.add(valorCarta);

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            tbRowVehiculos.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.fondo_juego) );
        } else {
            tbRowVehiculos.setBackground(ContextCompat.getDrawable(context, R.drawable.fondo_juego));
        }

        return valorCarta;
    }

    // Comprobamos si la carta sacada de la baraja no se ha sacado anteriormente.
    public void numEsRepetido(int numSacado, ArrayList<Integer> baraja)
    {
        numRepetido = false;

        for (int i = 0; i < baraja.size(); i++)
        {
            if (numSacado == baraja.get(i))
            {
                numRepetido = true;
                break;
            }
            else
            {
                numRepetido = false;
            }
        }
    }
}
