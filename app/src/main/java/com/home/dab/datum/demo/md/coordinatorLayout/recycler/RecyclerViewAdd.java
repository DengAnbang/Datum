package com.home.dab.datum.demo.md.coordinatorLayout.recycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.home.dab.datum.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdd extends AppCompatActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_add);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_add);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            strings.add(i + "");
        }
        HeadApt headApt = new HeadApt(this, strings);
        mRecyclerView.setAdapter(headApt);

    }
}
