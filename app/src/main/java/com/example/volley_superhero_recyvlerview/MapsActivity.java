package com.example.volley_superhero_recyvlerview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    static SQLiteDatabase mydatabase;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(com.example.volley_superhero_recyvlerview.MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    //Méthode qui se déclenchera au clic sur un item
    public boolean onOptionsItemSelected(MenuItem item) {
        //On regarde quel item a été cliqué grâce à son id et on déclenche une action
        switch (item.getItemId()) {
            case R.id.addMarker:
                startActivity(new Intent(com.example.volley_superhero_recyvlerview.MapsActivity.this, AddMarker.class));
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        sqliteFonction();

        final LatLng central = new LatLng(48.866667, 2.333333);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(central));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }


    }


    private void sqliteFonction() {
        deleteDatabase("lieux.db");
        mydatabase = openOrCreateDatabase("lieux.db", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Marker(Lat DOUBLE,Lng DOUBLE, Titre VARCHAR);");
        mydatabase.execSQL("INSERT INTO Marker VALUES(48.919525, 2.361661, 'Stade de France');");
        mydatabase.execSQL("INSERT INTO Marker VALUES(48.840901, 2.25446, 'Parc des princes');");
        mydatabase.execSQL("INSERT INTO Marker VALUES(48.8209, 2.35185, 'Stade Charléty');");


        Cursor resultSet = mydatabase.rawQuery("Select * from Marker",null);

        //onmarkerclick()

        while (resultSet.moveToNext())
        {
            LatLng pos = new LatLng(resultSet.getDouble(0), resultSet.getDouble(1));
            mMap.addMarker(new MarkerOptions().position(pos).title(resultSet.getString(2)).snippet(resultSet.getString(2)));
        }
        resultSet.close();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(com.example.volley_superhero_recyvlerview.MapsActivity.this, marker.getSnippet(), Toast.LENGTH_SHORT).show();
                lanceActivity(marker);
                 return true;
            }
        });
    }

    //callback
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(com.example.volley_superhero_recyvlerview.MapsActivity.this, "Pas de permision pour géoloc", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void lanceActivity(Marker marker) {
        Intent intent;
        intent = new Intent(this, ClickMarker.class);
        intent.putExtra("lat",  marker.getPosition().latitude);
        intent.putExtra("lng",  marker.getPosition().longitude);
        intent.putExtra("titre",  marker.getTitle());
        startActivity(intent);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Toast.makeText(com.example.volley_superhero_recyvlerview.MapsActivity.this, "Restart et rechargement de la base", Toast.LENGTH_SHORT).show();
        mMap.clear();
        Cursor resultSet = mydatabase.rawQuery("Select * from Marker",null);

        //onmarkerclick()

        while (resultSet.moveToNext())
        {
            LatLng pos = new LatLng(resultSet.getDouble(0), resultSet.getDouble(1));
            mMap.addMarker(new MarkerOptions().position(pos).title(resultSet.getString(2)).snippet(resultSet.getString(2)));
        }
        resultSet.close();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(com.example.volley_superhero_recyvlerview.MapsActivity.this, marker.getSnippet(), Toast.LENGTH_SHORT).show();
                 lanceActivity(marker);
                return true;
            }
        });

    }

}
