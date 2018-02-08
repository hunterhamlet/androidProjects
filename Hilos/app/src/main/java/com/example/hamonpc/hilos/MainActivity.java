package com.example.hamonpc.hilos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity implements Handler.Callback{
    ImageView imagen;
    TextView status;

    private Handler handler = new Handler(this);

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean handleMessage(Message message) {
        imagen = (ImageView)findViewById(R.id.targetImagen);
        Bitmap img = (Bitmap) message.obj;
        imagen.setImageBitmap(img);//mostramos la imagen descargada en bitmap
        String text = message.getData().getString("status");
        status = (TextView)findViewById(R.id.estatus);
        status.setText(text);
        return true;
    }

    private Runnable imageDownloader = new Runnable() {
        Bitmap imagenHilo = null;
        private void  enviarMensaje(String dato){
            Bundle bundle = new Bundle();
            bundle.putString("status",dato);
            Message message = new Message();
            message.setData(bundle);
            message.obj = imagenHilo;
            handler.sendMessage(message);
        }

        @Override
        public void run() {
        try{
            URL imagenURL = new URL
                    ("http://inmanga.com/Manga/GetMangaImage?identification=dda0c17a-83da-4ef6-8c65-4763e8fbe436");
            imagenHilo = BitmapFactory.decodeStream(imagenURL.openStream());
            Log.d("HIlos",Thread.currentThread()+"");

            if (imagenHilo!=null){
                enviarMensaje("Imagen descargada con exito");
            }else{
                enviarMensaje("Hubo un problema con la descarga");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("Hilos","Url malformada");
            enviarMensaje("La url usada esta malformada");
        }catch (IOException e){
            e.printStackTrace();
            Log.i("Hilos","Error de tipo IO Excption");
            enviarMensaje("Error de tipo IO Exception");
        }
        }
    };

    public void iniciarDescarga(View v){
        for (int i= 0;i<7;i++){
            //new  Thread(imageDownloader,"Hilo de descarga" + i).start();
            executor.execute(imageDownloader);
        }

    }



}
