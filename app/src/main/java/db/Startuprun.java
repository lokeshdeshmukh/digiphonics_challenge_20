package db;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Startuprun extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

     Intent myIntent = new Intent(context, Backgroundservice.class);
     context.startService(myIntent);

    }
}