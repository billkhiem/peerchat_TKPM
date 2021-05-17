package com.example.peerchat.InitThreads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.example.peerchat.roomData.MessageData;
import com.example.peerchat.chatManager.ChatHandle;


public class ServerInit extends Thread {

    Socket socket;
    ServerSocket serverSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public void write(byte[] bytes) {
        try {
            if(outputStream==null) {
                outputStream = socket.getOutputStream();
            }
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(8888);
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            if (outputStream == null) {
                Log.i("Thien", "nullll");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
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