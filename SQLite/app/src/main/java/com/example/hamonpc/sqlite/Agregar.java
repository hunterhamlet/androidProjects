package com.example.hamonpc.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by HamonPC on 28/09/2017.
 */

public class Agregar extends AppCompatActivity {

    EditText agregarNombre;
    EditText agregarPrecio;
    Button agregarProducto;
    SQLiteDatabase db;
    Cursor cursor;

    private static final String NOMBRE = "nombre";
    private static final String PRECIO = "precio";
    String agNombre;
    String agPrecio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.agregar);
        super.onCreate(savedInstanceState);

        agregarNombre = (EditText)findViewById(R.id.agregarnombre);
        agregarPrecio = (EditText)findViewById(R.id.agregarprecio);
        agregarProducto = (Button)findViewById(R.id.agregarproducto);

        //Abre la DB y la lee, realiza la priera consulta
        db = new DBHelper(this).getWritableDatabase();
        cursor = db.rawQuery("SELECT _id,nombre,precio FROM productos ORDER BY _id",null);

        agregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agNombre = agregarNombre.getText().toString();
                agPrecio = agregarPrecio.getText().toString();

                ContentValues cv = new ContentValues();

                cv.put(NOMBRE,agNombre);
                cv.put(PRECIO,agPrecio);
                db.insert("productos",NOMBRE,cv);
                finish();
            }
        });

    }

    //Sobreescribir onResume


    @Override
    protected void onResume() {
        db = new DBHelper(this).getWritableDatabase();
        super.onResume();
    }
}
