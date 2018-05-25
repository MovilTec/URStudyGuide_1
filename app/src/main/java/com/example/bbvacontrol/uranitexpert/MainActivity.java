package com.example.bbvacontrol.uranitexpert;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {
    public static final String EXTRA_MESSAGE = "com.example.uranitexpert.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

        public void sendMessage(View view) {
        Intent intent = new Intent(this, NetworkActivity.class);
        startActivity(intent);
    }
        public void openProgrammingActivity(View view){
        Intent intent = new Intent(this, Programming.class);
        startActivity(intent);
    }
        public void openDataBaseActivity(View view){
        Intent intent = new Intent(this, DataBase.class);
        startActivity(intent);
    }
        public void openClientSupportActivity(View view){
        Intent intent = new Intent(this, ClientSupport.class);
        startActivity(intent);
    }
}


