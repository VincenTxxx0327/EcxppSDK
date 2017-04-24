package com.ecxppsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ecxppsdk.utils.ConversionUtils;
import com.ecxppsdk.utils.DeviceUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.tv_main_text);
        textView.setText(ConversionUtils.bytes2HexString01(DeviceUtils.getDstMac(MainActivity.this)));
    }
}
