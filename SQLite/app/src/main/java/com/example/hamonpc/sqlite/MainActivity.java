package com.example.hamonpc.sqlite;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    //SQlite
    SQLiteDatabase db;
    ListAdapter adapter;
    Cursor cursor;
    ListView lista;
    Button imaBuscar;
    EditText buscarPalabra;

    public static final int OPCION_UNO = Menu.FIRST;

    private static final int AGREGAR_ID= Menu.FIRST;
    public static final  int BORRAR_ID = Menu.FIRST+1;
    public static  final int CERRAR_ID = Menu.FIRST + 2;
    public static  final  int ACTUALIZAR_ID = Menu.FIRST +3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        imaBuscar = (Button) findViewById(R.id.buscar);
        buscarPalabra=(EditText)findViewById(R.id.buscartext);

        db = new DBHelper(this).getWritableDatabase();

        cursor = db.rawQuery("SELECT _id,nombre,precio FROM productos ORDER BY _id",null);

        adapter = new SimpleCursorAdapter(this,R.layout.row,cursor,
                new String[]{"_id","nombre","precio"},
                new  int[]{R.id.idProducto,R.id.nombreProducto,R.id.precioProducto},0);

        lista = (ListView)findViewById(R.id.lista);//falta layout

        lista.setAdapter(adapter);

        registerForContextMenu(lista);

        imaBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] palBuscar = new String[1];
                palBuscar[0]= buscarPalabra.getText().toString() + "%";


                //Buscar en la base de datos
                cursor = db.rawQuery("SELECT * FROM productos WHERE nombre LIKE ?", palBuscar);
                ((SimpleCursorAdapter)adapter).changeCursor(cursor);

            }
        });

    }

    //Menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,OPCION_UNO,Menu.NONE,"Agregar nuevo producto");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case OPCION_UNO:
                Intent i = new Intent(this,Agregar.class);
                startActivityForResult(i,132);

        }
        return super.onOptionsItemSelected(item);
    }

    //Menu de contexto
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE,ACTUALIZAR_ID,Menu.NONE,"Actualizar registro");
        menu.add(Menu.NONE,BORRAR_ID,Menu.NONE,"Borrar registro");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case ACTUALIZAR_ID:
                AdapterView.AdapterContextMenuInfo registroActualizar =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                actualizar(registroActualizar.id);
                break;
            case BORRAR_ID:
                AdapterView.AdapterContextMenuInfo registroBorrar =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                borrar(registroBorrar.id);
        }
        return  true;
    }

    private  void actualizar(final long rowid){
        Cursor cActualizar = db.rawQuery("SELECT nombre,precio FROM productos WHERE _id=?",new String[]{String.valueOf(rowid)});

        cActualizar.moveToFirst();

        String nombreProducto = cActualizar.getString(cActualizar.getColumnIndex("nombre"));
        String precioProducto = cActualizar.getString(cActualizar.getColumnIndex("precio"));

        LayoutInflater inflater = LayoutInflater.from(this);
        View actualizarView = inflater.inflate(R.layout.editarl,null);
        final EditText nombre = (EditText) actualizarView.findViewById(R.id.editnombre);
        final EditText precio = (EditText) actualizarView.findViewById(R.id.editprecio);

        nombre.setText(nombreProducto);
        precio.setText(precioProducto);

        if(rowid > 0){
            new AlertDialog.Builder(this)
                    .setTitle("Actualizar registro")
                    .setView(actualizarView)
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ContentValues cv = new ContentValues();
                            cv.put("nombre",nombre.getText().toString());
                            cv.put("precio",precio.getText().toString());

                            db.update("productos",cv,"_id=?",new String[]{String.valueOf(rowid)});

                            cursor=db.rawQuery("SELECT _id,nombre,precio FROM productos ORDER BY _id",null);
                            ((SimpleCursorAdapter)adapter).changeCursor(cursor);
                        }
                    })
                    .show();
        }

    }



    private  void borrar(final long rowid){
        if(rowid > 0){
            new AlertDialog.Builder(this)
                    .setTitle("Estas seguro de borrar")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String[] args = {String.valueOf(rowid)};
                            db.delete("productos","_id=?",args);

                            cursor=db.rawQuery("SELECT _id,nombre,precio FROM productos ORDER BY _id",null);

                            ((SimpleCursorAdapter)adapter).changeCursor(cursor);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
        }
    }

    //sore escribur el metodo onResume


    @Override
    protected void onResume() {
        db = new DBHelper(this).getWritableDatabase();

        super.onResume();
    }
}
