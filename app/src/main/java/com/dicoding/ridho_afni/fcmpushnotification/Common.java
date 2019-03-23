package com.dicoding.ridho_afni.fcmpushnotification;

import com.dicoding.ridho_afni.fcmpushnotification.Remote.APIService;
import com.dicoding.ridho_afni.fcmpushnotification.Remote.RetrofitClient;

public class Common {
    public static String currentToken = "";

    private static String baseURL = "https://fcm.googleapis.com/";

    public static APIService getFCMCLient(){
        return RetrofitClient.getClient(baseURL).create(APIService.class);
    }
}
