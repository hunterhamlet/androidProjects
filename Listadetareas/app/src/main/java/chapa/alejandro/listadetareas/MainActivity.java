package chapa.alejandro.listadetareas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listaDinamica;
    private ArrayList<String> lista;
    private ArrayAdapter<String> adaptador;

    public static final int OPCION_UNO = Menu.FIRST;
    public static final int OPCION_DOS = Menu.FIRST+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDinamica = (ListView) findViewById(R.id.listaTareas);

        lista = new ArrayList<>();
        lista.add("Lavar el carro");
        lista.add("Hacer el super");
        lista.add("Cocinar");
        lista.add("Cortar el pasto");

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lista);

        listaDinamica.setAdapter(adaptador);

        listaDinamica.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String tarea = lista.get(position);
                lista.remove(position);
                adaptador.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Eliminaste "+tarea, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,OPCION_UNO,Menu.NONE,"Agregar nueva tarea");
        menu.add(Menu.NONE,OPCION_DOS,Menu.NONE,"Salir");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case OPCION_UNO:
                Intent i = new Intent(this,AgregarTarea.class);
                startActivityForResult(i,132);
                break;
            case OPCION_DOS:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 132) {
            if(resultCode==RESULT_OK){
                String tareaNueva = data.getData().toString();
                lista.add(tareaNueva);
                adaptador.notifyDataSetChanged();
            }
        }
    }
}
