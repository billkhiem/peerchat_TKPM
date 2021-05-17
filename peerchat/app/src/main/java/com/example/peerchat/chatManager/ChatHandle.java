package com.example.peerchat.chatManager;

import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import com.example.peerchat.InitThreads.ClientInit;
import com.example.peerchat.InitThreads.ServerInit;
import com.example.peerchat.ShowChatInterface;
import com.example.peerchat.customAdapters.ChatAdaptor;
import com.example.peerchat.SendChatInterface;
import com.example.peerchat.roomData.MessageDao;
import com.example.peerchat.roomData.MessageData;
import com.example.peerchat.roomData.UserDaoInterface;
import com.example.peerchat.roomData.UserData;

import java.net.InetAddress;
import java.util.Date;

public class ChatHandle {
    public static ChatHandle shared = new ChatHandle();

    public WifiP2pManager mManager;
    public WifiP2pManager.Channel mChannel;

    public UserDaoInterface userDao;
    public UserData currentChatUser;
    Boolean isHost = false;
    public MessageDao msgDao;
    public SendChatInterface delegate;
    public ChatAdaptor adaptor;
    public String connectedDeviceName = "";
    public ShowChatInterface showChatDelegate ;
    public ClientInit clientClass;
    public ServerInit serverClass;
    public ChatHandle() {

    }

    public void reload() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.i("Thien", "Main chat Discovery Started");
            }

            @Override
            public void onFailure(int reason) {
                Log.i("Thien", "Main chat Discovery not Started");
            }
        });
    }

    public void connect(WifiP2pConfig config, WifiP2pManager.ActionListener actionListener) {
        mManager.connect(mChannel,
                config,
                actionListener);
    }
    public void startHost() {
        isHost = true;
        if (serverClass != null) {
            serverClass = null;
        }
        Log.i("Thien", "device is host");
        serverClass = new ServerInit();
        serverClass.start();
        showChatDelegate.showChat();

    }
    public void startClient(InetAddress groupOwnerAddress) {
        isHost = false;
        if (clientClass != null) {
            clientClass = null;

        }
        Log.i("Thien", "device is client");
        clientClass = new ClientInit(groupOwnerAddress);
        clientClass.start();
        showChatDelegate.showChat();

    }

    public void sendChat(String msg) {
        appendChat("sed_" + msg);
        Date date= new Date();
        long time = date.getTime();

        msgDao.insertAll(new MessageData("sed_" + msg, currentChatUser.deviceName, time));
        for (MessageData user : msgDao.getAll()) {
            Log.i("Thien msg", user.messageContent);
            Log.i("Thien msg", user.senderId);
            Log.i("Thien msg", String.valueOf(user.timestamp));
        }
        if (isHost) {
            serverClass.write(msg.getBytes());
        } else {
            clientClass.write(msg.getBytes());
        }
    }

    public void appendChat(String msg) {
        adaptor.data.add(msg);
        adaptor.notifyDataSetChanged();
    }


}
