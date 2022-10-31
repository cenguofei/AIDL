package com.example.aidlclient;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aidlserver.IDemoService;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "aidlDemo";
    private IDemoService mDemoService;
    private boolean mIsBinded = false;
    private final ServiceConnection mConn = new ServiceConnection() {
        //当与远程Service绑定后，会回调该方法。
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            Log.i(TAG, "client:[onServiceConnected]componentName=" + componentName);
            mIsBinded = true;
            //得到一个远程Service中的Binder代理，而不是该Binder实例
            mDemoService = IDemoService.Stub.asInterface(binder);
            try {
                //远程控制设置name值
                mDemoService.setName("Andy Song");
                //远程获取设置的name值
                String myName = mDemoService.getName();
                Log.i(TAG, "client:[onServiceConnected]myName=" + myName);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        //该回调方法一般不会调用，如果在解绑的时候，发现该方法没有调用，不要惊慌，因为该方法的调用时机是Service被意外销毁时，比如内存不足时。
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "client:[onServiceDisconnected]");
            mIsBinded = false;
            mDemoService = null;
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mBindBtn = findViewById(R.id.bindBtn);
        mBindBtn.setOnClickListener(v -> {
            Log.i(TAG, "client:[bind button clicked]");
            //Android5.0及以后，出于对安全的考虑，Android系统对隐式启动Service做了限制，需要带上包名或者类名，这一点需要注意。
            Intent intent = new Intent();
            intent.setAction("action.aidl.server");
//            intent.setPackage("com.example.aidlserver");

            intent.setClassName("com.example.aidlserver","com.example.aidlserver.AidlService");

            bindService(intent, mConn, BIND_AUTO_CREATE);
            Log.i(TAG, "client:[bind over]");
        });
        Button mUnBindBtn = findViewById(R.id.unbindBtn);
        mUnBindBtn.setOnClickListener(v -> {
            Log.i(TAG, "client:[un bind button clicked]");
            //解除绑定，当调用unbindService时，一定要判断当前service是否是binded的，如果没有，就会报错。
            if (mIsBinded) {
                unbindService(mConn);
                mDemoService = null;
                mIsBinded = false;
            }
        });
    }
}
