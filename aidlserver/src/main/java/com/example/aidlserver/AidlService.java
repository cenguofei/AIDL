package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidlserver.IDemoService;


public class AidlService extends Service {
    private final static String TAG = "aidlDemo";

    public AidlService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "server:[onCreate]");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i(TAG, "server:[onBind]");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "server:[onUnbind]");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "server:[onDestroy]");
    }

    static class MyBinder extends IDemoService.Stub {
        private String mName = "";

        public void setName(String name) throws RemoteException{
            Log.i(TAG, "server:[setName]");
            mName = name;
        }

        @Override
        public String getName() throws RemoteException {
            Log.i(TAG, "server:[getName]");
            return mName;
        }
    }
}
