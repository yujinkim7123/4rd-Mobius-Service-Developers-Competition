package com.lifefriends.onem2m;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Created by araha on 2016-09-13.
 */
public class MqttClientRequest {

    @NotNull
    @Contract(pure = true)
    public static String notificationResponse(String response) {
        String responseMessage =
                "{\"rsc\":\"2000\",\n" +
                        "\"rqi\":\""+ response + "\",\n" +
                        "\"pc\":\"\"}";

        return responseMessage;
    }
}
