package com.example.hamonpc.ej_registrohoras;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="recurso";
    private static  final int DATABASE_VERSION = 1;
    private static final String USER_NOMBRE = "nombre";
    private static final String USER_LAST_NAME_F = "apellido_paterno";
    private static final String USER_LAST_NAME_M = "apellido_materno";
    private static final String USER_EMAIL = "email";
        private static final String FECHA_INIT = "fecha_inicio";
        private static final String FECHA_END = "fecha_termino";
        private  static final String ID_RECURSO ="_idRecurso";
        private static final String DIA = "dia";
        private static final String HORAS ="horas";
        private static final String COMENTARIO = "comentario";
        private static final String ID_SEMANA ="_idSemana";




    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tablaRecurso = "CREATE TABLE recurso (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "apellido_paterno TEXT," +
                "apellido_materno TEXT," +
                "email TEXT )";


        String tablaSemana = "CREATE TABLE semana (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha_inicio TEXT," +
                "fecha_termino TEXT," +
                "_idRecurso INTEGER," +
                "FOREIGN KEY (_idRecurso) REFERENCES recurso(_id))";

        String tablaRegistroDia = "CREATE TABLE registroDiaSemana (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dia TEXT," +
                "horas TEXT," +
                "comentario TEXT," +
                "_idSemana INTEGER," +
                " FOREIGN KEY (_idSemana) REFERENCES semana(_id))";

        String tablaProyectos = "CREATE TABLE proyectos (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreProyecto TEXT," +
                "_idRecurso INTEGER," +
                " FOREIGN KEY (_idRecurso) REFERENCES recurso(_id))";

        String tablaActividades = "CREATE TABLE actividades (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreActividad TEXT," +
                "_idDiaSemana INTEGER," +
                " FOREIGN KEY (_idDiaSemana) REFERENCES registroDiaSemana(_id))";



        sqLiteDatabase.execSQL(tablaRecurso);
        sqLiteDatabase.execSQL(tablaSemana);
        sqLiteDatabase.execSQL(tablaRegistroDia);
        sqLiteDatabase.execSQL(tablaProyectos);
        sqLiteDatabase.execSQL(tablaActividades);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS registroDiaSemana,recurso,semana");
        onCreate(sqLiteDatabase);
    }


}