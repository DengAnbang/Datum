package com.home.dab.datum.demo.recyclerDemo.itemSpread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.home.dab.datum.R;
import com.home.dab.datum.demo.recyclerDemo.DemoApt;
import com.home.dab.datum.demo.recyclerDemo.DemoBean;
import com.home.dab.datum.demo.recyclerDemo.SuspendDecoration;

import java.util.ArrayList;
import java.util.List;

public class ItemSpread extends AppCompatActivity {
    private RecyclerView mRecyclerDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_spread);
        mRecyclerDemo = (RecyclerView) findViewById(R.id.recycler_dome);
        mRecyclerDemo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<DemoBean> demoBeen = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            demoBeen.add(new DemoBean(i + "", i));
        }
        DemoApt demoApt = new DemoApt(this, demoBeen);
        mRecyclerDemo.setAdapter(demoApt);
        mRecyclerDemo.addItemDecoration(new SuspendDecoration(100, 50, SuspendDecoration.TITLE_GRAVITY_CENTER) {
            @Override
            public boolean isNewGroup(int priorGroupId, int nowGroupId) {
                return demoBeen.get(priorGroupId).getNum() != demoBeen.get(nowGroupId).getNum();
            }

            @Override
            public String showTitle(int position) {
                return demoBeen.get(position).getNum() + "";
            }
        });
    }
}
