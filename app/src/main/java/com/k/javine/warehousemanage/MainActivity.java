package com.k.javine.warehousemanage;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_stock;
    private Button btn_output;
    private Button btn_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        btn_stock = findViewById(R.id.btn_stock);
        btn_output = findViewById(R.id.btn_output);
        btn_input = findViewById(R.id.btn_input);
        btn_stock.setOnClickListener(this);
        btn_output.setOnClickListener(this);
        btn_input.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stock:
                Intent intent = new Intent(this, StockActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_output:
                intent = new Intent(this, ExportActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_input:
                intent = new Intent(this, StorageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
