package com.example.retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.retrofit.R;


public class MainActivity extends AppCompatActivity {

    private LinearLayout llProfessor, llDepartament, llCourse, llAllocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llProfessor = (LinearLayout) findViewById(R.id.llProfessor);
        llDepartament = (LinearLayout) findViewById(R.id.llDepartament);
        llCourse = (LinearLayout) findViewById(R.id.llCourse);
        llAllocation = (LinearLayout) findViewById(R.id.llAllocation);

        llProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfessorActivity.class);
                startActivity(intent);
            }
        });

        llDepartament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DepartamentActivity.class);
                startActivity(intent);
            }
        });

        llCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CourseActivity.class);
                startActivity(intent);
            }
        });

        llAllocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllocationActivity.class);
                startActivity(intent);
            }
        });
    }
}