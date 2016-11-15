package com.example.leo.gofish;

import android.content.Context;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Karlo on 11/15/2016.
 */

public class Downloader {
    private static File root = android.os.Environment.getExternalStorageDirectory();
    public static void downloadHourlyFile(Station s) {
        try {
            File dir = new File(root.getAbsolutePath() + "/stations/hourly");

            if(dir.exists() == false) { // checks if directory is available otherwise create one
                dir.mkdirs();
            }

            String fileName = s.getProvince() + "_" + s.getId() + "_hourly_hydrometric.csv"; // filename of csv

            File file = new File(dir, fileName);

            URL u = new URL("http://dd.weather.gc.ca/hydrometric/csv/ON/hourly/" + fileName); // url of website with csv files
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte [] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream dos = new DataOutputStream(new FileOutputStream(file)); // save file
            dos.write(buffer);
            dos.flush();
            dos.close();
        }
        catch (FileNotFoundException e) {

        }
        catch (IOException e) {

        }
    }
}
