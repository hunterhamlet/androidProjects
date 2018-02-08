package com.example.hamonpc.ej_registrohoras;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class ActualizarRegistro extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //Objetos en pantalla
    EditText horas,minutos,comentario,nombreProyecto,dia,mes;
    SQLiteDatabase db;
    Cursor cursor;
    long itemRecibido = 0;
    Spinner tiposActividad;
    boolean flagActivity;
    int flagSemana = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_registro);

        //Obtenemos el objet enviado
        db = new DBHelper(this).getWritableDatabase();
        itemRecibido = (long)getIntent().getExtras().getSerializable("idElement");
        Log.i("Objeto recibido: ",String.valueOf(itemRecibido));

        ///////////////////////////////////////////////////////////////////////////////////////////////
        Cursor cActualizar = db.rawQuery("SELECT * FROM registroDiaSemana WHERE _id=?",new String[]{String.valueOf(itemRecibido)});
        cActualizar.moveToFirst();
        String fecha = cActualizar.getString(cActualizar.getColumnIndex("dia"));
        String horasRec = cActualizar.getString(cActualizar.getColumnIndex("horas"));
        String comentarioRec = cActualizar.getString(cActualizar.getColumnIndex("comentario"));
        String diaAct = fecha.substring(0,2);
        String mesAct = fecha.substring(3,5);
        String horaAct = horasRec.substring(0,2);
        String minAct = horasRec.substring(3,5);
        flagSemana = Integer.parseInt(cActualizar.getString(cActualizar.getColumnIndex("_idSemana")));
        ///////////////////////////////////////////////////////////////////////////////////////////////

        //Iniciaizamos objetos
        dia = (EditText) findViewById(R.id.agregar_dia);
        mes = (EditText) findViewById(R.id.agregar_mes);
        horas = (EditText) findViewById(R.id.agregar_horas);
        minutos = (EditText) findViewById(R.id.agregar_minutos);
        comentario = (EditText)findViewById(R.id.agregar_comentario);
        tiposActividad = (Spinner)findViewById(R.id.actividades_spinner);
        nombreProyecto = (EditText)findViewById(R.id.nombre_proyecto);

        //Cremamos el spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tipo_actividad,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposActividad.setAdapter(adapter);
        tiposActividad.setOnItemSelectedListener(this);

        //Vemos los datos

        dia.setText(diaAct);
        mes.setText(mesAct);
        horas.setText(horaAct);
        minutos.setText(minAct);
        comentario.setText(comentarioRec);

        //Empezamos con el programa
        db = new DBHelper(this).getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM registroDiaSemana ORDER BY _id",null);

    }

    public void agregarRegistro(View v){
        String valueString = horas.getText().toString()+ minutos.getText().toString();
        int valorInt = Integer.parseInt(valueString);

        if((flagActivity && valorInt>= 30 && valorInt<= 800) || (flagActivity == false && valorInt == 400 || valorInt ==800) ) {
            ContentValues cvDia = new ContentValues();
            //Convertimos los datos obtenidos
            cvDia.put("dia",dia.getText().toString() + "/" + mes.getText().toString());
            cvDia.put("horas",horas.getText().toString()+":" + minutos.getText().toString());
            cvDia.put("comentario",comentario.getText().toString());
            if (flagSemana == 1){cvDia.put("_idSemana","1");}
            else{cvDia.put("_idSemana","2");}
            db.update("registroDiaSemana", cvDia, "_id=?", new String[]{String.valueOf(itemRecibido)});

            //Registrar actividades
            //cursor  = db.rawQuery("SELECT * FROM registroDiaSemana ORDER BY _id",null);

            setResult(RESULT_OK,new Intent());
            finish();

        }
        else if (flagActivity == false && (valorInt != 400 || valorInt !=800)){
            Toast.makeText(this, "Verificar horas o actividad.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Las horas tienen que estar entre 30 min y 8 horas.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(this, "La posicion es : " + i, Toast.LENGTH_SHORT).show();
        flagActivity = checkTipoActividad(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean checkTipoActividad(int tipoActividad){
        if (tipoActividad == 0) {return true;}
        else {return false;}
    }

}
