package com.cnvfirj.shared_examples;

import io.flutter.embedding.android.FlutterActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

    private String sharedText;
    private static final String CHANNEL = "app.channel.shared.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if(type != null)Log.d("TAG_TYPE",type);
        Log.d("TAG_ACTION",action);
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/*".equals(type)) {
                handleSendText(intent);
            }else if("image/*".equals(type)){
                handleSendImage(intent);
            }
        }
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                            if (call.method.contentEquals("getSharedText")) {
//
                            } result.success(sharedText);
//
//                                sharedText = null;
                        }
                );
    }

    void handleSendText(Intent intent) {
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if(sharedText!=null)Log.d("TAG TEXT",sharedText);
        else Log.d("TAG TEXT","null");
    }

    void handleSendImage(Intent intent){
        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if(uri!=null)Log.d("TAG IMG",uri.toString());
        else Log.d("TAG IMG", "null");
    }

}
