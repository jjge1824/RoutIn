package com.example.jose_jesus_guzman.avanti.ClasesViews;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jose_jesus_guzman.avanti.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, android.location.LocationListener {
    protected LocationManager locationManager;

    private double lattitud;
    private double longitud;

    private GoogleMap mMap;

    LatLng posicion1;
    LatLng posicion2;
    int contadorMarkers;

    MarkerOptions markerOptionsOrigen, markerOptionsDestino;
    Marker markerOrigen, markerDestino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        lattitud = location.getLatitude();
        longitud = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng posicionActual = new LatLng(lattitud, longitud);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(posicionActual));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                lattitud = location.getLatitude();
                longitud = location.getLongitude();
                LatLng posicionActual = new LatLng(lattitud, longitud);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(posicionActual));
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                if (contadorMarkers == 0){
                    markerOptionsOrigen = new MarkerOptions();
                    markerOptionsOrigen.anchor(0.0f, 1.0f)
                            .position(latLng)
                            .snippet("Origen")
                            .title("Destino")
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .draggable(true);
                    markerOrigen = mMap.addMarker(markerOptionsOrigen);
                    contadorMarkers += 1;
                    posicion1 = latLng;
                } else if (contadorMarkers == 1){
                    markerOptionsDestino = new MarkerOptions();
                    markerOptionsDestino.anchor(0.0f, 1.0f)
                            .position(latLng)
                            .snippet("Destino")
                            .title("Destino")
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .draggable(true);
                    markerDestino = mMap.addMarker(markerOptionsOrigen);

                    contadorMarkers += 1;
                    posicion2 = latLng;
                } else if (contadorMarkers == 2){

                   //TODO cambiar direccion de markers

                    mMap.addMarker(new MarkerOptions()
                            .anchor(0.0f, 1.0f)
                            .position(latLng)
                            .snippet("Origen")
                            .title("Origen")
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .draggable(true));
                    contadorMarkers -= 1;
                    posicion1 = posicion2;
                    posicion2 = latLng;
                }
            }
        });
    }

}
