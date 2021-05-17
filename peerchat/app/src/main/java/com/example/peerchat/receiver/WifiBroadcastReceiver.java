package com.example.peerchat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import com.example.peerchat.roomData.UserData;
import com.example.peerchat.chatManager.ChatHandle;

import java.util.List;

public class WifiBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    WifiP2pManager.PeerListListener peerListListener;
    WifiP2pManager.ConnectionInfoListener connectionInfoListener;

    public WifiBroadcastReceiver(WifiP2pManager mManager,
                                 WifiP2pManager.Channel mChannel,
                                 WifiP2pManager.PeerListListener PeerListListener,
                                 WifiP2pManager.ConnectionInfoListener connectionInfoListener) {
        this.mChannel = mChannel;
        this.mManager = mManager;
        this.peerListListener = PeerListListener;
        this.connectionInfoListener = connectionInfoListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(context, "Wifi is On", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Wifi is Off", Toast.LENGTH_SHORT).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (mManager != null) {
                mManager.requestPeers(mChannel, peerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isConnected()) {

                mManager.requestConnectionInfo(mChannel, connectionInfoListener);
                Log.i("Thien", "is connecteddddd");
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("Thien", "is fail connecteddddd");
                Toast.makeText(context, "not connect", Toast.LENGTH_SHORT).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            WifiP2pDevice myDevice = (WifiP2pDevice) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            ChatHandle.shared.connectedDeviceName = myDevice.deviceName;
            ChatHandle.shared.currentChatUser = new UserData(myDevice.deviceName.substring(3), myDevice.deviceName, myDevice.deviceAddress);

            Log.i("Thien device adress:", myDevice.deviceAddress);
            ChatHandle.shared.userDao.insertAll(ChatHandle.shared.currentChatUser);
            List<UserData> users = ChatHandle.shared.userDao.getAll();
            for (UserData user : users) {
                Log.i("Thien user 1:", user.name);
            }
            Log.d("Wifi Direct: My Device", myDevice.deviceName);
        }
    }


}
