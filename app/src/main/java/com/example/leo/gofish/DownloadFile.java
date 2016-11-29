package com.example.leo.gofish;

import android.app.ProgressDialog;
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

/**
 * Created by Karlo on 11/15/2016.
 */

class DownloadFile extends AsyncTask<Station, Void, Station> {
    private Context mContext;
    private static final String TAG = "Downloader";
    private ProgressDialog mProgressDialog;
    public AsyncDLResponse delegate = null;

    public DownloadFile(Context ctx) {
        mContext = ctx;
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG, "Initiate download...");
    }

    @Override
    protected Station doInBackground(Station... objects) {
        Station s = (Station) objects[0];
        try {
            File dir = new File(mContext.getFilesDir() + "/stations/hourly");

            if(dir.exists() == false) { // checks if directory is available otherwise create one
                dir.mkdirs();
            }

            String timeStamp = getTimestamp();
            Log.i(TAG, "Timestamp: " + timeStamp);

            String url = s.getProvince() + "_" +
                    s.getId() + "_hourly_hydrometric.csv"; // url file name

            String fileName =  timeStamp + "_" +
                    s.getProvince() + "_" +
                    s.getId() + "_hourly_hydrometric.csv"; // filename to be saved
            s.setFileName(fileName);
            File file = new File(dir, fileName);
            if(!file.exists() && !file.isFile()) {
               // mProgressDialog = ProgressDialog.show(mContext, "Download", "Downloading station data...", true); // show download progress bar

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
            Log.i(TAG, "File not found: " + e.toString());
        }
        catch (IOException e) {
            Log.i(TAG, "File not found: " + e.toString());
        }
        return s;
    }

    @Override
    protected void onPostExecute(Station s) {
        super.onPostExecute(s);
        delegate.onTaskComplete(s);
//        mProgressDialog.dismiss();
        Log.i(TAG, "Download successful!");
    }

    public String getTimestamp() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        return "" + year + month + day + hour;
    }
}
