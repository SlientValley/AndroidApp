package com.example.dell.watch;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class CDialog extends Dialog {

    private EditText base_time;
    private EditText add_time;
    private String CustomBaseTime;
    private String CustomAddTime;
    private int num;
    private Context mContext;

    public CDialog(Context context,int num,int theme){
        super(context,theme);
        mContext = context;
        this.num = num ;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.layout_setting, null);
        this.setContentView(layout);

        base_time = (EditText) findViewById(R.id.base_time);
        add_time = (EditText) findViewById(R.id.add_time);
        Button button = (Button) findViewById(R.id.confirm);
        Button button1 = (Button) findViewById(R.id.back);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (base_time.length() == 0 && add_time.length() == 0) {
                    Toast.makeText(layout.getContext(), "什么都没填呢", Toast.LENGTH_SHORT).show();
                } else if (base_time.length() == 0 && add_time.length() != 0) {
                    CustomBaseTime = "30";
                    CustomAddTime = add_time.getText().toString();
                    if (num == 1) {
                        MainActivity.time.setBaseTime(CustomBaseTime);
                        Toast.makeText(v.getContext(), "使用默认主时长", Toast.LENGTH_SHORT).show();
                    }
                    if (num == 2) {
                        Main2Activity.time.setBaseTime(CustomBaseTime);
                        Main2Activity.time.setAddTime(CustomAddTime);
                        Toast.makeText(v.getContext(), "使用默认主时长", Toast.LENGTH_SHORT).show();
                    }
                    CDialog.this.cancel();

                } else if(base_time.length() != 0 && add_time.length() == 0) {
                    CustomBaseTime = base_time.getText().toString();
                    CustomAddTime = "15";
                    if (num == 1) {
                        MainActivity.time.setBaseTime(CustomBaseTime);
                        Toast.makeText(v.getContext(), "设置成功", Toast.LENGTH_SHORT).show();
                    }
                    if (num == 2) {
                        Main2Activity.time.setBaseTime(CustomBaseTime);
                        Main2Activity.time.setAddTime(CustomAddTime);
                        Toast.makeText(v.getContext(), "使用默认加秒", Toast.LENGTH_SHORT).show();
                    }
                    CDialog.this.cancel();

                } else {
                    CustomBaseTime = base_time.getText().toString();
                    CustomAddTime = add_time.getText().toString();
                    if (num == 1) {
                        MainActivity.time.setBaseTime(CustomBaseTime);
                        Toast.makeText(v.getContext(), "设置成功", Toast.LENGTH_SHORT).show();
                    }
                    if (num == 2) {
                        Main2Activity.time.setBaseTime(CustomBaseTime);
                        Main2Activity.time.setAddTime(CustomAddTime);
                        Toast.makeText(v.getContext(), "设置成功", Toast.LENGTH_SHORT).show();
                    }
                    CDialog.this.cancel();
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CDialog.this.cancel();
            }
        });
    }

}

