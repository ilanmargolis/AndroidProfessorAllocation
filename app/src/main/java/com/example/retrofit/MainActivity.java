package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.retrofit.activities.DepartamentActivity;
import com.example.retrofit.activities.ProfessorActivity;


public class MainActivity extends AppCompatActivity {

    private Button btProfessor, btDepartament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btProfessor = (Button) findViewById(R.id.btProfessor);
        btDepartament = (Button) findViewById(R.id.btDepartament);

        btProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfessorActivity.class);
                startActivity(intent);
            }
        });

        btDepartament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DepartamentActivity.class);
                startActivity(intent);
            }
        });
    }


}