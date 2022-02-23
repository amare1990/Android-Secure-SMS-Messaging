package com.example.encdecsms;


import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;

import android.os.Bundle;

import android.telephony.SmsMessage;


public class SmsBroadCastReceiver extends BroadcastReceiver {


    @Override

    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();


        // Specify the bundle to get object based on SMS protocol "pdus"

        Object[] object = (Object[]) bundle.get("pdus");

        SmsMessage sms[] = new SmsMessage[object.length];

        Intent in=new Intent(context,DisplaySMSActivity.class);

        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        in.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        String msgContent = "";

        String originNum = "";

        StringBuffer sb = new StringBuffer();


        for (int i = 0; i < object.length; i++) {


            sms[i] = SmsMessage.createFromPdu((byte[]) object[i]);


            // get the received SMS content

            msgContent = sms[i].getDisplayMessageBody();



            //get the sender phone number

            originNum = sms[i].getDisplayOriginatingAddress();



            //aggregate the messages together when long message are fragmented

            sb.append(msgContent);



            //abort broadcast to cellphone inbox

            abortBroadcast();


        }



        //fill the sender's phone number into Intent

        in.putExtra("originNum", originNum);



        //fill the entire message body into Intent

        in.putExtra("msgContent", new String(sb));



        //start the DisplaySMSActivity.java

        context.startActivity(in);



    }


}