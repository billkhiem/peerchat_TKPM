package com.example.peerchat.chatManager;

import android.net.wifi.p2p.WifiP2pDevice;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String name;
    private String imageURL;
    public WifiP2pDevice device;

    public User(String id, String name, String imageURL, WifiP2pDevice device){
        this.id=id;
        this.name=name;
        this.imageURL=imageURL;
        this.device = device;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }


}
