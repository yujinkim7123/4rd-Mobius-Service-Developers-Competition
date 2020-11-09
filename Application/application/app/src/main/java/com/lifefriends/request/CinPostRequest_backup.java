package com.lifefriends.request;

import android.content.Context;

import com.lifefriends.R;
import com.lifefriends.onem2m.ContentInstanceObject;
import com.lifefriends.onem2m.IReceived;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CinPostRequest_backup extends Thread {
    private final Logger LOG = Logger.getLogger(CinPostRequest_backup.class.getName());
    private IReceived receiver;
    private String cnt_name = "";
    private Context context;
    public HttpURLConnection conn;

    public ContentInstanceObject contentInstance;
    public CinPostRequest_backup(Context context, String cnt_name, String con){
        this.context = context;
        this.cnt_name = cnt_name;       // set container

        contentInstance = new ContentInstanceObject();
        contentInstance.setContent(con);    // set contents
    }
    public void setReceiver(IReceived handler) { this.receiver = handler; }

    @Override
    public void run(){
        try {
            String _url = "http://"+ context.getString(R.string.cse_addr) +":"+ context.getString(R.string.cse_port)+"/"+ context.getString(R.string.cse_name)
                    + "/" + context.getString(R.string.ae_name) + "/" + cnt_name;

            URL url = new URL(_url);


            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);

            conn.setRequestProperty("Accept", "application/xml");
            conn.setRequestProperty("Content-Type", "application/vnd.onem2m-res+xml;ty=4");
            conn.setRequestProperty("locale", "ko");
            conn.setRequestProperty("X-M2M-RI", "12345");

            // need to change code
            conn.setRequestProperty("X-M2M-Origin", context.getString(R.string.ae_id) );

            String reqContent = contentInstance.makeXML();
            conn.setRequestProperty("Content-Length", String.valueOf(reqContent.length()));

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.write(reqContent.getBytes());
            dos.flush();
            dos.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String resp = "";
            String strLine="";
            while ((strLine = in.readLine()) != null) {
                resp += strLine;
            }
            if (resp != "") {
                receiver.getResponseBody(resp);
            }
            conn.disconnect();

        } catch (Exception exp) {
            LOG.log(Level.SEVERE, exp.getMessage());
        }
    }
}
