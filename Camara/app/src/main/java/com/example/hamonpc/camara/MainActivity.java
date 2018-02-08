package com.example.hamonpc.camara;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView foto;
    Button tomarFoto;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bp = (Bitmap)data.getExtras().get("data");
        foto.setImageBitmap(bp);
        crearNotificacion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foto = (ImageView) findViewById(R.id.imagen);
        tomarFoto = (Button)findViewById(R.id.button);

        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,10);
            }
        });
    }
    public void crearNotificacion(){
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Fotografia nueva")
                .setContentText("Se tomo una fotografia nueva")
                .setSmallIcon(R.drawable.iconapp)
                .build();

        NotificationManager notiManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notiManager.notify(0,noti);
    }
}
