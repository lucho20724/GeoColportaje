package uap.geocolportaje.geocoportaje.ActivitiesPrincipales;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import uap.geocolportaje.geocoportaje.Conexion;
import uap.geocolportaje.geocoportaje.Entidades.Punto;
import uap.geocolportaje.geocoportaje.FormulariosCreacion.nuevaubicacionActivity;
import uap.geocolportaje.geocoportaje.ListaSeleccion.listalibrosActivity;
import uap.geocolportaje.geocoportaje.ListaSeleccion.listapuntoActivity;
import uap.geocolportaje.geocoportaje.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;

    Conexion conn;

    ArrayList<Punto> listaPuntos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    public void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null)marcador.remove();

        marcador = mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .title("Ubicacion Actual")
                //.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)
        );
        mMap.animateCamera(miUbicacion);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGuardarPunto:
                ObtenerUbicacion();
                break;

            case R.id.btnUbicacion:
                miUbicacion();
                break;

            case R.id.btnVerPunto:
                Intent i = new Intent(this,listapuntoActivity.class);
                i.putExtra("Ver",true);
                startActivity(i);
                break;

            case R.id.btnEliminarPunto:
                Intent ii = new Intent(this,listapuntoActivity.class);
                ii.putExtra("Eliminar",true);
                startActivity(ii);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //miUbicacion();
        cargarPuntos();
        agregarPuntosGuardados();
    }

    private void cargarPuntos(){
        conn = new Conexion(this,"BD",null,1);
        SQLiteDatabase db= conn.getReadableDatabase();

        Punto punto=null;
        listaPuntos=new ArrayList<Punto>();

        try{
            Cursor cursor=db.rawQuery("SELECT id, lat, long, descripcion, titulo FROM punto",null);

            while (cursor.moveToNext()){
                punto = new Punto();
                punto.setId(cursor.getInt(0));
                punto.setLatitud(cursor.getDouble(1));
                punto.setLongitud(cursor.getDouble(2));
                punto.setDescripcion(cursor.getString(3));
                punto.setTitulo(cursor.getString(4));

                listaPuntos.add(punto);
            }
            db.close();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }

    }

    private void agregarPuntosGuardados(){
        LatLng coordenadas;
        String titulomarcador="";

        if(listaPuntos.size()>0){
            for(int i=0;i<listaPuntos.size();i++) {
                coordenadas = new LatLng(listaPuntos.get(i).getLatitud(), listaPuntos.get(i).getLongitud());
                titulomarcador = listaPuntos.get(i).getTitulo();

                marcador = mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .title(titulomarcador)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcadorpunto))
                );
            }
        }
    }

    public void ObtenerUbicacion() {
        String latitud="";
        String longitud="";
        String mensaje;

        //Obtener direccion
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Transformar direccion en coordenadas
        if(loc != null){
            latitud=String.valueOf(loc.getLatitude());
            longitud=String.valueOf(loc.getLongitude());
        }


        ArrayList<String> coordenadas = new ArrayList<>();
        coordenadas.add(latitud);
        coordenadas.add(longitud);
         mensaje= "Lat: "+String.valueOf(latitud)+" -  Long: "+ String.valueOf(longitud);

        //Envio de coordenadas a la activity para guardar en la BD
        Intent intent = new Intent(getApplicationContext(),nuevaubicacionActivity.class);
        intent.putStringArrayListExtra("coordenadas", coordenadas);
        //Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }



    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,1000,0,locListener);
    }

    LocationListener locListener= new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(getApplicationContext(),"Ubicacion deshabilitada. Por favor active la ubicación del celular",Toast.LENGTH_LONG).show();
        }
    };

}
