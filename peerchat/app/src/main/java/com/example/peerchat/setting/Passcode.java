package com.example.peerchat.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.peerchat.MainActivity;
import com.example.peerchat.R;
import com.example.peerchat.dataHandler.UserDataManager;

public class Passcode extends AppCompatActivity {

    PinEntryEditText pinEntryEditText;

    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        pass=UserDataManager.getPass("password",getApplicationContext());
        if (pass==null){
            pass="12345";
            UserDataManager.setPass("password","12345",getApplicationContext());
        }
            pinEntryEditText=findViewById(R.id.pin_entry);
            pinEntryEditText.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals(pass)){
                        Toast.makeText(getApplicationContext(),"Right pin",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Passcode.this, MainActivity.class));
                    } else{
                        Toast.makeText(getApplicationContext(),"Wrong pin",Toast.LENGTH_SHORT).show();
                    }
                }
            });



    }
}