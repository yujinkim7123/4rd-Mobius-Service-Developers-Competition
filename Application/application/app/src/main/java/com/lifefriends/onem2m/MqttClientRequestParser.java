package com.lifefriends.onem2m;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Created by araha on 2016-09-13.
 */
public class MqttClientRequestParser {
    private static String TAG = "MqttClientRequestParser";
    // xml parser

    // json parser
    @NotNull
    public static String notificationJsonParse(String message) throws Exception {
        JSONObject json = new JSONObject(message);
        String responserqi = json.getString("rqi");

        return responserqi;
    }

}
