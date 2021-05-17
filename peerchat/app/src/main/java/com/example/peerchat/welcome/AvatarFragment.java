package com.example.peerchat.welcome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.peerchat.MainActivity;
import com.example.peerchat.mainChat.MainScreen;
import com.example.peerchat.MainScreenNavigationInterface;
import com.example.peerchat.R;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


public class AvatarFragment extends Fragment implements View.OnClickListener, Serializable {
    MainScreenNavigationInterface delegate;
    Button chooseFromFilebtn;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public AvatarFragment() {

    }
    public AvatarFragment(MainScreenNavigationInterface delegate) {
        super(R.layout.fragment_avatar);
        this.delegate = delegate;
    }

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView avataImageView;
    Button goMain;
    MainActivity main;
    Context context = null;
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
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView().findViewById(R.id.takeImage).setOnClickListener(this);
        getView().findViewById(R.id.chooseFromFile).setOnClickListener(this);

        chooseFromFilebtn = (Button) getView().findViewById(R.id.chooseFromFile);
        chooseFromFilebtn.setOnClickListener(this);

        this.avataImageView = (ImageView) getView().findViewById(R.id.roundedImageView);


        goMain = (Button) getView().findViewById(R.id.goMain);
        goMain.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.i("Thien ", v.getTag().toString());
        if (v.getTag().toString().equals("capture")) {
            /*
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (Exception e) {
                Log.i("Thien ", e.toString());
                // display error state to the user
            }

             */
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            }
            else
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        } else if (v.getTag().toString().equals("fromFile")) {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
            Log.i("Son test ", v.getTag().toString());

        } else if (v.getTag().toString().equals("main")) {
            Intent intent=new Intent(context, MainScreen.class);
            startActivity(intent);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,1
            );
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] fileePathColum = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage,
                    fileePathColum, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(fileePathColum[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) getView().findViewById(R.id.roundedImageView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) getView().findViewById(R.id.roundedImageView);
            imageView.setImageBitmap(photo);
        }

    }

}

