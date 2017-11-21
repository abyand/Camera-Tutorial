package com.example.abyandafa.kameraku;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int CAMERA_REQUEST_CODE = 200;


    private Button tambah;
    private Button cari;
    private EditText namaku;
    private DBHelper db;
    private ImageView gambar;
    private Boolean cameraPermission;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tambah = (Button) findViewById(R.id.add1);
        cari = (Button) findViewById(R.id.cari);
        namaku = (EditText) findViewById(R.id.nama);
        gambar = (ImageView) findViewById(R.id.fotonya);
        db = new DBHelper(this);
        cameraPermission = false;
        getCameraPermission();



    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add1:
                if(cameraPermission == true)
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Log.d("MASUK", "onClick: ");
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }
                break;
            case R.id.cari:

                Foto foto = db.cariData(namaku.getText().toString());
                File img = new File(foto.getPath());
                if(img.exists())
                {
                    Bitmap blaImage = BitmapFactory.decodeFile(img.getAbsolutePath());
                    gambar.setImageBitmap(blaImage);
                }



        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST_CODE ) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Log.d("Ada foto", "onActivityResult: ");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String currentDateandTime = sdf.format(new Date());
            String imgName = "MY_IMAGE_" + currentDateandTime + ".jpg";
            String fileName = Environment.getExternalStorageDirectory().getPath() +
                    "/DCIM/Camera/" + imgName;
            Foto newImage = new Foto(namaku.getText().toString(), fileName);
            db.addImage(newImage);

            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(byteArray);
                fos.close();
                Toast.makeText(this, fileName + " saved", Toast.LENGTH_SHORT).show();

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            Log.e("tes", "OnActivityResult: ");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }

    private void getCameraPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            cameraPermission = true;


        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 20);

        }
    }
}
