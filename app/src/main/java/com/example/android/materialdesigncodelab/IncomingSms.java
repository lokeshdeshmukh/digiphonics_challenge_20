package com.example.android.materialdesigncodelab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static com.example.android.materialdesigncodelab.ListContentFragment.context;

/**
 * Created by Timmy on 2/5/17.
 */

public class IncomingSms extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    // Retrieves a map of extended data from the intent.

    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        try {
            if(bundle!=null){
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                    if(message.equalsIgnoreCase("+91"+PhoneRegistration.number)){
                        SharedPreferences sharedPreferences = context.getSharedPreferences("DIGI_PREF",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("number_verified","Yes");
                        editor.apply();
                        numberRegistered(true,context);
                    }

                }
            }
        }catch (Exception e){

        }
    }

    public void numberRegistered(boolean numReg, Context context){
        if(numReg){
            Toast.makeText(context, "Verification Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClassName("com.example.android.materialdesigncodelab","com.example.android.materialdesigncodelab.MainActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Could not Verify, try again", Toast.LENGTH_SHORT).show();

        }
    }

}
