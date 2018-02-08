package com.hamlet.recetario;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Inicializamos objetos
    ArrayList<Platillo>platillos;
    ListView listaPlatillos;
    ImageView imaSeleccionada;
    TextView textSeleccionado;
    ListView listaIngredientes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        platillos = new ArrayList<>();
        listaPlatillos = (ListView)findViewById(R.id.platillos);
        listaIngredientes = (ListView)findViewById(R.id.ingredientes_seleccionados);

        ArrayList<Ingrediente> ingredientesPuno = new ArrayList<>();
        ingredientesPuno.add(new Ingrediente("2","Jitomates"));
        ingredientesPuno.add(new Ingrediente("1 diente","ajo"));
        ingredientesPuno.add(new Ingrediente("1/4","Cebolla"));
        ingredientesPuno.add(new Ingrediente("1","Paquete de sopa"));
        ingredientesPuno.add(new Ingrediente("1L","Agua"));

        ArrayList<Ingrediente> ingredientesPdos = new ArrayList<>();
        ingredientesPdos.add(new Ingrediente("4","Jitomates"));
        ingredientesPdos.add(new Ingrediente("1/4","Cebolla"));
        ingredientesPdos.add(new Ingrediente("4","Tazas de arros"));
        ingredientesPdos.add(new Ingrediente("1/8","Ajo"));

        platillos.add(new Platillo("Sopa de fideos","",R.drawable.sopa,ingredientesPuno));
        platillos.add(new Platillo("Arroz blanco","",R.drawable.arroz,ingredientesPdos));

        listaPlatillos.setAdapter(new PlatilloAdaptador());

        imaSeleccionada = (ImageView)findViewById(R.id.imagenseleccionada);
        textSeleccionado = (TextView)findViewById(R.id.nombreseleccionado);

        //Metodo para cuando se selecciona una item de la lista
        listaPlatillos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Obtenemos el item selecionado
                Platillo platilloSeleccionado = platillos.get(i);

                //Actualizamos la pantalla superior de los paltillos
                imaSeleccionada.setImageResource(platilloSeleccionado.getImagen());
                textSeleccionado.setText(platilloSeleccionado.getNombre());

                //Creamos el adaptador para la vista de ingredientes
                listaIngredientes.setAdapter(new AdaptadorIngredientes(platilloSeleccionado.getIngredientes()));

                //

            }
        });



    }
    //Inner calss ingredientes
    class AdaptadorIngredientes extends ArrayAdapter{
        //Fields
        ArrayList<Ingrediente> ingredientesRec;

        AdaptadorIngredientes(ArrayList<Ingrediente> ingredientesRecibidos){
            super(MainActivity.this,R.layout.ingrediente_row,ingredientesRecibidos);
            this.ingredientesRec = ingredientesRecibidos;
        }

        //Sobreescribir metodo getView

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View rowIngredientes = getLayoutInflater().inflate(R.layout.ingrediente_row,parent,false);

            TextView cantidadIngrediente = (TextView) rowIngredientes.findViewById(R.id.cantidadingrediente);
            TextView nombreIngrediente = (TextView) rowIngredientes.findViewById(R.id.nombreingrediente);

            Ingrediente ingredienteActual = ingredientesRec.get(position);

            cantidadIngrediente.setText(ingredienteActual.getCantidad());
            nombreIngrediente.setText(ingredienteActual.getNombre());

            return rowIngredientes;
        }
    }
    //Inner Class platillo
    class PlatilloAdaptador extends ArrayAdapter<Platillo>{
        PlatilloAdaptador(){super(MainActivity.this,R.layout.platillo_row,platillos);}

        //Sobreescribir el getView
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //Creamos el infalter o vista row
            View row = getLayoutInflater().inflate(R.layout.platillo_row,parent,false);

            ImageView imgPlatillo = (ImageView)row.findViewById(R.id.platilloimagen);
            TextView tvPlatillo = (TextView)row.findViewById(R.id.platillonombre);

            Platillo platilloActual = platillos.get(position);

            imgPlatillo.setImageResource(platilloActual.getImagen());
            tvPlatillo.setText(platilloActual.getNombre());

            return row;
        }
    }
}
