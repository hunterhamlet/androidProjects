package com.example.hamonpc.ej_registrohoras;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class WeekView extends AppCompatActivity {
    //Menu
    private static final int AGREGAR = Menu.FIRST ;
    private static final int FINALIZAR = Menu.FIRST + 1;
    private static final int ACTUALIZAR = Menu.FIRST + 2;
    private static final int BORRAR = Menu.FIRST + 3;

    //Objetos
    TextView nombreRecursoWeekView;
    ListView semanaPasada;
    ListView semanaActual;
    SQLiteDatabase db;
    Cursor cursor;
    ListAdapter adapter1,adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_view);

        //Inicializamos los objetos
        nombreRecursoWeekView = (TextView)findViewById(R.id.nombre_recurso_week_view);
        semanaActual = (ListView)findViewById(R.id.semana_actual_week_view);
        semanaPasada = (ListView)findViewById(R.id.semana_anterior_week_view);


        //
        MyPreferences pref = new MyPreferences(this);
        if (pref.isFistTime()) {
            Toast.makeText(this, "Hola y bienvenido " + pref.getUserName(), Toast.LENGTH_LONG).show();
            pref.setOld(true);
        } else {
            Toast.makeText(this, "Bienvenido de nuevo", Toast.LENGTH_SHORT).show();
        }

        //Empezamos con el programa
        db = new DBHelper(this).getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM recurso ORDER BY _id",null);
        cursor.moveToFirst();

        //Mostramos nombre de la persona
        nombreRecursoWeekView.setText(cursor.getString(cursor.getColumnIndex("nombre")) + " "+
                cursor.getString(cursor.getColumnIndex("apellido_paterno")) + " " +
                cursor.getString(cursor.getColumnIndex("apellido_materno")));


        //Mostramos la linea de los dias actuales
        cursor = db.rawQuery("SELECT * FROM registroDiaSemana WHERE _idSemana = 2 ORDER BY dia",null);

        adapter1 = new SimpleCursorAdapter(this,R.layout.row,cursor,
                new String[]{"dia","horas","comentario"},
                new  int[]{R.id.fecha,R.id.horas,R.id.comentario},0);

        semanaActual.setAdapter(adapter1);

        //Menu de contexto
        registerForContextMenu(semanaActual);

        ((SimpleCursorAdapter) adapter1).changeCursor(cursor);


        //Actualizamos los datos Anteriores
        //Mostramos la linea de los dias actuales
        cursor = db.rawQuery("SELECT * FROM registroDiaSemana WHERE _idSemana = 1 ORDER BY dia",null);

        adapter2 = new SimpleCursorAdapter(this,R.layout.row,cursor,
                new String[]{"dia","horas","comentario"},
                new  int[]{R.id.fecha,R.id.horas,R.id.comentario},0);

        semanaPasada.setAdapter(adapter2);

        //Menu de contexto
        registerForContextMenu(semanaPasada);

        ((SimpleCursorAdapter) adapter2).changeCursor(cursor);




    }
    //Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, AGREGAR, Menu.NONE, "Agregar");
        menu.add(Menu.NONE, FINALIZAR, Menu.NONE, "Salir");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case AGREGAR :
                Intent i = new Intent(getApplicationContext(),AgregarRegistro.class);
                startActivityForResult(i,180);
                break;
            case FINALIZAR:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //Menu de contexto


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE,ACTUALIZAR,Menu.NONE,"Actualizar registro");
        menu.add(Menu.NONE,BORRAR,Menu.NONE,"Borrar registro");
    }


    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case ACTUALIZAR:
                AdapterView.AdapterContextMenuInfo actualizar = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

                //Agregado
                Intent i = new Intent(getApplicationContext(),ActualizarRegistro.class);
                final  long rowid = actualizar.id;
                i.putExtra("idElement",rowid);
                Log.i("Dato Enviado:",String.valueOf(rowid));
                startActivityForResult(i,180);
                break;
            case BORRAR:
                AdapterView.AdapterContextMenuInfo borrar = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                borrar(borrar.id);
        }
        return true;
    }

    private void  borrar (final long rowid){
        if(rowid > 0){
            new AlertDialog.Builder(this)
                    .setTitle("Estas seguro de borrar el registro")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String[] args = {String.valueOf(rowid)};
                            db.delete("registroDiaSemana","_id=?",args);
                            cursor = db.rawQuery("SELECT * FROM registroDiaSemana WHERE _idSemana = 2 ORDER BY dia",null);
                            ((SimpleCursorAdapter)adapter1).changeCursor(cursor);

                            cursor= db.rawQuery("SELECT * FROM registroDiaSemana WHERE _idSemana = 1 ORDER BY dia",null);
                            ((SimpleCursorAdapter)adapter2).changeCursor(cursor);
                        }
                    })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 180){
            if (resultCode == RESULT_OK){
                cursor = db.rawQuery("SELECT * FROM registroDiaSemana WHERE _idSemana = 2 ORDER BY dia",null);
                ((SimpleCursorAdapter)adapter1).changeCursor(cursor);

                cursor= db.rawQuery("SELECT * FROM registroDiaSemana WHERE _idSemana = 1 ORDER BY dia",null);
                ((SimpleCursorAdapter)adapter2).changeCursor(cursor);
                crearNotificacion();
            }

            }

}


    public void crearNotificacion(){

        Log.d("Main Activity","StarServices");
        Intent service = new Intent(this,MyServices.class);
        startService(service);

    }






    }

