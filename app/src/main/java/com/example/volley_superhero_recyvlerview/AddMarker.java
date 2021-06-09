package com.example.volley_superhero_recyvlerview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class AddMarker extends AppCompatActivity {

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON

    ArrayList<HashMap<String, String>> contactList;

    private String url = "";

    String titre;
    Double lat;
    Double lng;

    String addresse1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmarker_layout);

        Button btn = (Button) findViewById(R.id.ajouter);
        final EditText titremarker = (EditText) findViewById(R.id.titredumarker);
        final EditText adressemarker = (EditText) findViewById(R.id.adressedumarker);

        final TextView mTextView = (TextView) findViewById(R.id.text);

        contactList = new ArrayList<>();

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("===== CLIQUE BOUTON ======");
                final String adresse = adressemarker.getText().toString();
                titre = titremarker.getText().toString();
                try {
                    addresse1 = URLEncoder.encode(adresse, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("adresse",adresse);
                url = "https://maps.googleapis.com/maps/api/geocode/json?address="+addresse1+"+CA&key=AIzaSyCQ6OSm0vn9i78Nbq6_uF_PbstsWJboPMk";
                //url ="https://maps.googleapis.com/maps/api/geocode/json?address=93%20rue%20de%20bagnolet+CA&key=AIzaSyCQ6OSm0vn9i78Nbq6_uF_PbstsWJboPMk";
                new GetLongLat().execute();
               }
       });
    }

    private class GetLongLat extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(com.example.volley_superhero_recyvlerview.AddMarker.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            com.example.volley_superhero_recyvlerview.HttpHandler sh = new com.example.volley_superhero_recyvlerview.HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
//            System.out.println(jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject results = jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                    System.out.println(results);
                    lng = results.getDouble("lng");
                    lat = results.getDouble("lat");


                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
//            ListAdapter adapter = new SimpleAdapter(
//                    AddMarker.this, contactList,
//                    R.layout.list_item, new String[]{"name", "email",
//                    "mobile"}, new int[]{R.id.name,
//                    R.id.email, R.id.mobile});
//            lv.setAdapter(adapter);
            insert_SQLlite(lng, lat, titre);
        }

        public void insert_SQLlite(Double lng, Double lat, String titre){
            String str = "INSERT INTO Marker(Lat,Lng,Titre) VALUES ("+lat+", "+lng+", '"+ titre +"')";
            System.out.println(str);
            com.example.volley_superhero_recyvlerview.MapsActivity.mydatabase.execSQL(str);
            onBackPressed();
        }

    }









}
