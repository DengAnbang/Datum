package com.home.dab.datum.demo.md.coordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.home.dab.datum.R;
import com.home.dab.datum.demo.md.coordinatorLayout.head.HideHead;
import com.home.dab.datum.demo.md.coordinatorLayout.recycler.RecyclerViewAdd;
import com.home.dab.datum.demo.md.coordinatorLayout.simple.SimpleCL;

public class CoordinatorLayoutTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_test);

    }

    public void simple(View view) {
        startActivity(new Intent(CoordinatorLayoutTest.this, SimpleCL.class));
    }

    public void hideHead(View view) {
        startActivity(new Intent(CoordinatorLayoutTest.this, HideHead.class));
    }

    public void recycler(View view) {
        startActivity(new Intent(CoordinatorLayoutTest.this, RecyclerViewAdd.class));
    }
}
