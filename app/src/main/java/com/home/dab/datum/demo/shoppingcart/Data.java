package com.home.dab.datum.demo.shoppingcart;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by DAB on 2016/12/23 09:56.
 */

public class Data {
    private int selectSum;
    private boolean isSelect;
    private List<Commodity> commodities;
    private int childFirstPosition;
    private int id;
    private String title;

    public Data(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getSelectSum() {
        return selectSum;
    }

    public void setSelectSum(int selectSum) {
        this.selectSum = selectSum;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public List<Commodity> getCommodities() {
        if (commodities == null) {
            Random random = new Random();
            commodities = new ArrayList<>();
            for (int i = 0; i < 5 + random.nextInt(5); i++) {
                commodities.add(new Commodity(id, title, "商品" + i, "url", 0.00, "iconUrl"));
            }
        }
        return commodities;
    }

    public void setCommodities(List<Commodity> commodities) {
        this.commodities = commodities;
    }

    public int getChildFirstPosition() {
        return childFirstPosition;
    }

    public void setChildFirstPosition(int childFirstPosition) {
        this.childFirstPosition = childFirstPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class Commodity {
        private int ownerId;
        private String owenerTitle;
        private String name;
        private String url;
        private double commodity;
        private String iconUrl;

        public Commodity(int ownerId, @Nullable String owenerTitle, @Nullable String name, @Nullable String url, double commodity, @Nullable String iconUrl) {
            this.ownerId = ownerId;
            this.owenerTitle = owenerTitle;
            this.name = name;
            this.url = url;
            this.commodity = commodity;
            this.iconUrl = iconUrl;
        }

        public int getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(int ownerId) {
            this.ownerId = ownerId;
        }

        public String getOwenerTitle() {
            return owenerTitle;
        }

        public void setOwenerTitle(String owenerTitle) {
            this.owenerTitle = owenerTitle;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public double getCommodity() {
            return commodity;
        }

        public void setCommodity(double commodity) {
            this.commodity = commodity;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }
    }
}
