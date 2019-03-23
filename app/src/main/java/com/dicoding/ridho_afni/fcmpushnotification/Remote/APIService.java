package com.dicoding.ridho_afni.fcmpushnotification.Remote;

import com.dicoding.ridho_afni.fcmpushnotification.Model.MyResponse;
import com.dicoding.ridho_afni.fcmpushnotification.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAe5kbj-s:APA91bEJQ2MPpJ5rrwEWq_CsLmdYzqNKsGnuSZtSHkdMtw7HLvfW_GnAe-MLY3xkFFdjAaHtrDEF2PUNG33jzfG6cZp-F6Ll1NyVx6rrqg6NgBfUcBlXoMKAGr2aOv-Z_pImUSsGtLIQ"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
