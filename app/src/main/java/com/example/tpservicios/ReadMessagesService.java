package com.example.tpservicios;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import java.util.Date;

public class ReadMessagesService extends Service {
    private static boolean runFlag;

    public ReadMessagesService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flag, int StartId){
        Thread worker = new Thread(new MessageReader());
        worker.start();
        runFlag=true;
        return START_STICKY;
    }


    @Override
    public void onDestroy(){
        runFlag=false;
        Log.d("Salida", "Servicio detenido");
    }


    @Override
    public IBinder onBind(Intent intent) { return null; }


    private class MessageReader implements Runnable{
        private String sender;
        private String sms_Body;
        private String date;

        public void run(){
            Uri sms = Uri.parse("content://sms");
            int sms_counter = 1;
            Cursor c_sms = getContentResolver().query(sms, null,null,null, "date DESC");
            if(c_sms.getCount()>0){
                while(runFlag==true){
                    try{
                        int sms_nbr = c_sms.getCount();
                        if (sms_nbr > 0){
                            if (sms_nbr > 5){ sms_nbr = 5; }
                            while (c_sms.moveToNext() && sms_counter<=sms_nbr){
                                sms_counter++;
                                date = c_sms.getString(c_sms.getColumnIndexOrThrow(Telephony.Sms.DATE));
                                sender = c_sms.getString(c_sms.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                                sms_Body = c_sms.getString(c_sms.getColumnIndexOrThrow(Telephony.Sms.BODY));
                                Log.d("Fecha", new Date(Long.parseLong(date)).toString());
                                Log.d("Remitente", sender);
                                Log.d("Cuerpo de SMS", sms_Body);
                            }
                        }else{
                            Log.d("Salida", "No hay mensajes SMS para mostrar");
                        }
                        Thread.sleep(9000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}