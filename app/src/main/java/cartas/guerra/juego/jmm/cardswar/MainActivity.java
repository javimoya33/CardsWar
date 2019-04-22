package cartas.guerra.juego.jmm.cardswar;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cartas.guerra.juego.jmm.cardswar.BaseDeDatos.AccionesBot;
import cartas.guerra.juego.jmm.cardswar.FuncionesJugador.FuncionesBOT;
import cartas.guerra.juego.jmm.cardswar.FuncionesJugador.FuncionesGenerales;
import cartas.guerra.juego.jmm.cardswar.FuncionesJugador.FuncionesJugador;
import pl.droidsonroids.gif.GifImageView;

// Clase principal de la Actividad del juego.
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Layout principal de la vista
    public LinearLayout linearJuego;

    // Elementos contenidos en el Toolbar de la vista.
    public TextView tvToolbar;
    public Spinner spinnerOpciones;
    public ScrollView scrollContenidoInstrucciones;
    public Boolean logoInstruccionesPulsado = true;

    // Botones que representan las tres cartas a poder tirar.
    public TableLayout tableJugCartas;
    public RelativeLayout relJugCarta1;
    public RelativeLayout relJugCarta2;
    public RelativeLayout relJugCarta3;
    public TextView tvJugCarta1;
    public TextView tvJugCarta2;
    public TextView tvJugCarta3;
    public ImageView ivJugCarta1;
    public ImageView ivJugCarta2;
    public ImageView ivJugCarta3;
    public TableRow tbRowCartas;

    // Texto que representa las cuatro casillas de vehículos del jugador a sacar.
    public TableLayout tableJugVehiculos;
    public TableRow tbRowJugVeh;
    public RelativeLayout relJugVeh1;
    public RelativeLayout relJugVeh2;
    public RelativeLayout relJugVeh3;
    public RelativeLayout relJugVeh4;
    public TextView tvJugVeh1;
    public TextView tvJugVeh2;
    public TextView tvJugVeh3;
    public TextView tvJugVeh4;
    public ImageView ivJugVeh1;
    public ImageView ivJugVeh2;
    public ImageView ivJugVeh3;
    public ImageView ivJugVeh4;

    // Imagenes que representan la acción de descarta carta.
    public ImageView ivDejarCartas1;
    public ImageView ivDejarCartas2;
    public ImageView ivDejarCartas3;

    // Texto que controla internamente el estado de los vehículos del jugador.
    public TableRow tbRowJugCom1;
    public TableRow tbRowJugCom2;
    public TextView tvJugComodin11;
    public TextView tvJugComodin12;
    public TextView tvJugComodin21;
    public TextView tvJugComodin22;
    public TextView tvJugComodin31;
    public TextView tvJugComodin32;
    public TextView tvJugComodin41;
    public TextView tvJugComodin42;

    // Layout que contiene el espacio intermedio entre el tablero del Jugador y los tableros del BOT
    public RelativeLayout relEspacio;

    // Texto que se representa en las tres cartas a tirar.
    public TextView tvBotCarta1; public TextView tvBot2Carta1; public TextView tvBot3Carta1; public TextView tvBot4Carta1; public TextView tvBot5Carta1;
    public TextView tvBotCarta2; public TextView tvBot2Carta2; public TextView tvBot3Carta2; public TextView tvBot4Carta2; public TextView tvBot5Carta2;
    public TextView tvBotCarta3; public TextView tvBot2Carta3; public TextView tvBot3Carta3; public TextView tvBot4Carta3; public TextView tvBot5Carta3;

    // Texto que representa las cuatro casillas de vehículos del bot a sacar.
    public TableLayout tableBotVehiculos;
    public TableRow tbRowBotVeh;
    public TableRow tbRow2BotVeh;
    public TableRow tbRow3BotVeh;
    public TableRow tbRow4BotVeh;
    public TableRow tbRow5BotVeh;
    public TableRow[] tbRowVehs;

    // Contenedores, textos e imágenes que representan los vehículos del BOT
    public RelativeLayout relBot1Veh1;  public RelativeLayout relBot2Veh1;  public RelativeLayout relBot3Veh1; public RelativeLayout relBot4Veh1;  public RelativeLayout relBot5Veh1;
    public RelativeLayout relBot1Veh2;  public RelativeLayout relBot2Veh2;  public RelativeLayout relBot3Veh2; public RelativeLayout relBot4Veh2;  public RelativeLayout relBot5Veh2;
    public RelativeLayout relBot1Veh3;  public RelativeLayout relBot2Veh3;  public RelativeLayout relBot3Veh3; public RelativeLayout relBot4Veh3;  public RelativeLayout relBot5Veh3;
    public RelativeLayout relBot1Veh4;  public RelativeLayout relBot2Veh4;  public RelativeLayout relBot3Veh4; public RelativeLayout relBot4Veh4;  public RelativeLayout relBot5Veh4;
    public TextView tvBotVeh1; public TextView tvBot2Veh1; public TextView tvBot3Veh1; public TextView tvBot4Veh1; public TextView tvBot5Veh1;
    public TextView tvBotVeh2; public TextView tvBot2Veh2; public TextView tvBot3Veh2; public TextView tvBot4Veh2; public TextView tvBot5Veh2;
    public TextView tvBotVeh3; public TextView tvBot2Veh3; public TextView tvBot3Veh3; public TextView tvBot4Veh3; public TextView tvBot5Veh3;
    public TextView tvBotVeh4; public TextView tvBot2Veh4; public TextView tvBot3Veh4; public TextView tvBot4Veh4; public TextView tvBot5Veh4;
    public ImageView ivBotVeh1; public ImageView ivBot2Veh1; public ImageView ivBot3Veh1; public ImageView ivBot4Veh1; public ImageView ivBot5Veh1;
    public ImageView ivBotVeh2; public ImageView ivBot2Veh2; public ImageView ivBot3Veh2; public ImageView ivBot4Veh2; public ImageView ivBot5Veh2;
    public ImageView ivBotVeh3; public ImageView ivBot2Veh3; public ImageView ivBot3Veh3; public ImageView ivBot4Veh3; public ImageView ivBot5Veh3;
    public ImageView ivBotVeh4; public ImageView ivBot2Veh4; public ImageView ivBot3Veh4; public ImageView ivBot4Veh4; public ImageView ivBot5Veh4;

    // Texto que controla internamente el estado de los vehículos del BOT.
    public TableRow tbRowBotCom1; public TableRow tbRowBot2Com1; public TableRow tbRowBot3Com1; public TableRow tbRowBot4Com1; public TableRow tbRowBot5Com1;
    public TableRow tbRowBotCom2; public TableRow tbRowBot2Com2; public TableRow tbRowBot3Com2; public TableRow tbRowBot4Com2; public TableRow tbRowBot5Com2;
    public TextView tvBotComodin11; public TextView tvBot2Comodin11; public TextView tvBot3Comodin11; public TextView tvBot4Comodin11; public TextView tvBot5Comodin11;
    public TextView tvBotComodin12; public TextView tvBot2Comodin12; public TextView tvBot3Comodin12; public TextView tvBot4Comodin12; public TextView tvBot5Comodin12;
    public TextView tvBotComodin21; public TextView tvBot2Comodin21; public TextView tvBot3Comodin21; public TextView tvBot4Comodin21; public TextView tvBot5Comodin21;
    public TextView tvBotComodin22; public TextView tvBot2Comodin22; public TextView tvBot3Comodin22; public TextView tvBot4Comodin22; public TextView tvBot5Comodin22;
    public TextView tvBotComodin31; public TextView tvBot2Comodin31; public TextView tvBot3Comodin31; public TextView tvBot4Comodin31; public TextView tvBot5Comodin31;
    public TextView tvBotComodin32; public TextView tvBot2Comodin32; public TextView tvBot3Comodin32; public TextView tvBot4Comodin32; public TextView tvBot5Comodin32;
    public TextView tvBotComodin41; public TextView tvBot2Comodin41; public TextView tvBot3Comodin41; public TextView tvBot4Comodin41; public TextView tvBot5Comodin41;
    public TextView tvBotComodin42; public TextView tvBot2Comodin42; public TextView tvBot3Comodin42; public TextView tvBot4Comodin42; public TextView tvBot5Comodin42;

    // Arrays que contendrán el conjunto de Textview, Layout e ImageView de los vehículos y cartas del Jugador
    public TextView tvCartasJugadores[];
    public ImageView ivCartasJugadores[];
    public TextView tvVehiculosJugadores[];
    public ImageView ivVehiculosJugadores[];
    public RelativeLayout relVehiculosJugadores[];
    public ArrayList<String> ordenNombresEjercitos;
    public TextView tvComodinesJugadores[];
    public boolean tvVehBotPulsableInter[];
    public boolean tvVehBotPulsableExpulsion[];

    // Número de la tirada presente.
    private int numTirada = 3;

    // Array que contendrá la baraja de cartas.
    public static String[] barajaCartas;

    // String que representa el texto de cada carta y su ID.
    public String txtCarta;
    public String idTvCarta;

    // Textview que mostrará internamente el mensaje de victoria cuando se de la ocasión.
    public TextView tvMensajeVictoria;

    // Referencia a las clases de las diferencias funciones del juego.
    public FuncionesGenerales funcionesGenerales;
    public FuncionesJugador funcionesJugador;
    public FuncionesBOT funcionesBOT;

    // String que representa el tipo de vehículo multicolor y el vehículo pulsado.
    public String tipoMedVehMulticolor = "---";
    public String vehiculoPulsado = "";

    // Lista de booleanos del BOT que determinarán si un vehículo es intercambiable por la carta del jugador.
    public boolean tvVeh1Bot1PulsableInter = false;
    public boolean tvVeh2Bot1PulsableInter = false;
    public boolean tvVeh3Bot1PulsableInter = false;
    public boolean tvVeh4Bot1PulsableInter = false;
    public boolean tvVeh1Bot2PulsableInter = false;
    public boolean tvVeh2Bot2PulsableInter = false;
    public boolean tvVeh3Bot2PulsableInter = false;
    public boolean tvVeh4Bot2PulsableInter = false;
    public boolean tvVeh1Bot3PulsableInter = false;
    public boolean tvVeh2Bot3PulsableInter = false;
    public boolean tvVeh3Bot3PulsableInter = false;
    public boolean tvVeh4Bot3PulsableInter = false;
    public boolean tvVeh1Bot4PulsableInter = false;
    public boolean tvVeh2Bot4PulsableInter = false;
    public boolean tvVeh3Bot4PulsableInter = false;
    public boolean tvVeh4Bot4PulsableInter = false;
    public boolean tvVeh1Bot5PulsableInter = false;
    public boolean tvVeh2Bot5PulsableInter = false;
    public boolean tvVeh3Bot5PulsableInter = false;
    public boolean tvVeh4Bot5PulsableInter = false;

    // Lista de booleanos del BOT que determinarán si un vehículo puede recibir las armas expulsadas por el jugador.
    public boolean tvVeh1BotPulsableExpulsion = false;
    public boolean tvVeh2BotPulsableExpulsion = false;
    public boolean tvVeh3BotPulsableExpulsion = false;
    public boolean tvVeh4BotPulsableExpulsion = false;

    // Nombre del vehículo que va a expulsar las armas.
    public String tvVehComAExpulsar = "";

    // ArrayList que determinará que recogerá el orden de turno de los jugadores.
    public ArrayList<Integer> ordenTurno = new ArrayList<>();
    public ArrayList<Integer> ordenTurno2 = new ArrayList<>();

    // Entero que recogerá el número de jugador que van a participar en la partida.
    public static int numeroJugadores;

    // Variables que modificaran la vista en función de si el jugador gana o no la partida.
    public GifImageView ivGifVictoria;
    public ColorMatrix colorMatrix = new ColorMatrix();
    public ColorMatrixColorFilter filter;

    // Variable que recoge el sonido del juego.
    public SoundPool sonidoJuego;

    // Varibles que recogen el volumen máximo y el volumen predeterminado de la musica de juego.
    private final static int MAX_VOLUME = 100;
    private final static int soundVolume = 50;

    // Variable que recoge la música de fondo del juego.
    MediaPlayer musicaJuego;

    // Boolean que recoge si la musica estará encendida o no.
    public boolean volumenJuego = false;

    private AdView mAdView2;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_juego);

        String currentLang = Locale.getDefault().getLanguage();
        Toast.makeText(this, currentLang, Toast.LENGTH_LONG).show();

        MobileAds.initialize(this,
                "ca-app-pub-6925377246649300~5957769388");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6925377246649300/7646255573");
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest1);

        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }

            }

            @Override
            public void onAdOpened() {


            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }
        });

        mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);

        // Enlazamos la música que llevará de fondo el juego con su variable correspondiente.
        musicaJuego = MediaPlayer.create(getApplicationContext(), R.raw.musica_juego);

        // Recogemos la saturación que tendrá la vista de manera predeterminada.
        colorMatrix.setSaturation(0);
        filter = new ColorMatrixColorFilter(colorMatrix);

        // Enlazamos el gif de victoria del juego con su variable correspondiente.
        ivGifVictoria = findViewById(R.id.ivGifVictoria);

        // Enlazamos el contenedor principal del juego y los objetos del toolbar con sus variables correspondiente.
        linearJuego = findViewById(R.id.layoutJuego);
        tvToolbar = findViewById(R.id.tv_toolbar);
        spinnerOpciones = findViewById(R.id.spinner_opciones);
        scrollContenidoInstrucciones = findViewById(R.id.scrollInstrucciones);

        // Recogemos en un array las opciones que tendrá el spinner del toolbar y las imágenes que se representarán en ellas.
        final String[] numOptions = new String[]{"1", "2"};
        final int[] idOptions = new int[]{R.drawable.logo_instrucciones, R.drawable.volume};

        // Enlazamos la fila, los layouts, textviews e imageviews de las cartas del jugador con su variable correspondiente.
        tableJugCartas = findViewById(R.id.tableLayout3);
        tbRowCartas = findViewById(R.id.tbRowCartas);
        relJugCarta1 = findViewById(R.id.relJugCarta1);
        relJugCarta2 = findViewById(R.id.relJugCarta2);
        relJugCarta3 = findViewById(R.id.relJugCarta3);
        tvJugCarta1 = findViewById(R.id.tvJugCarta1);
        tvJugCarta2 = findViewById(R.id.tvJugCarta2);
        tvJugCarta3 = findViewById(R.id.tvJugCarta3);
        ivJugCarta1 = findViewById(R.id.ivJugCarta1);
        ivJugCarta2 = findViewById(R.id.ivJugCarta2);
        ivJugCarta3 = findViewById(R.id.ivJugCarta3);

        // Enlazamos los imageviews de descartar cartas con su variable correspondiente.
        ivDejarCartas1 = findViewById(R.id.ivDejarCartas1);
        ivDejarCartas2 = findViewById(R.id.ivDejarCartas2);
        ivDejarCartas3 = findViewById(R.id.ivDejarCartas3);

        // Enlazamos la fila, los layouts, textviews e imageviews de los vehículos del jugador con su variable correspondiente.
        tableJugVehiculos = findViewById(R.id.tableLayout);
        tbRowJugVeh = findViewById(R.id.tbRowJugVehiculos);
        relJugVeh1 = findViewById(R.id.relJugVeh1);
        relJugVeh2 = findViewById(R.id.relJugVeh2);
        relJugVeh3 = findViewById(R.id.relJugVeh3);
        relJugVeh4 = findViewById(R.id.relJugVeh4);
        tvJugVeh1 = findViewById(R.id.textJug0Vehiculo1);
        tvJugVeh2 = findViewById(R.id.textJug0Vehiculo2);
        tvJugVeh3 = findViewById(R.id.textJug0Vehiculo3);
        tvJugVeh4 = findViewById(R.id.textJug0Vehiculo4);
        ivJugVeh1 = findViewById(R.id.ivJug0Vehiculo1);
        ivJugVeh2 = findViewById(R.id.ivJug0Vehiculo2);
        ivJugVeh3 = findViewById(R.id.ivJug0Vehiculo3);
        ivJugVeh4 = findViewById(R.id.ivJug0Vehiculo4);

        // Recogemos las medidas de la pantalla del dispositivo donde se está ejecutando el juego.
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int widthWindow = dm.widthPixels;
        int heightWindow = dm.heightPixels;

        // Utilizamos esas medidas para colocar los imageviews de descartar cartas en la esquina superior derecha de las cartas
        // del jugador.
        ivDejarCartas1.getLayoutParams().height = heightWindow / 20;
        ivDejarCartas1.getLayoutParams().width = widthWindow / 10;
        ivDejarCartas1.requestLayout();
        ivDejarCartas2.getLayoutParams().height = heightWindow / 20;
        ivDejarCartas2.getLayoutParams().width = widthWindow / 10;
        ivDejarCartas2.requestLayout();
        ivDejarCartas3.getLayoutParams().height = heightWindow / 20;
        ivDejarCartas3.getLayoutParams().width = widthWindow / 10;
        ivDejarCartas3.requestLayout();

        // Enlazamos el el contendor con el espacio intermedio con su variable correspondiente.
        relEspacio = findViewById(R.id.relEspacio);

        // Enlazamos los estados internos de cada vehículo BOT con sus variables correspondientes.
        tbRowJugCom1 = findViewById(R.id.tbRowJugComodines1);
        tbRowJugCom2 = findViewById(R.id.tbRowJugComodines2);
        tvJugComodin11 = findViewById(R.id.textJugComodin11);
        tvJugComodin12 = findViewById(R.id.textJugComodin12);
        tvJugComodin21 = findViewById(R.id.textJugComodin21);
        tvJugComodin22 = findViewById(R.id.textJugComodin22);
        tvJugComodin31 = findViewById(R.id.textJugComodin31);
        tvJugComodin32 = findViewById(R.id.textJugComodin32);
        tvJugComodin41 = findViewById(R.id.textJugComodin41);
        tvJugComodin42 = findViewById(R.id.textJugComodin42);

        // Enlazamos las cartas BOT con su variable correspondiente.
        tvBotCarta1 = findViewById(R.id.textCartaBot1); tvBot2Carta1 = findViewById(R.id.textCarta2Bot1); tvBot3Carta1 = findViewById(R.id.textCarta3Bot1); tvBot4Carta1 = findViewById(R.id.textCarta4Bot1); tvBot5Carta1 = findViewById(R.id.textCarta5Bot1);
        tvBotCarta2 = findViewById(R.id.textCartaBot2); tvBot2Carta2 = findViewById(R.id.textCarta2Bot2); tvBot3Carta2 = findViewById(R.id.textCarta3Bot2); tvBot4Carta2 = findViewById(R.id.textCarta4Bot2); tvBot5Carta2 = findViewById(R.id.textCarta5Bot2);
        tvBotCarta3 = findViewById(R.id.textCartaBot3); tvBot2Carta3 = findViewById(R.id.textCarta2Bot3); tvBot3Carta3 = findViewById(R.id.textCarta3Bot3); tvBot4Carta3 = findViewById(R.id.textCarta4Bot3); tvBot5Carta3 = findViewById(R.id.textCarta5Bot3);

        // Enlazamos la tabla de vehículos, sus filas, layout, imageviews y textviews del BOT con su variable correspondiente.
        tableBotVehiculos = findViewById(R.id.tableLayout2);
        tbRowBotVeh = findViewById(R.id.tbRow1BotVehiculos); tbRow2BotVeh = findViewById(R.id.tbRow2BotVehiculos); tbRow3BotVeh = findViewById(R.id.tbRow3BotVehiculos); tbRow4BotVeh = findViewById(R.id.tbRow4BotVehiculos); tbRow5BotVeh = findViewById(R.id.tbRow5BotVehiculos);
        relBot1Veh1 = findViewById(R.id.relBot1Veh1); relBot2Veh1 = findViewById(R.id.relBot2Veh1); relBot3Veh1 = findViewById(R.id.relBot3Veh1); relBot4Veh1 = findViewById(R.id.relBot4Veh1); relBot5Veh1 = findViewById(R.id.relBot5Veh1);
        relBot1Veh2 = findViewById(R.id.relBot1Veh2); relBot2Veh2 = findViewById(R.id.relBot2Veh2); relBot3Veh2 = findViewById(R.id.relBot3Veh2); relBot4Veh2 = findViewById(R.id.relBot4Veh2); relBot5Veh2 = findViewById(R.id.relBot5Veh2);
        relBot1Veh3 = findViewById(R.id.relBot1Veh3); relBot2Veh3 = findViewById(R.id.relBot2Veh3); relBot3Veh3 = findViewById(R.id.relBot3Veh3); relBot4Veh3 = findViewById(R.id.relBot4Veh3); relBot5Veh3 = findViewById(R.id.relBot5Veh3);
        relBot1Veh4 = findViewById(R.id.relBot1Veh4); relBot2Veh4 = findViewById(R.id.relBot2Veh4); relBot3Veh4 = findViewById(R.id.relBot3Veh4); relBot4Veh4 = findViewById(R.id.relBot4Veh4); relBot5Veh4 = findViewById(R.id.relBot5Veh4);
        tvBotVeh1 = findViewById(R.id.textBot1Vehiculo1); tvBot2Veh1 = findViewById(R.id.textBot2Vehiculo1); tvBot3Veh1 = findViewById(R.id.textBot3Vehiculo1); tvBot4Veh1 = findViewById(R.id.textBot4Vehiculo1); tvBot5Veh1 = findViewById(R.id.textBot5Vehiculo1);
        tvBotVeh2 = findViewById(R.id.textBot1Vehiculo2); tvBot2Veh2 = findViewById(R.id.textBot2Vehiculo2); tvBot3Veh2 = findViewById(R.id.textBot3Vehiculo2); tvBot4Veh2 = findViewById(R.id.textBot4Vehiculo2); tvBot5Veh2 = findViewById(R.id.textBot5Vehiculo2);
        tvBotVeh3 = findViewById(R.id.textBot1Vehiculo3); tvBot2Veh3 = findViewById(R.id.textBot2Vehiculo3); tvBot3Veh3 = findViewById(R.id.textBot3Vehiculo3); tvBot4Veh3 = findViewById(R.id.textBot4Vehiculo3); tvBot5Veh3 = findViewById(R.id.textBot5Vehiculo3);
        tvBotVeh4 = findViewById(R.id.textBot1Vehiculo4); tvBot2Veh4 = findViewById(R.id.textBot2Vehiculo4); tvBot3Veh4 = findViewById(R.id.textBot3Vehiculo4); tvBot4Veh4 = findViewById(R.id.textBot4Vehiculo4); tvBot5Veh4 = findViewById(R.id.textBot5Vehiculo4);
        ivBotVeh1 = findViewById(R.id.ivBot1Vehiculo1); ivBot2Veh1 = findViewById(R.id.ivBot2Vehiculo1); ivBot3Veh1 = findViewById(R.id.ivBot3Vehiculo1); ivBot4Veh1 = findViewById(R.id.ivBot4Vehiculo1); ivBot5Veh1 = findViewById(R.id.ivBot5Vehiculo1);
        ivBotVeh2 = findViewById(R.id.ivBot1Vehiculo2); ivBot2Veh2 = findViewById(R.id.ivBot2Vehiculo2); ivBot3Veh2 = findViewById(R.id.ivBot3Vehiculo2); ivBot4Veh2 = findViewById(R.id.ivBot4Vehiculo2); ivBot5Veh2 = findViewById(R.id.ivBot5Vehiculo2);
        ivBotVeh3 = findViewById(R.id.ivBot1Vehiculo3); ivBot2Veh3 = findViewById(R.id.ivBot2Vehiculo3); ivBot3Veh3 = findViewById(R.id.ivBot3Vehiculo3); ivBot4Veh3 = findViewById(R.id.ivBot4Vehiculo3); ivBot5Veh3 = findViewById(R.id.ivBot5Vehiculo3);
        ivBotVeh4 = findViewById(R.id.ivBot1Vehiculo4); ivBot2Veh4 = findViewById(R.id.ivBot2Vehiculo4); ivBot3Veh4 = findViewById(R.id.ivBot3Vehiculo4); ivBot4Veh4 = findViewById(R.id.ivBot4Vehiculo4); ivBot5Veh4 = findViewById(R.id.ivBot5Vehiculo4);

        // Enlazamos las filas y los textviews del estado de los vehículos con su variable correspondiente.
        tbRowBotCom1 = findViewById(R.id.tbRowBotComodines1); tbRowBot2Com1 = findViewById(R.id.tbRowBot2Comodines1); tbRowBot3Com1 = findViewById(R.id.tbRowBot3Comodines1); tbRowBot4Com1 = findViewById(R.id.tbRowBot4Comodines1); tbRowBot5Com1 = findViewById(R.id.tbRowBot5Comodines1);
        tbRowBotCom2 = findViewById(R.id.tbRowBotComodines1); tbRowBot2Com2 = findViewById(R.id.tbRowBot2Comodines2); tbRowBot3Com2 = findViewById(R.id.tbRowBot3Comodines2); tbRowBot4Com2 = findViewById(R.id.tbRowBot4Comodines2); tbRowBot5Com2 = findViewById(R.id.tbRowBot5Comodines2);
        tvBotComodin11 = findViewById(R.id.textBotComodin11); tvBot2Comodin11 = findViewById(R.id.textBot2Comodin11); tvBot3Comodin11 = findViewById(R.id.textBot3Comodin11); tvBot4Comodin11 = findViewById(R.id.textBot4Comodin11); tvBot5Comodin11 = findViewById(R.id.textBot5Comodin11);
        tvBotComodin12 = findViewById(R.id.textBotComodin12); tvBot2Comodin12 = findViewById(R.id.textBot2Comodin12); tvBot3Comodin12 = findViewById(R.id.textBot3Comodin12); tvBot4Comodin12 = findViewById(R.id.textBot4Comodin12); tvBot5Comodin12 = findViewById(R.id.textBot5Comodin12);
        tvBotComodin21 = findViewById(R.id.textBotComodin21); tvBot2Comodin21 = findViewById(R.id.textBot2Comodin21); tvBot3Comodin21 = findViewById(R.id.textBot3Comodin21); tvBot4Comodin21 = findViewById(R.id.textBot4Comodin21); tvBot5Comodin21 = findViewById(R.id.textBot5Comodin21);
        tvBotComodin22 = findViewById(R.id.textBotComodin22); tvBot2Comodin22 = findViewById(R.id.textBot2Comodin22); tvBot3Comodin22 = findViewById(R.id.textBot3Comodin22); tvBot4Comodin22 = findViewById(R.id.textBot4Comodin22); tvBot5Comodin22 = findViewById(R.id.textBot5Comodin22);
        tvBotComodin31 = findViewById(R.id.textBotComodin31); tvBot2Comodin31 = findViewById(R.id.textBot2Comodin31); tvBot3Comodin31 = findViewById(R.id.textBot3Comodin31); tvBot4Comodin31 = findViewById(R.id.textBot4Comodin31); tvBot5Comodin31 = findViewById(R.id.textBot5Comodin31);
        tvBotComodin32 = findViewById(R.id.textBotComodin32); tvBot2Comodin32 = findViewById(R.id.textBot2Comodin32); tvBot3Comodin32 = findViewById(R.id.textBot3Comodin32); tvBot4Comodin32 = findViewById(R.id.textBot4Comodin32); tvBot5Comodin32 = findViewById(R.id.textBot5Comodin32);
        tvBotComodin41 = findViewById(R.id.textBotComodin41); tvBot2Comodin41 = findViewById(R.id.textBot2Comodin41); tvBot3Comodin41 = findViewById(R.id.textBot3Comodin41); tvBot4Comodin41 = findViewById(R.id.textBot4Comodin41); tvBot5Comodin41 = findViewById(R.id.textBot5Comodin41);
        tvBotComodin42 = findViewById(R.id.textBotComodin42); tvBot2Comodin42 = findViewById(R.id.textBot2Comodin42); tvBot3Comodin42 = findViewById(R.id.textBot3Comodin42); tvBot4Comodin42 = findViewById(R.id.textBot4Comodin42); tvBot5Comodin42 = findViewById(R.id.textBot5Comodin42);

        // Recogemos en un array la fila de vehículos del BOT.
        tbRowVehs = new TableRow[]{ tbRowJugVeh, tbRowBotVeh, tbRow2BotVeh, tbRow3BotVeh, tbRow4BotVeh, tbRow5BotVeh };

        // Recogemos en un array las cartas de todos los jugadores.
        tvCartasJugadores = new TextView[]{tvJugCarta1, tvJugCarta2, tvJugCarta3,
                tvBotCarta1, tvBotCarta2, tvBotCarta3, tvBot2Carta1, tvBot2Carta2, tvBot2Carta3,
                tvBot3Carta1, tvBot3Carta2, tvBot3Carta3, tvBot4Carta1, tvBot4Carta2, tvBot4Carta3,
                tvBot5Carta1, tvBot5Carta2, tvBot5Carta3};

        // Recogemos en un array los imageviews de las cartas del jugador.
        ivCartasJugadores = new ImageView[]{ivJugCarta1, ivJugCarta2, ivJugCarta3};

        // Recogemos en un array los textviews de los vehículos de todos los jugadores.
        tvVehiculosJugadores = new TextView[]{tvJugVeh1, tvJugVeh2, tvJugVeh3, tvJugVeh4,
                tvBotVeh1, tvBotVeh2, tvBotVeh3, tvBotVeh4, tvBot2Veh1, tvBot2Veh2, tvBot2Veh3, tvBot2Veh4,
                tvBot3Veh1, tvBot3Veh2, tvBot3Veh3, tvBot3Veh4, tvBot4Veh1, tvBot4Veh2, tvBot4Veh3, tvBot4Veh4,
                tvBot5Veh1, tvBot5Veh2, tvBot5Veh3, tvBot5Veh4};

        // Recogemos en un array los imageviews de los vehículos de todos los jugadores.
        ivVehiculosJugadores = new ImageView[]{ivJugVeh1, ivJugVeh2, ivJugVeh3, ivJugVeh4, ivBotVeh1, ivBotVeh2, ivBotVeh3, ivBotVeh4,
                ivBot2Veh1, ivBot2Veh2, ivBot2Veh3, ivBot2Veh4, ivBot3Veh1, ivBot3Veh2, ivBot3Veh3, ivBot3Veh4, ivBot4Veh1, ivBot4Veh2, ivBot4Veh3, ivBot4Veh4,
                ivBot5Veh1, ivBot5Veh2, ivBot5Veh3, ivBot5Veh4};

        // Recogemos en un array los layout de los vehículos de todos los jugadores.
        relVehiculosJugadores = new RelativeLayout[]{relJugVeh1, relJugVeh2, relJugVeh3, relJugVeh4,
                relBot1Veh1, relBot1Veh2, relBot1Veh3, relBot1Veh4, relBot2Veh1, relBot2Veh2, relBot2Veh3, relBot2Veh4,
                relBot3Veh1, relBot3Veh2, relBot3Veh3, relBot3Veh4, relBot4Veh1, relBot4Veh2, relBot4Veh3, relBot4Veh4,
                relBot5Veh1, relBot5Veh2, relBot5Veh3, relBot5Veh4};

        // Recogemos en un array los textviews de los estados de cada vehículos de todos los jugadores.
        tvComodinesJugadores = new TextView[]{
                tvJugComodin11, tvJugComodin12, tvJugComodin21, tvJugComodin22, tvJugComodin31, tvJugComodin32, tvJugComodin41, tvJugComodin42,
                tvBotComodin11, tvBotComodin12, tvBotComodin21, tvBotComodin22, tvBotComodin31, tvBotComodin32, tvBotComodin41, tvBotComodin42,
                tvBot2Comodin11, tvBot2Comodin12, tvBot2Comodin21, tvBot2Comodin22, tvBot2Comodin31, tvBot2Comodin32, tvBot2Comodin41, tvBot2Comodin42,
                tvBot3Comodin11, tvBot3Comodin12, tvBot3Comodin21, tvBot3Comodin22, tvBot3Comodin31, tvBot3Comodin32, tvBot3Comodin41, tvBot3Comodin42,
                tvBot4Comodin11, tvBot4Comodin12, tvBot4Comodin21, tvBot4Comodin22, tvBot4Comodin31, tvBot4Comodin32, tvBot4Comodin41, tvBot4Comodin42,
                tvBot5Comodin11, tvBot5Comodin12, tvBot5Comodin21, tvBot5Comodin22, tvBot5Comodin31, tvBot5Comodin32, tvBot5Comodin41, tvBot5Comodin42};

        // Recogemos en un array los booleanos que determinán si un vehículo es intercambiable o no.
        tvVehBotPulsableInter = new boolean[]{
                false, false, false, false, false,
                tvVeh1Bot1PulsableInter, tvVeh2Bot1PulsableInter, tvVeh3Bot1PulsableInter, tvVeh4Bot1PulsableInter,
                tvVeh1Bot2PulsableInter, tvVeh2Bot2PulsableInter, tvVeh3Bot2PulsableInter, tvVeh4Bot2PulsableInter,
                tvVeh1Bot3PulsableInter, tvVeh2Bot3PulsableInter, tvVeh3Bot3PulsableInter, tvVeh4Bot3PulsableInter,
                tvVeh1Bot4PulsableInter, tvVeh2Bot4PulsableInter, tvVeh3Bot4PulsableInter, tvVeh4Bot4PulsableInter,
                tvVeh1Bot5PulsableInter, tvVeh2Bot5PulsableInter, tvVeh3Bot5PulsableInter, tvVeh4Bot5PulsableInter};

        // Recogemos en un array los booleanos que determinarán si un vehículo puede recibir las armas expulsadas o no.
        tvVehBotPulsableExpulsion = new boolean[]{
                tvVeh1BotPulsableExpulsion, tvVeh2BotPulsableExpulsion, tvVeh3BotPulsableExpulsion, tvVeh4BotPulsableExpulsion};

        // Enlazamos el mensaje de victoria con su variable correspondiente.
        tvMensajeVictoria = findViewById(R.id.tvMensajeVictoria);

        sonidoJuego = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        // Creamos un ArrayList con los nombres que podrán tener los jugadores.
        final ArrayList<String> nombresEjercitos = new ArrayList<String>();
        nombresEjercitos.add("Wehrmacht");
        nombresEjercitos.add("Alsace");
        nombresEjercitos.add("Royal Air");
        nombresEjercitos.add("Bersaglieri");
        nombresEjercitos.add("Ejercito Rojo");
        nombresEjercitos.add("The Big Red");
        nombresEjercitos.add("Otsu");

        // Nombre de cada número de las cartas de la baraja.
        String vehiculo1 = "Batallon";
        String vehiculo2 = "Tanque";
        String vehiculo3 = "Submarino";
        String vehiculo4 = "Caza";
        String vehiculo5 = "Vehículo Multicolor";
        String arma1 = "Metralleta";
        String arma2 = "Lanzacohetes";
        String arma3 = "Torpedos";
        String arma4 = "Misiles";
        String arma5 = "Arma Multicolor";
        String antiArma1 = "Medicina1";
        String antiArma2 = "Medicina2";
        String antiArma3 = "Medicina3";
        String antiArma4 = "Medicina4";
        String antiArma5 = "Medicina Multicolor";
        String roboVehiculo = "Robar Vehículo";
        String cambiarVehiculo = "Cambiar Vehículo";
        String expulsarArmas = "Expulsar Armas";
        String intercambiarEquipo = "Intercambiar Brigada";
        String dejarCartas = "Dejar Cartas";

        // Tipografía principal del juego.
        Typeface typeface_tvToolbar = Typeface.createFromAsset(getAssets(),
                "fonts/NuevoDisco.ttf");

        // Personalizamos eltextview representado en el Toolbar.
        tvToolbar.setTextColor(Color.parseColor("#e1e1e1"));
        tvToolbar.setTypeface(typeface_tvToolbar);
        tvToolbar.setText(getResources().getString(R.string.tvToolbarComienzoPartida));

        // Recogemos de la actividad anterior el número de jugadores seleccionado por el ususario.
        Intent intentNumJug = getIntent();
        final String numJugadores = intentNumJug.getStringExtra("numJugadores");
        numeroJugadores = Integer.parseInt(numJugadores);

        // Determinamos el peso interno que tendrá la tabla de vehículos del BOT.
        tableBotVehiculos.setWeightSum(numeroJugadores - 1);
        ViewGroup.LayoutParams params = tableBotVehiculos.getLayoutParams();

        // Indicamos que la función clickable imageview de las cartas del jugador de descartarcarta quedará bloqueado
        // en un principio.
        ivJugCarta1.setClickable(false);
        ivJugCarta2.setClickable(false);
        ivJugCarta3.setClickable(false);
        ivDejarCartas1.setClickable(false);
        ivDejarCartas2.setClickable(false);
        ivDejarCartas3.setClickable(false);

        // Creamos la clase de las funciones generales del juego que afectarán tanto a Jugador como a BOT.
        funcionesGenerales = new FuncionesGenerales(tvVehiculosJugadores, ivVehiculosJugadores, tvComodinesJugadores, tvCartasJugadores, ivCartasJugadores,
                relVehiculosJugadores, numeroJugadores, nombresEjercitos, tableJugCartas, tbRowVehs, linearJuego, getApplicationContext(), tableJugVehiculos);

        // Determinamos que nombre de ejército tendrá cada jugador d ela partida.
        ordenNombresEjercitos = funcionesGenerales.decidirNombresEjercitos(numeroJugadores);

        // Creamos las clases que contendrá las funciones que afectarán al BOT y al Jugador del juego respectivamente.
        funcionesBOT = new FuncionesBOT(tvVehiculosJugadores, ivVehiculosJugadores, tvComodinesJugadores, tvCartasJugadores, numeroJugadores, tvToolbar,
                sonidoJuego, nombresEjercitos, ordenNombresEjercitos, ivGifVictoria, tableJugCartas, tbRowVehs, linearJuego, tableJugVehiculos,
                relVehiculosJugadores);
        funcionesJugador = new FuncionesJugador(tvVehiculosJugadores, ivVehiculosJugadores, tvComodinesJugadores, tvCartasJugadores, ivCartasJugadores,
                tipoMedVehMulticolor, vehiculoPulsado,
                relVehiculosJugadores, tbRowJugVeh, tvVehBotPulsableInter, tvVehBotPulsableExpulsion,
                tvVehComAExpulsar, getApplicationContext(), numeroJugadores, tvToolbar, sonidoJuego,
                nombresEjercitos, ordenNombresEjercitos, tableJugVehiculos);

        // Determinamos que fila de vehículos del BOT serán visibles en función del número de jugadores.
        switch (numeroJugadores)
        {
            case 2:
                params.height = 200;
                tableBotVehiculos.setLayoutParams(params);
                break;
            case 3:
                params.height = 250;
                tableBotVehiculos.setLayoutParams(params);
                tbRow2BotVeh.setVisibility(View.VISIBLE);
                tbRowBot2Com1.setVisibility(View.VISIBLE);
                tbRowBot2Com2.setVisibility(View.VISIBLE);
                break;
            case 4:
                params.height = 250;
                tableBotVehiculos.setLayoutParams(params);
                tbRow2BotVeh.setVisibility(View.VISIBLE);
                tbRow3BotVeh.setVisibility(View.VISIBLE);
                tbRowBot2Com1.setVisibility(View.VISIBLE);
                tbRowBot2Com2.setVisibility(View.VISIBLE);
                tbRowBot3Com1.setVisibility(View.VISIBLE);
                tbRowBot3Com2.setVisibility(View.VISIBLE);
                break;
            case 5:
                params.height = 300;
                tableBotVehiculos.setLayoutParams(params);
                tbRow2BotVeh.setVisibility(View.VISIBLE);
                tbRow3BotVeh.setVisibility(View.VISIBLE);
                tbRow4BotVeh.setVisibility(View.VISIBLE);
                tbRowBot2Com1.setVisibility(View.VISIBLE);
                tbRowBot2Com2.setVisibility(View.VISIBLE);
                tbRowBot3Com1.setVisibility(View.VISIBLE);
                tbRowBot3Com2.setVisibility(View.VISIBLE);
                tbRowBot4Com1.setVisibility(View.VISIBLE);
                tbRowBot4Com2.setVisibility(View.VISIBLE);
                break;
            case 6:
                params.height = 350;
                tableBotVehiculos.setLayoutParams(params);
                tbRow2BotVeh.setVisibility(View.VISIBLE);
                tbRow3BotVeh.setVisibility(View.VISIBLE);
                tbRow4BotVeh.setVisibility(View.VISIBLE);
                tbRow5BotVeh.setVisibility(View.VISIBLE);
                tbRowBot2Com1.setVisibility(View.VISIBLE);
                tbRowBot2Com2.setVisibility(View.VISIBLE);
                tbRowBot3Com1.setVisibility(View.VISIBLE);
                tbRowBot3Com2.setVisibility(View.VISIBLE);
                tbRowBot4Com1.setVisibility(View.VISIBLE);
                tbRowBot4Com2.setVisibility(View.VISIBLE);
                tbRowBot5Com1.setVisibility(View.VISIBLE);
                tbRowBot5Com2.setVisibility(View.VISIBLE);
                break;
        }

        // Enlazamos el número de la baraja con cada nombre de carta.
        barajaCartas = new String[]
                {       vehiculo1, vehiculo1, vehiculo1, vehiculo1, vehiculo1, // Del 0 a 4
                        vehiculo2, vehiculo2, vehiculo2, vehiculo2, vehiculo2, // Del 5 a 9
                        vehiculo3, vehiculo3, vehiculo3, vehiculo3, vehiculo3, // Del 10 a 14
                        vehiculo4, vehiculo4, vehiculo4, vehiculo4, vehiculo4, // Del 15 a 19
                        vehiculo5,
                        antiArma1, antiArma1, antiArma1, antiArma1,            // Del 21 a 24
                        antiArma2, antiArma2, antiArma2, antiArma2,            // Del 25 a 28
                        antiArma3, antiArma3, antiArma3, antiArma3,            // Del 29 a 32
                        antiArma4, antiArma4, antiArma4, antiArma4,            // Del 33 a 36
                        antiArma5,
                        arma1, arma1, arma1, arma1,                            // Del 21 a 24
                        arma2, arma2, arma2, arma2,                            // Del 25 a 28
                        arma3, arma3, arma3, arma3,                            // Del 29 a 32
                        arma4, arma4, arma4, arma4,                            // Del 33 a 36
                        arma5,                                                 // 37
                        roboVehiculo, roboVehiculo, roboVehiculo, roboVehiculo,// Del 38 a 41
                        cambiarVehiculo, cambiarVehiculo, cambiarVehiculo,     // Del 42 a 44
                        intercambiarEquipo, intercambiarEquipo,                // Del 45 al 46
                        expulsarArmas, expulsarArmas,                          // Del 47 al 48
                        dejarCartas, dejarCartas};                             // Del 49 al 50

        // Le asignamos un orden aleatorio a su variable correspondiente.
        ordenTurno = funcionesGenerales.decidirTurnos(Integer.parseInt(numJugadores));

        // A partir del orden generado reordenamos el Array desde el primer valor posterior a la posición del turno del Jugador
        // hasta el último valor previo a la posición del turno del Jugador.
        for (int i = funcionesGenerales.saberPosicionMiTurno() + 1; i < ordenTurno.size(); i++)
        {
            ordenTurno2.add(ordenTurno.get(i));
        }
        for (int i = 0; i < funcionesGenerales.saberPosicionMiTurno(); i++)
        {
            ordenTurno2.add(ordenTurno.get(i));
        }

        // Llamamos a la función que asigne aleatoriamente tres cartas a cada jugador.
        for (int i = 0; i < Integer.parseInt(numJugadores); i++)
        {
            funcionesGenerales.echarTresCartas(barajaCartas, tvCartasJugadores[i * 3 + 0],
                    tvCartasJugadores[ordenTurno.get(i) * 3 + 1], tvCartasJugadores[i * 3 + 2]);
        }

        // A partir de las cartas asignadas le asignamos su imagen de carta correspondiente.
        funcionesGenerales.determinarImagenCasillaCartas(tvCartasJugadores, ivCartasJugadores);

        final AccionesBot accionesBot = AccionesBot.getInstance();

        // Llamamos a un Timer que es el controlará el tiempo que deberá transcurrir entre turnos.
        final Timer timer = new Timer();
        final int contador = 0;
        TimerTask timerTask = new TimerTask() {

            // Contador que controlará la posición del OrdenTurnos en la que nos encontramos.
            int cont = contador;

            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // Si la posición del OrdenTrno no apunta a la posición del jugador entramos.
                        if (!ordenTurno.get(cont).equals(0))
                        {
                            for (int i = 0; i < tbRowVehs.length; i++)
                            {
                                if (i == ordenTurno.get(cont))
                                {
                                    // Resaltamos los layout del Jugador que tenga su turno en el momento presente.
                                    relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                    relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                    relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                    relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                }
                                else
                                {
                                    relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                    relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                    relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                    relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                    relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                    relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                    relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                    relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                }
                            }

                            funcionesBOT.accionBot(tvCartasJugadores, ivCartasJugadores, tvVehiculosJugadores, tvMensajeVictoria,
                                    numTirada, barajaCartas, getApplicationContext(), relVehiculosJugadores,
                                    ordenTurno, ordenTurno.get(cont), getCurrentFocus(), musicaJuego);

                            // Si el BOT que aacaba de lanzar su carta se convierte en campeón se cancela el timpo de turnos.
                            if (funcionesGenerales.botEsCampeon(accionesBot, ordenTurno.get(cont) - 1, filter, getApplicationContext(), musicaJuego))
                            {
                                timer.cancel();
                            }
                        }
                        else
                        {
                            for (int i = 0; i < tbRowVehs.length; i++)
                            {
                                relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                            }

                            // Si es turno del JUGADOR permitimos que las cartas y el descarte de cartas pueda ser clickable.
                            ivJugCarta1.setClickable(true);
                            ivJugCarta2.setClickable(true);
                            ivJugCarta3.setClickable(true);
                            ivDejarCartas1.setClickable(true);
                            ivDejarCartas2.setClickable(true);
                            ivDejarCartas3.setClickable(true);

                            // Reproducimos el sonido para cuando sea el turno del JUGADOR y lo informamos en el Toolbar.
                            funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.turno, sonidoJuego);
                            tvToolbar.setText(getResources().getString(R.string.tvToolbarTuTurno));
                            tbRowCartas.setBackgroundColor(Color.parseColor("#70FFFFFF"));

                            timer.cancel();
                        }

                        cont += 1;
                    }
                });
            }
        };
        // Decidimos que pasen 5000 milisegundos entre turnos.
        timer.schedule(timerTask, 5000, 5000);

        // Permitimos que las imágenes de las cartas se puedan arrastrar y soltar sobre cada una de las imáganes de los
        // vehículos de los jugadores.
        ivJugCarta1.setOnTouchListener(new ChoiceTouchListener(tvJugCarta1, tvJugCarta2, tvJugCarta3, idTvCarta));
        ivJugCarta2.setOnTouchListener(new ChoiceTouchListener(tvJugCarta2, tvJugCarta1, tvJugCarta3, idTvCarta));
        ivJugCarta3.setOnTouchListener(new ChoiceTouchListener(tvJugCarta3, tvJugCarta1, tvJugCarta2, idTvCarta));
        ivJugVeh1.setOnDragListener(new ChoiceDragListener(tvJugVeh1, tvBotVeh1, tvBotComodin11, tvBotComodin12,
                tvJugComodin11, tvJugComodin12, relBot1Veh1, relJugVeh1, tbRowJugVeh, tbRowBotVeh));
        ivJugVeh2.setOnDragListener(new ChoiceDragListener(tvJugVeh2, tvBotVeh2, tvBotComodin21, tvBotComodin22,
                tvJugComodin21, tvJugComodin22, relBot1Veh2, relJugVeh2, tbRowJugVeh, tbRowBotVeh));
        ivJugVeh3.setOnDragListener(new ChoiceDragListener(tvJugVeh3, tvBotVeh3, tvBotComodin31, tvBotComodin32,
                tvJugComodin31, tvJugComodin32, relBot1Veh3, relJugVeh3, tbRowJugVeh, tbRowBotVeh));
        ivJugVeh4.setOnDragListener(new ChoiceDragListener(tvJugVeh4, tvBotVeh4, tvBotComodin41, tvBotComodin42,
                tvJugComodin41, tvJugComodin42, relBot1Veh4, relJugVeh4, tbRowJugVeh, tbRowBotVeh));
        ivJugVeh1.setOnDragListener(new ChoiceDragListener(tvJugVeh1, tvBotVeh1, tvBotComodin11, tvBotComodin12,
                tvJugComodin11, tvJugComodin12, relBot2Veh1, relJugVeh1, tbRowJugVeh, tbRow2BotVeh));
        ivJugVeh2.setOnDragListener(new ChoiceDragListener(tvJugVeh2, tvBotVeh2, tvBotComodin21, tvBotComodin22,
                tvJugComodin21, tvJugComodin22, relBot2Veh2, relJugVeh2, tbRowJugVeh, tbRow2BotVeh));
        ivJugVeh3.setOnDragListener(new ChoiceDragListener(tvJugVeh3, tvBotVeh3, tvBotComodin31, tvBotComodin32,
                tvJugComodin31, tvJugComodin32, relBot2Veh3, relJugVeh3, tbRowJugVeh, tbRow2BotVeh));
        ivJugVeh4.setOnDragListener(new ChoiceDragListener(tvJugVeh4, tvBotVeh4, tvBotComodin41, tvBotComodin42,
                tvJugComodin41, tvJugComodin42, relBot2Veh4, relJugVeh4, tbRowJugVeh, tbRow2BotVeh));
        ivJugVeh1.setOnDragListener(new ChoiceDragListener(tvJugVeh1, tvBotVeh1, tvBotComodin11, tvBotComodin12,
                tvJugComodin11, tvJugComodin12, relBot3Veh1, relJugVeh1, tbRowJugVeh, tbRow3BotVeh));
        ivJugVeh2.setOnDragListener(new ChoiceDragListener(tvJugVeh2, tvBotVeh2, tvBotComodin21, tvBotComodin22,
                tvJugComodin21, tvJugComodin22, relBot3Veh2, relJugVeh2, tbRowJugVeh, tbRow3BotVeh));
        ivJugVeh3.setOnDragListener(new ChoiceDragListener(tvJugVeh3, tvBotVeh3, tvBotComodin31, tvBotComodin32,
                tvJugComodin31, tvJugComodin32, relBot3Veh3, relJugVeh3, tbRowJugVeh, tbRow3BotVeh));
        ivJugVeh4.setOnDragListener(new ChoiceDragListener(tvJugVeh4, tvBotVeh4, tvBotComodin41, tvBotComodin42,
                tvJugComodin41, tvJugComodin42, relBot4Veh4, relJugVeh4, tbRowJugVeh, tbRow3BotVeh));
        ivJugVeh1.setOnDragListener(new ChoiceDragListener(tvJugVeh1, tvBotVeh1, tvBotComodin11, tvBotComodin12,
                tvJugComodin11, tvJugComodin12, relBot4Veh1, relJugVeh1, tbRowJugVeh, tbRow4BotVeh));
        ivJugVeh2.setOnDragListener(new ChoiceDragListener(tvJugVeh2, tvBotVeh2, tvBotComodin21, tvBotComodin22,
                tvJugComodin21, tvJugComodin22, relBot4Veh2, relJugVeh2, tbRowJugVeh, tbRow4BotVeh));
        ivJugVeh3.setOnDragListener(new ChoiceDragListener(tvJugVeh3, tvBotVeh3, tvBotComodin31, tvBotComodin32,
                tvJugComodin31, tvJugComodin32, relBot4Veh3, relJugVeh3, tbRowJugVeh, tbRow4BotVeh));
        ivJugVeh4.setOnDragListener(new ChoiceDragListener(tvJugVeh4, tvBotVeh4, tvBotComodin41, tvBotComodin42,
                tvJugComodin41, tvJugComodin42, relBot4Veh4, relJugVeh4, tbRowJugVeh, tbRow4BotVeh));
        ivJugVeh1.setOnDragListener(new ChoiceDragListener(tvJugVeh1, tvBotVeh1, tvBotComodin11, tvBotComodin12,
                tvJugComodin11, tvJugComodin12, relBot5Veh1, relJugVeh1, tbRowJugVeh, tbRow5BotVeh));
        ivJugVeh2.setOnDragListener(new ChoiceDragListener(tvJugVeh2, tvBotVeh2, tvBotComodin21, tvBotComodin22,
                tvJugComodin21, tvJugComodin22, relBot5Veh2, relJugVeh2, tbRowJugVeh, tbRow5BotVeh));
        ivJugVeh3.setOnDragListener(new ChoiceDragListener(tvJugVeh3, tvBotVeh3, tvBotComodin31, tvBotComodin32,
                tvJugComodin31, tvJugComodin32, relBot5Veh3, relJugVeh3, tbRowJugVeh, tbRow5BotVeh));
        ivJugVeh4.setOnDragListener(new ChoiceDragListener(tvJugVeh4, tvBotVeh4, tvBotComodin41, tvBotComodin42,
                tvJugComodin41, tvJugComodin42, relBot5Veh4, relJugVeh4, tbRowJugVeh, tbRow5BotVeh));

        ivBotVeh1.setOnDragListener(new ChoiceDragListener(tvJugVeh1, tvBotVeh1, tvBotComodin11, tvBotComodin12,
                tvJugComodin11, tvJugComodin12, relBot1Veh1, relJugVeh1, tbRowJugVeh, tbRowBotVeh));
        ivBotVeh2.setOnDragListener(new ChoiceDragListener(tvJugVeh2, tvBotVeh2, tvBotComodin21, tvBotComodin22,
                tvJugComodin21, tvJugComodin22, relBot1Veh2, relJugVeh2, tbRowJugVeh, tbRowBotVeh));
        ivBotVeh3.setOnDragListener(new ChoiceDragListener(tvJugVeh3, tvBotVeh3, tvBotComodin31, tvBotComodin32,
                tvJugComodin31, tvJugComodin32, relBot1Veh3, relJugVeh3, tbRowJugVeh, tbRowBotVeh));
        ivBotVeh4.setOnDragListener(new ChoiceDragListener(tvJugVeh4, tvBotVeh4, tvBotComodin41, tvBotComodin42,
                tvJugComodin41, tvJugComodin42, relBot1Veh4, relJugVeh4, tbRowJugVeh, tbRowBotVeh));
        ivBot2Veh1.setOnDragListener(new ChoiceDragListener(tvJugVeh1, tvBot2Veh1, tvBot2Comodin11, tvBot2Comodin12,
                tvJugComodin11, tvJugComodin12, relBot2Veh1, relJugVeh1, tbRowJugVeh, tbRow2BotVeh));
        ivBot2Veh2.setOnDragListener(new ChoiceDragListener(tvJugVeh2, tvBot2Veh2, tvBot2Comodin21, tvBot2Comodin22,
                tvJugComodin21, tvJugComodin22, relBot2Veh2, relJugVeh2, tbRowJugVeh, tbRow2BotVeh));
        ivBot2Veh3.setOnDragListener(new ChoiceDragListener(tvJugVeh3, tvBot2Veh3, tvBot2Comodin31, tvBot2Comodin32,
                tvJugComodin31, tvJugComodin32, relBot2Veh3, relJugVeh3, tbRowJugVeh, tbRow2BotVeh));
        ivBot2Veh4.setOnDragListener(new ChoiceDragListener(tvJugVeh4, tvBot2Veh4, tvBot2Comodin41, tvBot2Comodin42,
                tvJugComodin41, tvJugComodin42, relBot2Veh4, relJugVeh4, tbRowJugVeh, tbRow2BotVeh));
        ivBot3Veh1.setOnDragListener(new ChoiceDragListener(tvJugVeh1, tvBot3Veh1, tvBot3Comodin11, tvBot3Comodin12,
                tvJugComodin11, tvJugComodin12, relBot3Veh1, relJugVeh1, tbRowJugVeh, tbRow3BotVeh));
        ivBot3Veh2.setOnDragListener(new ChoiceDragListener(tvJugVeh2, tvBot3Veh2, tvBot3Comodin21, tvBot3Comodin22,
                tvJugComodin21, tvJugComodin22, relBot3Veh2, relJugVeh2, tbRowJugVeh, tbRow3BotVeh));
        ivBot3Veh3.setOnDragListener(new ChoiceDragListener(tvJugVeh3, tvBot3Veh3, tvBot3Comodin31, tvBot3Comodin32,
                tvJugComodin31, tvJugComodin32, relBot3Veh3, relJugVeh3, tbRowJugVeh, tbRow3BotVeh));
        ivBot3Veh4.setOnDragListener(new ChoiceDragListener(tvJugVeh4, tvBot3Veh4, tvBot3Comodin41, tvBot3Comodin42,
                tvJugComodin41, tvJugComodin42, relBot3Veh4, relJugVeh4, tbRowJugVeh, tbRow3BotVeh));
        ivBot4Veh1.setOnDragListener(new ChoiceDragListener(tvJugVeh1, tvBot4Veh1, tvBot4Comodin11, tvBot4Comodin12,
                tvJugComodin11, tvJugComodin12, relBot4Veh1, relJugVeh1, tbRowJugVeh, tbRow4BotVeh));
        ivBot4Veh2.setOnDragListener(new ChoiceDragListener(tvJugVeh2, tvBot4Veh2, tvBot4Comodin21, tvBot4Comodin22,
                tvJugComodin21, tvJugComodin22, relBot4Veh2, relJugVeh2, tbRowJugVeh, tbRow4BotVeh));
        ivBot4Veh3.setOnDragListener(new ChoiceDragListener(tvJugVeh3, tvBot4Veh3, tvBot4Comodin31, tvBot4Comodin32,
                tvJugComodin31, tvJugComodin32, relBot4Veh3, relJugVeh3, tbRowJugVeh, tbRow4BotVeh));
        ivBot4Veh4.setOnDragListener(new ChoiceDragListener(tvJugVeh4, tvBot4Veh4, tvBot4Comodin41, tvBot4Comodin42,
                tvJugComodin41, tvJugComodin42, relBot4Veh4, relJugVeh4, tbRowJugVeh, tbRow4BotVeh));
        ivBot5Veh1.setOnDragListener(new ChoiceDragListener(tvJugVeh1, tvBot5Veh1, tvBot5Comodin11, tvBot5Comodin12,
                tvJugComodin11, tvJugComodin12, relBot5Veh1, relJugVeh1, tbRowJugVeh, tbRow5BotVeh));
        ivBot5Veh2.setOnDragListener(new ChoiceDragListener(tvJugVeh2, tvBot5Veh2, tvBot5Comodin21, tvBot5Comodin22,
                tvJugComodin21, tvJugComodin22, relBot5Veh2, relJugVeh2, tbRowJugVeh, tbRow5BotVeh));
        ivBot5Veh3.setOnDragListener(new ChoiceDragListener(tvJugVeh3, tvBot5Veh3, tvBot5Comodin31, tvBot5Comodin32,
                tvJugComodin31, tvJugComodin32, relBot5Veh3, relJugVeh3, tbRowJugVeh, tbRow5BotVeh));
        ivBot5Veh4.setOnDragListener(new ChoiceDragListener(tvJugVeh4, tvBot5Veh4, tvBot5Comodin41, tvBot5Comodin42,
                tvJugComodin41, tvJugComodin42, relBot5Veh4, relJugVeh4, tbRowJugVeh, tbRow5BotVeh));

        // Permitimos que se pueda clickar sobre los vehículos del BOT para cuando pueda ser intercambiable.
        ivBotVeh1.setOnClickListener(this);
        ivBotVeh2.setOnClickListener(this);
        ivBotVeh3.setOnClickListener(this);
        ivBotVeh4.setOnClickListener(this);
        ivBot2Veh1.setOnClickListener(this);
        ivBot2Veh2.setOnClickListener(this);
        ivBot2Veh3.setOnClickListener(this);
        ivBot2Veh4.setOnClickListener(this);
        ivBot3Veh1.setOnClickListener(this);
        ivBot3Veh2.setOnClickListener(this);
        ivBot3Veh3.setOnClickListener(this);
        ivBot3Veh4.setOnClickListener(this);
        ivBot4Veh1.setOnClickListener(this);
        ivBot4Veh2.setOnClickListener(this);
        ivBot4Veh3.setOnClickListener(this);
        ivBot4Veh4.setOnClickListener(this);
        ivBot5Veh1.setOnClickListener(this);
        ivBot5Veh2.setOnClickListener(this);
        ivBot5Veh3.setOnClickListener(this);
        ivBot5Veh4.setOnClickListener(this);

        // Permitimos que se pueda clickar sobre las imágenes de descartar cartas.
        ivDejarCartas1.setOnClickListener(this);
        ivDejarCartas2.setOnClickListener(this);
        ivDejarCartas3.setOnClickListener(this);

        // Creamos el adaptador del Spinner y le indicamos a cada opción del Spinnerlas propiedades que tendrá
        // en relación a su apariencia.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_spinner_options, numOptions) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                Typeface external_typeface_menuMain = Typeface.createFromAsset(getAssets(),
                        "fonts/NuevoDisco.ttf");

                ((TextView) v).setText("");
                ((TextView) v).setTypeface(external_typeface_menuMain);
                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setGravity(Gravity.CENTER);
                ((TextView) v).setBackground(ContextCompat.getDrawable(getApplicationContext(), idOptions[position]));

                return v;
            }

            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {

                View v =super.getDropDownView(position, convertView, parent);

                Typeface external_typeface_menuMain = Typeface.createFromAsset(getAssets(),
                        "fonts/NuevoDisco.ttf");

                ((TextView) v).setText("");
                ((TextView) v).setTypeface(external_typeface_menuMain);
                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setGravity(Gravity.CENTER);
                ((TextView) v).setBackground(ContextCompat.getDrawable(getApplicationContext(), idOptions[position]));

                return v;
            }


        };

        // Asignamos el adapatador a la variable que contiene el Spinner.
        spinnerOpciones.setAdapter(adapter);

        // Indicamos que por cada vez que se clickeé sobre alguna de las opciones se reproduzca o no la música del juego y se
        // visualice o no el tutorial del juego.
        spinnerOpciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0)
                {
                    if (logoInstruccionesPulsado)
                    {
                        scrollContenidoInstrucciones.setVisibility(View.GONE);
                        logoInstruccionesPulsado = false;
                        view.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.logo_instrucciones_pulsado));
                    }
                    else
                    {
                        scrollContenidoInstrucciones.setVisibility(View.VISIBLE);
                        logoInstruccionesPulsado = true;
                        view.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.logo_instrucciones));
                    }
                }
                else
                {
                    if (volumenJuego)
                    {
                        musicaJuego.pause();
                        volumenJuego = false;
                        view.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.volume));
                    }
                    else
                    {
                        musicaJuego.start();
                        volumenJuego = true;
                        view.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.volume_no));
                    }
                }
                //numJugadores = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Seleccionamos la primera opción de manera predeterminada.
        spinnerOpciones.setSelection(0);
    }

    @Override
    public void onClick(final View view) {

        if (view.equals(ivDejarCartas1))
        {
            // Cuando pulsamos una carta de descartar lo informamos en el Toolbar y reproducimos su sonido correspondiente.
            tbRowCartas.setBackgroundColor(Color.TRANSPARENT);
            tvToolbar.setText(getResources().getString(R.string.tvToolbarCartaNueva));
            funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.pasarcarta, sonidoJuego);

            // Determinamos que a partir de ahora que las cartas no sean clickables.
            ivJugCarta1.setClickable(false);
            ivJugCarta2.setClickable(false);
            ivJugCarta3.setClickable(false);
            ivDejarCartas1.setClickable(false);
            ivDejarCartas2.setClickable(false);
            ivDejarCartas3.setClickable(false);

            final AccionesBot accionesBot = AccionesBot.getInstance();

            // Llamamos a la función que controla el inventario de los vehículos y sus estados.
            funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, getApplicationContext(), musicaJuego);
            for (int i = 1; i < numeroJugadores; i++)
            {
                funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaJuego);
            }

            // Si el Jugador no se ha proclamado campeón seguimos con los siguientes turnos.
            if (!funcionesGenerales.jugEsCampeon(accionesBot, ivGifVictoria, getApplicationContext(), musicaJuego))
            {
                // Sacamos una nueva carta que sustituye a la carta echada o descartada.
                tvJugCarta1.setText(barajaCartas[funcionesJugador.sacarCarta()]);
                funcionesGenerales.determinarImagenCasillaCartas(tvCartasJugadores, ivCartasJugadores);

                // Llamamos a un Timer que es el controlará el tiempo que deberá transcurrir entre turnos.
                final Timer timer = new Timer();
                final int contador = 0;
                TimerTask timerTask = new TimerTask() {

                    // Contador que controlará la posición del OrdenTurnos en la que nos encontramos.
                    int cont = contador;

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Si la posición del OrdenTurno no apunta a la posición del jugador entramos.
                                if (cont < ordenTurno2.size())
                                {
                                    for (int i = 0; i < tbRowVehs.length; i++)
                                    {
                                        if (i == ordenTurno2.get(cont))
                                        {
                                            // Resaltamos los layout del Jugador que tenga su turno en el momento presente.
                                            relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                            relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                            relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                            relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                        }
                                        else
                                        {
                                            relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                            relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                            relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                            relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                            relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                            relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                            relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                            relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                        }
                                    }

                                    funcionesBOT.accionBot(tvCartasJugadores, ivCartasJugadores, tvVehiculosJugadores, tvMensajeVictoria,
                                            numTirada, barajaCartas, getApplicationContext(), relVehiculosJugadores,
                                            ordenTurno2, ordenTurno2.get(cont), view, musicaJuego);

                                    // Si el BOT que aacaba de lanzar su carta se convierte en campeón se cancela el timpo de turnos.
                                    if (funcionesGenerales.botEsCampeon(accionesBot, ordenTurno2.get(cont) - 1, filter, getApplicationContext(), musicaJuego))
                                    {
                                        timer.cancel();
                                    }

                                    cont += 1;
                                }
                                else
                                {
                                    for (int i = 0; i < tbRowVehs.length; i++)
                                    {
                                        relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                        relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                        relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                        relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);
                                    }

                                    // Si es turno del JUGADOR permitimos que las cartas y el descarte de cartas pueda ser clickable.
                                    ivJugCarta1.setClickable(true);
                                    ivJugCarta2.setClickable(true);
                                    ivJugCarta3.setClickable(true);
                                    ivDejarCartas1.setClickable(true);
                                    ivDejarCartas2.setClickable(true);
                                    ivDejarCartas3.setClickable(true);

                                    // Reproducimos el sonido para cuando sea el turno del JUGADOR y lo informamos en el Toolbar.
                                    funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.turno, sonidoJuego);
                                    tvToolbar.setText(getResources().getString(R.string.tvToolbarTuTurno));
                                    tbRowCartas.setBackgroundColor(Color.parseColor("#70FFFFFF"));

                                    timer.cancel();
                                }
                            }
                        });
                    }
                };
                // Decidimos que pasen 5000 milisegundos entre turnos.
                timer.schedule(timerTask, 5000, 5000);
            }
        }
        else if (view.equals(ivDejarCartas2))
        {
            // Cuando pulsamos una carta de descartar lo informamos en el Toolbar y reproducimos su sonido correspondiente.
            tbRowCartas.setBackgroundColor(Color.TRANSPARENT);
            tvToolbar.setText(getResources().getString(R.string.tvToolbarCartaNueva));
            funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.pasarcarta, sonidoJuego);

            // Determinamos que a partir de ahora que las cartas no sean clickables.
            ivJugCarta1.setClickable(false);
            ivJugCarta2.setClickable(false);
            ivJugCarta3.setClickable(false);
            ivDejarCartas1.setClickable(false);
            ivDejarCartas2.setClickable(false);
            ivDejarCartas3.setClickable(false);

            // Llamamos a la función que controla el inventario de los vehículos y sus estados.
            final AccionesBot accionesBot = AccionesBot.getInstance();
            funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, getApplicationContext(), musicaJuego);
            for (int i = 1; i < numeroJugadores; i++)
            {
                funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaJuego);
            }

            if (!funcionesGenerales.jugEsCampeon(accionesBot, ivGifVictoria, getApplicationContext(), musicaJuego))
            {
                tvJugCarta2.setText(barajaCartas[funcionesJugador.sacarCarta()]);
                funcionesGenerales.determinarImagenCasillaCartas(tvCartasJugadores, ivCartasJugadores);

                // Llamamos a un Timer que es el controlará el tiempo que deberá transcurrir entre turnos.
                final Timer timer = new Timer();
                final int contador = 0;
                TimerTask timerTask = new TimerTask() {

                    // Contador que controlará la posición del OrdenTurnos en la que nos encontramos.
                    int cont = contador;

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Si la posición del OrdenTurno no apunta a la posición del jugador entramos.
                                if (cont < ordenTurno2.size())
                                {
                                    for (int i = 0; i < tbRowVehs.length; i++)
                                    {
                                        if (i == ordenTurno2.get(cont))
                                        {
                                            // Resaltamos los layout del Jugador que tenga su turno en el momento presente.
                                            relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                            relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                            relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                            relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                        }
                                        else
                                        {
                                            relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                            relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                            relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                            relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                            relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                            relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                            relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                            relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                        }
                                    }

                                    //que hacer despues de 10 segundos
                                    funcionesBOT.accionBot(tvCartasJugadores, ivCartasJugadores, tvVehiculosJugadores, tvMensajeVictoria,
                                            numTirada, barajaCartas, getApplicationContext(), relVehiculosJugadores,
                                            ordenTurno2, ordenTurno2.get(cont), view, musicaJuego);

                                    // Si el BOT que aacaba de lanzar su carta se convierte en campeón se cancela el timpo de turnos.
                                    if (funcionesGenerales.botEsCampeon(accionesBot, ordenTurno2.get(cont) - 1, filter, getApplicationContext(), musicaJuego))
                                    {
                                        timer.cancel();
                                    }

                                    System.out.println("Estu -> " + cont);
                                    cont += 1;
                                }
                                else
                                {
                                    for (int i = 0; i < tbRowVehs.length; i++)
                                    {
                                        relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                        relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                        relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                        relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                        relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                        relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                        relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                        relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                    }

                                    // Si es turno del JUGADOR permitimos que las cartas y el descarte de cartas pueda ser clickable.
                                    ivJugCarta1.setClickable(true);
                                    ivJugCarta2.setClickable(true);
                                    ivJugCarta3.setClickable(true);
                                    ivDejarCartas1.setClickable(true);
                                    ivDejarCartas2.setClickable(true);
                                    ivDejarCartas3.setClickable(true);

                                    // Reproducimos el sonido para cuando sea el turno del JUGADOR y lo informamos en el Toolbar.
                                    funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.turno, sonidoJuego);
                                    tvToolbar.setText(getResources().getString(R.string.tvToolbarTuTurno));
                                    tbRowCartas.setBackgroundColor(Color.parseColor("#70FFFFFF"));

                                    timer.cancel();
                                }
                            }
                        });
                    }
                };
                // Decidimos que pasen 5000 milisegundos entre turnos.
                timer.schedule(timerTask, 5000, 5000);
            }
        }
        else if (view.equals(ivDejarCartas3))
        {
            // Cuando pulsamos una carta de descartar lo informamos en el Toolbar y reproducimos su sonido correspondiente.
            tbRowCartas.setBackgroundColor(Color.TRANSPARENT);
            tvToolbar.setText(getResources().getString(R.string.tvToolbarCartaNueva));
            funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.pasarcarta, sonidoJuego);

            // Determinamos que a partir de ahora que las cartas no sean clickables.
            ivJugCarta1.setClickable(false);
            ivJugCarta2.setClickable(false);
            ivJugCarta3.setClickable(false);
            ivDejarCartas1.setClickable(false);
            ivDejarCartas2.setClickable(false);
            ivDejarCartas3.setClickable(false);

            // Llamamos a la función que controla el inventario de los vehículos y sus estados.
            final AccionesBot accionesBot = AccionesBot.getInstance();
            funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, getApplicationContext(), musicaJuego);
            for (int i = 1; i < numeroJugadores; i++)
            {
                funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaJuego);
            }

            if (!funcionesGenerales.jugEsCampeon(accionesBot, ivGifVictoria, getApplicationContext(), musicaJuego))
            {
                tvJugCarta3.setText(barajaCartas[funcionesJugador.sacarCarta()]);
                funcionesGenerales.determinarImagenCasillaCartas(tvCartasJugadores, ivCartasJugadores);

                // Llamamos a un Timer que es el controlará el tiempo que deberá transcurrir entre turnos.
                final Timer timer = new Timer();
                final int contador = 0;
                TimerTask timerTask = new TimerTask() {

                    // Contador que controlará la posición del OrdenTurnos en la que nos encontramos.
                    int cont = contador;

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Si la posición del OrdenTurno no apunta a la posición del jugador entramos.
                                if (cont < ordenTurno2.size())
                                {
                                    for (int i = 0; i < tbRowVehs.length; i++)
                                    {
                                        if (i == ordenTurno2.get(cont))
                                        {
                                            // Resaltamos los layout del Jugador que tenga su turno en el momento presente.
                                            relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                            relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                            relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                            relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                        }
                                        else
                                        {
                                            relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                            relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                            relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                            relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                            relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                            relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                            relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                            relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                        }
                                    }

                                    //que hacer despues de 10 segundos
                                    funcionesBOT.accionBot(tvCartasJugadores, ivCartasJugadores, tvVehiculosJugadores, tvMensajeVictoria,
                                            numTirada, barajaCartas, getApplicationContext(), relVehiculosJugadores,
                                            ordenTurno2, ordenTurno2.get(cont), view, musicaJuego);

                                    // Si el BOT que aacaba de lanzar su carta se convierte en campeón se cancela el timpo de turnos.
                                    if (funcionesGenerales.botEsCampeon(accionesBot, ordenTurno2.get(cont) - 1, filter, getApplicationContext(), musicaJuego))
                                    {
                                        timer.cancel();
                                    }

                                    System.out.println("Estu -> " + cont);
                                    cont += 1;
                                }
                                else
                                {
                                    for (int i = 0; i < tbRowVehs.length; i++)
                                    {
                                        relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                        relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                        relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                        relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                        relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                        relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                        relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                        relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                    }

                                    // Si es turno del JUGADOR permitimos que las cartas y el descarte de cartas pueda ser clickable.
                                    ivJugCarta1.setClickable(true);
                                    ivJugCarta2.setClickable(true);
                                    ivJugCarta3.setClickable(true);
                                    ivDejarCartas1.setClickable(true);
                                    ivDejarCartas2.setClickable(true);
                                    ivDejarCartas3.setClickable(true);

                                    // Reproducimos el sonido para cuando sea el turno del JUGADOR y lo informamos en el Toolbar.
                                    funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.turno, sonidoJuego);
                                    tvToolbar.setText(getResources().getString(R.string.tvToolbarTuTurno));
                                    tbRowCartas.setBackgroundColor(Color.parseColor("#70FFFFFF"));

                                    timer.cancel();
                                }
                            }
                        });
                    }
                };
                // Decidimos que pasen 5000 milisegundos entre turnos.
                timer.schedule(timerTask, 5000, 5000);
            }
        }
        else
        {
            // Entramos en esta condición en caso de que el JUGADOR haya clickado sobre eun BOT rival en la acción de
            // intercambio.
            tbRowCartas.setBackgroundColor(Color.TRANSPARENT);
            funcionesGenerales.determinarImagenCasillaCartas(tvCartasJugadores, ivCartasJugadores);

            // Realizamos un control de inventario antes de realizar el intercambio.
            final AccionesBot accionesBot = AccionesBot.getInstance();
            funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, getApplicationContext(), musicaJuego);
            for (int i = 1; i < numeroJugadores; i++)
            {
                funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaJuego);
            }

            for (int i = 4; i < ivVehiculosJugadores.length; i++)
            {
                if (view.equals(ivVehiculosJugadores[i]))
                {
                    // Entramos en esta condicción en caso de que el layout esté resaltado como intercambiable.
                    if (relVehiculosJugadores[i].getAlpha() < 1.0)
                    {
                        for (int e = 0; e < 4; e++)
                        {
                            if (tvVehiculosJugadores[e].getText().equals(funcionesJugador.tvVehJugAIntercambiar))
                            {
                                // Determinamos porque número de BOT vamos a intercambia rnuestro vehículo para que luego aparezca su nombre en
                                // el toolbar.
                                String idTbRowBotVeh = ivVehiculosJugadores[i].getResources().getResourceEntryName(view.getId());
                                int numBot = Integer.parseInt(idTbRowBotVeh.substring(5, 6));
                                tvToolbar.setText(getResources().getString(R.string.tvToolbarIntercambioUnidadJug1) + tvVehiculosJugadores[e].getText()
                                        + getResources().getString(R.string.tvToolbarIntercambioUnidadJug2) + tvVehiculosJugadores[i].getText()
                                        + getResources().getString(R.string.tvToolbarIntercambioUnidad3) + ordenNombresEjercitos.get(numBot - 1));

                                // Realizamos el intercambio entre los textview de vehículos y estados correspondientes.
                                tvVehiculosJugadores[e].setText(tvVehiculosJugadores[i].getText());
                                tvComodinesJugadores[e * 2].setText(tvComodinesJugadores[i * 2 + 0].getText());
                                tvComodinesJugadores[e * 2 + 1].setText(tvComodinesJugadores[i * 2 + 1].getText());

                                tvVehiculosJugadores[i].setText(funcionesJugador.tvVehJugAIntercambiar);
                                tvComodinesJugadores[i * 2 + 0].setText(funcionesJugador.tvCom1JugAIntercambiar);
                                tvComodinesJugadores[i * 2 + 1].setText(funcionesJugador.tvCom2JugAIntercambiar);

                                // Eliminamos el resaltado de los vehículos intercambiables una vez que la acción ha sido
                                // realizada y volvemos a asignarle como no clickable.
                                tbRowCartas.setBackgroundColor(Color.TRANSPARENT);
                                for (int o = 0; o < ivVehiculosJugadores.length; o++)
                                {
                                    relVehiculosJugadores[o].setAlpha(1f);
                                    relVehiculosJugadores[o].setBackgroundColor(Color.TRANSPARENT);
                                    ivVehiculosJugadores[o].setAlpha(1f);
                                    ivVehiculosJugadores[o].setBackgroundColor(Color.TRANSPARENT);
                                }

                                for (int u = 0; u < relVehiculosJugadores.length; u++)
                                {
                                    relVehiculosJugadores[u].setClickable(false);
                                    ivDejarCartas1.setClickable(false);
                                    ivDejarCartas2.setClickable(false);
                                    ivDejarCartas3.setClickable(false);

                                    relVehiculosJugadores[u].setAlpha(1f);
                                    relVehiculosJugadores[u].setBackgroundColor(Color.TRANSPARENT);
                                }

                                // Reproducimos el sonido de la acción de intercambio.
                                funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.intercambio, sonidoJuego);

                                break;
                            }
                        }

                        // Una vez realizada la acción volvemos a revisar el inventario de vehículos mostrados y sus
                        // estados correspondientes.
                        for (int a = 1; a < numeroJugadores; a++)
                        {
                            funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, a, tvMensajeVictoria, musicaJuego);
                        }
                        funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, getApplicationContext(), musicaJuego);
                        funcionesGenerales.determinarImagenCasillaCartas(tvCartasJugadores, ivCartasJugadores);
                        funcionesGenerales.determinarImagenCasillaVehiculos(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);
                        funcionesGenerales.determinarImagenCasillaVehiculosBot(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);

                        if (!funcionesGenerales.jugEsCampeon(accionesBot, ivGifVictoria, getApplicationContext(), musicaJuego))
                        {
                            // Llamamos a un Timer que es el controlará el tiempo que deberá transcurrir entre turnos.
                            final Timer timer = new Timer();
                            final int contador = 0;
                            TimerTask timerTask = new TimerTask() {

                                // Contador que controlará la posición del OrdenTurnos en la que nos encontramos.
                                int cont = contador;

                                @Override
                                public void run() {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            // Si la posición del OrdenTurno no apunta a la posición del jugador entramos.
                                            if (cont < ordenTurno2.size())
                                            {
                                                for (int i = 0; i < tbRowVehs.length; i++)
                                                {
                                                    if (i == ordenTurno2.get(cont))
                                                    {
                                                        // Resaltamos los layout del Jugador que tenga su turno en el momento presente.
                                                        relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                        relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                        relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                        relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                    }
                                                    else
                                                    {
                                                        relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                                        relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                                        relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                                        relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                                        relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                                        relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                                        relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                                        relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                                    }
                                                }

                                                //que hacer despues de 10 segundos
                                                funcionesBOT.accionBot(tvCartasJugadores, ivCartasJugadores, tvVehiculosJugadores, tvMensajeVictoria,
                                                        numTirada, barajaCartas, getApplicationContext(), relVehiculosJugadores,
                                                        ordenTurno2, ordenTurno2.get(cont), view, musicaJuego);

                                                // Si el BOT que aacaba de lanzar su carta se convierte en campeón se cancela el timpo de turnos.
                                                if (funcionesGenerales.botEsCampeon(accionesBot, ordenTurno2.get(cont) - 1, filter, getApplicationContext(), musicaJuego))
                                                {
                                                    timer.cancel();
                                                }

                                                cont += 1;
                                            }
                                            else
                                            {
                                                for (int i = 0; i < tbRowVehs.length; i++)
                                                {
                                                    relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                                    relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                                    relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                                    relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                                    relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                                    relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                                    relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                                    relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                                }

                                                // Si es turno del JUGADOR permitimos que las cartas y el descarte de cartas pueda ser clickable.
                                                ivJugCarta1.setClickable(true);
                                                ivJugCarta2.setClickable(true);
                                                ivJugCarta3.setClickable(true);
                                                ivDejarCartas1.setClickable(true);
                                                ivDejarCartas2.setClickable(true);
                                                ivDejarCartas3.setClickable(true);

                                                // Reproducimos el sonido para cuando sea el turno del JUGADOR y lo informamos en el Toolbar.
                                                funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.turno, sonidoJuego);
                                                tvToolbar.setText(getResources().getString(R.string.tvToolbarTuTurno));
                                                tbRowCartas.setBackgroundColor(Color.parseColor("#70FFFFFF"));

                                                timer.cancel();
                                            }
                                        }
                                    });
                                }
                            };
                            // Decidimos que pasen 5000 milisegundos entre turnos.
                            timer.schedule(timerTask, 5000, 5000);
                        }
                    }
                }
            }
        }
    }

    private final class ChoiceTouchListener implements View.OnTouchListener{

        TextView tvCartaPulsada;
        TextView tvCartaNoPulsada1;
        TextView tvCartaNoPulsada2;
        String idTextViewCarta;

        public ChoiceTouchListener(TextView tvPulsada, TextView tvNoPulsada1, TextView tvNoPulsada2, String idTvView)
        {
            this.tvCartaPulsada = tvPulsada;
            this.tvCartaNoPulsada1 = tvNoPulsada1;
            this.tvCartaNoPulsada2 = tvNoPulsada2;
            this.idTextViewCarta = idTvView;
        }

        // Método que será llamado cuando pulsamos sobre una carta.
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if ((motionEvent.getAction() == MotionEvent.ACTION_DOWN) && ivJugCarta1.isClickable())
            {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.pulsar, sonidoJuego);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(data, shadowBuilder, null, 0);
                } else {
                    view.startDrag(data, shadowBuilder, null, 0);
                }

                tbRowCartas.setBackgroundColor(Color.TRANSPARENT);

                // Recogemos el valor de la carta que acabamos de pulsar y su ID
                txtCarta = (String) tvCartaPulsada.getText();
                idTvCarta = tvCartaPulsada.getResources().getResourceEntryName(view.getId());

                // Llamamos a la función que controla el inventario de los vehículos y sus estados.
                AccionesBot accionesBot = AccionesBot.getInstance();
                funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, getApplicationContext(), musicaJuego);
                for (int i = 1; i < numeroJugadores; i++)
                {
                    funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaJuego);
                }

                // Llamamos a las funciones que recogen la acción de pulsado dependiendo del tipo de carta. Si la acción
                // relacionada con la carta pulsada se puede realizar se resaltarán los vehículos rivales sobre los que se
                // puede soltar la carta.
                funcionesJugador.pulseReunirVehiculo(txtCarta, (String) tvJugVeh1.getText(), relJugVeh1, accionesBot);
                funcionesJugador.pulseReunirVehiculo(txtCarta, (String) tvJugVeh2.getText(), relJugVeh2, accionesBot);
                funcionesJugador.pulseReunirVehiculo(txtCarta, (String) tvJugVeh3.getText(), relJugVeh3, accionesBot);
                funcionesJugador.pulseReunirVehiculo(txtCarta, (String) tvJugVeh4.getText(), relJugVeh4, accionesBot);

                funcionesJugador.pulseLanzarMedicina(txtCarta);
                funcionesJugador.pulseLanzarArma(txtCarta);

                funcionesJugador.pulseDejarCartas(txtCarta);

                for (int i = 1; i < numeroJugadores; i++)
                {
                    funcionesJugador.pulseIntercambiarEquipo(txtCarta, tbRowBotVeh, relVehiculosJugadores, view);
                    funcionesJugador.pulseIntercambiarEquipo(txtCarta, tbRow2BotVeh, relVehiculosJugadores, view);
                    funcionesJugador.pulseIntercambiarEquipo(txtCarta, tbRow3BotVeh, relVehiculosJugadores, view);
                    funcionesJugador.pulseIntercambiarEquipo(txtCarta, tbRow4BotVeh, relVehiculosJugadores, view);
                    funcionesJugador.pulseIntercambiarEquipo(txtCarta, tbRow5BotVeh, relVehiculosJugadores, view);

                    funcionesJugador.pulseRoboVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 0].getText(), relVehiculosJugadores[(i) * 4 + 0],
                            accionesBot, i);
                    funcionesJugador.pulseRoboVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 1].getText(), relVehiculosJugadores[(i) * 4 + 1],
                            accionesBot, i);
                    funcionesJugador.pulseRoboVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 2].getText(), relVehiculosJugadores[(i) * 4 + 2],
                            accionesBot, i);
                    funcionesJugador.pulseRoboVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 3].getText(), relVehiculosJugadores[(i) * 4 + 3],
                            accionesBot, i);

                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 0].getText(), (String)  tvVehiculosJugadores[0].getText(),
                            relJugVeh1, relVehiculosJugadores[(i) * 4 + 0], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 0].getText(), (String)  tvVehiculosJugadores[1].getText(),
                            relJugVeh2, relVehiculosJugadores[(i) * 4 + 0], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 0].getText(), (String)  tvVehiculosJugadores[2].getText(),
                            relJugVeh3, relVehiculosJugadores[(i) * 4 + 0], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 0].getText(), (String)  tvVehiculosJugadores[3].getText(),
                            relJugVeh4, relVehiculosJugadores[(i) * 4 + 0], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);

                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 1].getText(), (String)  tvVehiculosJugadores[0].getText(),
                            relJugVeh1, relVehiculosJugadores[(i) * 4 + 1], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 1].getText(), (String)  tvVehiculosJugadores[1].getText(),
                            relJugVeh2, relVehiculosJugadores[(i) * 4 + 1], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 1].getText(), (String)  tvVehiculosJugadores[2].getText(),
                            relJugVeh3, relVehiculosJugadores[(i) * 4 + 1], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 1].getText(), (String)  tvVehiculosJugadores[3].getText(),
                            relJugVeh4, relVehiculosJugadores[(i) * 4 + 1], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);

                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 2].getText(), (String)  tvVehiculosJugadores[0].getText(),
                            relJugVeh1, relVehiculosJugadores[(i) * 4 + 2], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 2].getText(), (String)  tvVehiculosJugadores[1].getText(),
                            relJugVeh2, relVehiculosJugadores[(i) * 4 + 2], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 2].getText(), (String)  tvVehiculosJugadores[2].getText(),
                            relJugVeh3, relVehiculosJugadores[(i) * 4 + 2], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 2].getText(), (String)  tvVehiculosJugadores[3].getText(),
                            relJugVeh4, relVehiculosJugadores[(i) * 4 + 2], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);

                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 3].getText(), (String)  tvVehiculosJugadores[0].getText(),
                            relJugVeh1, relVehiculosJugadores[(i) * 4 + 3], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 3].getText(), (String)  tvVehiculosJugadores[1].getText(),
                            relJugVeh2, relVehiculosJugadores[(i) * 4 + 3], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 3].getText(), (String)  tvVehiculosJugadores[2].getText(),
                            relJugVeh3, relVehiculosJugadores[(i) * 4 + 3], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                    funcionesJugador.pulseIntercambiarVehiculo(
                            txtCarta, (String)  tvVehiculosJugadores[(i) * 4 + 3].getText(), (String)  tvVehiculosJugadores[3].getText(),
                            relJugVeh4, relVehiculosJugadores[(i) * 4 + 3], getApplicationContext(), accionesBot, i, i,
                            ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, false);
                }

                funcionesJugador.pulseExpulsarArmas(txtCarta, ivDejarCartas1, ivDejarCartas2, ivDejarCartas3);
            }

            return false;
        }
    }

    private class ChoiceDragListener implements View.OnDragListener
    {
        AccionesBot accionesBot = AccionesBot.getInstance();

        TextView textViewJugVeh;
        TextView textViewBotVeh;
        TextView textViewBotCom1;
        TextView textViewBotCom2;
        TextView textViewJugCom1;
        TextView textViewJugCom2;
        RelativeLayout relativeBotVeh;
        RelativeLayout relativeJugVeh;
        TableRow tableRowJugVeh;
        TableRow tableRowBotVeh;

        public ChoiceDragListener(TextView tvJugVeh, TextView tvBotVeh, TextView tvBotCom1, TextView tvBotCom2,
                                  TextView tvJugCom1, TextView tvJugCom2, RelativeLayout relBotVeh,
                                  RelativeLayout relJugVeh, TableRow tbRowJugVeh, TableRow tbRowBotVeh)
        {
            this.textViewJugVeh = tvJugVeh;
            this.textViewBotVeh = tvBotVeh;
            this.textViewBotCom1 = tvBotCom1;
            this.textViewBotCom2 = tvBotCom2;
            this.textViewJugCom1 = tvJugCom1;
            this.textViewJugCom2 = tvJugCom2;
            this.relativeBotVeh = relBotVeh;
            this.relativeJugVeh = relJugVeh;
            this.tableRowJugVeh = tbRowJugVeh;
            this.tableRowBotVeh = tbRowBotVeh;
        }

        @Override
        public boolean onDrag(final View view, DragEvent dragEvent) {

            switch(dragEvent.getAction())
            {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                case DragEvent.ACTION_DROP:

                    // Llamamos a la función que controla el inventario de los vehículos y sus estados.
                    funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, getApplicationContext(), musicaJuego);
                    for (int i = 1; i < numeroJugadores; i++)
                    {
                        funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaJuego);
                    }

                    // Entramos cuando se suelte una de las cartas pulsadas.
                    switch (idTvCarta)
                    {
                        case "ivJugCarta1":

                            if (funcionesJugador.dejarCarta(tvJugCarta1, tvJugCarta2, tvJugCarta3,
                                    numTirada, barajaCartas, relativeJugVeh, textViewJugVeh, textViewBotVeh,
                                    textViewJugCom1, textViewJugCom2, textViewBotCom1, textViewBotCom2, relativeBotVeh,
                                    tableRowJugVeh, tableRowBotVeh, tvMensajeVictoria, accionesBot, view,
                                    ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, tableJugCartas, tbRowVehs, linearJuego,
                                    filter))
                            {
                                ivJugCarta1.setClickable(false);
                                ivJugCarta2.setClickable(false);
                                ivJugCarta3.setClickable(false);
                                ivDejarCartas1.setClickable(false);
                                ivDejarCartas2.setClickable(false);
                                ivDejarCartas3.setClickable(false);

                                // Llamamos a la función que controla el inventario de los vehículos y sus estados.
                                for (int i = 1; i < numeroJugadores; i++)
                                {
                                    funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaJuego);
                                }
                                funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, getApplicationContext(), musicaJuego);
                                funcionesGenerales.determinarImagenCasillaCartas(tvCartasJugadores, ivCartasJugadores);
                                funcionesGenerales.determinarImagenCasillaVehiculos(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);
                                funcionesGenerales.determinarImagenCasillaVehiculosBot(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);

                                if (!txtCarta.equals("Cambiar Vehículo"))
                                {
                                    if (!funcionesGenerales.jugEsCampeon(accionesBot, ivGifVictoria, getApplicationContext(), musicaJuego))
                                    {
                                        // Llamamos a un Timer que es el controlará el tiempo que deberá transcurrir entre turnos.
                                        final Timer timer = new Timer();
                                        final int contador = 0;
                                        TimerTask timerTask = new TimerTask() {

                                            // Contador que controlará la posición del OrdenTurnos en la que nos encontramos.
                                            int cont = contador;

                                            @Override
                                            public void run() {

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        // Si la posición del OrdenTurno no apunta a la posición del jugador entramos.
                                                        if (cont < ordenTurno2.size())
                                                        {
                                                            for (int i = 0; i < tbRowVehs.length; i++)
                                                            {
                                                                if (i == ordenTurno2.get(cont))
                                                                {
                                                                    // Resaltamos los layout del Jugador que tenga su turno en el momento presente.
                                                                    relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                    relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                    relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                    relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                }
                                                                else
                                                                {
                                                                    relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                                                    relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                                                    relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                                                    relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                                                    relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                                                    relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                                                    relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                                                    relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                                                }
                                                            }
                                                            //que hacer despues de 10 segundos
                                                            funcionesBOT.accionBot(tvCartasJugadores, ivCartasJugadores, tvVehiculosJugadores, tvMensajeVictoria,
                                                                    numTirada, barajaCartas, getApplicationContext(), relVehiculosJugadores,
                                                                    ordenTurno2, ordenTurno2.get(cont), view, musicaJuego);

                                                            funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, ordenTurno2.get(cont), tvMensajeVictoria, musicaJuego);
                                                            funcionesGenerales.determinarImagenCasillaVehiculosBot(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);

                                                            // Si el BOT que aacaba de lanzar su carta se convierte en campeón se cancela el timpo de turnos.
                                                            if (funcionesGenerales.botEsCampeon(accionesBot, ordenTurno2.get(cont) - 1, filter, getApplicationContext(), musicaJuego))
                                                            {
                                                                timer.cancel();
                                                            }

                                                            System.out.println("Estu -> " + cont);
                                                            cont += 1;
                                                        }
                                                        else
                                                        {
                                                            for (int i = 0; i < tbRowVehs.length; i++)
                                                            {
                                                                relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                                                relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                                                relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                                                relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                                                relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                                                relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                                                relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                                                relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                                            }

                                                            // Si es turno del JUGADOR permitimos que las cartas y el descarte de cartas pueda ser clickable.
                                                            ivJugCarta1.setClickable(true);
                                                            ivJugCarta2.setClickable(true);
                                                            ivJugCarta3.setClickable(true);
                                                            ivDejarCartas1.setClickable(true);
                                                            ivDejarCartas2.setClickable(true);
                                                            ivDejarCartas3.setClickable(true);

                                                            // Reproducimos el sonido para cuando sea el turno del JUGADOR y lo informamos en el Toolbar.
                                                            funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.turno, sonidoJuego);
                                                            tvToolbar.setText(getResources().getString(R.string.tvToolbarTuTurno));
                                                            tbRowCartas.setBackgroundColor(Color.parseColor("#70FFFFFF"));

                                                            timer.cancel();
                                                        }
                                                    }
                                                });
                                            }
                                        };
                                        // Decidimos que pasen 5000 milisegundos entre turnos.
                                        timer.schedule(timerTask, 5000, 5000);
                                    }
                                }
                            }
                            break;

                        case "ivJugCarta2":

                            if (funcionesJugador.dejarCarta(tvJugCarta2, tvJugCarta1, tvJugCarta3,
                                    numTirada, barajaCartas, relativeJugVeh, textViewJugVeh, textViewBotVeh,
                                    textViewJugCom1, textViewJugCom2, textViewBotCom1, textViewBotCom2, relativeBotVeh,
                                    tableRowJugVeh, tableRowBotVeh, tvMensajeVictoria, accionesBot, view,
                                    ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, tableJugCartas, tbRowVehs, linearJuego,
                                    filter))
                            {
                                System.out.println("Jota2");

                                ivJugCarta1.setClickable(false);
                                ivJugCarta2.setClickable(false);
                                ivJugCarta3.setClickable(false);
                                ivDejarCartas1.setClickable(false);
                                ivDejarCartas2.setClickable(false);
                                ivDejarCartas3.setClickable(false);

                                // Llamamos a la función que controla el inventario de los vehículos y sus estados.
                                for (int i = 1; i < numeroJugadores; i++)
                                {
                                    funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaJuego);
                                }
                                funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, getApplicationContext(), musicaJuego);
                                funcionesGenerales.determinarImagenCasillaCartas(tvCartasJugadores, ivCartasJugadores);
                                funcionesGenerales.determinarImagenCasillaVehiculos(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);
                                funcionesGenerales.determinarImagenCasillaVehiculosBot(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);

                                if (!txtCarta.equals("Cambiar Vehículo"))
                                {
                                    if (!funcionesGenerales.jugEsCampeon(accionesBot, ivGifVictoria, getApplicationContext(), musicaJuego))
                                    {
                                        // Llamamos a un Timer que es el controlará el tiempo que deberá transcurrir entre turnos.
                                        final Timer timer = new Timer();
                                        final int contador = 0;
                                        TimerTask timerTask = new TimerTask() {

                                            // Contador que controlará la posición del OrdenTurnos en la que nos encontramos.
                                            int cont = contador;

                                            @Override
                                            public void run() {

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        // Si la posición del OrdenTurno no apunta a la posición del jugador entramos.
                                                        if (cont < ordenTurno2.size())
                                                        {
                                                            for (int i = 0; i < tbRowVehs.length; i++)
                                                            {
                                                                if (i == ordenTurno2.get(cont))
                                                                {
                                                                    relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                    relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                    relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                    relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                }
                                                                else
                                                                {
                                                                    relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                                                    relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                                                    relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                                                    relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                                                    relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                                                    relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                                                    relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                                                    relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                                                }
                                                            }

                                                            //que hacer despues de 10 segundos
                                                            funcionesBOT.accionBot(tvCartasJugadores, ivCartasJugadores, tvVehiculosJugadores, tvMensajeVictoria,
                                                                    numTirada, barajaCartas, getApplicationContext(), relVehiculosJugadores,
                                                                    ordenTurno2, ordenTurno2.get(cont), view, musicaJuego);

                                                            funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, ordenTurno2.get(cont), tvMensajeVictoria, musicaJuego);
                                                            funcionesGenerales.determinarImagenCasillaVehiculosBot(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);

                                                            // Si el BOT que aacaba de lanzar su carta se convierte en campeón se cancela el timpo de turnos.
                                                            if (funcionesGenerales.botEsCampeon(accionesBot, ordenTurno2.get(cont) - 1, filter, getApplicationContext(), musicaJuego))
                                                            {
                                                                timer.cancel();
                                                            }
                                                            cont += 1;
                                                        }
                                                        else
                                                        {
                                                            for (int i = 0; i < tbRowVehs.length; i++)
                                                            {
                                                                relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                                                relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                                                relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                                                relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                                                relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                                                relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                                                relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                                                relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                                            }

                                                            // Si es turno del JUGADOR permitimos que las cartas y el descarte de cartas pueda ser clickable.
                                                            ivJugCarta1.setClickable(true);
                                                            ivJugCarta2.setClickable(true);
                                                            ivJugCarta3.setClickable(true);
                                                            ivDejarCartas1.setClickable(true);
                                                            ivDejarCartas2.setClickable(true);
                                                            ivDejarCartas3.setClickable(true);

                                                            // Reproducimos el sonido para cuando sea el turno del JUGADOR y lo informamos en el Toolbar.
                                                            funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.turno, sonidoJuego);
                                                            tvToolbar.setText(getResources().getString(R.string.tvToolbarTuTurno));
                                                            tbRowCartas.setBackgroundColor(Color.parseColor("#70FFFFFF"));

                                                            timer.cancel();
                                                        }
                                                    }
                                                });
                                            }
                                        };
                                        // Decidimos que pasen 5000 milisegundos entre turnos.
                                        timer.schedule(timerTask, 5000, 5000);
                                    }
                                }
                            }
                            break;

                        case "ivJugCarta3":

                            if (funcionesJugador.dejarCarta(tvJugCarta3, tvJugCarta2, tvJugCarta1,
                                    numTirada, barajaCartas, relativeJugVeh, textViewJugVeh, textViewBotVeh,
                                    textViewJugCom1, textViewJugCom2, textViewBotCom1, textViewBotCom2, relativeBotVeh,
                                    tableRowJugVeh, tableRowBotVeh, tvMensajeVictoria, accionesBot, view,
                                    ivDejarCartas1, ivDejarCartas2, ivDejarCartas3, tableJugCartas, tbRowVehs, linearJuego,
                                    filter))
                            {
                                // Llamamos a la función que controla el inventario de los vehículos y sus estados.
                                for (int i = 1; i < numeroJugadores; i++)
                                {
                                    funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, i, tvMensajeVictoria, musicaJuego);
                                }
                                funcionesGenerales.controlVehiculosComodinesPuntos(accionesBot, tvMensajeVictoria, ivGifVictoria, getApplicationContext(), musicaJuego);
                                funcionesGenerales.determinarImagenCasillaCartas(tvCartasJugadores, ivCartasJugadores);
                                funcionesGenerales.determinarImagenCasillaVehiculos(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);
                                funcionesGenerales.determinarImagenCasillaVehiculosBot(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);

                                ivJugCarta1.setClickable(false);
                                ivJugCarta2.setClickable(false);
                                ivJugCarta3.setClickable(false);
                                ivDejarCartas1.setClickable(false);
                                ivDejarCartas2.setClickable(false);
                                ivDejarCartas3.setClickable(false);

                                if (!txtCarta.equals("Cambiar Vehículo"))
                                {
                                    if (!funcionesGenerales.jugEsCampeon(accionesBot, ivGifVictoria, getApplicationContext(), musicaJuego))
                                    {
                                        // Llamamos a un Timer que es el controlará el tiempo que deberá transcurrir entre turnos.
                                        final Timer timer = new Timer();
                                        final int contador = 0;
                                        TimerTask timerTask = new TimerTask() {

                                            // Contador que controlará la posición del OrdenTurnos en la que nos encontramos.
                                            int cont = contador;

                                            @Override
                                            public void run() {

                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        // Si la posición del OrdenTurno no apunta a la posición del jugador entramos.
                                                        if (cont < ordenTurno2.size())
                                                        {
                                                            for (int i = 0; i < tbRowVehs.length; i++)
                                                            {
                                                                if (i == ordenTurno2.get(cont))
                                                                {
                                                                    relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                    relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                    relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                    relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.parseColor("#70FFFFFF"));
                                                                }
                                                                else
                                                                {
                                                                    relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                                                    relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                                                    relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                                                    relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                                                    relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                                                    relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                                                    relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                                                    relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                                                }
                                                            }

                                                            //que hacer despues de 10 segundos
                                                            funcionesBOT.accionBot(tvCartasJugadores, ivCartasJugadores, tvVehiculosJugadores, tvMensajeVictoria,
                                                                    numTirada, barajaCartas, getApplicationContext(), relVehiculosJugadores,
                                                                    ordenTurno2, ordenTurno2.get(cont), view, musicaJuego);

                                                            funcionesGenerales.controlVehiculosComodinesPuntosBot(accionesBot, ordenTurno2.get(cont), tvMensajeVictoria, musicaJuego);
                                                            funcionesGenerales.determinarImagenCasillaVehiculosBot(tvVehiculosJugadores, ivVehiculosJugadores, accionesBot);

                                                            // Si el BOT que aacaba de lanzar su carta se convierte en campeón se cancela el timpo de turnos.
                                                            if (funcionesGenerales.botEsCampeon(accionesBot, ordenTurno2.get(cont) - 1, filter, getApplicationContext(), musicaJuego))
                                                            {
                                                                timer.cancel();
                                                            }
                                                            cont += 1;
                                                        }
                                                        else
                                                        {
                                                            for (int i = 0; i < tbRowVehs.length; i++)
                                                            {
                                                                relVehiculosJugadores[i * 4 + 0].setBackgroundColor(Color.TRANSPARENT);
                                                                relVehiculosJugadores[i * 4 + 1].setBackgroundColor(Color.TRANSPARENT);
                                                                relVehiculosJugadores[i * 4 + 2].setBackgroundColor(Color.TRANSPARENT);
                                                                relVehiculosJugadores[i * 4 + 3].setBackgroundColor(Color.TRANSPARENT);

                                                                relVehiculosJugadores[i * 4 + 0].setAlpha(1f);
                                                                relVehiculosJugadores[i * 4 + 1].setAlpha(1f);
                                                                relVehiculosJugadores[i * 4 + 2].setAlpha(1f);
                                                                relVehiculosJugadores[i * 4 + 3].setAlpha(1f);
                                                            }

                                                            // Si es turno del JUGADOR permitimos que las cartas y el descarte de cartas pueda ser clickable.
                                                            ivJugCarta1.setClickable(true);
                                                            ivJugCarta2.setClickable(true);
                                                            ivJugCarta3.setClickable(true);
                                                            ivDejarCartas1.setClickable(true);
                                                            ivDejarCartas2.setClickable(true);
                                                            ivDejarCartas3.setClickable(true);

                                                            // Reproducimos el sonido para cuando sea el turno del JUGADOR y lo informamos en el Toolbar.
                                                            funcionesGenerales.reproducirSonidoJuego(getApplicationContext(), R.raw.turno, sonidoJuego);
                                                            tvToolbar.setText(getResources().getString(R.string.tvToolbarTuTurno));
                                                            tbRowCartas.setBackgroundColor(Color.parseColor("#70FFFFFF"));

                                                            timer.cancel();
                                                        }
                                                    }
                                                });
                                            }
                                        };
                                        // Decidimos que pasen 5000 milisegundos entre turnos.
                                        timer.schedule(timerTask, 5000, 5000);
                                    }
                                }
                            }
                            break;

                        default:
                            break;
                    }

                    if (!txtCarta.equals("Cambiar Vehículo"))
                    {
                        tbRowCartas.setBackgroundColor(Color.TRANSPARENT);
                        for (int i = 0; i < ivVehiculosJugadores.length; i++)
                        {
                            relVehiculosJugadores[i].setAlpha(1f);
                            relVehiculosJugadores[i].setBackgroundColor(Color.TRANSPARENT);
                            ivVehiculosJugadores[i].setAlpha(1f);
                            ivVehiculosJugadores[i].setBackgroundColor(Color.TRANSPARENT);
                        }
                    }

                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    break;
            }

            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        musicaJuego.start();
        final float volume = (float) (1 - (Math.log(MAX_VOLUME - soundVolume) / Math.log(MAX_VOLUME)));
        musicaJuego.setVolume(volume, volume);
        musicaJuego.setLooping(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        musicaJuego.stop();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

        MediaPlayer musicaCierre = MediaPlayer.create(getApplicationContext(), R.raw.cerrarpartida);
        musicaCierre.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        musicaJuego.stop();

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        musicaJuego.stop();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

        MediaPlayer musicaCierre = MediaPlayer.create(getApplicationContext(), R.raw.cerrarpartida);
        musicaCierre.start();
    }
}
