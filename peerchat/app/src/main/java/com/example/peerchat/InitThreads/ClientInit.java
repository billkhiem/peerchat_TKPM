package com.example.peerchat.InitThreads;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.example.peerchat.roomData.MessageData;
import com.example.peerchat.chatManager.ChatHandle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientInit extends Thread {
	private static final int SERVER_PORT = 8888;

    String hostAdd;
    private InputStream inputStream;
    private OutputStream outputStream;
    Socket socket;

    public ClientInit(InetAddress hostAddress){
        hostAdd = hostAddress.getHostAddress();
        socket = new Socket();

    }

    public void write(byte[] bytes){

        try {
             if(outputStream==null) {
                 outputStream = socket.getOutputStream();
             }

             outputStream.write(bytes);
//            new ObjectOutputStream(outputStream).writeObject(mss);
//            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Log.i("Thien","client is running");
        try {
            socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
            Log.i("Thien","socket is connected");
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            Log.i("Thien","host is having a problem");
            e.printStackTrace();
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("Thien","server is receiving");
                byte[] buffer = new byte[1024];
                int bytes;

                while (socket != null){
                    try {
                        bytes = inputStream.read(buffer);
                        if(bytes > 0){
                            int finalBytes = bytes;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String tempMsg = new String(buffer, 0, finalBytes);
                                    ChatHandle.shared.appendChat("rev_" + tempMsg);
                                    Date date= new Date();
                                    long time = date.getTime();
                                    ChatHandle.shared.msgDao.insertAll(new MessageData("rev_" + tempMsg, ChatHandle.shared.currentChatUser.deviceName, time));

//                                    Notification mNotification = new Notification.Builder(context)
//                                            .setContentTitle(message.getChatName())
//                                            .setContentText(message.getmText())
//                                            .setSmallIcon(R.drawable.icon_notification)
//                                            .setContentIntent(pIntent)
//                                            .setSound(notification)
//                                            .build();
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                }

        });
    }
}

