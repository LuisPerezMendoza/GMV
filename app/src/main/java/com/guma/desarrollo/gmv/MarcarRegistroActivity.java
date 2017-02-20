package com.guma.desarrollo.gmv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.File;

public class MarcarRegistroActivity extends AppCompatActivity {
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("PASO 1 [Registrar visita]");
        findViewById(R.id.btnGoToStep2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MarcarRegistroActivity.this,AccionesActivity.class));
            }
        });
        img = (ImageView)this.findViewById(R.id.iv_callQr);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                File imagesFolder = new File(
                        Environment.getExternalStorageDirectory(), "Tutorialeshtml5");
                imagesFolder.mkdirs();
                File image = new File(imagesFolder, "foto.jpg");
                Uri uriSavedImage = Uri.fromFile(image);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(cameraIntent, 1);
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bMap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory()+"/Tutorialeshtml5/"+"foto.jpg");
            img.setImageBitmap(bMap);
        }
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    break;
        }
    }
}
