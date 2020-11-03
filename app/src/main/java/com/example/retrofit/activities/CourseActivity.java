package com.example.retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.retrofit.R;
import com.example.retrofit.adapter.CourseAdapter;
import com.example.retrofit.adapter.DepartamentAdapter;
import com.example.retrofit.config.RetrofitConfig;
import com.example.retrofit.config.RoomConfig;
import com.example.retrofit.model.Course;
import com.example.retrofit.model.Departament;
import com.example.retrofit.model.Professor;
import com.example.retrofit.repository.ResultEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseActivity extends AppCompatActivity {

    private RecyclerView rvCourse;
    private CourseAdapter courseAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        rvCourse = (RecyclerView) findViewById(R.id.rvCourse);
        rvCourse.setLayoutManager(new LinearLayoutManager(CourseActivity.this));
    }

    @Override
    public void onResume() {
        super.onResume();

        getAllCourse(new ResultEvent() {
            @Override
            public <T> void onResult(T result) {
                // Quando houver resultado mostre os valores na tela
                List<Course> courseList = (List<Course>) result;

                courseAdapter = new CourseAdapter(CourseActivity.this, courseList);
                rvCourse.setAdapter(courseAdapter);
            }

            @Override
            public void onFail(String message) {
                // Quando houver falha exiba uma mensagem de erro
                Toast.makeText(CourseActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_generic, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(CourseActivity.this, CourseDadosActivity.class);
                intent.putExtra("course", new Course());
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getAllCourse(ResultEvent resultEvent) {

        Call<List<Course>> call = new RetrofitConfig().getCourseService().getAllCourse();

        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                List<Course> courseList = response.body();

                RoomConfig.getInstance(CourseActivity.this).courseDao().insertAll(courseList);

                resultEvent.onResult(courseList);
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}