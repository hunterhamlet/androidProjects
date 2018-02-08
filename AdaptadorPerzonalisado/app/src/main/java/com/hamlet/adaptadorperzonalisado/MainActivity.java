package com.hamlet.adaptadorperzonalisado;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    ArrayList<Disco> discos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.lista);
        discos = new ArrayList<Disco>();

        discos.add(new Disco(R.drawable.avnihtmare,"Nightmare","Avenged Sevenfold","18"));
        discos.add(new Disco(R.drawable.bmth_spirit,"That's the spirit","Bring Me The Horizon","20"));

        lista.setAdapter(new DiscoAdaptador());


    }

    class DiscoAdaptador extends ArrayAdapter<Disco>{
        DiscoAdaptador(){
            super(MainActivity.this,R.layout.disco_row,discos);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.disco_row,parent,false);

            ImageView portada = (ImageView)row.findViewById(R.id.cover);
            TextView precio = (TextView)row.findViewById(R.id.price);
            TextView  titulo = (TextView) row.findViewById(R.id.title);
            TextView album = (TextView) row.findViewById(R.id.album);

            Disco discoActual = discos.get(position);

            portada.setImageResource(discoActual.getCover());
            precio.setText("$" + discoActual.getPrecio());
            titulo.setText(discoActual.getTitulo());
            album.setText(discoActual.getAlbum());

            return row;
        }
    }
}
