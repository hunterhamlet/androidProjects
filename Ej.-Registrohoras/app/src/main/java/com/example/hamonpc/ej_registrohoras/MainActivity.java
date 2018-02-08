package com.example.hamonpc.ej_registrohoras;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    //Globales
    Cursor cursor;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyPreferences pref = new MyPreferences(MainActivity.this);
        if (!pref.isFistTime()){
            Intent i = new Intent(getApplicationContext(),WeekView.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            finish();
        }else {
            Toast.makeText(this, "Bienvenido, crea un nuevo usuario, por favor", Toast.LENGTH_LONG).show();
        }
    }

    public void registrarNuevoUsuario(View v){
        EditText recursoName = (EditText)findViewById(R.id.nombre_recurso);
        EditText recursoApellidoPaterno = (EditText)findViewById(R.id.apellido_paterno_recurso);
        EditText recursoApellidoMaterno = (EditText)findViewById(R.id.apelido_materno_recurso);
        EditText recursoEmail = (EditText)findViewById(R.id.correo_recurso);

        MyPreferences pref = new MyPreferences(this);
        pref.setUserName(recursoName.getText().toString().trim());
        pref.setUserLastNameF(recursoApellidoPaterno.getText().toString().trim());
        pref.setUserLastNameM(recursoApellidoMaterno.getText().toString().trim());
        pref.setUserEmail(recursoEmail.getText().toString().trim());

        db = new DBHelper(this).getWritableDatabase();//crea las tablas y la db
        cursor = db.rawQuery("SELECT * FROM recurso ORDER BY _id",null);
        ContentValues cvInit = new ContentValues();

        cvInit.put("nombre",recursoName.getText().toString());
        cvInit.put("apellido_paterno",recursoApellidoPaterno.getText().toString());
        cvInit.put("apellido_materno",recursoApellidoMaterno.getText().toString());
        cvInit.put("email",recursoEmail.getText().toString());
        db.insert("recurso","nombre",cvInit);

        takeSemanaActual();

        Intent i = new Intent(getApplicationContext(),WeekView.class);
        startActivity(i);
        finish();
    }
    //Metodos
    //Obtencion de semana

    public void takeSemanaActual(){
        int primerDiaActual,primerDiaAnterior,mesAnterior,mesActual,numeroDia,diaFinalAnterior,diaFinalActual;
        String auxDiaPrimero,auxDiaFinal,auxDia ="";
        //Creamos Instancia calendario
        java.util.Calendar pruebaCalendario = java.util.Calendar.getInstance();
////////////////////////////////////////////////////////////////////////////////////////////////////
        //Nos movemos la semana anterior
        pruebaCalendario.add(java.util.Calendar.WEEK_OF_YEAR,-1);
        mesAnterior = pruebaCalendario.get(java.util.Calendar.MONTH);
        Log.i("Mes anterior", String.valueOf(mesAnterior + 1));

        //Nos movemos al primer dia de la semana y tomamos la fecha
        numeroDia = pruebaCalendario.get(java.util.Calendar.DAY_OF_WEEK);
        pruebaCalendario.add(java.util.Calendar.DAY_OF_WEEK,- (numeroDia-2));
        primerDiaAnterior = pruebaCalendario.get(pruebaCalendario.DATE);
        Log.i("P D semana pasada: ", String.valueOf(primerDiaAnterior));

        //LLenamos la tabla de semanas
        cursor = db.rawQuery("SELECT * FROM semana ORDER BY _id",null);
        ContentValues cvAux = new ContentValues();
        //Semana Anterior
        auxDiaPrimero = String.valueOf(primerDiaAnterior) + "/"+ "0"+ String.valueOf(mesAnterior+1);
        diaFinalAnterior = primerDiaAnterior + (numeroDia-2);
        auxDiaFinal = String.valueOf(diaFinalAnterior) + "/"+ "0"+ String.valueOf(mesAnterior);
        cvAux.put("fecha_inicio",auxDiaPrimero);
        cvAux.put("fecha_termino",auxDiaFinal);
        cvAux.put("_idRecurso","1");
        db.insert("semana","fecha_inicio",cvAux);

        //Llenamos la tabla de dias
        cursor = db.rawQuery("SELECT * FROM registroDiaSemana ORDER BY _id",null);
        ContentValues cvDia = new ContentValues();
        for (int i =primerDiaAnterior;i<=diaFinalAnterior;i++ ) {
            auxDia = String.valueOf(i) + "/" + "0" + String.valueOf(mesAnterior+1);
            cvDia.put("dia", auxDia);
            cvDia.put("horas", "00:00");
            cvDia.put("comentario", "");
            cvDia.put("_idSemana", 1);
            db.insert("registroDiaSemana", "dia", cvDia);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //Nos movemos al dia de la semana actual
        pruebaCalendario.add(java.util.Calendar.WEEK_OF_YEAR,1);
        mesActual = pruebaCalendario.get(java.util.Calendar.MONTH);
        primerDiaActual = pruebaCalendario.get(pruebaCalendario.DATE);
        Log.i("Mes Actual: ", String.valueOf(mesActual + 2));
        Log.i("P D semana Actual: ", String.valueOf(primerDiaActual));

        //LLenamos la tabla de semanas
        cursor = db.rawQuery("SELECT * FROM semana ORDER BY _id",null);
        cvAux = new ContentValues();
        //Semana Actual
        auxDiaPrimero = "0"+ String.valueOf(primerDiaActual) + "/"+ String.valueOf(mesActual+1);
        diaFinalActual = primerDiaActual + (numeroDia-2);
        auxDiaFinal = "0"+String.valueOf(diaFinalActual) + "/"+ String.valueOf(mesAnterior);
        cvAux.put("fecha_inicio",auxDiaPrimero);
        cvAux.put("fecha_termino",auxDiaFinal);
        cvAux.put("_idRecurso","1");
        db.insert("semana","fecha_inicio",cvAux);

        for (int i =primerDiaActual;i<=diaFinalActual;i++ ) {
            auxDia = "0"+String.valueOf(i) + "/" +  String.valueOf(mesActual+1);
            cvDia.put("dia", auxDia);
            cvDia.put("horas", "00:00");
            cvDia.put("comentario", "");
            cvDia.put("_idSemana", 2);
            db.insert("registroDiaSemana", "dia", cvDia);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////



    }
}
