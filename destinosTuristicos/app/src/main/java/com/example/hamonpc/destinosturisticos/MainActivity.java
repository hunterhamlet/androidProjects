package com.example.hamonpc.destinosturisticos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Objetos
    ListView listadestinos;
    ArrayList<String> nombreDestinos;
    ArrayList<Destino> destinos;
    ArrayAdapter<String>adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos objetos
        listadestinos = (ListView)findViewById(R.id.listadestinos);
        nombreDestinos = new ArrayList<String>();
        destinos = new ArrayList<Destino>();


        //Creamos objetos
        Destino dUno = new Destino("Puerto Vallarta","México","Playas,mujeres","$1500 x noche",
                "http://deidayvuelta.mx/wp-content/uploads/2017/04/puertovallarta.jpg");
        Destino dDos = new Destino("Paris","Francia","Ropa,Torre Ifel","$250 USD x noche",
                "https://cache-graphicslib.viator.com/graphicslib/thumbs360x240/7845/SITours/entrada-de-acceso-prioritario-a-la-torre-eiffel-con-anfitri-n-in-paris-299567.jpg");

        destinos.add(dUno);
        destinos.add(dDos);

        nombreDestinos.add(dUno.getNombre());
        nombreDestinos.add(dDos.getNombre());

        //Inicializamos el adaptador
        adaptador = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1,nombreDestinos);

        //Creamos la vista de contactos
        listadestinos.setAdapter(adaptador);

        listadestinos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Obtenemos el destino actual
                Destino pasarDestino = destinos.get(i);

                //Pasamos el objeto
                Intent intent = new Intent(MainActivity.this,InfoDestino.class);
                intent.putExtra("destino",pasarDestino);
                startActivityForResult(intent,132);

            }
        });




    }
}
