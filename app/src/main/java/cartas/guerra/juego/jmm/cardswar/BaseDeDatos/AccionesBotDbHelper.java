package cartas.guerra.juego.jmm.cardswar.BaseDeDatos;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Acer on 10/09/2018.
 */
    public class AccionesBotDbHelper extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "accionesbot.db";

    public AccionesBotDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
