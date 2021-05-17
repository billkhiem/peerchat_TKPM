package com.example.peerchat.mainChat;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import android.view.MenuItem;

import android.util.Log;

import android.view.View;
import android.widget.Toast;


import com.example.peerchat.InitThreads.ClientInit;
import com.example.peerchat.InitThreads.ServerInit;
import com.example.peerchat.SendChatInterface;
import com.example.peerchat.ShowChatInterface;
import com.example.peerchat.roomData.AppDataBase;
import com.example.peerchat.roomData.MessageData;
import com.example.peerchat.roomData.UserData;
import com.example.peerchat.setting.LanguageChange;
import com.example.peerchat.setting.PasswordUpdate;
import com.example.peerchat.setting.ProfileUpdate;
import com.example.peerchat.R;
import com.example.peerchat.customAdapters.ViewPagerAdapter;

import com.example.peerchat.chatManager.ChatHandle;
import com.example.peerchat.chatManager.User;
import com.google.android.material.tabs.TabLayout;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import com.example.peerchat.receiver.WifiBroadcastReceiver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */


public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ShowChatInterface
         {

    AppDataBase db;
    IntentFilter mIntentFilter;
    WifiP2pManager mManager;
    User user;

    WifiP2pManager.Channel mChannel;
    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] devicesArray;

    UserFragment userFragment = new UserFragment();
    BroadcastReceiver mReceiver;

    Boolean isHost = false;


    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    Intent intent;
    private DrawerLayout drawerLayout;

    public void showChat() {
        intent = new Intent(MainScreen.this, MessageActivity.class);
        intent.putExtra("username", ChatHandle.shared.connectedDeviceName.substring(3));
        startActivity(intent);
    }

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            peers.clear();
            peers.addAll(peerList.getDeviceList());

            deviceNameArray = new String[peerList.getDeviceList().size()];
            devicesArray = new WifiP2pDevice[peerList.getDeviceList().size()];
            int index = 0;
            for (WifiP2pDevice device : peerList.getDeviceList()) {
                deviceNameArray[index] = device.deviceName;
                devicesArray[index] = device;
                index++;
            }
            userFragment.setListUsr(devicesArray);
            Log.i("Thien", "did set devices array");

            if (peers.size() == 0) {
                Toast.makeText(getApplicationContext(), "No device found", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };

    public WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            final InetAddress groupOwnerAddress = info.groupOwnerAddress;
            if (info.groupFormed && info.isGroupOwner) {
                ChatHandle.shared.startHost();
            } else if (info.groupFormed) {
                ChatHandle.shared.startClient(groupOwnerAddress);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         db = Room.databaseBuilder(this.getApplicationContext(), AppDataBase.class, "dataa")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        ChatHandle.shared.userDao = db.userDaoInterface();
        ChatHandle.shared.msgDao = db.messageDao();

        setContentView(R.layout.activity_main_screen);

        ChatHandle.shared.showChatDelegate = this;

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() { }
            @Override
            public void onFailure(int reason) { }
        });
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        Method m = null;
        try {
            m = mManager.getClass().getMethod("setDeviceName", new Class[]{mChannel.getClass(), String.class,
                    WifiP2pManager.ActionListener.class});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            m.invoke(mManager, mChannel, "PC_Thien", new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() { }

                @Override
                public void onFailure(int reason) { }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        mReceiver = new WifiBroadcastReceiver(mManager,
                mChannel,
                peerListListener,
                connectionInfoListener);

        mIntentFilter = new IntentFilter();
        // Indicates a change in the Wi-Fi P2P status.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        // Indicates a change in the list of available peers.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        // Indicates this device's details have changed.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        registerReceiver(mReceiver, mIntentFilter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_page);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ChatFragment(), "Chat");
        viewPagerAdapter.addFragment(this.userFragment, "User");
        this.userFragment.reloadHandle = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
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
        };

        this.userFragment.setOpenChatDelegate(this);

        Log.i("Thien", "Main chat");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, 100);
    }

    public void checkPermission(String permission, int requestCode) {

        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(this.getApplicationContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_profile:
                intent = new Intent(this, ProfileUpdate.class);
                startActivity(intent);
                break;
            case R.id.change_password:
                intent = new Intent(this, PasswordUpdate.class);
                startActivity(intent);
                break;
            case R.id.change_language:
                intent = new Intent(this, LanguageChange.class);
                startActivity(intent);
                break;
            case R.id.logout:
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showChatWith(User user) {

        final WifiP2pDevice device = user.device;
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        Log.i("Thien device name", user.device.deviceName);
        this.user = user;

        WifiP2pManager.ActionListener actionListener = new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        };

        mManager.connect(mChannel,
                config,
                actionListener);
    }

}