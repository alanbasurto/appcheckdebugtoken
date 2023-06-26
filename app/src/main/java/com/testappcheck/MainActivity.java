package com.testappcheck;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.appcheck.debug.internal.StorageHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {
    private Button btnTest;
    private TextView txtTest;
    private final String debugSecret ="7EC6EDDB-36C8-4189-AFB6-8330E300141F";

    static final String DEBUG_SECRET_KEY = "com.google.firebase.appcheck.debug.DEBUG_SECRET";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest = findViewById(R.id.btn1);
        txtTest = findViewById(R.id.textView);
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(/*context=*/ this);
        
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        StorageHelper storageHelper =
                new StorageHelper(
                        firebaseApp.getApplicationContext(), firebaseApp.getPersistenceKey());
        storageHelper.saveDebugSecret(debugSecret);
        firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance());
        String debugSecretSaved = storageHelper.retrieveDebugSecret();
        txtTest.setText( debugSecretSaved);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue(debugSecretSaved);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cuando se presione el botón, realiza una acción aquí
                txtTest.setText("botonn pressed");
                myRef.setValue("Hello,");
            }
        });
    }
}