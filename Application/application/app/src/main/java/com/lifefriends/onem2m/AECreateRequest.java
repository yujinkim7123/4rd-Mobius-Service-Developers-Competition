package com.lifefriends.onem2m;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AECreateRequest extends Thread{
    private final Logger LOG = Logger.getLogger(AECreateRequest.class.getName());
    String TAG = AECreateRequest.class.getName();
    private IReceived receiver;
    int responseCode = 0;
    public ApplicationEntityObject applicationEntity;
    public void setReceiver(IReceived handler){this.receiver = handler;}
    public AECreateRequest(AE ae){
        applicationEntity = new ApplicationEntityObject();
        applicationEntity.setResourceName(ae.getappName());
        Log.d(TAG, ae.getappName());
    }


    public void run(CSEBase csebase){
        try{
            String sb = csebase.getServiceUrl();
            URL mUrl = new URL(sb);

            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);

        }catch (Exception exp) {
            LOG.log(Level.SEVERE, exp.getMessage());
        }
    }
}
