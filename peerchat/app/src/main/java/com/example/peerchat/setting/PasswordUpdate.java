package com.example.peerchat.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peerchat.R;
import com.example.peerchat.dataHandler.UserDataManager;

public class PasswordUpdate extends AppCompatActivity {


    EditText oldPin,newPin,confirmPin;
    Button submit_btn;
    String pass;

    SharedPreferences sharedPreferences;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);

        this.setTitle("Password Settings");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        oldPin=findViewById(R.id.old_pin);
        newPin=findViewById(R.id.new_pin);
        confirmPin=findViewById(R.id.confirm_pin);
        submit_btn=findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass=oldPin.getEditableText().toString();
                String newpass=newPin.getEditableText().toString();
                String confirmpass=confirmPin.getEditableText().toString();
                if (oldpass.length()==0 || newpass.length()==0 || confirmpass.length()==0){
                    Toast.makeText(getApplicationContext(),"Please fill in a blank field",Toast.LENGTH_SHORT).show();
                } else{
                    pass=UserDataManager.getPass("password",getApplicationContext());
                    if (!oldpass.equals(pass)){
                        Toast.makeText(getApplicationContext(),"Wrong pin",Toast.LENGTH_SHORT).show();
                    } else if (newpass.length()!=5 || confirmpass.length()!=5){
                        Toast.makeText(getApplicationContext(),"Pin must have exactly 5 digits",Toast.LENGTH_SHORT).show();
                    } else if (!newpass.equals(confirmpass)){
                        Toast.makeText(getApplicationContext(),"Two pins do not match",Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getApplicationContext(),"New pin has set succesfully",Toast.LENGTH_SHORT).show();
                        UserDataManager.setPass("password",newpass,getApplicationContext());
                        oldPin.setText("");
                        newPin.setText("");
                        confirmPin.setText("");
                    }
                }

            }
        });







    }
}