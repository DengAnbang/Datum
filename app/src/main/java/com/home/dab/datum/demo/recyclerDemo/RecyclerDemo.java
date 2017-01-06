package com.home.dab.datum.demo.recyclerDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.home.dab.datum.R;
import com.home.dab.datum.demo.recyclerDemo.itemSpread.ItemSpread;
import com.home.dab.datum.demo.recyclerDemo.nested.RecyclerNested;

public class RecyclerDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_demo);

    }

    public void itemSpread(View view) {
        startActivity(new Intent(RecyclerDemo.this, ItemSpread.class));
    }

    public void nested(View view) {
        startActivity(new Intent(RecyclerDemo.this, RecyclerNested.class));
    }
}
