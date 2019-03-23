package com.dicoding.ridho_afni.fcmpushnotification;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.ridho_afni.fcmpushnotification.Model.MyResponse;
import com.dicoding.ridho_afni.fcmpushnotification.Model.Notification;
import com.dicoding.ridho_afni.fcmpushnotification.Model.Sender;
import com.dicoding.ridho_afni.fcmpushnotification.Remote.APIService;
import com.dicoding.ridho_afni.fcmpushnotification.app.Config;
import com.dicoding.ridho_afni.fcmpushnotification.utils.NotificationUtils;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

   MaterialEditText metTitle, metContent;
   Button btnPushNotif;
   APIService apiService;
   private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metTitle = (MaterialEditText) findViewById(R.id.etTitle);
        metContent = (MaterialEditText) findViewById(R.id.etContent);
        btnPushNotif = (Button) findViewById(R.id.btnPushNotif);

        apiService = Common.getFCMCLient();

        btnPushNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(metTitle.getText().toString())){
                    Toast.makeText(MainActivity.this, "quired", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(metContent.getText().toString())){
                    Toast.makeText(MainActivity.this, "quired", Toast.LENGTH_SHORT).show();
                }else {
                    // send request
                    Notification notification = new Notification(metTitle.getText().toString(), metContent.getText().toString());
                    Sender sender = new Sender(Common.currentToken, notification); // send to itself
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.body().success == 1) {
                                        MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.notification);
                                        mPlayer.start();
                                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {
                                    Log.d("Error", t.getMessage());
                                }
                            });
                }
            }
        });

        Common.currentToken = FirebaseInstanceId.getInstance().getToken();

        Log.d("MY TOKEN ", Common.currentToken);

    }

    // Playing notification sound
    public void playNotificationSound() {
        try {
            final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification.mp3");
//            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                    + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
