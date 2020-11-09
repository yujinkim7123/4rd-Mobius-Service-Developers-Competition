package com.lifefriends.request;

import android.content.Context;
import android.util.Log;

import com.lifefriends.R;
import com.lifefriends.onem2m.IReceived;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CinGetRequest extends Thread {
    private String TAG = "CinGetRequest";
    private final Logger LOG = Logger.getLogger(CinGetRequest.class.getName());
    private IReceived receiver;
    private String cnt_name = "";
    private Context context;


    public CinGetRequest(Context context, String cnt_name) {
        this.context = context;
        this.cnt_name = cnt_name;
    }
    public void setReceiver(IReceived handler) { this.receiver = handler; }

    @Override
    public void run() {
        try {
            String _url = "http://"+ context.getString(R.string.cse_addr) +":"+ context.getString(R.string.cse_port)+"/"+ context.getString(R.string.cse_name)
                    + "/" + context.getString(R.string.ae_name) + "/" + cnt_name + "/latest";
            URL url = new URL(_url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(false);

            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("X-M2M-RI", "12345");
            // need to add code (ae id)
            conn.setRequestProperty("X-M2M-Origin", context.getString(R.string.ae_id) );
            // conn.setRequestProperty("nmtype", "long");
            conn.connect();

            String strResp = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String strLine= "";
            while ((strLine = in.readLine()) != null) {
                strResp += strLine;
            }
            if ( strResp != "" ) {
                receiver.getResponseBody(strResp);
            }
            Log.d(TAG, strResp);// debug
            conn.disconnect();

        } catch (Exception exp) {
            LOG.log(Level.WARNING, exp.getMessage());
        }
    }
}
