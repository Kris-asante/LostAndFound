package com.example.krisperezcyrus.lostfound;

import com.example.krisperezcyrus.lostfound.Remote.APIService;
import com.example.krisperezcyrus.lostfound.Remote.RetrofitClient;

public class Common {
    public static String currentToken = "";

    private static String baseUrl ="https://fcm.googleapis.com";

    public static APIService getFCMClient()
    {

        return RetrofitClient.getClient(baseUrl).create(APIService.class);
    }
}
