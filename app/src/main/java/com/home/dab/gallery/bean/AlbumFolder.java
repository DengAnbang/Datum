package com.home.dab.gallery.bean;

import java.util.ArrayList;

/**
 * Created by DAB on 2017/1/15 18:50.
 */

public class AlbumFolder {
    private int id;
    private String name;
    private ArrayList<AlbumPicture> mAlbumPictures;

    public AlbumFolder(int id, String name, ArrayList<AlbumPicture> albumPictures) {
        this.id = id;
        this.name = name;
        mAlbumPictures = albumPictures;
    }

    private AlbumPicture getCoverPhoto() {
        if (null != mAlbumPictures && mAlbumPictures.size() > 0) {
            return mAlbumPictures.get(0);
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<AlbumPicture> getAlbumPictures() {
        return mAlbumPictures;
    }

    public void setAlbumPictures(ArrayList<AlbumPicture> albumPictures) {
        mAlbumPictures = albumPictures;
    }
}
