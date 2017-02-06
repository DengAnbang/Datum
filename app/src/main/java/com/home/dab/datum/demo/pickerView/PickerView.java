package com.home.dab.datum.demo.pickerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.home.dab.datum.R;

import java.util.ArrayList;
import java.util.List;

public class PickerView extends AppCompatActivity {
    private static final String TAG = "PickerView";
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_picker);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            strings.add(i + "我");
        }
        PickerViewAdapter pickerViewAdapter = new PickerViewAdapter(mRecyclerView,strings);
        mRecyclerView.setAdapter(pickerViewAdapter);

    }
}
