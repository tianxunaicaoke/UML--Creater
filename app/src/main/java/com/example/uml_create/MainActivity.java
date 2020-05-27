package com.example.uml_create;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.umlannotation.IncludeClass;

@IncludeClass(umlNode = "android",umlPackage = "activity",umlNote = "this is for android to show UI")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
