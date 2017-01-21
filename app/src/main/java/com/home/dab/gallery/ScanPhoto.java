package com.home.dab.gallery;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.SparseArray;

import com.home.dab.gallery.bean.AlbumFolder;
import com.home.dab.gallery.bean.AlbumPicture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DAB on 2017/1/15 14:27.
 */

public class ScanPhoto {

    private static final String[] FIELDS = {
            //文件夹的ID
            MediaStore.Images.Media.BUCKET_ID,
            // 文件夹名称
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            // 图片ID
            MediaStore.Images.Media._ID,
            // 图片名称
            MediaStore.Images.Media.DISPLAY_NAME,
            // 图片路径
            MediaStore.Images.Media.DATA
    };

    public static List<AlbumFolder> scan(Context context) {
        Cursor cursor = null;
        try {
            List<AlbumFolder> albumFolders = new ArrayList<>();
            SparseArray<AlbumFolder> folderSparseArray = new SparseArray<>();
            cursor = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, FIELDS);
            while (cursor.moveToNext()) {
                int folderId = cursor.getInt(0);
                String folderName = cursor.getString(1);
                int photoId = cursor.getInt(2);
                String photoName = cursor.getString(3);
                String photoPath = cursor.getString(4);
                AlbumPicture albumPhoto = new AlbumPicture(photoId, photoName, photoPath, false);
                AlbumFolder albumFolder = folderSparseArray.get(folderId);
                if (albumFolder == null) {
                    ArrayList<AlbumPicture> albumPictures = new ArrayList<>();
                    albumFolder = new AlbumFolder(folderId, folderName, albumPictures);
                    folderSparseArray.put(folderId, albumFolder);
                }
                albumFolder.getAlbumPictures().add(albumPhoto);
            }
            for (int i = 0; i < folderSparseArray.size(); i++) {
                AlbumFolder albumFolder = folderSparseArray.get(folderSparseArray.keyAt(i));
                albumFolders.add(albumFolder);
            }
            return albumFolders;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;
    }
}
