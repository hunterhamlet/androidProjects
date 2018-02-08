package com.a99minutos.a99minutos.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.a99minutos.a99minutos.R;
import com.a99minutos.a99minutos.classes.Driver;
import com.a99minutos.a99minutos.classes.Vehicle;
import com.a99minutos.a99minutos.reference.PruebasActivity;
import com.a99minutos.a99minutos.ui.fragments.InfoPersonalFragment;
import com.a99minutos.a99minutos.ui.fragments.InfoVehicleFragment;
import com.a99minutos.a99minutos.ui.fragments.UploadDocumentFragment;
import com.a99minutos.a99minutos.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountActivity extends AppCompatActivity implements
        InfoPersonalFragment.BtnPersonalListener,
        InfoVehicleFragment.BtnVehicleListener,
        UploadDocumentFragment.btnDocumentsUpload{
    //Variables

    //Entities
    private Driver driverSave;
    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;
    private ArrayList<Bitmap> pictures;
    //private Realm realm;

    //---------------------------------Metodos Override---------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Show Toolbar in Activity
        showToolBar("Registro", true);

        //Show Fragment in Activity Container
        showFragment(new InfoPersonalFragment(),R.id.create_account_container);

        //Get db
       // realm = DBHelper.getDB();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    //---------------------------------Metodos Interfaces-------------------------------------------
    @Override
    public void onClickPersonalBtn(Driver driver) {
        if (!checkFieldsDriver(driver)){
        }else{
            driverSave = driver;
            //preferences.setFirstName(driver.getFirstName());
            //preferences.setLastName(driver.getLastName());
            showFragment(new InfoVehicleFragment(),R.id.create_account_container);
        }
    }

    @Override
    public void onClickVehicleBtn(Vehicle vehicle) {
        if (!checkFieldsVehicle(vehicle)){
        }else{
            driverSave.setVehicle(vehicle);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("pictures",pictures);
            UploadDocumentFragment uploadDocumentFragment =
                    new UploadDocumentFragment().newInstance(bundle);
            showFragment(uploadDocumentFragment,R.id.create_account_container);
            //Intent intent = new Intent(this, PruebasActivity.class);
            //intent.putExtra("driver",driverSave);
            //startActivity(intent);
        }
    }

    @Override
    public void onClickUploadBtn() {

    }

    //---------------------------------Metodos ButterKnife-------------------------------------------


    //---------------------------------Metodos Generales--------------------------------------------
    public void showToolBar(String titleToolBar,boolean upBtn){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titleToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upBtn);

    }

    private void showFragment(Fragment fragment, int idContainer){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(idContainer,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
    }

    private boolean checkFieldsDriver (Driver driver){
        if ( driver.getFirstName().equals("") && driver.getLastName().equals("") && driver.getContactTel().equals("")
                && driver.getEmergencyTel().equals("") && driver.getClabe().equals("")){
            Toast.makeText(this, "Comprueba tus datos.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (driver.getFirstName().equals("")){
            Toast.makeText(this, "¿No tienes un nombre?", Toast.LENGTH_SHORT).show();
            return false;
        }else if(driver.getLastName().equals("")){
            Toast.makeText(this, "¿No tienes apellidos?", Toast.LENGTH_SHORT).show();
            return false;
        }else if (driver.getContactTel().equals("") ){
            Toast.makeText(this, "¿Cual es tu numero de contacto?", Toast.LENGTH_SHORT).show();
            return false;
        }else if (driver.getContactTel().equals("") != true && driver.getContactTel().length() != 10) {
            Toast.makeText(this, "Verifica tu numero de contacto.", Toast.LENGTH_SHORT).show();
            return false;
        } else if( driver.getEmergencyTel().equals("") ) {
            Toast.makeText(this, "¿Y tu numero de emergencia?", Toast.LENGTH_SHORT).show();
            return false;
        }else if( driver.getEmergencyTel().equals("") != true && driver.getEmergencyTel().length() != 10){
            Toast.makeText(this, "Verifica tu numero de emergencia.", Toast.LENGTH_SHORT).show();
            return false;
        }else if (driver.getClabe().equals("")) {
            Toast.makeText(this, "¿No quieres que te paguen?", Toast.LENGTH_SHORT).show();
            return false;
        }else if (driver.getClabe().equals("") != true && driver.getClabe().length() != 18){
            Toast.makeText(this, "Verifica tu CLABE.", Toast.LENGTH_SHORT).show();
            return false;
        }else if(driver.getBank().equals("")){
            Toast.makeText(this, "Elige un banco.", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private boolean checkFieldsVehicle(Vehicle vehicle) {
        if (vehicle.getBrand().equals("") || vehicle.getPlates().equals("") ||
                vehicle.getYear().equals("") || vehicle.getColor().equals("")) {
            Toast.makeText(this, "Comprueba los datos de tu vehiculo.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (vehicle.getPlates().equals("") == false && vehicle.getPlates().length() < 5) {
            Toast.makeText(this, "Comprueba la placa de tu vehiculo.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (vehicle.getYear().equals("") == false && vehicle.getYear().length() < 4) {
            Toast.makeText(this, "Comprueba el año de tu vehiculo.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


}
