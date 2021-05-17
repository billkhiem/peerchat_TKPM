package com.example.peerchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.example.peerchat.dataHandler.UserDataManager;
import com.example.peerchat.mainChat.ChatFragment;
import com.example.peerchat.welcome.WelcomeFragment;
import com.example.peerchat.welcome.AvatarFragment;

public class MainActivity extends AppCompatActivity implements MainScreenNavigationInterface {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    WelcomeFragment welcomeFragment;
    AvatarFragment avatarFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        UserDataManager.getInstance().setContext(getApplicationContext());
        setContentView(R.layout.activity_main);
        welcomeFragment = new WelcomeFragment(this);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment, welcomeFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void pop() {
        if (this.getSupportFragmentManager().getBackStackEntryCount() > 1) {
            Log.i("MainActivity", "did pop fragment");
            this.getSupportFragmentManager().popBackStackImmediate();
        } else {
            Log.i("MainActivity", "finish app");
//            this.finish();
        }
    }

    @Override
    public void showAvatarSelect() {
        ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_left,R.anim.slide_in_right,R.anim.slide_out_right);
        avatarFragment = new AvatarFragment(this);
        ft.replace(R.id.main_fragment, avatarFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void showChatHome() {

    }

    @Override
    public void onBackPressed() {
        this.pop();
    }
}