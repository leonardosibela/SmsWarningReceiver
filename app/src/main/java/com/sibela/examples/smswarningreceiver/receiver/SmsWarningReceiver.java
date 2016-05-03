package com.sibela.examples.smswarningreceiver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;

import com.sibela.examples.smswarningreceiver.WarningActivity;

public class SmsWarningReceiver extends BroadcastReceiver {

    public static final String APP_PACKAGE = "com.sibela.examples.smswarningreceiver";

    public SmsWarningReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] messages = (Object[]) bundle.get("pdus");
        byte[] message = (byte[]) messages[0];
        SmsMessage smsMessage = SmsMessage.createFromPdu(message);
        if (smsMessage.getMessageBody().toLowerCase().trim().equals("teste")) {
            Intent i = new Intent();
            i.setClassName(APP_PACKAGE, warningActivityPackageAndName());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    private String warningActivityPackageAndName() {
        return APP_PACKAGE + "." + WarningActivity.class.getSimpleName();
    }
}