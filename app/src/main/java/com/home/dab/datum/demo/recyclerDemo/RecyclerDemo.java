package com.home.dab.datum.demo.recyclerDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.home.dab.datum.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerDemo extends AppCompatActivity {
    private RecyclerView mRecyclerDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_demo);
        mRecyclerDemo = (RecyclerView) findViewById(R.id.recycler_dome);
        mRecyclerDemo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<DemoBean> demoBeen = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            demoBeen.add(new DemoBean(i + "", i));
        }
        DemoApt demoApt = new DemoApt(this, demoBeen);
//        TextView textView = new TextView(this);
//        textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        textView.setText("hhhhh");
//        textView.setBackgroundColor(Color.BLUE);

        mRecyclerDemo.addItemDecoration(new SuspendDecoration() {
            @Override
            boolean isFirstInGroup(int priorGroupId, int nowGroupId) {
                return demoBeen.get(priorGroupId).getNum() != demoBeen.get(nowGroupId).getNum();
            }
        });

        mRecyclerDemo.setAdapter(demoApt);
    }


}
