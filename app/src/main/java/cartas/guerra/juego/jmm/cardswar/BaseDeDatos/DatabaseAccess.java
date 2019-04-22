package cartas.guerra.juego.jmm.cardswar.BaseDeDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new AccionesBotDbHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public ArrayList<Integer> getProbCartaByOrganoBot(String cartaAccion1, String dejCartaAccion1,
                                             String cartaAccion2, String dejCartaAccion2, String cartaAccion3,
                                             String dejCartaAccion3, String tabla1,
                                             int numInmunizados, int numMedicados, int numLimpios, int numVirus, int numNo)
    {

        Cursor cursor = database.rawQuery("SELECT " + cartaAccion1 + ", " + dejCartaAccion1 + ", "
                + cartaAccion2 + ", " + dejCartaAccion2 + ", " + cartaAccion3 + ", " + dejCartaAccion3
                + " FROM " + tabla1
                + " WHERE bot_numInmunizado = '" + numInmunizados + "' AND bot_numMedicado = '" + numMedicados
                + "' AND bot_numLimpio = '" + numLimpios + "' AND bot_numVirus = '" + numVirus
                + "' AND bot_numNoOrgano = '" + numNo + "';", new String[]{});

        System.out.println("Might1 -> SELECT " + cartaAccion1 + ", " + dejCartaAccion1 + ", "
                + cartaAccion2 + ", " + dejCartaAccion2 + ", " + cartaAccion3 + ", " + dejCartaAccion3
                + " FROM " + tabla1
                + " WHERE bot_numInmunizado = '" + numInmunizados + "' AND bot_numMedicado = '" + numMedicados
                + "' AND bot_numLimpio = '" + numLimpios + "' AND bot_numVirus = '" + numVirus
                + "' AND bot_numNoOrgano = '" + numNo + "';");

        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        ArrayList<Integer> probCarta = new ArrayList<>();

        do
        {
            probCarta.add(cursor.getInt(0));
            probCarta.add(cursor.getInt(1));
            probCarta.add(cursor.getInt(2));
            probCarta.add(cursor.getInt(3));
            probCarta.add(cursor.getInt(4));
            probCarta.add(cursor.getInt(5));

            System.out.println("Antoni -> " + cursor.getInt(0));
            System.out.println("Antoni -> " + cursor.getInt(1));
            System.out.println("Antoni -> " + cursor.getInt(2));
            System.out.println("Antoni -> " + cursor.getInt(3));
            System.out.println("Antoni -> " + cursor.getInt(4));
            System.out.println("Antoni -> " + cursor.getInt(5));
        }
        while(cursor.moveToNext());

        return probCarta;
    }

    public ArrayList getProbCartaByOrganoJug(String cartaAccion1, String dejCartaAccion1,
                                             String cartaAccion2, String dejCartaAccion2, String cartaAccion3,
                                             String dejCartaAccion3, String tabla1,
                                             int numInmunizados, int numMedicados, int numLimpios, int numVirus, int numNo)
    {

        Cursor cursor = database.rawQuery("SELECT " + cartaAccion1 + ", " + dejCartaAccion1 + ", "
                + cartaAccion2 + ", " + dejCartaAccion2 + ", " + cartaAccion3 + ", " + dejCartaAccion3
                + " FROM " + tabla1
                + " WHERE jug_numInmunizado = '" + numInmunizados + "' AND jug_numMedicado = '" + numMedicados
                + "' AND jug_numLimpio = '" + numLimpios + "' AND jug_numVirus = '" + numVirus
                + "' AND jug_numNoOrgano = '" + numNo + "';", new String[]{});

        if (cursor != null)
        {
            cursor.moveToFirst();
        }

        ArrayList<Integer> probCarta = new ArrayList<>();

        do
        {
            probCarta.add(cursor.getInt(0));
            probCarta.add(cursor.getInt(1));
            probCarta.add(cursor.getInt(2));
            probCarta.add(cursor.getInt(3));
            probCarta.add(cursor.getInt(4));
            probCarta.add(cursor.getInt(5));

            System.out.println("Antoni -> " + cursor.getInt(0));
            System.out.println("Antoni -> " + cursor.getInt(1));
            System.out.println("Antoni -> " + cursor.getInt(2));
            System.out.println("Antoni -> " + cursor.getInt(3));
            System.out.println("Antoni -> " + cursor.getInt(4));
            System.out.println("Antoni -> " + cursor.getInt(5));
        }
        while(cursor.moveToNext());

        cursor.close();

        return probCarta;
    }
}
