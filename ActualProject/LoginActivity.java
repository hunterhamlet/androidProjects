package com.a99minutos.a99minutos.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.a99minutos.a99minutos.R;
import com.a99minutos.a99minutos.classes.Driver;
import com.a99minutos.a99minutos.utils.Preferences;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity{

    //Entities
    Preferences preferences;

    //---------------------------------Metodos Override--------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ButterKnife init in this context
        ButterKnife.bind(this);

        //Realm Init (just once per app)
        //DBHelper.DBHelperInit(this);

        //SharedPreferences
        preferences = new Preferences(getApplicationContext());
        if(!preferences.isFirstTime()){
            Intent intent = new Intent(this,DriverMain.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Bienvenido, lee tú codigo QR o crea un nuevo usuario.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            }else {
                preferences.setOld(true);
                Toast.makeText(this, "Scanenado " + result.getContents(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,DriverMain.class);
                startActivity(intent);
                finish();
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //---------------------------------Metodos Generales--------------------------------------------
    public void loginAccess(View view){
        /**
         * @Nombre: loginAccess
         * @param: View view
         * @Descripción: Función que es invocada cuando el boton "login_scan" es presionado, crea
         * objeto del tipo IntentIntegrator par apoder manejar la lectura de código QR, BC.
         * @see: https://github.com/journeyapps/zxing-android-embedded*/
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Escanea tu codigo QR de Don Veloz.");
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }

    public void loginCreateAccount(View view){
        /**
         * @Nombre: loginCreateAccount
         * @param: View view
         * @Descripción: Función que nos manda a la activity "CreateAccountActivity" para poder
         * registrar a un nuevo usuario.*/
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

}
