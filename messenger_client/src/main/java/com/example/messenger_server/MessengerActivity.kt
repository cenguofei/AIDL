package com.example.messenger_server

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.example.messenger_client.R

class MessengerActivity : AppCompatActivity() {
    private var mService: Messenger? = null
    private val mGetReplyMessenger = Messenger(MessengerHandler())

    private class MessengerHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MyConstants.MSG_FROM_SERVICE -> Log.i(
                    TAG,
                    "receive msg from Service:" + msg.data.getString("reply")
                )
                else -> super.handleMessage(msg)
            }
        }
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            mService = Messenger(service)
            Log.d(TAG, "bind service")
            val msg: Message = Message.obtain(null, MyConstants.MSG_FROM_CLIENT)
            bundleOf("msg" to "hello, this is client.").also {
                msg.data = it
            }
            msg.replyTo = mGetReplyMessenger
            try {
                mService?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(className: ComponentName) {}
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        requestPermissions(arrayOf("com.example.messenger_server.ACCESS_MESSENGER_SERVICE"),1)
        Log.i(TAG,"begin bind")
        val intent = Intent().apply {
            action = "com.example.MessengerService.launch"
            component = ComponentName("com.example.messenger_server","com.example.messenger_server.MessengerService")
        }
        bindService(intent, mConnection, BIND_AUTO_CREATE)
        Log.i(TAG,"end bind")
    }

    override fun onDestroy() {
        unbindService(mConnection)
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.i(TAG,"requestCode = $requestCode")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val TAG = "client_bind"
    }
}
