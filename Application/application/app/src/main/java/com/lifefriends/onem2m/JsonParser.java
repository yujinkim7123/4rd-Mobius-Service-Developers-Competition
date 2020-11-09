package com.lifefriends.onem2m;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    String TAG = "JasonParser";

    public String getContainerContent(String msg){
        String con = "";
        try {
            JSONObject jsonObject = new JSONObject(msg);
            con = jsonObject.getJSONObject("m2m:cin").getString("con");
            Log.d(TAG, "Content is " + con);
        } catch (JSONException e) {
            Log.e(TAG, "JSONObject error!");
        }
        return con;
    }

    public String getRatioInContainer(String msg){
        String con = "";
        String ratio = "";
        try {
            JSONObject jsonObject = new JSONObject(msg);
            con = jsonObject.getJSONObject("m2m:rsp").getString("m2m:cin");
            JSONObject rsp = jsonObject.getJSONObject("m2m:rsp");
            JSONArray cin = rsp.getJSONArray("m2m:cin");

            int num_pos = 0;    // num of "1" in cnt
            int num_neg = 0;    // num of "0" in cnt
            for (int i =0; i<cin.length(); i++){
                JSONObject con_ = (JSONObject) cin.get(i);
                Log.d(TAG, con_.getString("con"));
                if(con_.getString("con").contains("-1")){
                    num_neg++;
                }
                else if(con_.getString("con").contains("1")){
                    num_pos++;
                }
            }
            ratio = "" + num_neg + ", " + num_pos;

        } catch (JSONException e) {
            Log.e(TAG, "JSONObject error!");
        }
        return ratio;
    }
}
