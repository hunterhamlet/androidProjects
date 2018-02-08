package com.a99minutos.a99minutos.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 99minutos on 26/01/18.
 */

public class Preferences{
    //Entities
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context _contexto;


    //Attrubutes
    public static final int PRIVATE_MODE = 0;
    public  static final String PREF_NAME = "99minutos.com";
    public  static final String IS_FIRST_TIME = "IsFirstTime";
    public  static  final String FIRST_NAME = "name";
    public  static  final String LAST_NAME = "apellidos";
    public static final String EMAIL = "correoRecurso";

    //Constructor
    public Preferences(Context context) {
        this._contexto = context;
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    //Getters & Setters
    public String getFirstName (){
        return preferences.getString(FIRST_NAME,"");
    }

    public void setUserName(String s){
        editor.putString(FIRST_NAME,s);
        editor.commit();
    }

    public String getLastName (){
        return preferences.getString(LAST_NAME,"");
    }

    public void setLastName(String s){
        editor.putString(LAST_NAME,s);
        editor.commit();
    }

    public String getEmail (){
        return preferences.getString(EMAIL,"");
    }

    public void setEmail(String s){
        editor.putString(EMAIL,s);
        editor.commit();
    }
    //Methods
    public boolean isFirstTime (){
        return preferences.getBoolean(IS_FIRST_TIME,true);
    }

    public void setOld(boolean b){
        if (b){
            editor.putBoolean(IS_FIRST_TIME,false);
            editor.commit();
        }
    }

}
