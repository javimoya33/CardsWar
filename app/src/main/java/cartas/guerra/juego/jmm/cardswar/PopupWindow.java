package cartas.guerra.juego.jmm.cardswar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Acer on 11/01/2019.
 */
public class PopupWindow extends Activity {

    public TextView tvMensajeCabecera;
    public TextView tvMensajeNuevaPartida;

    public Button butSiNuevaPartida;
    public Button butNoNuevaPartida;

    public MediaPlayer musicaPopup;
    private final static int MAX_VOLUME = 100;
    private final static int soundVolume = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        tvMensajeCabecera = findViewById(R.id.mensajeCabecera);
        tvMensajeNuevaPartida = findViewById(R.id.mensajeNuevaPartida);

        butSiNuevaPartida = findViewById(R.id.siNuevaPartida);
        butNoNuevaPartida = findViewById(R.id.noNuevaPartida);

        Intent intentNumJug = getIntent();
        final String mensajeCabecera = intentNumJug.getStringExtra("mensajeCabecera");

        if (mensajeCabecera.equals("Lo siento, has PERDIDO la partida"))
        {
            musicaPopup = MediaPlayer.create(this, R.raw.derrota);
            musicaPopup.start();

            final float volume = (float) (1 - (Math.log(MAX_VOLUME - soundVolume) / Math.log(MAX_VOLUME)));
            musicaPopup.setVolume(volume, volume);
            musicaPopup.setLooping(false);
        }
        else
        {
            musicaPopup = MediaPlayer.create(this, R.raw.victoria);
            musicaPopup.start();

            final float volume = (float) (1 - (Math.log(MAX_VOLUME - soundVolume) / Math.log(MAX_VOLUME)));
            musicaPopup.setVolume(volume, volume);
            musicaPopup.setLooping(false);
        }

        Typeface typeface_mensCabecera = Typeface.createFromAsset(getAssets(),
                "fonts/NuevoDisco.ttf");

        tvMensajeCabecera.setTextColor(Color.parseColor("#e1e1e1"));
        tvMensajeCabecera.setTypeface(typeface_mensCabecera);
        tvMensajeCabecera.setText(mensajeCabecera);
        tvMensajeNuevaPartida.setTextColor(Color.parseColor("#e1e1e1"));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width, height);
        getWindow().setLayout((int) (width*.8), (int) (height*.3));

        butSiNuevaPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                musicaPopup.stop();
                Intent restarApp = new Intent(getApplicationContext(), MenuMain.class);
                startActivity(restarApp);
            }
        });

        butNoNuevaPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                musicaPopup.stop();
                MediaPlayer musicaCierre = MediaPlayer.create(getApplicationContext(), R.raw.cerrarpartida);
                musicaCierre.start();

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}
