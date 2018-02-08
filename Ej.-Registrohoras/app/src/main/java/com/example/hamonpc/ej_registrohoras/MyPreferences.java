package com.example.hamonpc.ej_registrohoras;
import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _contexto;

    private static  final int PRIVATE_MODE =  0;
    public  static final String PREF_NAME = "RegistroHorasAPP";
    public  static final String IS_FIRSTTIME = "IsFirstTime";
    public  static  final String USER_NAME = "name";
    public  static  final String LAST_NAME_FATHER = "apellidoPaterno";
    public static final String LAST_NAME_MOTHER = "apellidoMaterno";
    public static final String EMAIL = "correoRecurso";


    public MyPreferences (Context context){
        this._contexto = context;
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public  boolean isFistTime(){
        //Si es la primera vez
        return  pref.getBoolean(IS_FIRSTTIME,true);

    }

    public  void setOld (boolean b){
        if (b){
            //Realizar cambios en el editor
            editor.putBoolean(IS_FIRSTTIME,false);
            editor.commit();
        }
    }

    //Getters
    public  String getUserName(){
        return pref.getString(USER_NAME,"");
    }

    public String getLastNameFather() {
        return pref.getString(LAST_NAME_FATHER,"");
    }

    public String getLastNameMother() {
        return pref.getString(LAST_NAME_MOTHER,"");
    }

    public String getEMAIL() {
        return pref.getString(EMAIL,"");
    }

    //Setters
    public  void setUserName (String s){
        editor.putString(USER_NAME,s);
        editor.commit();
    }

    public  void setUserLastNameF (String s){
        editor.putString(LAST_NAME_FATHER,s);
        editor.commit();
    }

    public  void setUserLastNameM (String s){
        editor.putString(LAST_NAME_MOTHER,s);
        editor.commit();
    }

    public  void setUserEmail (String s){
        editor.putString(EMAIL,s);
        editor.commit();
    }
}
