package cartas.guerra.juego.jmm.cardswar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by Acer on 15/10/2018.
 */
public class MenuMain extends AppCompatActivity {

    Spinner spinner_MenuMain;
    Button button_MenuMain;
    TextView tvNumJugadores;

    View espacioIntermedio;

    String numJugadores = "2";
    String[] mOptions = {"2", "3", "4", "5", "6"};

    private AdView mAdView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mAdView1 = findViewById(R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);

        espacioIntermedio = findViewById(R.id.espacioIntermedio);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int widthWindow = dm.widthPixels;
        int heightWindow = dm.heightPixels;

        espacioIntermedio.getLayoutParams().height = heightWindow / 20;
        espacioIntermedio.getLayoutParams().width = widthWindow;
        espacioIntermedio.requestLayout();

        spinner_MenuMain = findViewById(R.id.spinner_menumain);
        button_MenuMain = findViewById(R.id.button_menumain);
        tvNumJugadores = findViewById(R.id.tv_numJugadores);
        tvNumJugadores.setPaintFlags(tvNumJugadores.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        Typeface typeface_menuMain = Typeface.createFromAsset(getAssets(),
                "fonts/NuevoDisco.ttf");

        tvNumJugadores.setTypeface(typeface_menuMain);
        button_MenuMain.setTypeface(typeface_menuMain);

        /*ArrayAdapter<String> myspinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, mOptions);

        myspinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_MenuMain.setAdapter(myspinnerArrayAdapter);*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_spinner_item, mOptions) {

            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                Typeface external_typeface_menuMain = Typeface.createFromAsset(getAssets(),
                        "fonts/NuevoDisco.ttf");

                ((TextView) v).setTypeface(external_typeface_menuMain);
                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setGravity(Gravity.CENTER);

                return v;
            }

            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {

                View v =super.getDropDownView(position, convertView, parent);

                Typeface external_typeface_menuMain = Typeface.createFromAsset(getAssets(),
                        "fonts/NuevoDisco.ttf");

                ((TextView) v).setTypeface(external_typeface_menuMain);
                ((TextView) v).setTextColor(Color.parseColor("#FFFFFF"));
                ((TextView) v).setGravity(Gravity.CENTER);

                return v;
            }


        };

        button_MenuMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_button = new Intent(getApplicationContext(), MainActivity.class);
                intent_button.putExtra("numJugadores", numJugadores);
                startActivity(intent_button);
            }
        });

        /*ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mOptions);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

        spinner_MenuMain.setAdapter(adapter);

        spinner_MenuMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                numJugadores = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_MenuMain.setSelection(1);
    }
}
