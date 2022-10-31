package com.example.messenger_server

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log

class MessengerService : Service() {

    private val handler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            MyConstants.MSG_FROM_CLIENT -> {
                Log.i(TAG, "receive msg from Client:" + msg.data.getString("msg"))
                val client = msg.replyTo
                val replyMessage: Message = Message.obtain(null, MyConstants.MSG_FROM_SERVICE)
                val bundle = Bundle()
                bundle.putString("reply", "嗯，你的消息我已经收到，稍后会回复你。")
                replyMessage.data = bundle
                try {
                    client.send(replyMessage)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }
        true
    }

    private class MessengerHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MyConstants.MSG_FROM_CLIENT -> {
                    Log.i(TAG, "receive msg from Client:" + msg.data.getString("msg"))
                    val client = msg.replyTo
                    val replyMessage: Message = Message.obtain(null, MyConstants.MSG_FROM_SERVICE)
                    val bundle = Bundle()
                    bundle.putString("reply", "嗯，你的消息我已经收到，稍后会回复你。")
                    replyMessage.data = bundle
                    try {
                        client.send(replyMessage)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    private val mMessenger = Messenger(MessengerHandler())
    override fun onBind(intent: Intent): IBinder? {
        return mMessenger.binder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        private const val TAG = "MessengerService"
    }
}
