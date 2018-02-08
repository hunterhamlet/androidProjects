package com.example.hamonpc.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HamonPC on 28/09/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="productsDB";
    private static  final int DATABASE_VERSION = 2;
    private static final String NOMBRE = "nombre";
    private static final String PRECIO = "precio";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tabla = "CREATE TABLE productos(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "nombre TEXT,precio TEXT)";

        sqLiteDatabase.execSQL(tabla);

        ContentValues cv = new ContentValues();

        cv.put(NOMBRE,"Leche");
        cv.put(PRECIO,"15");
        sqLiteDatabase.insert("productos",NOMBRE,cv);

        cv.put(NOMBRE,"cafe");
        cv.put(PRECIO,"25");
        sqLiteDatabase.insert("productos",NOMBRE,cv);

        cv.put(NOMBRE,"Manzanas");
        cv.put(PRECIO,"30");
        sqLiteDatabase.insert("productos",NOMBRE,cv);

        cv.put(NOMBRE,"Pan");
        cv.put(PRECIO,"30");
        sqLiteDatabase.insert("productos",NOMBRE,cv);

        cv.put(NOMBRE,"Galletas");
        cv.put(PRECIO,"9");
        sqLiteDatabase.insert("productos",NOMBRE,cv);

        cv.put(NOMBRE,"Refresco");
        cv.put(PRECIO,"16");
        sqLiteDatabase.insert("productos",NOMBRE,cv);

        cv.put(NOMBRE,"Atun");
        cv.put(PRECIO,"18");
        sqLiteDatabase.insert("productos",NOMBRE,cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS productos");
        onCreate(sqLiteDatabase);

    }
}
