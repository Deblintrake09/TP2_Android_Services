package com.example.tpservicios;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btEmpezar, btParar;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1000);
        }
        inicializarVista();
    }

    public void inicializarVista(){
        btEmpezar = findViewById(R.id.btEmpezar);
        btParar = findViewById(R.id.btParar);
        i = new Intent(MainActivity.this, ReadMessagesService.class);

        btEmpezar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ startService(i);}
        });

        btParar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ stopService(i);}
        });
    }
}