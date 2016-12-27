package com.home.dab.datum.demo.md;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.home.dab.datum.R;
import com.home.dab.datum.demo.md.coordinatorLayout.CoordinatorLayoutTest;
import com.home.dab.datum.demo.md.textInputLayout.TextInputLayout;

public class MaterialDesign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
    }

    public void coordinatorLayout(View view) {
        startActivity(new Intent(MaterialDesign.this, CoordinatorLayoutTest.class));
    }

    public void textInputLayout(View view) {
        startActivity(new Intent(MaterialDesign.this, TextInputLayout.class));
    }
}
