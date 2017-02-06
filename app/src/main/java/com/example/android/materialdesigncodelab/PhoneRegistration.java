package com.example.android.materialdesigncodelab;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.android.materialdesigncodelab.R.drawable.c;

public class PhoneRegistration extends AppCompatActivity {

    Button update;
    EditText editPhone;
    Context context;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    public static String number = "";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_registration);

        update = (Button)findViewById(R.id.updateButton);
        editPhone = (EditText)findViewById(R.id.ephone);
        context = this;
        sharedPreferences = context.getSharedPreferences("DIGI_PREF", Context.MODE_PRIVATE);
        String number_verfied = sharedPreferences.getString("number_verified","No");

        if(number_verfied.equalsIgnoreCase("Yes")){
            Intent intent = new Intent(context,MainActivity.class);
            startActivity(intent);
            finish();
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = editPhone.getText().toString().trim();
                if(!number.equalsIgnoreCase("")){
                    SmsManager sms= SmsManager.getDefault();
                    PendingIntent piSent=PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT"), 0);
                    PendingIntent piDelivered=PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED"), 0);
                    sms.sendTextMessage(number, null, "+91"+number, piSent, piDelivered);
                } else {
                    Snackbar.make(findViewById(android.R.id.content),"Kindly Enter the number",Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    public void onResume() {
        super.onResume();
        smsSentReceiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                // TODO Auto-generated method stub
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS has been sent", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic Failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No Service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio Off", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

            }
        };
        smsDeliveredReceiver=new BroadcastReceiver() {
            public void onReceive(Context arg0, Intent arg1) {
                // TODO Auto-generated method stub
                switch(getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS Delivered", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        registerReceiver(smsSentReceiver, new IntentFilter("SMS_SENT"));
        registerReceiver(smsDeliveredReceiver, new IntentFilter("SMS_DELIVERED"));


    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }

    public void numberRegistered(boolean numReg){
        if(numReg){
            Toast.makeText(getBaseContext(), "Verification Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context,MainActivity.class);
            context.startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), "Could not Verify, try again", Toast.LENGTH_SHORT).show();

        }
    }
}
