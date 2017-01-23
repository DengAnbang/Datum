package com.home.dab.datum.demo.recyclerDemo.picker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.home.dab.datum.R;

import java.util.ArrayList;
import java.util.List;

public class PickerRecyclerView extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private static final String TAG = "PickerRecyclerView";
    private List<String> mStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_picker);
        mStrings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mStrings.add(i + "");
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.e(TAG, "onCreate: ");
        PickerRecyclerAdapter pickerRecyclerAdapter = new PickerRecyclerAdapter(mRecyclerView, mStrings);
        mRecyclerView.setAdapter(pickerRecyclerAdapter);
//        pickerRecyclerAdapter.setStringList(mStrings);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }
}
