package com.example.hamonpc.smstelefono;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText numero,texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numero = (EditText)findViewById(R.id.numerotelefonico);
        texto = (EditText)findViewById(R.id.ingresatexto);

        //Pedir permiso
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},10);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},11);

    }

    public void realizarLlamada(View v){
        String aMarcar = "tel:" + numero.getText().toString();
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(aMarcar)));
    }

    public void enviarMensaje(View v){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(numero.getText().toString(),null,texto.getText().toString(),null,null);

        Toast.makeText(MainActivity.this,"Mensaje enviado",Toast.LENGTH_LONG);

    }
}
