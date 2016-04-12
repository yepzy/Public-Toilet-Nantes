package com.iut_nantes.yanis_cyril.toilets_locator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public List<Toilets> list_toilets;
    private LatLng myLocation;
    private ListView listView;
    private Toilet_list adapt;
    private int nbResult;

    private String type;
    private String auto;
    private String access;

    public int curToilets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toilet_info_display);

        ((ImageButton) findViewById(R.id.imageButton)).setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listResult);
        list_toilets = new ArrayList<Toilets>();

        Bundle extra = getIntent().getExtras();

        nbResult = Integer.valueOf(extra.getString("nbResult"));
        myLocation = (LatLng) extra.get("localisation");
        type = (String) extra.get("type");
        auto = (String) extra.get("automatique");
        access = (String) extra.get("accessibilite");

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://data.nantes.fr/api/publication/24440040400129_NM_NM_00170/Toilettes_publiques_nm_STBL/content?limit=" + nbResult + "&filter={\"_l\":{\"$near\":[" + myLocation.latitude + "," + myLocation.longitude + "]}";


        if (!type.equalsIgnoreCase("null")) {
            url = url + ",\"TYPE\":{\"$eq\":\"" + type + "\"}";
        }
        if (!auto.equalsIgnoreCase("null")) {
            url = url + ",\"AUTOMATIQUE\":{\"$eq\":\"" + auto.toLowerCase() + "\"}";
        }
        if (!access.equalsIgnoreCase("null")) {
            url = url + ",\"ACCESSIBILITE_PMR\":{\"$eq\":\"" + access.toLowerCase() + "\"}";
        }

        url = url + "}";

        Log.i("requestAPI", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            traitementJson(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResultActivity.this, "Erreur :" + error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        queue.add(stringRequest);
        queue.start();
        adapt = new Toilet_list(this, R.layout.toilets_list, list_toilets);
        listView.setAdapter(adapt);
        listView.setOnItemClickListener(this);
    }

    private void traitementJson(JSONObject json) {
        try {
            JSONArray jsonArray = json.getJSONArray("data");
            JSONObject curObject = null;
            RelativeLayout loading = (RelativeLayout) findViewById(R.id.loadingPanel);
            loading.setVisibility(View.INVISIBLE);
            for (int i = 0; i < jsonArray.length(); i++) {
                curObject = jsonArray.getJSONObject(i);
                LatLng itLocation = new LatLng(curObject.getJSONArray("_l").getDouble(0), curObject.getJSONArray("_l").getDouble(1));
                list_toilets.add(new Toilets(
                        i,
                        curObject.getString("ADRESSE"),
                        curObject.getString("COMMUNE"),
                        curObject.getString("POLE"),
                        curObject.getString("TYPE"),
                        curObject.getString("AUTOMATIQUE"),
                        curObject.getString("ACCESSIBILITE_PMR"),
                        curObject.getString("INFOS_HORAIRES"),
                        itLocation,
                        curObject.getJSONObject("geo").getString("name")
                ));
                listView.invalidateViews();
            }
        } catch (JSONException e) {
            Log.e("jsonError", e.getMessage());
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toilets sel = (Toilets) listView.getItemAtPosition(position);
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + sel.get_location().latitude + "," + sel.get_location().longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    @Override
    public void onClick(View v) {

        Intent help = new Intent(ResultActivity.this, Help.class);
        startActivity(help);
    }
}
