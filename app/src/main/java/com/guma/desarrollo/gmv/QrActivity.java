package com.guma.desarrollo.gmv;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }
    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
    @Override
    public void handleResult(Result rawResult) {
       //Log.e("handler", rawResult.getText()); // Prints scan results
       // Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        final ArrayList<String> strings = new ArrayList<String>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();

        if (rawResult.getText()!=""){
            strings.add(rawResult.getText());
            getIntent().putStringArrayListExtra("myResulte",strings);
            setResult(RESULT_OK,getIntent());
            finish();
        }


    }

}
