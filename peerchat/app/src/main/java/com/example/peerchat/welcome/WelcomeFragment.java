package com.example.peerchat.welcome;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.UserManager;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peerchat.MainActivity;
import com.example.peerchat.MainScreenNavigationInterface;
import com.example.peerchat.R;
import com.example.peerchat.dataHandler.PrefKey;
import com.example.peerchat.dataHandler.UserDataManager;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class WelcomeFragment extends Fragment implements View.OnClickListener {

    MainScreenNavigationInterface delegate;

    private static final int ACCESS_FINE_LOCATION_CODE = 100;
    public WelcomeFragment(MainScreenNavigationInterface delegate) {
        super(R.layout.fragment_welcome);
        this.delegate = delegate;

    }

    MainActivity main;
    Context context = null;
    EditText username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            main = (MainActivity) getActivity();

        } catch (Exception e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ACCESS_FINE_LOCATION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.getContext(),
                        "Access fine location Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this.getContext(),
                        "Access fine location Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Debug", "New welcome screen");
        view.findViewById(R.id.joinBtn).setOnClickListener(this);

        username = (EditText) view.findViewById(R.id.userNameEditText);
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = myDevice.getName();
        if(deviceName.contains("PC_"))
            username.setText(deviceName.substring(3));
        else username.setText(deviceName);

    }

    @Override
    public void onClick(View v) {
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        myDevice.setName("PC_" + username.getText().toString());
        UserDataManager.getInstance().write(PrefKey.PREF_UNAME, username.getText().toString());
        this.delegate.showAvatarSelect();
    }

}