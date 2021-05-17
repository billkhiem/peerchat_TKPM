package com.example.peerchat.mainChat;

import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.peerchat.customAdapters.UserAdapter;
import com.example.peerchat.R;
import com.example.peerchat.ShowChatInterface;
import com.example.peerchat.chatManager.User;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> users;
    WifiP2pDevice[] devicesArray;
    Button reloadButton;
    View.OnClickListener reloadHandle;
    ShowChatInterface delegate;

    public UserFragment() {
        // Required empty public constructor´
    }

    public void setOpenChatDelegate(ShowChatInterface delegate) {
        this.delegate = delegate;
    }

    public void setListUsr(WifiP2pDevice[] devicesArray) {
        this.devicesArray = devicesArray;
        this.users.clear();
        for (WifiP2pDevice wifiP2pDevice : devicesArray) {
//            if (wifiP2pDevice.deviceName.contains("PC_")) {
            User us = new User("1", wifiP2pDevice.deviceName.substring(3), "Default", wifiP2pDevice);
                this.users.add(us);
//            }
        }
        userAdapter.delegate = this.delegate;
        userAdapter.updateUsers(this.users);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i("Thien", "create");

        this.users = new ArrayList<>();

        reloadButton = (Button) view.findViewById(R.id.reloadBtn);
        reloadButton.setOnClickListener(reloadHandle);
        readUser();
        return view;
    }

    private void readUser() {
        this.users.clear();

        userAdapter = new UserAdapter(getContext(), this.users);
        recyclerView.setAdapter(userAdapter);
    }
}