package com.example.hamonpc.destinosturisticos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by HamonPC on 27/09/2017.
 */

public class InfoDestino extends AppCompatActivity {
    ImageView imagenDestino;
    TextView nombreDestino,paisDestno,atractivosDestino,costoDestino;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infodestino);

        //Instanciamos los objetos
        imagenDestino = (ImageView)findViewById(R.id.infoimage);
        nombreDestino =(TextView)findViewById(R.id.infolugar);
        paisDestno = (TextView)findViewById(R.id.infopais);
        atractivosDestino = (TextView)findViewById(R.id.infoatractivos);
        costoDestino = (TextView)findViewById(R.id.infocosto);

        //Recibimos contacto
        Destino destinoRecibico = (Destino)getIntent().getExtras().getSerializable("destino");

        //Mostramos datos
        nombreDestino.setText(destinoRecibico.getNombre());
        paisDestno.setText(destinoRecibico.getPais());
        atractivosDestino.setText(destinoRecibico.getAtractivos());
        costoDestino.setText(destinoRecibico.getCostoaproximado());

        //Mostramos Imagen
        new Descarga().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                destinoRecibico.getUrDestino());


    }
    //AsynTask
    public class Descarga extends AsyncTask<String,Integer,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                URL imagenUrl = new URL(strings[0]);
                Bitmap imagen = BitmapFactory.decodeStream(imagenUrl.openStream());
                return imagen;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView targetImage = (ImageView)findViewById(R.id.infoimage);
            targetImage.setImageBitmap(bitmap);
        }
    }

}
