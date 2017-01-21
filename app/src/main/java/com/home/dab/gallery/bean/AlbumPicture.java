package com.home.dab.gallery.bean;

/**
 * Created by DAB on 2017/1/15 19:41.
 */

public class AlbumPicture {
    private int id;
    private String name;
    private String path;
    private boolean isChoice;

    public AlbumPicture(int id, String name, String path, boolean isChoice) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.isChoice = isChoice;
    }
}
