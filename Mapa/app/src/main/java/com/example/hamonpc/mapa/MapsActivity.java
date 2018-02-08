package com.example.hamonpc.mapa;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Boolean mapaListo = false;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Cargamos la base de datos

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(19.3712544, -99.1796028);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.0f));

        mapaListo = true;

    }

    public void limpiarMapa (View v){
        if(mapaListo){
            mMap.clear();
        }
    }

    public void ponerMarcador (View v){
        if(mapaListo){
            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.iconmarcador);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMaker = Bitmap.createScaledBitmap(b,150,180,false);
            LatLng ubicacion = new LatLng(19.3712544, -99.1796028);
            Marker marcador = mMap.addMarker(new MarkerOptions().position(ubicacion)); //coloca el marcador

            marcador.setIcon(BitmapDescriptorFactory.fromBitmap(smallMaker));

            marcador.setTitle("Mi marcador");
        }
    }

    Boolean estiloMapa = false;

    public void ponerEstilos (View v){
        if(mapaListo){
            if(estiloMapa){
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                estiloMapa = false;
            }else{
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                estiloMapa = true;
            }
        }
    }

    public  void ponerPoligono(View v){
        if (mapaListo){
            PolygonOptions options = new PolygonOptions()
                    .add(
                            new LatLng(19.378825,-99.180870),
                            new LatLng(19.380050,-99.177126),
                            new LatLng(19.376877,-99.178156),
                            new LatLng(19.377014,-99.179524),
                            new LatLng(19.377975,-99.181010)
                    ).fillColor(Color.argb(150,250,130,140))
                    .strokeColor(Color.GREEN)
                    .addHole(Arrays.asList(
                            new LatLng(19.377833,-99.179685),
                            new LatLng(19.377752,-99.178457),
                            new LatLng(19.378698,-99.179111)
                    ));

            Polygon polygon = mMap.addPolygon(options);
        }
    }

    public void ponerCirculo (View v){
        if(mapaListo){
            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(19.3633908,-99.1868958))
                    .radius(500)
                    .fillColor(Color.argb(200,134,230,111));

            Circle circle = mMap.addCircle(circleOptions);
        }
    }

    public void ponerLinea (View v){
        if(mapaListo){
            PolylineOptions opciones = new PolylineOptions()
                    .add(new LatLng(19.376284,-99.187372))
                    .add(new LatLng(19.372281,-99.187801))
                    .add(new LatLng(19.368096,-99.181235))
                    .add(new LatLng(19.376284,-99.179132))
                    .width(32)
                    .color(Color.rgb(23,156,201));
            Polyline polilinear = mMap.addPolyline(opciones);

            List<LatLng> points = polilinear.getPoints();
            points.add(new LatLng(19.370809,-99.171150));
            polilinear.setPoints(points);

        }
    }


}
