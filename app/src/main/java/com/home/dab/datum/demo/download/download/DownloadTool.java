package com.home.dab.datum.demo.download.download;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by DAB on 2016/12/12 11:03.
 */

public class DownloadTool {
    //保存文件
    public static void saveFile(long startIndex, InputStream inputStream, String fileStoreDir, String fileName) throws IOException {
        byte[] buf = new byte[2048];
        int len;
        try {
            File dir = new File(fileStoreDir);
            if (!dir.exists()) {
                boolean mkdirs = dir.mkdirs();

            }
            File file = new File(dir, fileName);
//            if (!file.exists()) {
//                boolean mkdirs = file.mkdirs();
//
//            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(startIndex);
            while ((len = inputStream.read(buf)) != -1) {
                raf.write(buf, 0, len);
            }
            raf.close();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
