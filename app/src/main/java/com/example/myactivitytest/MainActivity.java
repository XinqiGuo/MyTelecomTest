package com.example.myactivitytest;

import static com.example.myactivitytest.ActionUtils.BROADCAST_RECEIVER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    //声明代表手机状态的集合
    private List<String> statusValues = new ArrayList<>();

    private TelecomManager telecomManager;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate : ");
        setContentView(R.layout.activity_main);
        initView();
        initBroadcast();
//        initCall();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart : ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume : ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause : ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart : ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop : ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy : ");
    }

    private void initBroadcast() {
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_RECEIVER);
        registerReceiver(myBroadcastReceiver, filter);
    }

    private void initView() {
        Log.i(TAG, "initView()");
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.myactivitytest.MY_BORADCAST");
                intent.setPackage(getPackageName());
                sendBroadcast(intent);
            }
        });
    }

    public void sendBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction(BROADCAST_RECEIVER);
        sendBroadcast(intent);
    }

    private void initCall() {
        Uri uri = Uri.fromParts("tel", "12345", null);
        Bundle extras = new Bundle();
        extras.putBoolean(TelecomManager.EXTRA_START_CALL_WITH_SPEAKERPHONE, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                telecomManager.placeCall(uri, extras);
            }
        }

    }

    @Override
    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0x123 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            //获取系统的TelephonyManager对象
            TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        }
        //获取代表状态名称的数组
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    public void goToMyActivity(View view) {
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
    }
}