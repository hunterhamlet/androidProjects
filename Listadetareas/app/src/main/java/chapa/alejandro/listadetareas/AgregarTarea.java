package chapa.alejandro.listadetareas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ATL_DESARROLLO1 on 26/09/2017.
 */

public class AgregarTarea extends AppCompatActivity {

    EditText tarea;
    Button agregar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_tarea);

        tarea = (EditText) findViewById(R.id.tarea);
        agregar = (Button) findViewById(R.id.agregarTarea);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.setData(Uri.parse(tarea.getText().toString()));
                setResult(RESULT_OK,data);
                finish();
            }
        });



    }
}
