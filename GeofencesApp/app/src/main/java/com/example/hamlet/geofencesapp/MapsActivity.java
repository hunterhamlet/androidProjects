package com.example.hamlet.geofencesapp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        //INTERFACES PARA UTILIZAR OnMapClickListener,OnMarkerClickListener
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        //INTERFACES PARA GoogleAPIClient
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        //INTERFAZ PARA LOCALIZACION
        LocationListener,
        ResultCallback<Status>{

    //
    private static final String TAG = "JHMM";
    private static final int UPDATE_INTERVAL = 1000;//3*60*1000;//3 MINUTES
    private static final int FAST_INTERVAL =UPDATE_INTERVAL/2;//30*1000;//3 SECONDS
    private static final int REQ_PERMISSION = 999;
    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "My Geofence";
    private static final float GEOFENCE_RADIUS = 50.0f; // in meters
    private final int GEOFENCE_REQ_CODE = 0;
    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
    public static final int OPCION_UNO = Menu.FIRST;
    public static final int OPCION_DOS = Menu.FIRST+1;
    private final String KEY_GEOFENCE_LAT = "GEOFENCE LATITUDE";
    private final String KEY_GEOFENCE_LON = "GEOFENCE LONGITUDE";
    //ENTITIES
    private GoogleMap mMap;
    private TextView textLat,textLon;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private Marker locationMarker, geoFenceMarker;
    private PendingIntent geoFencingPendingIntent;
    private Circle geoFenceLimits;


    //---------------------------------------METODOS OVERRIDE---------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //ENTITIES INSTANCES
        textLat = findViewById(R.id.texlat);
        textLon = findViewById(R.id.texlon);

        //MAP INIT
        initMap();

        //CREATE GOOGLEAPICLIENT
        createGoogleApi();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"onMapReady() init");
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        if (checkPermission()) mMap.setMyLocationEnabled(true);
        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    //METODOS PARA MAPS
    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "onMapClick("+latLng +")");
        markerForGeoFences(latLng);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClickListener: " + marker.getPosition() );
        return false;
    }

    //METODOS PARA GOOGLE API CLIENT
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG,"onConnected()");
        getLastKnowLocation();
        recoverGeofenceMarker();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG,"onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG,"onConnectionFailed()");
    }

    //METODOS PARA ACTIVITY

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    //METODOS PARA LOCATION
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged ["+location+"]");
        lastLocation = location;
        writeActualLocation(location);
    }

    //METODOS PERMISION
    // Verify user's response of the permission requested
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ( requestCode ) {
            case REQ_PERMISSION: {
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    // Permission granted
                    getLastKnowLocation();

                } else {
                    // Permission denied
                    permissionsDenied();
                }
                break;
            }
        }
    }

    //METODO PARA LA CREACION DE MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,OPCION_UNO,Menu.NONE,"Agregar Geofence");
        menu.add(Menu.NONE,OPCION_DOS,Menu.NONE,"Remover Geofence");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case OPCION_UNO:
                startGeofence();
                Toast.makeText(this, "Geofence Agregada", Toast.LENGTH_SHORT).show();
                break;
            case  OPCION_DOS:
                clearGeofence();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.i(TAG, "onResult: " + status);
        if ( status.isSuccess() ) {
            saveGeofence();
            drawGeofence();
        } else {
            // inform about fail
        }
    }
    //--------------------------------------METODOS GENERALES---------------------------------------

    private void createGoogleApi() {
        Log.d(TAG,"createGoogleApi()");
        if(googleApiClient == null){
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLastKnowLocation() {
        Log.d(TAG,"getLastKnowLocation()");
        if(checkPermission()){
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastLocation != null){
                Log.d(TAG,"LastKnowLocation "
                        + "Lat: " + lastLocation.getLatitude()
                        + "Lon: " + lastLocation.getLongitude());
                writeLastLocation();
                startLocationUpdates();
            }else {
                Log.d(TAG,"Localidad no recupeada");
                startLocationUpdates();
            }
        }else {askPermission();}
    }

    private void askPermission() {
        Log.d(TAG,"askPermission()");
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQ_PERMISSION);
    }

    private void startLocationUpdates() {
        Log.d(TAG,"startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FAST_INTERVAL);

        if (checkPermission()){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);
        }
    }

    private void writeLastLocation() {
        writeActualLocation(lastLocation);
    }

    private void writeActualLocation(Location location) {
        //ESCRIBIMOS LAS COORDENADAS EN LA UI
        textLat.setText("" + lastLocation.getLatitude());
        textLon.setText("" + lastLocation.getLongitude());
        markerLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private boolean checkPermission() {
        //CHECK PERMISSION TO ACCESS LOCATION
        Log.d(TAG, "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }

    private void permissionsDenied() {
        Log.d(TAG, "permissionsDenied()");
    }

    private void markerForGeoFences(LatLng latLng) {
        Log.d(TAG,"markerForGeoFences(" +latLng +")");
        String title = latLng.latitude + ", " + latLng.longitude;
        //DEFINE MARKER OPTIONS
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .title(title);
        if(mMap != null){
            if (geoFenceMarker != null){
                geoFenceMarker.remove();
            }
            geoFenceMarker= mMap.addMarker(markerOptions);
            drawGeofence();
        }

    }

    private void markerLocation(LatLng latLng){
        /*BitmapDrawable drawable = (BitmapDrawable)getResources(). getDrawable(R.drawable.moto_marker);
        Bitmap anImage = drawable.getBitmap();*/


        Log.d(TAG,"markerLocation(" + latLng +")");
        String title = latLng.latitude + ", " + latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(bitmapDescriptorFromVector(this, R.drawable.moto_marker))
                .anchor(0.5f,0.5f)
                .flat(true);

        if ( mMap!=null ) {
            // Remove the anterior marker
            if ( locationMarker != null ) {locationMarker.remove();}
            locationMarker = mMap.addMarker(markerOptions);
            float zoom = 14f;
            //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
            //mMap.animateCamera(cameraUpdate);
        }
    }

    private Geofence createGeofences(LatLng latLng,float radius){
        /**Función: Crea una geofence con sus propiedades**/
        Log.d(TAG,"createGeofences()");
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion(latLng.latitude,latLng.longitude,radius)
                .setExpirationDuration(GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    private GeofencingRequest createGeofencingRequest(Geofence geofence){
        /**Función: Solicitud de peticion de geofence**/
        Log.d(TAG,"createGeofencingRequest()");
        return  new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();

    }

    private  PendingIntent createGeofencePendingIntent(){
        Log.d(TAG,"createGeofencingPendingIntent()");
        if(geoFencingPendingIntent != null){
            return geoFencingPendingIntent;
        }

        Intent intent = new Intent(this,GeofenceTransitionServices.class);
        return PendingIntent.getService(this,GEOFENCE_REQ_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void addGeofence(GeofencingRequest request){
        /** Función: Agrega un GeofenceRequest a la lista de monitoreo del dispositivo
         * */
        Log.d(TAG,"addGeofence()");
        if (checkPermission()){
            LocationServices.GeofencingApi.addGeofences(
                    googleApiClient,
                    request,
                    createGeofencePendingIntent())
                    .setResultCallback((ResultCallback<? super Status>) this);
        }

    }

    private void drawGeofence(){
        Log.d(TAG,"");
        if (geoFenceLimits != null){
            geoFenceLimits.remove();
        }

        CircleOptions circleOptions = new CircleOptions()
                .center(geoFenceMarker.getPosition())
                .strokeColor(Color.argb(50, 70,70,70))
                .fillColor(Color.argb(100, 150,150,150))
                .radius(GEOFENCE_RADIUS);

        geoFenceLimits = mMap.addCircle(circleOptions);
    }

    private void startGeofence(){
        /**Función: Empieza con la creacion de la Geocerca*/
        Log.d(TAG,"startGeofence()");
        if (geoFenceMarker != null){
            Geofence geofence = createGeofences(geoFenceMarker.getPosition(),GEOFENCE_RADIUS);
            GeofencingRequest geofencingRequest = createGeofencingRequest(geofence);
            addGeofence(geofencingRequest);
        }else {
            Log.e(TAG,"El marcador para el Geofence es nulo");
        }
    }

    // Create a Intent send by the notification
    public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent( context, MapsActivity.class );
        intent.putExtra( NOTIFICATION_MSG, msg );
        return intent;
    }

    // Saving GeoFence marker with prefs mng
    private void saveGeofence() {
        Log.d(TAG, "saveGeofence()");
        SharedPreferences sharedPref = getPreferences( Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putLong( KEY_GEOFENCE_LAT, Double.doubleToRawLongBits( geoFenceMarker.getPosition().latitude ));
        editor.putLong( KEY_GEOFENCE_LON, Double.doubleToRawLongBits( geoFenceMarker.getPosition().longitude ));
        editor.apply();
    }

    // Recovering last Geofence marker
    private void recoverGeofenceMarker() {
        Log.d(TAG, "recoverGeofenceMarker");
        SharedPreferences sharedPref = getPreferences( Context.MODE_PRIVATE );

        if ( sharedPref.contains( KEY_GEOFENCE_LAT ) && sharedPref.contains( KEY_GEOFENCE_LON )) {
            double lat = Double.longBitsToDouble( sharedPref.getLong( KEY_GEOFENCE_LAT, -1 ));
            double lon = Double.longBitsToDouble( sharedPref.getLong( KEY_GEOFENCE_LON, -1 ));
            LatLng latLng = new LatLng( lat, lon );
            markerForGeoFences(latLng);
            drawGeofence();
        }
    }

        // Clear Geofence
    private void clearGeofence() {
        Log.d(TAG, "clearGeofence()");
        LocationServices.GeofencingApi.removeGeofences(
                googleApiClient,
                createGeofencePendingIntent()
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if ( status.isSuccess() ) {
                    // remove drawing
                    removeGeofenceDraw();
                }
            }
        });
    }

    private void removeGeofenceDraw() {
        Log.d(TAG, "removeGeofenceDraw()");
        if ( geoFenceMarker != null)
            geoFenceMarker.remove();
        if ( geoFenceLimits != null )
            geoFenceLimits.remove();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }





}
