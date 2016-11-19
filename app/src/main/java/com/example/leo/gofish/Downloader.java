package com.example.leo.gofish;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Karlo on 11/15/2016.
 */

class DownloadFile extends AsyncTask<Object, Void, Void> {
    private Context mContext;
    private final String DL_TAG = "Downloader";

    public DownloadFile(Context ctx) {
        mContext = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(DL_TAG, "Initiate download...");
    }

    @Override
    protected Void doInBackground(Object... objects) {
        Station s = (Station) objects[0];
        try {
            File dir = new File(mContext.getFilesDir() + "/stations/hourly");

            if(dir.exists() == false) { // checks if directory is available otherwise create one
                dir.mkdirs();
            }

            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);

            String timestamp = year + "" + month + "" + day + "" + hour;

            Log.i(DL_TAG, "Timestamp: " + timestamp);

            String url = s.getProvince() + "_" +
                    s.getId() + "_hourly_hydrometric.csv"; // url file name

            String fileName =  timestamp + "_" +
                    s.getProvince() + "_" +
                    s.getId() + "_hourly_hydrometric.csv"; // filename to be saved

            File file = new File(dir, fileName);
            if(!file.exists() && !file.isFile()) {

                URL u = new URL("http://dd.weather.gc.ca/hydrometric/csv/ON/hourly/" + url); // url of website with csv files
                URLConnection conn = u.openConnection();
                int contentLength = conn.getContentLength();

                DataInputStream stream = new DataInputStream(u.openStream());

                byte[] buffer = new byte[contentLength];
                stream.readFully(buffer);
                stream.close();

                DataOutputStream dos = new DataOutputStream(new FileOutputStream(file)); // save file
                dos.write(buffer);
                dos.flush();
                dos.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.i(DL_TAG, "File not found: " + e.toString());
        }
        catch (IOException e) {
            Log.i(DL_TAG, "File not found: " + e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i(DL_TAG, "Download successful!");
    }
}
