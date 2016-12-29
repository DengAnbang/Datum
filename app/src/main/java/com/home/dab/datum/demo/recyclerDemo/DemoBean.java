package com.home.dab.datum.demo.recyclerDemo;

/**
 * Created by DAB on 2016/12/29 11:18.
 */

public class DemoBean {
    private String name;
    private int num;

    public DemoBean(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public int getNum() {
        return num / 10;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
