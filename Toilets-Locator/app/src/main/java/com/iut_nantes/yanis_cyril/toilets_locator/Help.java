package com.iut_nantes.yanis_cyril.toilets_locator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Help extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
    }

    public void back(View v) {
        finish();
    }
}
