package com.example.secondproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver
{
    private final String TAG = BroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getExtras().getString("xyi");
        Toast.makeText(context, "Broadcast Received with data " + data, Toast.LENGTH_LONG).show();

    }

}
