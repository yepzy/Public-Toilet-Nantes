package com.iut_nantes.yanis_cyril.toilets_locator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleApiClient myGoogleApiClient;
    private LatLng localisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();


        ((Button) findViewById(R.id.searchButton)).setOnClickListener(this);

        List<String> listType = new ArrayList<String>();
        listType.add("Indifférent");
        listType.add("Mobilier");
        listType.add("Bâtiment");
        ArrayAdapter<String> adaptSpinType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listType);
        Spinner spinType = (Spinner) findViewById(R.id.spinType);
        spinType.setAdapter(adaptSpinType);

        List<String> listAuto = new ArrayList<String>();
        listAuto.add("Indifférent");
        listAuto.add("Oui");
        listAuto.add("Non");
        ArrayAdapter<String> adaptSpinAuto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listAuto);
        Spinner spinAuto = (Spinner) findViewById(R.id.spinAutomatique);
        spinAuto.setAdapter(adaptSpinAuto);

        List<String> listAccess = new ArrayList<String>();
        listAccess.add("Indifférent");
        listAccess.add("Oui");
        listAccess.add("Non");
        ArrayAdapter<String> adaptSpinAccess = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listAccess);
        Spinner spinAccess = (Spinner) findViewById(R.id.spinAccessibilite);
        spinAccess.setAdapter(adaptSpinAccess);

        List<String> listNbResult = new ArrayList<String>();
        listNbResult.add("5");
        listNbResult.add("10");
        listNbResult.add("15");
        listNbResult.add("20");
        listNbResult.add("30");
        listNbResult.add("50");
        listNbResult.add("Tous");
        ArrayAdapter<String> adaptSpinNbResult = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listNbResult);
        Spinner spinNbResult = (Spinner) findViewById(R.id.spinNbResult);
        spinNbResult.setAdapter(adaptSpinNbResult);

        location(getCurrentFocus());
    }

    int PLACE_PICKER_REQUEST = 1;

    @Override
    public void onClick(View v) {
        String type = ((Spinner) findViewById(R.id.spinType)).getSelectedItem().toString();
        if (type.equals("Indifférent")) {
            type = "null";
        }
        if (type.equals("Bâtiment")) {
            type = "Bâti";
        }
        Log.i("value type", type);

        String auto = ((Spinner) findViewById(R.id.spinAutomatique)).getSelectedItem().toString();
        if (auto.equals("Indifférent")) {
            auto = "null";
        }
        Log.i("value automatique", auto);

        String access = ((Spinner) findViewById(R.id.spinAccessibilite)).getSelectedItem().toString();
        if (access.equals("Indifférent")) {
            access = "null";
        }
        Log.i("value Accessibilité", access);


        String nbResult = ((Spinner) findViewById(R.id.spinNbResult)).getSelectedItem().toString();
        if (nbResult.equals("Tous")) {
            nbResult = "10000";
        }
        int nbResultInt = Integer.valueOf(nbResult);
        Log.i("value nbResult", nbResult);
        if (localisation != null) {
            Intent displayRes = new Intent(MainActivity.this, ResultActivity.class);
            displayRes.putExtra("type", type).putExtra("automatique", auto).putExtra("accessibilite", access).putExtra("nbResult", nbResult).putExtra("localisation", localisation);
            startActivity(displayRes);
        } else {
            Toast.makeText(getApplicationContext(), "Vous n'êtes pas géolocalisé!", Toast.LENGTH_LONG).show();
            location(getCurrentFocus());
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                localisation = place.getLatLng();
            }
        }
    }

    public void location(View v) {
        onStart();
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        myGoogleApiClient.disconnect();
        super.onStop();
    }
}
